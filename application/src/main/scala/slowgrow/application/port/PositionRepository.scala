package slowgrow.application.port

import cats.effect.IO
import slowgrow.domain.assets.AssetSym
import slowgrow.domain.portfolio.{Portfolio, Position}

trait PositionRepository {

  def create(position: Position): IO[Either[DataCreateException, Unit]]

  def listByPortfolioId(portfolioId: Portfolio.Id): IO[Either[DataAccessException, Seq[Position]]]

  def listOpenByPortfolioId(portfolioId: Portfolio.Id): IO[Either[DataAccessException, Seq[Position]]]

  def listOpenByPortfolioAndSymbol(
      portfolioId: Portfolio.Id,
      sym: AssetSym
  ): IO[Either[DataAccessException, Seq[Position]]]
}
