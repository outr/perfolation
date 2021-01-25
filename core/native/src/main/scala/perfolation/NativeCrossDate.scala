package perfolation

import scala.scalanative.posix.timeOps.tmOps
import scala.scalanative.posix.time
import scala.scalanative.posix.time.tm
import scala.scalanative.unsafe.{Ptr, fromCString}

class NativeCrossDate(override val milliseconds: Long, dms: Ptr[time.time_t]) extends CrossDate {
  time.tzset()
  // tmOps object that allows us to access localtime data
  val date = new tmOps(time.localtime(dms).asInstanceOf[Ptr[tm]])

  private val dst: Int = if (time.daylight() == 1) {
    3600000
  } else {
    0
  }

  override def hour24: Int = date.tm_hour
  override def minuteOfHour: Int = date.tm_min
  override def secondOfMinute: Int = date.tm_sec
  override def milliOfSecond: Int = (milliseconds % 1000L).asInstanceOf[Int]
  override def isAM: Boolean = hour24 < 12
  override def timeZoneOffsetMillis: Int = dst - (1000 * time.timezone().asInstanceOf[Int])
  override def year: Int = date.tm_year + 1900
  override def month: Int = date.tm_mon
  override def dayOfWeek: Int = date.tm_wday + 1
  override def dayOfMonth: Int = date.tm_mday
  override def dayOfYear: Int = date.tm_yday
  override def timeZone: String = {
    val ptr = if (time.daylight() == 0) {
      time.tzname()._1
    } else {
      time.tzname()._2
    }
    fromCString(ptr)
  }
}