package slowgrow.domain.portfolio

sealed trait Side

object Side {
  case object Long extends Side
  case object Short extends Side
}
