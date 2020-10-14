package instruments

import java.io.File

/**
 * Comparing stream cases
 *
 * @tparam F - stream context
 */
trait TestCases[F[_]] {

  def rangeToListOfStrings(range: Range): F[List[String]]

  def countCharsInEachLine(file: File): F[Unit]

}
