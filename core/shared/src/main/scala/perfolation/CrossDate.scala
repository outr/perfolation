package perfolation

import NumberFormatUtil._

trait CrossDate extends Any {
  /**
    * A field containing the numeric value corresponding to the current time - the number of milliseconds elapsed
    * since January 1, 1970 00:00:00 UTC, with leap seconds ignored. Roughly the point in time this date represents.
    */
  def milliseconds: Long
  /**
    * Evaluates to the millisecond within the second. E.g., at 10:04:15.250 PM, `milliOfSecond` is 250.
    */
  def milliOfSecond: Int
  /**
    * Evaluates to the second within the minute. E.g., at 10:04:15.250 PM, `secondOfMinute` is 15.
    */
  def secondOfMinute: Int
  /**
    * Evaluates to the minute within the hour. E.g., at 10:04:15.250 PM, `minuteOfHour` is 4.
    */
  def minuteOfHour: Int
  /**
    * Evaluates to the hour within the date. E.g., at 10:04:15.250 PM, `hour24` is 10. Note this is "military time".
    */
  def hour24: Int
  /**
    * Evaluates to `false` if `hour24` is less than 12; otherwise 'true'.
    */
  def isAM: Boolean
  /**
    * Evaluates to the time difference between UTC time and local time, in minutes. If UTC is in the future relative
    * to local time, this will be positive.
    */
  def timeZoneOffsetMillis: Int
  /**
    * Evaluates to the time difference between UTC time and local time, in minutes.
    */
  def timeZoneOffsetMM: Int = -(timeZoneOffsetMillis / (1000 * 60))
  /**
    * Evaluates to the time difference between UTC time and local time, in hours.
    */
  def timeZoneOffsetHH: Int = -(timeZoneOffsetMillis / (1000 * 60 * 60))
  /**
    * A string representing the abbreviation for the time zone. This value will be adjusted as necessary for Daylight Saving Time.
    */
  def timeZone: String

  /**
    * Evaluates to the year (4 digits for 4-digit years) of the specified date according to local time.
    */
  def year: Int
  /**
    * Evaluates to the month (0-11) in the specified date according to local time.
    */
  def month: Int
  /**
    * Evaluates to the day of the week (1-7) for the specified date according to local time.
    */
  def dayOfWeek: Int
  /**
    * Evaluates to the day of the month (1-31) for the specified date according to local time.
    */
  def dayOfMonth: Int
  /**
    * Evaluates to the day of the year (0-365) for the specified date according to local time.
    */
  def dayOfYear: Int

  /**
    * Evaluates to the standard 12-hour time format for the current date.
    */
  def hour12: Int = hour24 match {
    case 0 => 12
    case i if i > 12 => i - 12
    case i => i
  }
  /**
    * Evaluates to 'true' if 'hour24' is 12-24
    */
  def isPM: Boolean = !isAM
  /**
    * A field containing the numeric value corresponding to the current time - the number of seconds elapsed
    * * since January 1, 1970 00:00:00 UTC, with leap seconds ignored. Roughly the point in time this date represents.
    */
  def secondsOfEpoch: Long = milliseconds / 1000L

  // Time short-hand
  /**
    * Hour of the day for the 24-hour clock, formatted as two digits with a leading zero as necessary i.e. 00 - 23
    */
  def H: String = int(hour24, 2)
  /**
    * Hour for the 12-hour clock, formatted as two digits with a leading zero as necessary, i.e. 01 - 12.
    */
  def I: String = int(hour12, 2)
  /**
    * Hour of the day for the 24-hour clock, i.e. 0 - 23.
    */
  def k: String = hour24.toString
  /**
    * Hour for the 12-hour clock, i.e. 1 - 12.
    */
  def l: String = hour12.toString
  /**
    * Minute within the hour formatted as two digits with a leading zero as necessary, i.e. 00 - 59.
    */
  def M: String = int(minuteOfHour, 2)
  /**
    * Seconds within the minute, formatted as two digits with a leading zero as necessary, i.e. 00 - 60 ("60" is a special value required to support leap seconds).
    */
  def S: String = int(secondOfMinute, 2)
  /**
    * Millisecond within the second formatted as three digits with leading zeros as necessary, i.e. 000 - 999.
    */
  def L: String = int(milliOfSecond, 3)
  /**
    * Locale-specific morning or afternoon marker in lower case, e.g."am" or "pm".
    */
  def p: String = if (isAM) "am" else "pm"
  /**
    * Locale-specific morning or afternoon marker in upper case, e.g."AM" or "PM".
    */
  def P: String = if (isAM) "AM" else "PM"
  /**
    * RFC 822 style numeric time zone offset from GMT, e.g. -0800. This value will be adjusted as necessary for Daylight Saving Time.
    */
  def z: String = {
    val sign = if (timeZoneOffsetMillis >= 0) "+" else "-"
    p"$sign${timeZoneOffsetHH.f(2)}$timeZoneOffsetMM"
  }
  /**
    * A string representing the abbreviation for the time zone. This value will be adjusted as necessary for Daylight Saving Time.
    */
  def Z: String = timeZone
  /**
    * A field containing the numeric value in String form corresponding to the current time - the number of seconds elapsed
    * since January 1, 1970 00:00:00 UTC, with leap seconds ignored. Roughly the point in time this date represents.
    */
  def s: String = secondsOfEpoch.toString
  /**
    * A field containing the numeric value in String form corresponding to the current time - the number of milliseconds elapsed
    * since January 1, 1970 00:00:00 UTC, with leap seconds ignored. Roughly the point in time this date represents.
    */
  def Q: String = milliseconds.toString

