package slowgrow.domain.values

case class Qty(value: BigDecimal) {
  require(value >= 0, "Quantity must be grater than 0.")
}
