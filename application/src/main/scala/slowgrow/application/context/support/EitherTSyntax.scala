package slowgrow.application.context.support

import cats.data.EitherT
import cats.effect.IO

trait EitherTSyntax {

  implicit class RichEither[L <: Throwable, R](self: IO[Either[L, R]]) {
    def asT: EitherT[IO, Throwable, R] = EitherT.apply(self)

    def handle[E](f: L => E): EitherT[IO, E, R] = EitherT.apply(self).leftMap(f)

  }

}
