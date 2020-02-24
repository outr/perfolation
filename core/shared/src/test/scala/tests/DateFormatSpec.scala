package tests

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DateFormatSpec extends AnyWordSpec with Matchers {
  "Date Formatting" should {
    import perfolation._

    val date1: Long = 1524606965775L
    val hour = (21 - date1.t.timeZoneOffsetHH) % 24

    "retrieve millisecond info" in {
      date1.t.milliseconds should be(date1)
      date1.t.milliOfSecond should be(775)
      date1.t.Q should be(date1.toString)
      date1.t.L should be("775")
    }
    "retrieve seconds info" in {
      date1.t.secondOfMinute should be(5)
      date1.t.secondsOfEpoch should be(date1 / 1000L)
      date1.t.S should be("05")
      date1.t.s.toInt should be(date1 / 1000L)
    }
    "retrieve minutes info" in {
      date1.t.minuteOfHour should be(56)
      date1.t.M should be("56")
    }
    "retrieve hours info" in {
      date1.t.hour24 should be(hour)
      date1.t.hour12 should be(hour % 12)
      date1.t.isAM should be(false)
      date1.t.isPM should be(true)
      date1.t.H should be(hour.toString)
      date1.t.I should be((hour % 12).f(2))
      date1.t.k should be(hour.toString)
      date1.t.l should be((hour % 12).toString)
      date1.t.p should be("pm")
      date1.t.P should be("PM")
    }
    "retrieve days info" in {
      date1.t.dayOfWeek should be(3)
      date1.t.dayOfMonth should be(24)
      date1.t.dayOfYear should be(113)
      date1.t.A should be("Tuesday")
      date1.t.a should be("Tues")
      date1.t.j should be("114")
      date1.t.d should be("24")
      date1.t.e should be("24")
    }
    "retrieve week info" in {
    }
    "retrieve month info" in {
      date1.t.month should be(3)
      date1.t.B should be("April")
      date1.t.b should be("Apr")
      date1.t.h should be("Apr")
      date1.t.m should be("04")
    }
    "retrieve years info" in {
      date1.t.year should be(2018)
      date1.t.C should be("20")
      date1.t.Y should be("2018")
      date1.t.y should be("18")
    }
    /*"retrieve miscellaneous info" in {
      date1.t.timeZoneOffsetMM should be(300)
      date1.t.timeZoneOffsetHH should be(5)
      date1.t.timeZone should be("CDT")
      date1.t.z should be("-05300")
      date1.t.Z should be("CDT")
    }
    "format a date properly" in {
      date1.t.R should be("16:56")
      date1.t.T should be("16:56:05")
      date1.t.r should be("04:56:05:pm")
      date1.t.D should be("04/24/18")
      date1.t.F should be("2018-04-24")
      date1.t.c should be("Tues Apr 24 16:56:05 -05300 2018")
    }*/
  }
}