package slowgrow.application.port

import slowgrow.domain.assets.{Asset, AssetSym}

trait AssetRepository[F[_]] {

  def save(assets: List[Asset]): F[Unit]

  def findBy(sym: AssetSym): F[Asset]

  def findAll(): F[List[Asset]]
}
