package perfolation.numeric

case class Grouping(every: Int, char: Char)

object Grouping {
  val None: Grouping = Grouping(0, ' ')
  val US: Grouping = Grouping(3, ',')
}