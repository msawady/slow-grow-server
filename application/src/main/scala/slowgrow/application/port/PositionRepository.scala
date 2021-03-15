package slowgrow.application.port

import cats.effect.IO
import slowgrow.domain.portfolio.{Portfolio, Position}

trait PositionRepository {

  def listBy(portfolioId: Portfolio.Id): IO[Either[DataAccessException, Seq[Position]]]

  def listOpenBy(portfolioId: Portfolio.Id): IO[Either[DataAccessException, Seq[Position]]]
}
