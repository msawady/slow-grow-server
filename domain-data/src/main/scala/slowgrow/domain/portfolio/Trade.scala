package slowgrow.domain.portfolio

import slowgrow.domain.assets.AssetSym
import slowgrow.domain.values.{Amount, InvestorId, Price, Qty}

import java.time.ZonedDateTime

case class Trade(
    tradeId: Trade.Id,
    investorId: InvestorId,
    sym: AssetSym,
    side: Side,
    qty: Qty,
    acquiredPrice: Price,
    amount: Amount,
    tradedAt: ZonedDateTime
)

object Trade {

  case class Id(value: String)
}
