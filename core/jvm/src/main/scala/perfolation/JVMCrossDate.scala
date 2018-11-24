package perfolation

import java.util.{Calendar, TimeZone}

class JVMCrossDate(val calendar: java.util.Calendar) extends AnyVal with CrossDate {
  override def milliseconds: Long = calendar.getTimeInMillis
  override def hour24: Int = calendar.get(Calendar.HOUR_OF_DAY)
  override def minuteOfHour: Int = calendar.get(Calendar.MINUTE)
  override def secondOfMinute: Int = calendar.get(Calendar.SECOND)
  override def milliOfSecond: Int = calendar.get(Calendar.MILLISECOND)
  override def isAM: Boolean = calendar.get(Calendar.AM_PM) == 0
  override def timeZoneOffsetMillis: Int = -(calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET))
  override def year: Int = calendar.get(Calendar.YEAR)
  override def month: Int = calendar.get(Calendar.MONTH)
  override def dayOfWeek: Int = calendar.get(Calendar.DAY_OF_WEEK)
  override def dayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)
  override def dayOfYear: Int = calendar.get(Calendar.DAY_OF_YEAR) - 1
  override def timeZone: String = calendar.getTimeZone.getDisplayName(false, TimeZone.SHORT)
}