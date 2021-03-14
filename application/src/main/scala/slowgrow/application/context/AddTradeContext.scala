package slowgrow.application.context

import cats.effect.IO
import slowgrow.application.context.AddTradeContext.{
  ContextException,
  DataRegisterException,
  TargetPortfolioNotFoundException
}
import slowgrow.application.context.support.EitherTSyntax
import slowgrow.application.port.{DataAccessException, DataCreateException, PortfolioRepository, TradeRepository}
import slowgrow.domain.assets.AssetSym
import slowgrow.domain.portfolio.{Portfolio, Side, Trade}
import slowgrow.domain.values.{InvestorId, Price, Qty}
import wvlet.log.LogSupport

import java.time.ZonedDateTime

class AddTradeContext(
    tradeRepository: TradeRepository,
    portfolioRepository: PortfolioRepository
) extends LogSupport
    with EitherTSyntax {

  def addTrade(
      investorId: InvestorId,
      portfolioName: Portfolio.Name,
      sym: AssetSym,
      side: Side,
      qty: Qty,
      price: Price,
      tradedAt: ZonedDateTime
  ): IO[Either[ContextException, Unit]] = {

    type ET = ContextException

    val task = for {

      portfolio <- portfolioRepository.findBy(investorId, portfolioName)
        .handle[ET](e => TargetPortfolioNotFoundException(portfolioName, e))

      id = Trade.Id("hoge")
      trade = new Trade(
        tradeId = id,
        investorId = investorId,
        sym = sym,
        side = side,
        qty = qty,
        acquiredPrice = price,
        amount = qty * price,
        tradedAt = tradedAt
      )

      _ <- tradeRepository.create(trade)
        .handle[ET](e => DataRegisterException(e))
      _ <- portfolioRepository.appendTrade(portfolio.id, id)
        .handle[ET](e => DataRegisterException(e))

      // if Positions are cached for view, we need to evict cache for this position

    } yield ()

    task.value
  }
}

object AddTradeContext {

  trait ContextException

  case class TargetPortfolioNotFoundException(portfolioName: Portfolio.Name, e: DataAccessException)
      extends RuntimeException(s"Failed to find Portfolio: $portfolioName.", e)
      with ContextException

  case class DataRegisterException(e: DataCreateException) extends ContextException

}
