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

  protected[perfolation] def apply(i: Int,
                                   f: Int,
                                   maxI: Int,
                                   maxF: Int,
                                   g: Boolean,
                                   c: Option[Currency],
                                   rm: RoundingMode): NumberFormat = {
    val nf = threadLocalNumberFormat.get()
    nf.setGroupingUsed(g)
    c.foreach(nf.setCurrency)
    nf.setMaximumFractionDigits(if (maxF == -1) f else maxF)
    nf.setMinimumFractionDigits(f)
    nf.setMaximumIntegerDigits(if (maxI == -1) i else maxI)
    nf.setMinimumIntegerDigits(i)
    nf.setParseIntegerOnly(maxF == 0)
    nf.setRoundingMode(rm)
    nf
  }

}
