package slowgrow.application.context

import cats.data.EitherT
import cats.effect.IO
import slowgrow.application.context.GetPortfolioContext.ContextError.{TargetPortfolioNotFoundException, Unexpected}
import slowgrow.application.context.support.EitherTSyntax
import slowgrow.application.port.{DataAccessException, PortfolioRepository, PositionRepository}
import slowgrow.domain.assets.AssetSym
import slowgrow.domain.portfolio.{Portfolio, Position, ProfitAndLoss, Side}
import slowgrow.domain.values.{Amount, InvestorId, Price, Qty}
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
      bookAmount: Amount,
      marketAmount: Amount,
      unrealizedPnl: ProfitAndLoss,
      positions: Seq[Position.Id]
  )

  implicit class SummarizingPositions(self: Seq[Position]) {

    def summarize(): Seq[SummarizedPosition] = {
      self.groupBy(x => (x.symbol, x.side)).map {
        case ((sym, side), xs) =>
          val currentQty = Qty(xs.map(_.currentQty.value).sum)
          val bookAmount = Amount(xs.map(x => (x.currentQty * x.averagePrice).value).sum)
          val marketAmount = Amount(xs.map(x => (x.currentQty * x.marketPrice).value).sum)

          SummarizedPosition(
            symbol = sym,
            side = side,
            currentQty = currentQty,
            averagePrice = bookAmount / currentQty,
            marketPrice = xs.head.marketPrice,
            bookAmount = bookAmount,
            marketAmount = marketAmount,
            unrealizedPnl = marketAmount - bookAmount,
            positions = xs.map(_.positionId)
          )
      }.toSeq
    }
  }

  implicit class RichAmount(self: Amount) {

    def -(amount: Amount): ProfitAndLoss = {
      require(
        self.currency == amount.currency,
        s"Currency must be Same. LeftSideCcy: ${self.currency}, RightSideCcy: ${amount.currency}"
      )
      ProfitAndLoss(self.value - amount.value)
    }
  }

}
