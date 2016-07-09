// See LICENSE for license details.

package examples.test

import Chisel.iotesters._
import example.Counter
import org.scalatest.Matchers
import scala.util.Random

class CounterUnitTester(c: Counter, b: Option[Backend] = None) extends PeekPokeTester(c, _backend=b) {
  var x = 0

  val r = Random

  for(n <- 1 to 100) {
    val enable: BigInt = BigInt(r.nextInt % 2).abs
    val period: BigInt = BigInt(r.nextInt % (1 << c.w)).abs

    poke(c.io.enable, enable)
    poke(c.io.period, period)
    expect(c.io.out, x)
    println("X=" + x)

    step(1)
    if (enable == 1) {
      x = x + 1
      if (x > period) x = 0
    }
  }
}

class CounterTester extends ChiselFlatSpec with Matchers {
  "Counter" should "calculate counter " in {
    runPeekPokeTester(() => new Counter(8)) {
      (c, b) => new CounterUnitTester(c, b)
    } should be (true)
  }
}
