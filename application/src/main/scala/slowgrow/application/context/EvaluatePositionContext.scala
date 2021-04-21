package slowgrow.application.context

import cats.effect.IO
import slowgrow.application.context.AddPositionContext.ContextException
import slowgrow.application.port.PositionRepository

class EvaluatePositionContext(
    positionRepository: PositionRepository,
    evaluatePositionContext: EvaluatePositionContext
) {

  def evaluatePosition(): IO[Either[ContextException, Unit]] = ???
}

object EvaluatePositionContext {

  sealed trait ContextException
}
