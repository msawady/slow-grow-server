package slowgrow.domain.portfolio

import slowgrow.domain.assets.AssetSym
import slowgrow.domain.values.{InvestorId, Price, Qty}

import java.time.ZonedDateTime

case class Position(
    investorId: InvestorId,
    symbol: AssetSym,
    side: Side,
    qty: Qty,
    averagePrice: Price,
    marketPrice: Price,
    unrealizedPnl: ProfitAndLoss,
    realizedPnl: ProfitAndLoss,
    evaluatedAt: ZonedDateTime
)
