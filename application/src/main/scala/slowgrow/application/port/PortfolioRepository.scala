package slowgrow.application.port

import cats.effect.IO
import slowgrow.domain.portfolio.Portfolio
import slowgrow.domain.values.InvestorId

trait PortfolioRepository {

  def findBy(investorId: InvestorId, portfolioName: Portfolio.Name): IO[Either[DataAccessException, Portfolio]]

}
