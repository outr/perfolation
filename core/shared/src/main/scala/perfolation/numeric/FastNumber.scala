package perfolation.numeric

object FastNumber {
  private val threadLocal = new ThreadLocal[FastNumber] {
    override def initialValue(): FastNumber = new FastNumber
  }

  def apply[Return](value: BigDecimal)(f: FastNumber => Return): Return = {
    val fn = threadLocal.get()
    fn.set(value)
    f(fn)
  }
}

class FastNumber {
  val integer: Array[Char] = new Array[Char](255)
  val fraction: Array[Char] = new Array[Char](255)

  def set(value: BigDecimal): Unit = {
    clear()
    val s = value.toString
    val decimal = s.indexOf('.')

    if (decimal != -1) {
      s.substring(0, decimal).reverse.zipWithIndex.foreach {
        case (char, index) => integer(integer.length - (index + 1)) = char
      }
      s.substring(decimal + 1).zipWithIndex.foreach {
        case (char, index) => fraction(index) = char
      }
    } else {
      s.reverse.zipWithIndex.foreach {
        case (char, index) => integer(integer.length - (index + 1)) = char
      }
    }
  }

  def setMinimumIntegerDigits(min: Int): Unit = {
    (0 until min).foreach { i =>
      val index = integer.length - (i + 1)
      val char = integer(index)
      if (char == ' ') {
        integer(index) = '0'
      }
    }
  }

  def setMaximumIntegerDigits(max: Int): Unit = {
    (0 until integer.length - max).foreach { index =>
      val char = integer(index)
      if (char != ' ') {
        integer(index) = ' '
      }
    }
  }

  def setMinimumFractionDigits(min: Int): Unit = {
    (0 until min).foreach { index =>
      val char = fraction(index)
      if (char == ' ') {
        fraction(index) = '0'
      }
    }
  }

  def setMaximumFractionDigits(max: Int, roundingMode: RoundingMode): Unit = {
    if (fraction(max) != ' ') {   // Exceeded max
      val last = fraction(max).asDigit
      if (roundingMode.roundUp(last)) {
        incrementFraction(max - 1)
      }
      (max until fraction.length).takeWhile(fraction(_) != ' ').foreach { index =>
        fraction(index) = ' '
      }
    }
  }

  def group(grouping: Grouping, offset: Int = 0): Unit = if (grouping.every > 0) {
    val position = integer.length - (offset + grouping.every + 1)
    if (integer(position) != ' ') {
      insertInteger(grouping.char, position)
      group(grouping, offset + grouping.every + 1)
    }
  }

  def insertInteger(char: Char, position: Int): Unit = {
    (0 to position).foreach { index =>
      integer(index) = integer(index + 1)
    }
    integer(position) = char
  }

  private def incrementFraction(index: Int): Unit = {
    val v = fraction(index).asDigit
    if (v < 9) {
      val char: Char = Character.forDigit(v + 1, 10)
      fraction(index) = char
    } else {
      fraction(index) = 0
      incrementFraction(index - 1)
    }
  }

  def clear(): Unit = integer.indices.foreach { index =>
    integer(index) = ' '
    fraction(index) = ' '
  }

  override def toString: String = {
    val b = new StringBuilder
    var write = false
    integer.indices.foreach { index =>
      val char = integer(index)
      if (write || char != ' ') {
        write = true

        b.append(char)
      }
    }
    write = false
    fraction.takeWhile(_ != ' ').foreach { char =>
      if (!write) {
        write = true
        b.append('.')
      }
      b.append(char)
    }
    b.toString()
  }
}