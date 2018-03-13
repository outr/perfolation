package perfolation

import java.math.RoundingMode
import java.util.{Currency, Locale}

class DoubleImplicits(val d: Double) extends AnyVal {
  def f(i: Int = 1,
        f: Int = 2,
        maxI: Int = 9,
        maxF: Int = 100,
        g: Boolean = true,
        c: Currency = Currency.getInstance(Locale.getDefault),
        rm: RoundingMode = RoundingMode.HALF_UP): String = {
    NumberFormatUtil(i, f, maxI, maxF, g, c, rm).format(d)
  }
}
