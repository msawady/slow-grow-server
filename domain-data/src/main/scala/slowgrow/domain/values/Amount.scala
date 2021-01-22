package slowgrow.domain.values

import scala.math.BigDecimal

case class Amount(value: BigDecimal) {

  def /(price: Price): Qty = Qty(value / price.value)
}

object Amount {

  def apply(value: BigDecimal): Amount = {
    new Amount(value.setScale(2))
  }
}
