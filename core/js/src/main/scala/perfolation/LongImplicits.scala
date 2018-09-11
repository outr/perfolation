package perfolation

import java.math.RoundingMode
import java.util.Currency

class LongImplicits(val l: Long) extends AnyVal {
  def t: CrossDate = CrossDate(l)

  // Number format
  def f(i: Int = 1,
        f: Int = 2,
        maxI: Int = 9,
        maxF: Int = 100,
        g: Boolean = true,
        c: Option[Currency] = None,
        rm: RoundingMode = RoundingMode.HALF_UP): String = {
    l.toString
//    ThreadLocalNumberFormat(i, f, maxI, maxF, g, c, rm).format(l)
  }
}