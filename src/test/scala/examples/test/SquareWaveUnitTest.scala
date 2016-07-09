// See LICENSE for license details.

package examples.test

import Chisel.iotesters._
import example.SquareWave
import org.scalatest.Matchers
import scala.util.Random

class SquareWaveUnitTester(c: SquareWave, b: Option[Backend] = None) extends PeekPokeTester(c, _backend=b) {
  var x = 0
  var o = 0

  val r = Random

  for(n <- 1 to 100) {
    val enable: BigInt = BigInt(r.nextInt % 2).abs
    val period: BigInt = BigInt(r.nextInt % (1 << c.w)).abs

    poke(c.io.enable, enable)
    poke(c.io.period, period)
    expect(c.io.out, o)
    println("X=" + x + " O=" + o)

    step(1)
    if (x == 0) {
      o = (1 - o)
    }
    if (enable == 1) {
      x = x + 1
      if (x > period) {
        x = 0
      }
    }
  }
}

class SquareWaveTester extends ChiselFlatSpec with Matchers {
  "SquareWave" should "calculate counter " in {
    runPeekPokeTester(() => new SquareWave(4)) {
      (c, b) => new SquareWaveUnitTester(c, b)
    } should be (true)
  }
}
