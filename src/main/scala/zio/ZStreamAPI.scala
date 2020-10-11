package zio

import instruments.{TestCases, Timer}
import zio.clock.Clock
import zio.stream.{Sink, ZStream}

class ZStreamAPI extends TestCases[Task]
  with Timer[Task] {
  override def rangeToListOfStrings(range: Range): Task[List[String]] = {
    val sink = Sink.collectAll[String].map(_.toList)
    ZStream.fromIterable(range).map(_.toString) >>> sink
  }

  override def apiName: String = "zio-streams"

  override def timer[R](
    task: => Task[R], retries: Int
  ): Task[Unit] = ZIO.collectAll(List.fill(retries)(timer(task))).map(printTime)

  override def timer[R](
    task: => Task[R]
  ): Task[Long] = task
    .timed
    .map(_._1.toMillis)
    .provideLayer(Clock.live)
}
