package perfolation

import scala.scalajs.js
import scala.scalajs.js.Date

class JavaScriptCrossDate(override val milliseconds: Long, d: Date) extends CrossDate {
  override def hour24: Int = d.getHours()
  override def minuteOfHour: Int = d.getMinutes()
  override def secondOfMinute: Int = d.getSeconds()
  override def milliOfSecond: Int = d.getMilliseconds()
  override def isAM: Boolean = hour24 < 12
  override def timeZoneOffsetMillis: Int = d.getTimezoneOffset() * 60 * 1000
  override def year: Int = d.getFullYear()
  override def month: Int = d.getMonth()
  override def dayOfWeek: Int = d.getDay()
  override def dayOfMonth: Int = d.getDate()
  override def dayOfYear: Int = {
    val diff = d.getTime() - Platform.startOfYear.getTime()
    val oneDay = 1000.0 * 60.0 * 60.0 * 24.0
    math.floor(diff / oneDay).toInt
  }
  override def timeZone: String = {
    val s = d.asInstanceOf[js.Dynamic].toLocaleString("en", js.Dynamic.literal("timeZoneName" -> "short")).asInstanceOf[String]
    s.split(' ').last
  }
}
