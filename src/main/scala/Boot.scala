import java.io.File

import akka.AkkaStreamAPI
import akka.actor.ActorSystem
import akka.stream.Materializer
import monix.ObservableStreamAPI
import monix.execution.Scheduler.Implicits.global
import zio.ZStreamAPI

object Boot extends App {

  val system = ActorSystem()
  implicit val mat: Materializer = Materializer(system)

  val parallelism = 10
  val akkaStreamAPI = new AkkaStreamAPI(parallelism)
  val monixStreamAPI = new ObservableStreamAPI(parallelism)
  val zioStreamAPI = new ZStreamAPI(parallelism)

  val range = 1 to 100000
  val file = new File("src/main/resources/test_file.tsv")

  for {
    _ <- akkaStreamAPI.timer(akkaStreamAPI.rangeToListOfStrings(range), 30)
    _ <- monixStreamAPI.timer(monixStreamAPI.rangeToListOfStrings(range), 30).runToFuture
    _ <- zio.Runtime.global.unsafeRunToFuture(zioStreamAPI.timer(zioStreamAPI.rangeToListOfStrings(range), 30))
    _ = println("------- File -------")
    _ <- akkaStreamAPI.timer(akkaStreamAPI.countCharsInEachLine(file), 30)
    _ <- monixStreamAPI.timer(monixStreamAPI.countCharsInEachLine(file), 30).runToFuture
    _ <- zio.Runtime.global.unsafeRunToFuture(zioStreamAPI.timer(zioStreamAPI.countCharsInEachLine(file), 30))
  } yield println("Finished")
}
