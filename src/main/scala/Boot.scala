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
}
