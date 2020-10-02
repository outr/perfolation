import scala.language.experimental.macros
import scala.language.implicitConversions
import scala.util.Try

package object perfolation {
  implicit def int2Implicits(i: Int): IntImplicits = new IntImplicits(i)
  implicit def long2Implicits(l: Long): LongImplicits = new LongImplicits(l)
  implicit def double2Implicits(d: Double): DoubleImplicits = new DoubleImplicits(d)
  implicit def bigDecimal2Implicits(d: BigDecimal): BigDecimalImplicits = new BigDecimalImplicits(d)
}