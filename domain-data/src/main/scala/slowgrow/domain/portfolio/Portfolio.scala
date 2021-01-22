package slowgrow.domain.portfolio

case class Portfolio(
    name: Portfolio.Name,
    positions: Seq[Position]
)

object Portfolio {

  case class Name(value: String)
}
