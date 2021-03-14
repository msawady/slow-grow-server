package slowgrow.application.context

import cats.effect.IO
import slowgrow.application.port.TradeRepository
import slowgrow.domain.portfolio.{Portfolio, Trade}
import slowgrow.domain.values.InvestorId
import wvlet.log.LogSupport

class GetPortfolioContext(
    tradeRepository: TradeRepository
) extends LogSupport {

  def getPortfolio(investorId: InvestorId, portfolioName: Portfolio.Name, trade: Trade): IO[Unit] = {
    for {
      _ <- tradeRepository.create(trade)

    } yield ()
    // if Positions are cached for view, we need to evict cache for this position
  }
}
