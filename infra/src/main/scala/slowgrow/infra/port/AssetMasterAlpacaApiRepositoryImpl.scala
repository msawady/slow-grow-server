package slowgrow.infra.port

import cats.effect.IO
import net.jacobpeterson.alpaca.AlpacaAPI
import net.jacobpeterson.alpaca.enums.AssetStatus
import net.jacobpeterson.domain.alpaca.asset.{Asset => AlpacaAsset}
import slowgrow.application.port.AssetMasterRepository
import slowgrow.domain.assets.{Asset, AssetClass, AssetSym}

import scala.jdk.CollectionConverters._


class AssetMasterAlpacaApiRepositoryImpl(alpacaApi: AlpacaAPI) extends AssetMasterRepository[IO] {
  override def loadAll(): IO[List[Asset]] = {
    IO.delay(
      // alpaca seems doesn't provide assetClass other than "us_equity"
      alpacaApi.getAssets(AssetStatus.ACTIVE, "us_equity")
    ).map(values => List.from(values.asScala).map(toDomainAsset))
  }

  def toDomainAsset(alpacaAsset: AlpacaAsset): Asset = {
    Asset(
      symbol = AssetSym(alpacaAsset.getSymbol),
      assetClass = Option.when(alpacaAsset.getClass_.nonEmpty)(alpacaAsset.getClass_ match {
        case "us_equity" => AssetClass.USEquity
        case _ => throw new IllegalArgumentException(s"unsupported asset class: ${alpacaAsset.getClass_}")
      })
    )
  }
}
