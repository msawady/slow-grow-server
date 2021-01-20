package slowgrow.domain.assets

sealed trait AssetClass

object AssetClass {

  case object Stock extends AssetClass
}
