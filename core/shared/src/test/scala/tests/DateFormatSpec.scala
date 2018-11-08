package tests

import java.util.{Calendar, TimeZone}

import org.scalatest.{Matchers, WordSpec}

class DateFormatSpec extends WordSpec with Matchers {
  "Date Formatting" should {
    import perfolation._

    val date1: Long = 1524588965775L
    val gmtDate: Long = date1 + date1.t.timeZoneOffsetMillis
    // time we are testing is GMT: Tuesday, April 24, 2018 4:56:05.775 PM

    "retrieve millisecond info" in {
      date1.t.milliseconds should be(1524588965775L)
      gmtDate.t.milliOfSecond should be(775)
      gmtDate.t.timeZoneOffsetMillis
      date1.t.Q should be("1524588965775")
      gmtDate.t.L should be("775")
    }

    "retrieve seconds info" in {
      gmtDate.t.secondOfMinute should be(5)
      date1.t.secondsOfEpoch should be(1524588965)
      gmtDate.t.S should be("05")
      date1.t.s.toInt should be(1524588965)
    }

    "retrieve minutes info" in {
      gmtDate.t.minuteOfHour should be(56)
      gmtDate.t.M should be("56")
    }

    "retrieve hours info" in {
      gmtDate.t.hour24 should be(16)
      gmtDate.t.hour12 should be(4)
      gmtDate.t.isAM should be(false)
      gmtDate.t.isPM should be(true)
      gmtDate.t.H should be("16")
      gmtDate.t.I should be("04")
      gmtDate.t.k should be("16")
      gmtDate.t.l should be("4")
      gmtDate.t.p should be("pm")
      gmtDate.t.P should be("PM")
    }

    "retrieve days info" in {
      gmtDate.t.dayOfWeek should be(3)
      gmtDate.t.dayOfMonth should be(24)
      gmtDate.t.dayOfYear should be(113)
      gmtDate.t.A should be("Tuesday")
      gmtDate.t.a should be("Tues")
      gmtDate.t.j should be("114")
      gmtDate.t.d should be("24")
      gmtDate.t.e should be("24")
    }

    "retrieve week info" in {
    }

    "retrieve month info" in {
      gmtDate.t.month should be(3)
      gmtDate.t.B should be("April")
      gmtDate.t.b should be("Apr")
      gmtDate.t.h should be("Apr")
      gmtDate.t.m should be("04")
    }


    "retrieve years info" in {
      gmtDate.t.year should be(2018)
      gmtDate.t.C should be("20")
      gmtDate.t.Y should be("2018")
      gmtDate.t.y should be("18")
    }

    "retrieve miscellaneous info" in {
      gmtDate.t.timeZoneOffsetMM //should be("240") enter your timezone offset minutes here
      gmtDate.t.timeZoneOffsetHH //should be("04") enter your timezone offset hours here
      gmtDate.t.timeZone //should be("AST") enter your timezone abbreviation here
      gmtDate.t.z //should be("+0400") enter your offset hours here
      gmtDate.t.Z //should be("AST") enter your timezone abbreviation here
    }

    "format a date properly" in {
      gmtDate.t.R should be("16:56")
      gmtDate.t.T should be("16:56:05")
      gmtDate.t.r should be("04:56:05:pm")
      gmtDate.t.D should be("04/24/18")
      gmtDate.t.F should be("2018-04-24")
      gmtDate.t.c //should be("Tues Apr 24 16:56:05 +0400 2018") relative to testing environment; enter your time zone info
    }
  }
}