package zio

import java.io.File

import instruments.{TestCases, Timer}
import zio.clock.Clock
import zio.stream.{Sink, ZStream}

class ZStreamAPI(parallelism: Int) extends TestCases[Task]
  with Timer[Task] {

  override def apiName: String = "zio-streams"

  override def timer[R](
    task: => Task[R],
    retries: Int
  ): Task[Unit] = ZIO.collectAllPar(List.fill(retries)(timer(task))).map(printTime)

  override def timer[R](
    task: => Task[R]
  ): Task[Long] = task
    .timed
    .map(_._1.toMillis)
    .provideLayer(Clock.live)

  // *** -----cases----- ***

  override def rangeToListOfStrings(range: Range): Task[List[String]] = {
    val sink = Sink.collectAll[String].map(_.toList)
    ZStream.fromIterable(range).map(_.toString) >>> sink
  }

  override def countCharsInEachLine(file: File): Task[Unit] = {
    val linesSource = scala.io.Source.fromFile(file)
    ZStream
      .fromIterator(linesSource.getLines())
      .mapMParUnordered(parallelism) { line =>
        Task(line.trim.length)
      }.run(Sink.drain)
  }
}
