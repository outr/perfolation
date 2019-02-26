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
  }
}