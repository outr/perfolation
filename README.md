# perfolation
[![Build Status](https://travis-ci.org/outr/perfolation.svg?branch=master)](https://travis-ci.org/outr/perfolation)
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/outr/perfolation)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.outr/perfolation_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.outr/perfolation_2.12)
[![Latest version](https://index.scala-lang.org/outr/perfolation/perfolation/latest.svg)](https://index.scala-lang.org/outr/perfolation)

Originally designed for interpolation performance improvements, but as Scala's built-in interpolation has improved, this
has become redundant. However, formatted interpolation is still both slow and painful to work with. To that end, this
library has changed to providing convenient features for formatting dates and numbers.

## Setup

Perfolation supports Scala on JVM, JS, and Native with support for 2.11, 2.12, 2.13, and 3

Load the core dependency with SBT:
```sbt
libraryDependencies += "com.outr" %% "perfolation" % "1.2.5"
```

Or the `unit` dependency for size conversions with SBT:
```sbt
libraryDependencies += "com.outr" %% "perfolation-unit" % "1.2.5"
```

## Main Features

### Numeric Formatting

Numeric implicits are supported for `Int`, `Long`, `Double`, and `BigDecimal` exposing a simple method `f`:

```scala
def f(i: Int,               // Minimum integer digits. Defaults to 1
      f: Int = 0,           // Minimum fraction digits. Defaults to 0 for Int/Long and 2 for Double/BigDecimal 
      maxI: Int,            // Maximum integer digits. Defaults to -1 for no maximum
      maxF: Int,            // Maximum fraction digits. Defaults to -1 to use the same as `f`
      g: Grouping,          // Grouping mode (Defaults to Grouping.None)
      rm: RoundingMode      // Rounding mode (Defaults to RoundingMode.HalfUp)
     ): String
```

Some simple examples:
```scala
import perfolation._

4.f(i = 2)                            // 04
40.f(f = 2)                           // 40.00
400.0.f()                             // 400.00
4000.0.f(f = 3, g = Grouping.US)      // 4,000.000
```

Most of this follows a similar concept to `f` interpolation, but with a type-safe mechanism. Most commonly, you are
likely to find this useful in interpolations like:
```scala
println(s"The value is: ${value.f(g = Grouping.US)}")
```

### Date Formatting

Perfolation provides a convenient `CrossDate` to make working with dates much easier between the JVM, JS, and Native.
Similar to numeric formatting, date implicits are supported for `Int`, `Long`, `Double`, and `BigDecimal` although this
is most commonly just used on `Long` for timestamps. Perfolation exposes a simple method `t`:

```scala
def t: CrossDate
```

Some simple examples:
```scala
import perfolation._

val date = System.currentTimeMillis()
date.t.milliseconds                 // Milliseconds on date
date.t.hour24                       // Hour in 24-hour time
date.t.dayOfWeek                    // Numeric day of the week
date.t.A                            // Full named day of the week (ex. "Tuesday")
```

See the docs for `CrossDate` for a complete reference.

### Unit Formatting

Unit formatting requires the `perfolation-unit` module, but provides conversion between byte-based sizes. For example:
```scala
import perfolation.unit._

Information.useBinary()       // Configure Information to use binary conversions
5.kb.bytes                    // 5120L
5.gb.bytes                    // 5368709120L

Information.useDecimal()      // Configure Information to use decimal conversions
5.kb.bytes                    // 5000L
5.gb.bytes                    // 5000000000L

(5 * 1000 * 1000).b.format()  // "5.00 mb"
```

The `format` method has a similar signature to that of `f` in numeric formatting:
```scala
def format(i: Int = 1,
           f: Int = 2,
           maxI: Int = -1,
           maxF: Int = -1,
           g: Grouping = Grouping.None,
           rm: RoundingMode = RoundingMode.HalfUp,
           showUnit: ShowUnit = ShowUnit.Abbreviation): String
```