package slowgrow.application.port

import cats.effect.IO
import slowgrow.domain.assets.AssetSym
import slowgrow.domain.portfolio.Trade
import slowgrow.domain.values.InvestorId

trait TradeRepository {

  def create(trade: Trade): IO[Either[DataCreateException, Unit]]

  def getBy(tradeId: Trade.Id): IO[Either[DataAccessException, Trade]]

  def listBy(investorId: InvestorId, sym: AssetSym): IO[Either[DataAccessException, Seq[Trade]]]

}
