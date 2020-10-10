package zio

import cases.TestCases
import zio.stream.{Sink, ZStream}

class ZStreamAPI extends TestCases[Task] {
  override def rangeToListOfStrings(range: Range): Task[List[String]] = {
    val sink = Sink.collectAll[String].map(_.toList)
    ZStream.fromIterable(range).map(_.toString) >>> sink
  }
}
