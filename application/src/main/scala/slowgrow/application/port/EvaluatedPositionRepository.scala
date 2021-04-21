package slowgrow.application.port

import cats.effect.IO
import slowgrow.domain.assets.AssetSym
import slowgrow.domain.portfolio.{EvaluatedPosition, Portfolio}

trait EvaluatedPositionRepository {

  def listByPortfolioId(portfolioId: Portfolio.Id): IO[Either[DataAccessException, Seq[EvaluatedPosition]]]

  def listOpenByPortfolioId(portfolioId: Portfolio.Id): IO[Either[DataAccessException, Seq[EvaluatedPosition]]]

  def listOpenByPortfolioAndSymbol(
      portfolioId: Portfolio.Id,
      sym: AssetSym
  ): IO[Either[DataAccessException, Seq[EvaluatedPosition]]]
}
