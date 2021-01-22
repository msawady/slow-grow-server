package slowgrow.domain.values

case class Price(value: BigDecimal) {
  require(value >= 0, "Price must be grater than 0.")
}
