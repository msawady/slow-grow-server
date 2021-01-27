package slowgrow.application.context

import cats.effect.IO
import slowgrow.application.port.{AssetMasterRepository, AssetRepository}
import wvlet.log.LogSupport

class LoadAssetsContext(
    assetMasterRepository: AssetMasterRepository,
    assetRepository: AssetRepository
) extends LogSupport {

  def loadAsset(): IO[Unit] = {

    for {
      data <- assetMasterRepository.loadAll()
      persisted <- assetRepository.findAll()
      newData = data.toSet -- persisted
      _ = info(s"will persisting ${newData.size}")
      _ = info(s"""
      symbols:
       ${newData.map(_.symbol.value).mkString("\n")}
       """)
      _ <- assetRepository.save(newData.toList)
    } yield ()
  }
}
