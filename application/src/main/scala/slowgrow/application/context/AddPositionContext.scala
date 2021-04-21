package slowgrow.application.context

import cats.effect._
import slowgrow.application.context.support.EitherTSyntax
import slowgrow.application.port.{DataAccessException, PortfolioRepository, PositionRepository}
import slowgrow.domain.assets.AssetSym
import slowgrow.domain.portfolio.{Portfolio, Position, Side}
import slowgrow.domain.values.{InvestorId, Price, Qty}
import wvlet.log.LogSupport

import java.time.ZonedDateTime

class AddPositionContext(
    positionRepository: PositionRepository,
    portfolioRepository: PortfolioRepository
) extends LogSupport
    with EitherTSyntax {

  import AddPositionContext._

  def addPosition(
      investorId: InvestorId,
      portfolioName: Portfolio.Name,
      sym: AssetSym,
      side: Side,
      qty: Qty,
      price: Price,
      tradedAt: ZonedDateTime
  ): IO[Either[ContextException, Position.Id]] = {

    type ET = ContextException

    val task = for {

      portfolio <- portfolioRepository.findBy(investorId, portfolioName)
        .handle[ET](e => new TargetPortfolioNotFoundException(portfolioName, e))

      id = Position.Id("hoge")
      position = Position(
        positionId = id,
        investorId = investorId,
        portfolioId = portfolio.id,
        symbol = sym,
        side = side,
        initialQty = qty,
        currentQty = qty,
        price = price,
        openDate = tradedAt,
        closeDate = None
      )

      _ <- positionRepository.create(position).handle[ET](e => new Unexpected(e))
      // TODO: fire and forget position evaluation

    } yield id

    task.value
  }
}

object AddPositionContext {

  trait ContextException

  final class TargetPortfolioNotFoundException(portfolioName: Portfolio.Name, e: DataAccessException)
      extends RuntimeException(s"Failed to find Portfolio: $portfolioName.", e)
      with ContextException

  final class Unexpected(e: Throwable) extends RuntimeException(e) with ContextException
}
