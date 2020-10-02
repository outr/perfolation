package perfolation

import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.Throughput))
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(value = 1, jvmArgs = Array(
  "-server",
  "-Xms1g",
  "-Xmx1g",
  "-XX:NewSize=512m",
  "-XX:MaxNewSize=512m",
  "-XX:InitialCodeCacheSize=256m",
  "-XX:ReservedCodeCacheSize=256m",
  "-XX:+AlwaysPreTouch"
))
class SimpleBenchmark {

  @Benchmark
  def doubleWithFFormat: String =
    s"${123.456.f()}"

  @Benchmark
  def longWithFFormat: String =
    s"${1234567.f()}"

}
