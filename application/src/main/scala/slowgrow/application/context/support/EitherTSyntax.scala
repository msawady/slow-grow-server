package slowgrow.application.context.support

import cats.data.EitherT
import cats.effect.IO
import slowgrow.application.port.{DataAccessException, DataCreateException}

trait EitherTSyntax {

  implicit class RichEither[L <: Throwable, R](self: IO[Either[L, R]]) {
    def asT: EitherT[IO, Throwable, R] = EitherT.apply(self)

    def handleLeftWith[LL](f: L => LL): EitherT[IO, LL, R] = EitherT.apply(self).leftMap(f)

  }

  implicit class RichDataAccessEither[R](self: IO[Either[DataAccessException, R]]) {

    def handle[LL](f: DataAccessException => LL): EitherT[IO, LL, R] = EitherT.apply(self).leftMap(f)
  }

  implicit class RichDataCreateEither[R](self: IO[Either[DataCreateException, R]]) {

    def handle[LL](f: DataCreateException => LL): EitherT[IO, LL, R] = EitherT.apply(self).leftMap(f)
  }
}
