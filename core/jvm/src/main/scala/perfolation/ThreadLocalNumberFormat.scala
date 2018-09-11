package perfolation

import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Currency

object ThreadLocalNumberFormat {
  // Make sure the platform is initialized
  Platform

  private val threadLocalNumberFormat = new ThreadLocal[NumberFormat]{
    override protected def initialValue(): NumberFormat = NumberFormat.getInstance()
  }

  protected[perfolation] def apply(i: Int = 1,
            f: Int = 2,
            maxI: Int = 9,
            maxF: Int = 100,
            g: Boolean = true,
            c: Option[Currency] = None,
            rm: RoundingMode = RoundingMode.HALF_UP): NumberFormat = {
    val nf = threadLocalNumberFormat.get()
    nf.setGroupingUsed(g)
    c.foreach(nf.setCurrency)
    nf.setMaximumFractionDigits(maxF)
    nf.setMinimumFractionDigits(f)
    nf.setMaximumIntegerDigits(maxI)
    nf.setMinimumIntegerDigits(i)
    nf.setParseIntegerOnly(maxF == 0)
    nf.setRoundingMode(rm)
    nf
  }

}
