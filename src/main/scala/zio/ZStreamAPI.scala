package zio

import java.io.File
import zio.stream.{Sink, ZStream}

class ZStreamAPI(parallelism: Int) {

  def rangeToListOfStrings(range: Range): Task[List[String]] = {
    val sink = Sink.collectAll[String].map(_.toList)
    ZStream.fromIterable(range).map(_.toString) >>> sink
  }

  def countCharsInEachLine(file: File): Task[Unit] = {
    val linesSource = scala.io.Source.fromFile(file)
    ZStream
      .fromIterator(linesSource.getLines())
      .mapMParUnordered(parallelism) { line =>
        Task(line.trim.length)
      }.run(Sink.drain)
  }
}
