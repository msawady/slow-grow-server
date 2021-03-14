package slowgrow.domain.values

case class Qty(value: BigDecimal) {
  require(value >= 0, "Quantity must be grater than 0.")

  def *(price: Price): Amount = Amount(value * price.value, price.currency)

}
