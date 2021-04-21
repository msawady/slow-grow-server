package slowgrow.domain.market_data

import slowgrow.domain.assets.AssetSym
import slowgrow.domain.values.Price

import java.time.ZonedDateTime

case class MarketPrice(sym: AssetSym, price: Price, pricedAt: ZonedDateTime)
