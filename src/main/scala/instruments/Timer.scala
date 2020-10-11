package instruments

trait Timer[F[_]] {

  def apiName: String

  def timer[R](task: => F[R]): F[Long]

  def timer[R](task: => F[R], retries: Int): F[Unit]

  def getMillis: Long = System.currentTimeMillis()

  def printTime(measurements: List[Long]): Unit = measurements match {
    case Nil  => println("Measurements are empty")
    case list =>
      val size = list.size
      val max = list.max
      val min = list.min
      val average = list.sum / size
      println(
        s"""$apiName measurements:
           |retries - $size
           |max - $max
           |min - $min
           |average - $average
           |""".stripMargin
      )
  }

  def printTime(millis: Long): Unit = println(s"--- $apiName API takes $millis millis ---")

}
