package slowgrow.application.port

import slowgrow.domain.assets.Asset

trait AssetMasterRepository[F[_]] {

  def loadAll(): F[List[Asset]]
}
