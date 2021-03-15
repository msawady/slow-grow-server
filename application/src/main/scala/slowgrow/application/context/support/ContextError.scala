package slowgrow.application.context.support

trait ContextError[+Context] extends Throwable

case class GenericContextError[Context](e: Throwable) extends ContextError[Context]
