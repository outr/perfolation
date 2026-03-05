package perfolation

import java.time.ZonedDateTime
import java.time.format.TextStyle
import java.util.Locale

class ZonedCrossDate(override val milliseconds: Long, zdt: ZonedDateTime) extends CrossDate {
  override def milliOfSecond: Int = (milliseconds % 1000L).toInt
  override def secondOfMinute: Int = zdt.getSecond
  override def minuteOfHour: Int = zdt.getMinute
  override def hour24: Int = zdt.getHour
  override def isAM: Boolean = hour24 < 12
  override def timeZoneOffsetMillis: Int = zdt.getOffset.getTotalSeconds * 1000
  override def timeZone: String = zdt.getZone.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
  override def year: Int = zdt.getYear
  override def month: Int = zdt.getMonthValue - 1
  override def dayOfWeek: Int = zdt.getDayOfWeek.getValue % 7 + 1
  override def dayOfMonth: Int = zdt.getDayOfMonth
  override def dayOfYear: Int = zdt.getDayOfYear - 1
}
