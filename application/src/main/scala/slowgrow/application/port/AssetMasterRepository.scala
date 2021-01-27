package slowgrow.application.port

import cats.effect.IO
import slowgrow.domain.assets.Asset

trait AssetMasterRepository {

  def loadAll(): IO[List[Asset]]
}
