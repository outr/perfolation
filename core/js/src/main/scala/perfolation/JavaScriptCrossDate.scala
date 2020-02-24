package perfolation

import scala.scalajs.js
import scala.scalajs.js.Date

class JavaScriptCrossDate(val date: Date) extends AnyVal with CrossDate {
  override def milliseconds: Long = date.getTime().toLong
  override def hour24: Int = date.getHours().toInt
  override def minuteOfHour: Int = date.getMinutes().toInt
  override def secondOfMinute: Int = date.getSeconds().toInt
  override def milliOfSecond: Int = date.getMilliseconds().toInt
  override def isAM: Boolean = hour24 < 12
  override def timeZoneOffsetMillis: Int = (-date.getTimezoneOffset() * 60 * 1000).toInt
  override def year: Int = date.getFullYear().toInt
  override def month: Int = date.getMonth().toInt
  override def dayOfWeek: Int = date.getDay().toInt + 1
  override def dayOfMonth: Int = date.getDate().toInt
  override def dayOfYear: Int = {
    val beginning = new Date(date.getFullYear().toInt, 0, 0)
    val diff = date.getTime() - beginning.getTime()
    val oneDay = 1000.0 * 60.0 * 60.0 * 24.0
    math.floor(diff / oneDay).toInt - 1
  }
  override def timeZone: String = {
    val s = date.asInstanceOf[js.Dynamic].toLocaleString("en", js.Dynamic.literal("timeZoneName" -> "short")).asInstanceOf[String]
    s.split(' ').last
  }
}
