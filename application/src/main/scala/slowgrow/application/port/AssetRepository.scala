package slowgrow.application.port

import cats.effect.IO
import slowgrow.domain.assets.{Asset, AssetSym}

trait AssetRepository {

  def save(assets: List[Asset]): IO[Unit]

  def findBy(sym: AssetSym): IO[Asset]

  def findAll(): IO[List[Asset]]
}
