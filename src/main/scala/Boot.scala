import akka.AkkaStreamAPI
import akka.actor.ActorSystem
import akka.stream.Materializer
import monix.ObservableStreamAPI
import monix.execution.Scheduler.Implicits.global
import zio.ZStreamAPI

object Boot extends App {

  val system = ActorSystem()
  implicit val mat: Materializer = Materializer(system)

  val akkaStreamAPI = new AkkaStreamAPI()
  val monixStreamAPI = new ObservableStreamAPI()
  val zioStreamAPI = new ZStreamAPI

  val range = 1 to 100000

  for {
    _ <- akkaStreamAPI.timer(akkaStreamAPI.rangeToListOfStrings(range), 30)
    _ <- monixStreamAPI.timer(monixStreamAPI.rangeToListOfStrings(range), 30).runToFuture
    _ <- zio.Runtime.global.unsafeRunToFuture(zioStreamAPI.timer(zioStreamAPI.rangeToListOfStrings(range), 30))
  } yield println("Finished")
}
