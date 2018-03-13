import scala.language.experimental.macros
import scala.language.implicitConversions

package object perfolation {
  implicit def long2Implicits(l: Long): LongImplicits = new LongImplicits(l)
  implicit def double2Implicits(d: Double): DoubleImplicits = new DoubleImplicits(d)

  implicit class Perfolation(val sc: StringContext) extends AnyVal {
    def p(args: Any*): String = macro Macros.p
  }
}