package spec

import org.scalatest.matchers.should.Matchers
import perfolation.unit._
import org.scalatest.wordspec.AnyWordSpec

class InformationSpec extends AnyWordSpec with Matchers {
  "Information" when {
    "using binary" should {
      "validate bytes" in {
        Information.useBinary()
        5.b.bytes should be(5L)
      }
      "validate kilobytes" in {
        5.kb.bytes should be(5120L)
      }
      "validate megabytes" in {
        5.mb.bytes should be(5242880L)
      }
      "validate gigabytes" in {
        5.gb.bytes should be(5368709120L)
      }
      "validate terabytes" in {
        5.tb.bytes should be(5497558138880L)
      }
      "validate petabytes" in {
        5.pb.bytes should be(5629499534213120L)
      }
      "validate exabytes" in {
        5.eb.bytes should be(5764607523034234880L)
      }
      "validate zettabytes" in {
        5.zb.bytes should be(BigInt("5902958103587056517120"))
      }
      "validate yottabytes" in {
        5.yb.bytes should be(BigInt("6044629098073145873530880"))
      }
      "format kilobytes" in {
        5.kb.toString should be("5.00 KiB")
      }
    }
    "using decimal" should {
      "validate bytes" in {
        Information.useDecimal()
        5.b.bytes should be(5L)
      }
      "validate kilobytes" in {
        5.kb.bytes should be(5000L)
      }
      "validate megabytes" in {
        5.mb.bytes should be(5000000L)
      }
      "validate gigabytes" in {
        5.gb.bytes should be(5000000000L)
      }
      "validate terabytes" in {
        5.tb.bytes should be(5000000000000L)
      }
      "validate petabytes" in {
        5.pb.bytes should be(5000000000000000L)
      }
      "validate exabytes" in {
        5.eb.bytes should be(5000000000000000000L)
      }
      "validate zettabytes" in {
        5.zb.bytes should be(BigInt("5000000000000000000000"))
      }
      "validate yottabytes" in {
        5.yb.bytes should be(BigInt("5000000000000000000000000"))
      }
      "format kilobytes" in {
        5.kb.toString should be("5.00 kb")
      }
    }
  }
}