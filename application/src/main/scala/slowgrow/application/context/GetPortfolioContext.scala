package slowgrow.application.context

import cats.data.EitherT
import cats.effect.IO
import slowgrow.application.context.GetPortfolioContext.ContextError.{TargetPortfolioNotFoundException, Unexpected}
import slowgrow.application.context.support.EitherTSyntax
import slowgrow.application.port.{DataAccessException, PortfolioRepository, PositionRepository}
import slowgrow.domain.assets.AssetSym
import slowgrow.domain.portfolio.{Portfolio, Position, ProfitAndLoss, Side}
import slowgrow.domain.values.{InvestorId, Price, Qty}
import wvlet.log.LogSupport

class GetPortfolioContext(
    portfolioRepository: PortfolioRepository,
    positionRepository: PositionRepository
) extends LogSupport
    with EitherTSyntax {

  import GetPortfolioContext._

  def getPortfolio(
      investorId: InvestorId,
      portfolioName: Portfolio.Name
  ): IO[Either[ContextError, Result]] = {

    val task = for {
      portfolio <- portfolioRepository.findBy(investorId, portfolioName).handle[ContextError](e =>
        new TargetPortfolioNotFoundException(portfolioName, e)
      )
      positions <- EitherT(positionRepository.listOpenBy(portfolio.id)).leftMap[ContextError](e => new Unexpected(e))

    } yield Result(portfolio, summary = positions.summarize())

    task.value
  }
}

object GetPortfolioContext {

  sealed trait ContextError

  object ContextError {
    final class Unexpected(e: Throwable) extends RuntimeException(e) with ContextError

    final class TargetPortfolioNotFoundException(portfolioName: Portfolio.Name, e: DataAccessException)
        extends RuntimeException(s"Failed to find Portfolio: $portfolioName.", e)
        with ContextError
  }

  case class Result(
      portfolio: Portfolio,
      summary: Seq[SummarizedPosition]
  )

  case class SummarizedPosition(
      symbol: AssetSym,
      side: Side,
      currentQty: Qty,
      averagePrice: Price,
      marketPrice: Price,
      unrealizedPnl: ProfitAndLoss,
      positions: Seq[Position.Id]
  )

  implicit class SummarizingPositions(self: Seq[Position]) {
    def summarize(): Seq[SummarizedPosition] = ???
  }

}
