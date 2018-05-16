package perfolation

import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Currency

object NumberFormatUtil {
  // Make sure the platform is initialized
  Platform

  private var map = Map.empty[String, NumberFormatter]

  def apply(i: Int = 1,
            f: Int = 2,
            maxI: Int = 9,
            maxF: Int = 100,
            g: Boolean = true,
            c: Option[Currency] = None,
            rm: RoundingMode = RoundingMode.HALF_UP): NumberFormatter = synchronized {
    val key: String = p"$i,$f,$maxI,$maxF,$g,$c,$rm"
    map.get(key) match {
      case Some(nf) => nf
      case None => {
        val nf = new NumberFormatter(i, f, maxI, maxF, g, c, rm)
        map += key -> nf
        nf
      }
    }
  }

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

  class NumberFormatter(i: Int,
                        f: Int,
                        maxI: Int,
                        maxF: Int,
                        g: Boolean,
                        c: Option[Currency],
                        rm: RoundingMode) {
    private lazy val nf = {
      val nf = NumberFormat.getInstance()
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

    def format(l: Long): String = synchronized(nf.format(l))
    def format(d: Double): String = synchronized(nf.format(d))
  }
}