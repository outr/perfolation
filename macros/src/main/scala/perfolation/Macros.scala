package perfolation

import scala.StringContext.InvalidEscapeException
import scala.annotation.compileTimeOnly
import scala.language.experimental.macros
import scala.reflect.macros.blackbox

@compileTimeOnly("Enable macros to expand")
object Macros {
  def p(c: blackbox.Context)(args: c.Expr[Any]*): c.Expr[String] = px(c)(args: _*)(scala.StringContext.processEscapes)
  def raw(c: blackbox.Context)(args: c.Expr[Any]*): c.Expr[String] = px(c)(args: _*)(identity)

  private[this] def px(c: blackbox.Context)(args: c.Expr[Any]*)(process: String => String): c.Expr[String] = {
    import c.universe._

    val constants = (c.prefix.tree match {
      case Apply(_, List(Apply(_, literals))) => literals
    }).map { case Literal(Constant(s: String)) =>
      try process(s) catch {
        case ex: InvalidEscapeException => c.abort(c.enclosingPosition, ex.getMessage)
      }
    }

    if (args.isEmpty) c.Expr(Literal(Constant(constants.mkString)))
    else {
      val (valDeclarations, values) = args.map { arg =>
        arg.tree match {
          case tree @ Literal(Constant(_)) =>
            (EmptyTree, if (tree.tpe <:< definitions.NullTpe) q"(null: String)" else tree)
          case tree =>
            val name = TermName(c.freshName())
            val tpe = if (tree.tpe <:< definitions.NullTpe) typeOf[String] else tree.tpe
            (q"val $name: $tpe = $arg", Ident(name))
        }
      }.unzip

      val stringBuilderWithAppends = constants.zipAll(values, "", null)
        .foldLeft(q"perfolation.stringBuilder()") { case (sb, (s, v)) =>
          val len = s.length
          if (len == 0) {
            if (v == null) sb
            else q"$sb.append($v)"
          } else if (len == 1) {
            if (v == null) q"$sb.append(${s.charAt(0)})"
            else q"$sb.append(${s.charAt(0)}).append($v)"
          } else {
            if (v == null) q"$sb.append($s)"
            else q"$sb.append($s).append($v)"
          }
        }

      c.Expr(c.typecheck(q"..$valDeclarations; $stringBuilderWithAppends.toString"))
    }
  }
}
