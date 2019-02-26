package tests

import org.scalatest.{Matchers, WordSpec}

class NumberFormatSpec extends WordSpec with Matchers {
  "Number Formatting" should {
    import perfolation._

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
  }
}