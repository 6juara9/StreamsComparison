package jmh

import java.util.concurrent.TimeUnit

import akka.stream.scaladsl.{Sink, Source}
import jmh.TestData._
import monix.reactive.Observable
import org.openjdk.jmh.annotations.{Benchmark, BenchmarkMode, Mode, OutputTimeUnit}
import zio.Chunk
import zio.stream.ZStream

import scala.concurrent.Future

@BenchmarkMode(Array(Mode.All))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class Metrics {

  @Benchmark
  def checkListOfIntsToStringZio(): Chunk[String] =
    zioRuntime
      .unsafeRun(
        ZStream
          .fromIterable(TestData.listOfInt)
          .map(_.toString)
          .runCollect
      )


  @Benchmark
  def checkListOfIntsToStringMonix(): List[String] =
    Observable
      .from(TestData.listOfInt)
      .map(_.toString)
      .toListL
      .runSyncUnsafe()


  @Benchmark
  def checkListOfIntsToStringAkka(): Future[List[String]] =
    Source(TestData.listOfInt)
      .map(_.toString)
      .runWith(Sink.collection[String, List[String]])
}
