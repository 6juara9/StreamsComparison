package jmh

import akka.actor.ActorSystem
import akka.stream.Materializer

object TestData {
  val listOfInt: Vector[Int] = (1 to 100000).toVector
  val zioRuntime: zio.Runtime[zio.ZEnv] = zio.Runtime.default
  val system: ActorSystem = ActorSystem()
  implicit val mat: Materializer = Materializer(system)
}
