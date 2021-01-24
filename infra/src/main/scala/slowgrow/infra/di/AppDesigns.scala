package slowgrow.infra.di

import cats.effect.IO
import net.jacobpeterson.alpaca.AlpacaAPI
import slowgrow.application.port.AssetMasterRepository
import slowgrow.infra.port.AssetMasterAlpacaApiRepositoryImpl
import wvlet.airframe._


object AppDesigns {

  def provide: DesignWithContext[AssetMasterAlpacaApiRepositoryImpl] = Design.newDesign
    .bind[AlpacaAPI].toInstance(new AlpacaAPI())
    .bind[AssetMasterRepository[IO]].toSingletonOf[AssetMasterAlpacaApiRepositoryImpl]

}
