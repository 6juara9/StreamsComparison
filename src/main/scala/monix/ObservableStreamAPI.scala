package monix

import java.io.File

import monix.eval.Task
import monix.reactive.Observable

import scala.io.BufferedSource

class ObservableStreamAPI(parallelism: Int) {

  def rangeToListOfStrings(
    range: Range
  ): Task[List[String]] =
    Observable
      .from(range)
      .map(_.toString)
      .toListL

  def countCharsInEachLine(file: File): Task[Unit] = {
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
