package instruments

trait Timer[F[_]] {

  def apiName: String

  def timer[R](task: => F[R]): F[Unit]

  def timer[R](task: => F[R], retries: Int): F[List[Unit]]

  def getMillis: Long = System.currentTimeMillis()

  def printTime(millis: Long): Unit = println(s"--- $apiName API takes $millis millis ---")

}
