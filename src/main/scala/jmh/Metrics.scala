package jmh

import java.util.concurrent.TimeUnit

import akka.stream.scaladsl.{Sink, Source}
import jmh.TestData.{zioRuntime, _}
import monix.execution.Scheduler.Implicits.global
import monix.reactive.Observable
import org.openjdk.jmh.annotations.{Benchmark, BenchmarkMode, Mode, OutputTimeUnit}
import zio.Chunk
import zio.stream.ZStream

import scala.concurrent.Future

class Metrics {

  @Benchmark
  @BenchmarkMode(Array(Mode.AverageTime))
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  def checkZio(): Chunk[String] =
    zioRuntime
      .unsafeRun(
        ZStream
          .fromIterable(TestData.listOfInt)
          .map(_.toString)
          .runCollect
      )


  @Benchmark
  @BenchmarkMode(Array(Mode.AverageTime))
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  def checkMonix(): List[String] =
    Observable
      .from(TestData.listOfInt)
      .map(_.toString)
      .toListL.runSyncUnsafe()


  @Benchmark
  @BenchmarkMode(Array(Mode.AverageTime))
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  def checkAkka(): Future[List[String]] =
    Source(TestData.listOfInt).map(_.toString).runWith(Sink.collection[String, List[String]])
}
