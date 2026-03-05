package tests

import java.time.ZoneId

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.language.implicitConversions

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
      date1.t.s.toLong should be(date1 / 1000L)
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
  }
  "Date Formatting with explicit timezone" should {
    import perfolation._

    val date1: Long = 1524606965775L
    // 2018-04-24T21:56:05.775Z in UTC
    val utc = ZoneId.of("UTC")
    // 2018-04-24T16:56:05.775-05:00 in US/Central (CDT)
    val cdt = ZoneId.of("America/Chicago")
    // 2018-04-25T07:26:05.775+09:30 in Australia/Darwin (ACST, +09:30)
    val acst = ZoneId.of("Australia/Darwin")

    "format date in UTC" in {
      val d = date1.t(utc)
      d.hour24 should be(21)
      d.minuteOfHour should be(56)
      d.secondOfMinute should be(5)
      d.milliOfSecond should be(775)
      d.year should be(2018)
      d.month should be(3)
      d.dayOfMonth should be(24)
      d.dayOfWeek should be(3)
      d.isAM should be(false)
      d.timeZoneOffsetMillis should be(0)
      d.z should be("+0000")
      d.F should be("2018-04-24")
      d.T should be("21:56:05")
    }
    "format date in America/Chicago (CDT)" in {
      val d = date1.t(cdt)
      d.hour24 should be(16)
      d.minuteOfHour should be(56)
      d.secondOfMinute should be(5)
      d.year should be(2018)
      d.month should be(3)
      d.dayOfMonth should be(24)
      d.timeZoneOffsetMillis should be(-5 * 3600 * 1000)
      d.z should be("-0500")
      d.F should be("2018-04-24")
      d.T should be("16:56:05")
    }
    "format date with half-hour offset (Australia/Darwin, ACST +09:30)" in {
      val d = date1.t(acst)
      d.hour24 should be(7)
      d.minuteOfHour should be(26)
      d.dayOfMonth should be(25)
      d.timeZoneOffsetMillis should be(9 * 3600 * 1000 + 30 * 60 * 1000)
      d.z should be("+0930")
    }
  }
}