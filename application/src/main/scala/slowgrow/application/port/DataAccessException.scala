package slowgrow.application.port

trait DataAccessException extends Throwable

case class DataNotFoundException(key: String*)
    extends RuntimeException(s"Data not found for key: ${key.mkString(",")}")
    with DataAccessException
