package tests

import org.scalatest.{Matchers, WordSpec}

class NumberFormatSpec extends WordSpec with Matchers {
  "Number Formatting" should {
    import perfolation._

    "format a double properly" in {
      val double: Double = 15245.1254
      double.d.f()
    }
  }
}