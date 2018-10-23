package perfolation

import scala.scalanative.posix.timeOps.tmOps
import scala.scalanative.native
import scala.scalanative.native.Ptr
import scala.scalanative.posix.time.{gmtime, localtime, time_t}


class NativeCrossDate(override val milliseconds: Long, dms: Ptr[time_t]) extends CrossDate {

  //
  val date = new tmOps(localtime(dms))

  val bmsptr: Ptr[time_t] = native.stackalloc[time_t]
  !bmsptr = milliseconds / 1000L
  val g: tmOps = new tmOps(gmtime(bmsptr))
  val gm: Long = 31540000000L * g.tm_year + 86400000L * g.tm_yday + 3600000L * g.tm_hour + 60000L * g.tm_min + 1000 * g.tm_sec
  val dm: Long = 31540000000L * date.tm_year + 86400000L * date.tm_yday + 3600000L * date.tm_hour + 60000L * date.tm_min + 1000 * date.tm_sec

  override def hour24: Int = date.tm_hour
  override def minuteOfHour: Int = date.tm_min
  override def secondOfMinute: Int = date.tm_sec
  override def milliOfSecond: Int = (milliseconds % 1000L).asInstanceOf[Int]
  override def isAM: Boolean = hour24 < 12
  override def timeZoneOffsetMillis: Int = (gm - dm).asInstanceOf[Int]
  override def year: Int = date.tm_year + 1900
  override def month: Int = date.tm_mon
  override def dayOfWeek: Int = date.tm_wday + 1
  override def dayOfMonth: Int = date.tm_mday
  override def dayOfYear: Int = date.tm_yday
  override def timeZone: String = "asdf"
}