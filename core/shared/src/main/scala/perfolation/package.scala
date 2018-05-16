import scala.language.experimental.macros
import scala.language.implicitConversions
import scala.util.Try

package object perfolation {
  implicit def long2Implicits(l: Long): LongImplicits = new LongImplicits(l)
  implicit def double2Implicits(d: Double): DoubleImplicits = new DoubleImplicits(d)

  implicit class Perfolation(val sc: StringContext) extends AnyVal {
    def p(args: Any*): String = macro Macros.p
    def raw(args: Any*): String = macro Macros.raw
  }

  def stringBuilder(): java.lang.StringBuilder = pool.get()

  private[this] final val size = Try(sys.props.getOrElse("perfolation.buffer.size", "").toInt).getOrElse(16384)

  private[this] final val pool = new ThreadLocal[java.lang.StringBuilder] {
    override def initialValue(): java.lang.StringBuilder = new java.lang.StringBuilder(size)

    override def get(): java.lang.StringBuilder = {
      var sb = super.get()
      if (sb.capacity > size) {
        sb = initialValue()
        set(sb)
      } else sb.setLength(0)
      sb
    }
  }
}