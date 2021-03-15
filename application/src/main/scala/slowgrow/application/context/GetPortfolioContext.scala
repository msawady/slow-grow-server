package slowgrow.application.context

import cats.effect.IO
import slowgrow.application.context.support.{ContextError, EitherTSyntax}
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
  ): IO[Either[ContextError[GetPortfolioContext], Result]] = {

    val task = for {
      portfolio <- portfolioRepository.findBy(investorId, portfolioName).handle2[GetPortfolioContext](e =>
        TargetPortfolioNotFoundException(portfolioName, e)
      )
      positions <- positionRepository.listOpenBy(portfolio.id).toRightT

    } yield Result(portfolio, summary = positions.summarize())

    task.value
  }
}

object GetPortfolioContext {

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

  case class TargetPortfolioNotFoundException(portfolioName: Portfolio.Name, e: DataAccessException)
      extends RuntimeException(s"Failed to find Portfolio: $portfolioName.", e)
      with ContextError[GetPortfolioContext]

}
