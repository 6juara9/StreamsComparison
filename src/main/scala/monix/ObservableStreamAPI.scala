package monix

import instruments.{TestCases, Timer}
import monix.eval.Task
import monix.reactive.Observable

class ObservableStreamAPI extends TestCases[Task]
  with Timer[Task] {

  override def rangeToListOfStrings(
    range: Range
  ): Task[List[String]] =
    Observable
      .from(range)
      .map(_.toString)
      .toListL

  override def apiName: String = "monix-streams"

  override def timer[R](
    task: => Task[R], retries: Int
  ): Task[Unit] = Task.sequence(List.fill(retries)(timer(task))).map(printTime)

  override def timer[R](task: => Task[R]): Task[Long] =
    task.timed.map(_._1.toMillis)
}
