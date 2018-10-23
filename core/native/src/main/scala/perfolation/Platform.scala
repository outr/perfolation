package perfolation

import scala.scalanative.native
import scala.scalanative.posix.time._


object Platform {
  def createDate(l: Long): CrossDate = {

    val bmsptr = native.stackalloc[time_t]
    !bmsptr = l / 1000L

    new NativeCrossDate(l, bmsptr)
  }
}