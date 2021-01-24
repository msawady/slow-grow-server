package slowgrow.domain.assets

sealed trait AssetClass

object AssetClass {

  case object USEquity extends AssetClass
}
