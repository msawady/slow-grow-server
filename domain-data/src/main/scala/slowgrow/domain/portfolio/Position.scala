package slowgrow.domain.portfolio

import slowgrow.domain.assets.AssetSym
import slowgrow.domain.values.{InvestorId, Price, Qty}

import java.time.ZonedDateTime

case class Position(
    positionId: Position.Id,
    investorId: InvestorId,
    portfolioId: Portfolio.Id,
    symbol: AssetSym,
    side: Side,
    initialQty: Qty,
    currentQty: Qty,
    averagePrice: Price,
    marketPrice: Price,
    unrealizedPnl: ProfitAndLoss,
    realizedPnl: ProfitAndLoss,
    openDate: ZonedDateTime,
    closeDate: Option[ZonedDateTime],
    evaluatedAt: ZonedDateTime
)

object Position {

  case class Id(value: String)
}
