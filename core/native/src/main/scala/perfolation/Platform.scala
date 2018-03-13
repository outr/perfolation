package perfolation

import java.util.Calendar

object Platform {
  def createDate(l: Long): CrossDate = {
    val c = Calendar.getInstance()
    c.setTimeInMillis(l)
    new JVMCrossDate(l, c)
  }
}