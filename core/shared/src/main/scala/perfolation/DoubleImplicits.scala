package perfolation

import java.math.RoundingMode
import java.util.{Currency, Locale}

class DoubleImplicits(val d: Double) extends AnyVal {
  def f(i: Int = 1,
        f: Int = 2,
        maxI: Int = 9,
        maxF: Int = -1,
        g: Boolean = true,
        c: Option[Currency] = None,
        rm: RoundingMode = RoundingMode.HALF_UP): String = {
    ThreadLocalNumberFormat(i, f, maxI, maxF, g, c, rm).format(d)
  }
}