  // Date short-hand
  /**
    * Locale-specific full month name, e.g. "January", "February".
    */
  def B: String = CrossDate.Month.Long(month)
  /**
    * Locale-specific abbreviated month name, e.g. "Jan", "Feb".
    */
  def b: String = CrossDate.Month.Short(month)
  /**
    * Same as 'b'.
    */
  def h: String = CrossDate.Month.Short(month)
  /**
    * Locale-specific full name of the day of the week, e.g. "Sunday", "Monday"
    */
  def A: String = CrossDate.Week.Long(dayOfWeek - 1)
  /**
    * Locale-specific short name of the day of the week, e.g. "Sun", "Mon"
    */
  def a: String = CrossDate.Week.Short(dayOfWeek - 1)
  /**
    * Four-digit year divided by 100, formatted as two digits with leading zero as necessary, i.e. 00 - 99
    */
  def C: String = (year / 100).toString
  /**
    * Year, formatted as at least four digits with leading zeros as necessary, e.g. 0092 equals 92 CE for the Gregorian calendar.
    */
  def Y: String = year.toString
  /**
    * Last two digits of the year, formatted with leading zeros as necessary, i.e. 00 - 99.
    */
  def y: String = (year % 100).toString
  /**
    * Day of year, formatted as three digits with leading zeros as necessary, e.g. 001 - 366 for the Gregorian calendar.
    */
  def j: String = (dayOfYear + 1).toString
  /**
    * Month, formatted as two digits with leading zeros as necessary, i.e. 01 - 13.
    */
  def m: String = int(month + 1, 2)
  /**
    * Day of month, formatted as two digits with leading zeros as necessary, i.e. 01 - 31
    */
  def d: String = int(dayOfMonth, 2)
  /**
    * Day of month, formatted as two digits, i.e. 1 - 31.
    */
  def e: String = dayOfMonth.toString
  /**
    * Time formatted for the 24-hour clock as "%tH:%tM"
    */
  def R: String = p"$H:$M"
  /**
    * Time formatted for the 24-hour clock as "%tH:%tM:%tS".
    */
  def T: String = p"$H:$M:$S"
  /**
    * Time formatted for the 12-hour clock as "%tI:%tM:%tS %Tp". The location of the morning or afternoon marker ('%Tp')
    * may be locale-dependent.
    */
  def r: String = p"$I:$M:$S:$p"
  /**
    * Date formatted as "%tm/%td/%ty".
    */
  def D: String = p"$m/$d/$y"
  /**
    * 	ISO 8601 complete date formatted as "%tY-%tm-%td".
    */
  def F: String = p"$Y-$m-$d"
  /**
    * Date and time formatted as "%ta %tb %td %tT %tZ %tY", e.g. "Sun Jul 20 16:17:00 EDT 1969".
    */
  def c: String = p"$a $b $d $T $z $Y"
}

object CrossDate {
  private val cache = new ThreadLocal[CrossDate]

  def apply(l: Long): CrossDate = Option(cache.get()) match {
    case Some(d) if d.milliseconds == l => d
    case _ => {
      val d = Platform.createDate(l)
      cache.set(d)
      d
    }
  }

  object Week {
    val Long: Vector[String] = Vector(
      "Sunday",
      "Monday",
      "Tuesday",
      "Wednesday",
      "Thursday",
      "Friday",
      "Saturday"
    )
    val Short: Vector[String] = Vector(
      "Sun",
      "Mon",
      "Tues",
      "Wed",
      "Thurs",
      "Fri",
      "Sat"
    )
  }
  object Month {
    val Long: Vector[String] = Vector(
      "January",
      "February",
      "March",
      "April",
      "May",
      "June",
      "July",
      "August",
      "September",
      "October",
      "November",
      "December"
    )
    val Short: Vector[String] = Vector(
      "Jan",
      "Feb",
      "Mar",
      "Apr",
      "May",
      "Jun",
      "Jul",
      "Aug",
      "Sep",
      "Oct",
      "Nov",
      "Dec"
    )
  }
}