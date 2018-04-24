package tests

import org.scalatest.{Matchers, WordSpec}

class DateFormatSpec extends WordSpec with Matchers {
  "Date Formatting" should {
    import perfolation._

    val date1: Long = 1524588965775L

    "format a date properly" in {
      date1.t.year should be(2018)
      date1.t.month should be(3)
      date1.t.dayOfMonth should be(24)
    }
  }
}