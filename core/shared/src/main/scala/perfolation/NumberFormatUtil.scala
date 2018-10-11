package perfolation

object NumberFormatUtil {

  def int(i: Int, digits: Int): String = {
    val s = i.toString
    val padTo = digits - s.length
    def zeroes(n: Int): String = if (n > 0) "0".concat(zeroes(n-1)) else ""

    if (padTo >= 0) zeroes(padTo).concat(s)
    else throw new RuntimeException(p"intFormat padding not available for $digits!")
  }
}