package slowgrow.application.context

import cats.effect.IO
import slowgrow.application.context.support.EitherTSyntax
import slowgrow.application.port.{AssetMasterRepository, AssetRepository}
import wvlet.log.LogSupport

class LoadAssetsContext(
    assetMasterRepository: AssetMasterRepository,
    assetRepository: AssetRepository
) extends LogSupport
    with EitherTSyntax {

  def loadAsset(): IO[Either[Throwable, Unit]] = {

    val task = for {
      data <- assetMasterRepository.loadAll().asT
      persisted <- assetRepository.findAll().asT
      newData = data.toSet -- persisted
      _ = info(s"will persisting ${newData.size}")
      _ = info(s"""
      symbols:
       ${newData.map(_.symbol.value).mkString("\n")}
       """)
      _ <- assetRepository.save(newData.toList).asT
    } yield ()

    task.value
  }
}
