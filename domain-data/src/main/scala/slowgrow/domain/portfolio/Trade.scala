package slowgrow.domain.portfolio

import slowgrow.domain.assets.AssetSym
import slowgrow.domain.values.{Qty, USDAmount, USDPrice}

case class Trade(
    sym: AssetSym,
    side: Side,
    qty: Qty,
    acquiredPrice: USDPrice,
    amount: USDAmount
)
