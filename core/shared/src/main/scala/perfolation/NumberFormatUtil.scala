package perfolation

object NumberFormatUtil {

  def int(i: Int, digits: Int): String = {
    val s = i.toString
    val padTo = digits - s.length
    padTo match {
      case _ if `padTo` <= 0 => s
      case 1 => "0".concat(s)
      case 2 => "00".concat(s)
      case 3 => "000".concat(s)
      case 4 => "0000".concat(s)
      case 5 => "00000".concat(s)
      case 6 => "000000".concat(s)
      case 7 => "0000000".concat(s)
      case 8 => "00000000".concat(s)
      case 9 => "000000000".concat(s)
      case _ => throw new RuntimeException(s"intFormat padding not available for $digits!")
    }
  }
}