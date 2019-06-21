package tests

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.Matchers
import perfolation.numeric.{FastNumber, Grouping, RoundingMode}

class NumberFormatSpec extends AnyWordSpec with Matchers {
  "Number Formatting" should {
    import perfolation._

    "use a FastNumber properly" in {
      val fn = new FastNumber
      fn.set(12.345)
      fn.toString should be("12.345")
      fn.setMinimumIntegerDigits(3)
      fn.toString should be("012.345")
      fn.set(1234.567)
      fn.setMaximumIntegerDigits(2)
      fn.toString should be("34.567")
      fn.setMinimumFractionDigits(5)
      fn.toString should be("34.56700")
      fn.setMaximumFractionDigits(2, RoundingMode.HalfUp)
      fn.toString should be("34.57")
      fn.set(123456789.0)
      fn.setMaximumFractionDigits(0, RoundingMode.HalfUp)
      fn.toString should be("123456789")
      fn.group(Grouping.US)
      fn.toString should be("123,456,789")
    }
    "format an integer to two digits" in {
      4.f(2) should be("04")
      40.f(2) should be("40")
      400.f(2) should be("400")
    }
    "format an integer to two fraction digits" in {
      4.f(f = 2) should be("4.00")
      40.f(f = 2) should be("40.00")
      400.f(f = 2) should be("400.00")
    }
    "format a double to two digits" in {
      4.0.f(2) should be("04.00")
      40.0.f(2) should be("40.00")
      400.0.f(2) should be("400.00")
      4.1.f(2) should be("04.10")
      40.1.f(2) should be("40.10")
      400.1.f(2) should be("400.10")
      4.12.f(2) should be("04.12")
      40.12.f(2) should be("40.12")
      400.12.f(2) should be("400.12")
      4.123.f(2) should be("04.12")
      40.123.f(2) should be("40.12")
      400.123.f(2) should be("400.12")
      4.126.f(2) should be("04.13")
    }
    "format a BigDecimal to 0 digits" in {
      BigDecimal(6481415348.78).f(f = 0) should be("6481415349")
      BigDecimal(9999999999.99).f(f = 0) should be("10000000000")
    }
    "format a negative number properly" in {
      (-100.0).f(2) should be("-100.00")
      (-0.5).f(2) should be("-0.50")
      (-0.5).f(2, g = Grouping.US) should be("-0.50")
      (-444).f(g = Grouping.US) should be("-444")
    }
  }
}