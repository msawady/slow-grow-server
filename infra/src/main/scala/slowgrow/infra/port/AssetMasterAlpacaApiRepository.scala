package slowgrow.infra.port

import cats.effect.IO
import slowgrow.application.port.AssetMasterRepository
import slowgrow.domain.assets.Asset

class AssetMasterAlpacaApiRepository extends AssetMasterRepository[IO] {
  override def loadAll(): IO[List[Asset]] = ???
}
