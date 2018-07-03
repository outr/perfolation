package perfolation

object NumberFormatUtil {

  def int(i: Int, digits: Int): String = {
    val s = i.toString
    val padTo = digits - s.length
    if (padTo <= 0) {
      s
    } else if (padTo == 1) {
      "0".concat(s)
    } else if (padTo == 2) {
      "00".concat(s)
    } else if (padTo == 3) {
      "000".concat(s)
    } else if (padTo == 4) {
      "0000".concat(s)
    } else if (padTo == 5) {
      "00000".concat(s)
    } else if (padTo == 6) {
      "000000".concat(s)
    } else if (padTo == 7) {
      "0000000".concat(s)
    } else if (padTo == 8) {
      "00000000".concat(s)
    } else if (padTo == 9) {
      "000000000".concat(s)
    } else {
      throw new RuntimeException(s"intFormat padding not available for $digits!")
    }
  }
}