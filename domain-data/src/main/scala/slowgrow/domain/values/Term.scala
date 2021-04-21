package slowgrow.domain.values

sealed trait Term

object Term {
  case object OneMin extends Term
  case object FiveMin extends Term

  case object OneHour extends Term
  case object FourHour extends Term

  case object OneDay extends Term
  case object OneWeek extends Term
  case object OneMonth extends Term
}
