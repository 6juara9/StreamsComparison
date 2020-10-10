package monix

import cases.TestCases
import monix.eval.Task
import monix.reactive.Observable

class ObservableStreamAPI extends TestCases[Task] {

  override def rangeToListOfStrings(
    range: Range
  ): Task[List[String]] =
    Observable
      .from(range)
      .map(_.toString)
      .toListL
}
