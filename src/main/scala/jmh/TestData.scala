package jmh

import akka.actor.ActorSystem
import akka.stream.Materializer
import monix.execution.Scheduler
import zio.internal.Executor

import scala.concurrent.ExecutionContext

object TestData {
  val listOfInt: Vector[Int] = (1 to 100000).toVector
  val system: ActorSystem = ActorSystem()
  val ec: ExecutionContext = system.dispatcher
  implicit val mat: Materializer = Materializer(system)
  val zioRuntime: zio.Runtime[zio.ZEnv] = zio.Runtime.default.withExecutor(Executor.fromExecutionContext(2048)(ec))
  implicit val monixScheduler: Scheduler = Scheduler.apply(ec)
}
