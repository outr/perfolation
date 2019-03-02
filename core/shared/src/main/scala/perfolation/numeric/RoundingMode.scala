package perfolation.numeric

trait RoundingMode {
  def roundUp(i: Int): Boolean
}

object RoundingMode {
  case object HalfUp extends RoundingMode {
    override def roundUp(i: Int): Boolean = i >= 5
  }
  case object HalfDown extends RoundingMode {
    override def roundUp(i: Int): Boolean = i > 5
  }
  case object Ceil extends RoundingMode {
    override def roundUp(i: Int): Boolean = i > 0
  }
  case object Floor extends RoundingMode {
    override def roundUp(i: Int): Boolean = false
  }
}