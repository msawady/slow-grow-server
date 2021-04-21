package slowgrow.domain.market_data

import slowgrow.domain.values.{Price, Term}

case class Bar(
    open: Price,
    high: Price,
    low: Price,
    close: Price,
    term: Term
)
