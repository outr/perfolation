package perfolation

import scala.scalanative.posix.timeOps.tmOps
import scala.math._
import scala.scalanative.native
import scala.scalanative.native.{CLong, Ptr}
import scala.scalanative.posix.time.{gmtime, localtime, mktime, time_t, tm}


class NativeCrossDate(override val milliseconds: Long, d: tmOps) extends CrossDate {

//  val bmsptr: Ptr[time_t] = native.stackalloc[time_t]
//  !bmsptr = milliseconds
//  val g: tmOps = new tmOps(gmtime(bmsptr).asInstanceOf[Ptr[tm]])

  override def hour24: Int = d.tm_hour
  override def minuteOfHour: Int = d.tm_min
  override def secondOfMinute: Int = d.tm_sec
  override def milliOfSecond: Int = (milliseconds % 1000L).asInstanceOf[Int]
  override def isAM: Boolean = hour24 < 12
  override def timeZoneOffsetMillis: Int = 14400000 //abs(mktime(gmtime(bmsptr).asInstanceOf[Ptr[tm]]).asInstanceOf[CLong] - mktime(localtime(bmsptr).asInstanceOf[Ptr[tm]]).asInstanceOf[CLong]).asInstanceOf[Int]
  override def year: Int = d.tm_year + 1900
  override def month: Int = d.tm_mon
  override def dayOfWeek: Int = d.tm_wday + 1
  override def dayOfMonth: Int = d.tm_mday
  override def dayOfYear: Int = d.tm_yday
  override def timeZone: String = "asdf"
}