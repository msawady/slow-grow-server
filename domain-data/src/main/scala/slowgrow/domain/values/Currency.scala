package slowgrow.domain.values

sealed trait Currency {
  val scale: Int
}

object Currency {

  case object USD extends Currency {
    override val scale: Int = 2
  }
}
