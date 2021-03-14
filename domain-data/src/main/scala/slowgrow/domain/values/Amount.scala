package slowgrow.domain.values

import scala.math.BigDecimal

case class Amount(value: BigDecimal, currency: Currency = Currency.USD) {

  def scale: Int = currency.scale

  def /(price: Price): Qty = {
    require(
      this.currency == price.currency,
      s"Currency must be Same. AmountCcy: ${this.currency}, PriceCcy: ${price.currency}"
    )
    Qty(value / price.value)
  }
}

object Amount {

  def apply(value: BigDecimal, currency: Currency = Currency.USD): Amount =
    new Amount(value.setScale(currency.scale), currency)
}
