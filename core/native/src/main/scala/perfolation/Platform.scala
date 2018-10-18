package perfolation

import scala.scalanative.native
import scala.scalanative.native.{CLong, Ptr}
import scala.scalanative.posix.time._
import scala.scalanative.posix.timeOps.tmOps


object Platform {
  def createDate(l: Long): CrossDate = {

//    val msptr = native.stackalloc[CLong]
//    !msptr = l

    val bmsptr = native.stackalloc[time_t]
    !bmsptr = l / 1000L
    val d = new tmOps(localtime(bmsptr).asInstanceOf[Ptr[tm]])

    new NativeCrossDate(l, d)
  }
}