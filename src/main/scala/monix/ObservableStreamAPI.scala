package monix

import java.io.File

import instruments.{TestCases, Timer}
import monix.eval.Task
import monix.reactive.Observable
import scala.io.BufferedSource

class ObservableStreamAPI(parallelism: Int) extends TestCases[Task]
  with Timer[Task] {

  override def apiName: String = "monix-streams"

  override def timer[R](
    task: => Task[R], retries: Int
  ): Task[Unit] =
    Task.parSequenceUnordered(List.fill(retries)(timer(task))).map(printTime)

  override def timer[R](task: => Task[R]): Task[Long] =
    task.timed.map(_._1.toMillis)

  // *** -----cases----- ***

  override def rangeToListOfStrings(
    range: Range
  ): Task[List[String]] =
    Observable
      .from(range)
      .map(_.toString)
      .toListL

  override def countCharsInEachLine(file: File): Task[Unit] = {
    val linesSource: BufferedSource = scala.io.Source.fromFile(file)
    Observable
      .fromIterator(Task(linesSource.getLines()))
      .mapParallelUnordered(parallelism) { line =>
        Task.now(line.trim.length)
      }
      .toListL
      .foreachL(_ => linesSource.close())
  }
}
