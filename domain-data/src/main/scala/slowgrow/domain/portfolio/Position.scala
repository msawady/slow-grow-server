package slowgrow.domain.portfolio

import slowgrow.domain.assets.AssetSym
import slowgrow.domain.values.{Qty, USDPrice}

import java.time.ZonedDateTime

case class Position(
    symbol: AssetSym,
    side: Side,
    qty: Qty,
    averagePrice: USDPrice,
    marketPrice: USDPrice,
    unrealizedPnl: ProfitAndLoss,
    realizedPnl: ProfitAndLoss,
    evaluatedAt: ZonedDateTime
)
