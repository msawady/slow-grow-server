package slowgrow.domain.values

import scala.math.BigDecimal

case class USDAmount(value: BigDecimal) {
  val scale: Int = 2

  def /(price: USDPrice): Qty = Qty(value / price.value)
}

object USDAmount {

  def apply(value: BigDecimal): USDAmount = new USDAmount(value.setScale(2))
}
