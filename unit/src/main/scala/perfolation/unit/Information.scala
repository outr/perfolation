package perfolation.unit

import perfolation._
import perfolation.numeric.{Grouping, RoundingMode}

class Information(val bytes: BigInt) extends AnyVal {
  def *(that: Information): Information = new Information(this.bytes * that.bytes)

  def unit: Information = Information.largestToSmallest.find(u => bytes - u.bytes > 0L).get

  def format(i: Int = 1,
             f: Int = 2,
             maxI: Int = -1,
             maxF: Int = -1,
             g: Grouping = Grouping.None,
             rm: RoundingMode = RoundingMode.HalfUp,
             showUnit: ShowUnit = ShowUnit.Abbreviation): String = {
    val u = unit
    val d = Information.map(u)
    val value = bytes.toDouble / u.bytes.toDouble
    val s = value.f(i, f, maxI, maxF, g, rm)
    showUnit match {
      case ShowUnit.None => s
      case ShowUnit.Abbreviation => p"$s ${d.abbreviation}"
      case ShowUnit.Full => p"$s ${d.full}"
    }
  }

  override def toString: String = format()
}

object Information {
  private var usingBinary = true

  private def largestToSmallest: List[Information] = if (usingBinary) binary.largestToSmallest else decimal.largestToSmallest

  lazy val Byte: Information = new Information(1L)
  def Kilobyte: Information = if (usingBinary) binary.Kilobyte else decimal.Kilobyte
  def Megabyte: Information = if (usingBinary) binary.Megabyte else decimal.Megabyte
  def Gigabyte: Information = if (usingBinary) binary.Gigabyte else decimal.Gigabyte
  def Terabyte: Information = if (usingBinary) binary.Terabyte else decimal.Terabyte
  def Petabyte: Information = if (usingBinary) binary.Petabyte else decimal.Petabyte
  def Exabyte: Information = if (usingBinary) binary.Exabyte else decimal.Exabyte
  def Zettabyte: Information = if (usingBinary) binary.Zettabyte else decimal.Zettabyte
  def Yottabyte: Information = if (usingBinary) binary.Yottabyte else decimal.Yottabyte

  private def map: Map[Information, InformationDetail] = if (usingBinary) binary.map else decimal.map

  object binary {
    private val base: Long = 1024L

    def Byte: Information = Information.Byte
    lazy val Kilobyte: Information = new Information(base)
    lazy val Megabyte: Information = Kilobyte * Kilobyte
    lazy val Gigabyte: Information = Megabyte * Kilobyte
    lazy val Terabyte: Information = Gigabyte * Kilobyte
    lazy val Petabyte: Information = Terabyte * Kilobyte
    lazy val Exabyte: Information = Petabyte * Kilobyte
    lazy val Zettabyte: Information = Exabyte * Kilobyte
    lazy val Yottabyte: Information = Zettabyte * Kilobyte

    lazy val all: List[Information] = List(Byte, Kilobyte, Megabyte, Gigabyte, Terabyte, Petabyte, Exabyte, Zettabyte, Yottabyte)
    private[unit] lazy val map: Map[Information, InformationDetail] = Map(
      Byte -> InformationDetail("b", "byte"),
      Kilobyte -> InformationDetail("KiB", "kibibyte"),
      Megabyte -> InformationDetail("MiB", "mebibyte"),
      Gigabyte -> InformationDetail("GiB", "gibibyte"),
      Terabyte -> InformationDetail("TiB", "tebibyte"),
      Petabyte -> InformationDetail("PiB", "pebibyte"),
      Exabyte -> InformationDetail("EiB", "exbibyte"),
      Zettabyte -> InformationDetail("ZiB", "zebibyte"),
      Yottabyte -> InformationDetail("YiB", "yobibyte")
    )
    private[unit] lazy val largestToSmallest = all.reverse
  }
  object decimal {
    private val base: Long = 1000L

    def Byte: Information = Information.Byte
    lazy val Kilobyte: Information = new Information(base)
    lazy val Megabyte: Information = Kilobyte * Kilobyte
    lazy val Gigabyte: Information = Megabyte * Kilobyte
    lazy val Terabyte: Information = Gigabyte * Kilobyte
    lazy val Petabyte: Information = Terabyte * Kilobyte
    lazy val Exabyte: Information = Petabyte * Kilobyte
    lazy val Zettabyte: Information = Exabyte * Kilobyte
    lazy val Yottabyte: Information = Zettabyte * Kilobyte

    lazy val all: List[Information] = List(Byte, Kilobyte, Megabyte, Gigabyte, Terabyte, Petabyte, Exabyte, Zettabyte, Yottabyte)
    private[unit] lazy val map: Map[Information, InformationDetail] = Map(
      Byte -> InformationDetail("b", "byte"),
      Kilobyte -> InformationDetail("kb", "kilobyte"),
      Megabyte -> InformationDetail("mb", "megabyte"),
      Gigabyte -> InformationDetail("gb", "gigabyte"),
      Terabyte -> InformationDetail("tb", "terabyte"),
      Petabyte -> InformationDetail("pb", "petabyte"),
      Exabyte -> InformationDetail("eb", "exabyte"),
      Zettabyte -> InformationDetail("zb", "zettabyte"),
      Yottabyte -> InformationDetail("yb", "yottabyte")
    )
    private[unit] lazy val largestToSmallest = all.reverse
  }

  def isBinary: Boolean = usingBinary
  def useBase2(): Unit = usingBinary = true
  def useBase1024(): Unit = useBase2()
  def useBinary(): Unit = useBase2()

  def isDecimal: Boolean = !usingBinary
  def useBase10(): Unit = usingBinary = false
  def useBase1000(): Unit = useBase10()
  def useDecimal(): Unit = useBase10()
}