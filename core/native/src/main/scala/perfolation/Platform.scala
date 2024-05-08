package perfolation

import scala.scalanative.unsafe._
import scala.scalanative.posix.time.time_t

object Platform {
  def createDate(l: Long): CrossDate = {
    val bmsptr = stackalloc[time_t]()
    !bmsptr = (l / 1000L).toCSSize

    new NativeCrossDate(l, bmsptr)
  }
}