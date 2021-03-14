package slowgrow.domain.portfolio

import slowgrow.domain.values.InvestorId

case class Portfolio(
    id: Portfolio.Id,
    investorId: InvestorId,
    name: Portfolio.Name,
    positions: Seq[Position]
)

object Portfolio {

  case class Id(value: String)

  case class Name(value: String)
}
