package perfolation

import scala.scalanative.posix.timeOps.tmOps
import scala.scalanative.native.{Ptr, fromCString}
import scala.scalanative.posix.time._


class NativeCrossDate(override val milliseconds: Long, dms: Ptr[time_t]) extends CrossDate {

  tzset
  // tmOps object that allows us to access localtime data
  val date = new tmOps(localtime(dms))

  override def hour24: Int = date.tm_hour
  override def minuteOfHour: Int = date.tm_min
  override def secondOfMinute: Int = date.tm_sec
  override def milliOfSecond: Int = (milliseconds % 1000L).asInstanceOf[Int]
  override def isAM: Boolean = hour24 < 12
  override def timeZoneOffsetMillis: Int = 1000 * timezone().asInstanceOf[Int]
  override def year: Int = date.tm_year + 1900
  override def month: Int = date.tm_mon
  override def dayOfWeek: Int = date.tm_wday + 1
  override def dayOfMonth: Int = date.tm_mday
  override def dayOfYear: Int = date.tm_yday
  override def timeZone: String = fromCString(!tzname._1)
}