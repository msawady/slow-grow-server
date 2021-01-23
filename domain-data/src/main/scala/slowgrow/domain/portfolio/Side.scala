package slowgrow.domain.portfolio

sealed trait Side

object Side {
  case object Buy extends Side
  case object Sell extends Side
}
