package slowgrow.domain.values

case class Price(value: BigDecimal, currency: Currency = Currency.USD) {
  // Price can be negative like oil-futures in 2020/4.

  def *(qty: Qty): Amount = Amount(value * qty.value, this.currency)
}
