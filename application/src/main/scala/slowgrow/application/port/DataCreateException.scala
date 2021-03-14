package slowgrow.application.port

trait DataCreateException extends Throwable

case class DataAlreadyExistsException(key: String*)
    extends RuntimeException(s"Data already exists for key: ${key.mkString(",")}")
    with DataAccessException
