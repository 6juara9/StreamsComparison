package akka

import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import cases.TestCases

import scala.concurrent.Future

class AkkaStreamAPI(implicit mat: Materializer) extends TestCases[Future] {

  override def rangeToListOfStrings(
    range: Range
  ): Future[List[String]] = {
    val source: Source[Int, NotUsed] = Source(range)
    val flow = Flow[Int].map(_.toString)
    val sink = Sink.collection[String, List[String]]
    source.via(flow).runWith(sink)
  }

}
