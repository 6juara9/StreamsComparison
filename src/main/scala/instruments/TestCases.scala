package instruments

/**
 * Comparing stream cases
 *
 * @tparam F - stream context
 */
trait TestCases[F[_]] {

  def rangeToListOfStrings(range: Range): F[List[String]]

}
