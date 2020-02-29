package perfolation

import scala.language.implicitConversions

package object unit {
  implicit def long2Information(bytes: Long): Information = new Information(bytes)
  implicit def double2Information(bytes: Double): Information = new Information(bytes.toLong)
  implicit def bigInt2Information(bytes: BigInt): Information = new Information(bytes)
  implicit def bigDecimal2Information(bytes: BigDecimal): Information = new Information(bytes.toBigInt)

  import Information._

  implicit class BigIntConversion(val value: BigInt) extends AnyVal {
    def b: Information = value * Byte
    def byte: Information = value * Byte
    def bytes: Information = value * Byte

    def kb: Information = value * Kilobyte
    def kilobyte: Information = value * Kilobyte
    def kilobytes: Information = value * Kilobyte

    def mb: Information = value * Megabyte
    def megabyte: Information = value * Megabyte
    def megabytes: Information = value * Megabyte

    def gb: Information = value * Gigabyte
    def gigabyte: Information = value * Gigabyte
    def gigabytes: Information = value * Gigabyte

    def tb: Information = value * Terabyte
    def terabyte: Information = value * Terabyte
    def terabytes: Information = value * Terabyte

    def pb: Information = value * Petabyte
    def petabyte: Information = value * Petabyte
    def petabytes: Information = value * Petabyte

    def eb: Information = value * Exabyte
    def exabyte: Information = value * Exabyte
    def exabytes: Information = value * Exabyte

    def zb: Information = value * Zettabyte
    def zettabyte: Information = value * Zettabyte
    def zettabytes: Information = value * Zettabyte

    def yb: Information = value * Yottabyte
    def yottabyte: Information = value * Yottabyte
    def yottabytes: Information = value * Yottabyte
  }

  implicit class LongConversion(val value: Long) extends AnyVal {
    def b: Information = value * Byte
    def byte: Information = value * Byte
    def bytes: Information = value * Byte

    def kb: Information = value * Kilobyte
    def kilobyte: Information = value * Kilobyte
    def kilobytes: Information = value * Kilobyte

    def mb: Information = value * Megabyte
    def megabyte: Information = value * Megabyte
    def megabytes: Information = value * Megabyte

    def gb: Information = value * Gigabyte
    def gigabyte: Information = value * Gigabyte
    def gigabytes: Information = value * Gigabyte

    def tb: Information = value * Terabyte
    def terabyte: Information = value * Terabyte
    def terabytes: Information = value * Terabyte

    def pb: Information = value * Petabyte
    def petabyte: Information = value * Petabyte
    def petabytes: Information = value * Petabyte

    def eb: Information = value * Exabyte
    def exabyte: Information = value * Exabyte
    def exabytes: Information = value * Exabyte

    def zb: Information = value * Zettabyte
    def zettabyte: Information = value * Zettabyte
    def zettabytes: Information = value * Zettabyte

    def yb: Information = value * Yottabyte
    def yottabyte: Information = value * Yottabyte
    def yottabytes: Information = value * Yottabyte
  }

  implicit class DoubleConversion(val value: Double) extends AnyVal {
    def b: Information = value * Byte
    def byte: Information = value * Byte
    def bytes: Information = value * Byte

    def kb: Information = value * Kilobyte
    def kilobyte: Information = value * Kilobyte
    def kilobytes: Information = value * Kilobyte

    def mb: Information = value * Megabyte
    def megabyte: Information = value * Megabyte
    def megabytes: Information = value * Megabyte

    def gb: Information = value * Gigabyte
    def gigabyte: Information = value * Gigabyte
    def gigabytes: Information = value * Gigabyte

    def tb: Information = value * Terabyte
    def terabyte: Information = value * Terabyte
    def terabytes: Information = value * Terabyte

    def pb: Information = value * Petabyte
    def petabyte: Information = value * Petabyte
    def petabytes: Information = value * Petabyte

    def eb: Information = value * Exabyte
    def exabyte: Information = value * Exabyte
    def exabytes: Information = value * Exabyte

    def zb: Information = value * Zettabyte
    def zettabyte: Information = value * Zettabyte
    def zettabytes: Information = value * Zettabyte

    def yb: Information = value * Yottabyte
    def yottabyte: Information = value * Yottabyte
    def yottabytes: Information = value * Yottabyte
  }
}