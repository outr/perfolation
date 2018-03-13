# perfolation
[![Build Status](https://travis-ci.org/outr/perfolation.svg?branch=master)](https://travis-ci.org/outr/perfolation)
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/outr/perfolation)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.outr/perfolation_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.outr/perfolation_2.12)
[![Latest version](https://index.scala-lang.org/outr/perfolation/perfolation/latest.svg)](https://index.scala-lang.org/outr/perfolation)

Performance focused interpolation

Based on the original work of Dmitry Komanov (https://medium.com/@dkomanov/scala-string-interpolation-performance-21dc85e83afd).

## What is it?

As shown in the article by Dmitry Komanov, Scala's `s""` and `f""` interpolation are incredibly slow. This is an effort
make String interpolation as fast as it should be.

## Main Features

### String interpolation

Original:
```scala
s"Interpolation of $string"
```

Better:
```scala
p"Interpolation of $string"
```

### Type-safe alternatives to String format

Original:
```scala
f"Time: $timeInMillis%tT, Date: $timeInMillis%tD"
```

Better:
```scala
p"Time: ${timeInMillis.t.T}, Date: ${timeInMillis.t.D}"
```

## Getting Started

Support for Scala (2.11, 2.12), Scala.js (2.11, 2.12), and ScalaNative (2.11)

SBT Dependencies:

```
libraryDependencies += "com.outr" %%% "perfolation" % "1.0.0"
```

Usage:

Simply import `perfolation._` and start using:

```scala
import perfolation._

val greeting = "Hello, World!"
val time = System.currentTimeMillis()

println(p"$greeting @ ${time.t.T}")
```