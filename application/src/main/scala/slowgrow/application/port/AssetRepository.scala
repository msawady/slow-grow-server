package slowgrow.application.port

import cats.effect.IO
import slowgrow.domain.assets.{Asset, AssetSym}

trait AssetRepository {

  def save(assets: List[Asset]): IO[Either[DataCreateException, Unit]]

  def findBy(sym: AssetSym): IO[Either[DataAccessException, Asset]]

  def findAll(): IO[Either[DataAccessException, List[Asset]]]
}
