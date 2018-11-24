package perfolation

import scala.scalajs.js
import scala.scalajs.js.Date

class JavaScriptCrossDate(val date: Date) extends AnyVal with CrossDate {
  override def milliseconds: Long = date.getTime().toLong
  override def hour24: Int = date.getHours()
  override def minuteOfHour: Int = date.getMinutes()
  override def secondOfMinute: Int = date.getSeconds()
  override def milliOfSecond: Int = date.getMilliseconds()
  override def isAM: Boolean = hour24 < 12
  override def timeZoneOffsetMillis: Int = date.getTimezoneOffset() * 60 * 1000
  override def year: Int = date.getFullYear()
  override def month: Int = date.getMonth()
  override def dayOfWeek: Int = date.getDay() + 1
  override def dayOfMonth: Int = date.getDate()
  override def dayOfYear: Int = {
    val diff = date.getTime() - Platform.startOfYear.getTime()
    val oneDay = 1000.0 * 60.0 * 60.0 * 24.0
    math.floor(diff / oneDay).toInt - 1
  }
  override def timeZone: String = {
    val s = date.asInstanceOf[js.Dynamic].toLocaleString("en", js.Dynamic.literal("timeZoneName" -> "short")).asInstanceOf[String]
    s.split(' ').last
  }
}
