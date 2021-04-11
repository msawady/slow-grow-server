package slowgrow.application.port

import cats.effect.IO
import slowgrow.domain.assets.Asset

trait AssetMasterRepository {

  def loadAll(): IO[Either[DataAccessException, List[Asset]]]
}

object AssetMasterRepository {

  final class ExternalDataSourceAccessException(e: Throwable)
      extends RuntimeException(e: Throwable)
      with DataAccessException
}
