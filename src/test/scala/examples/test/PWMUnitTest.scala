// See LICENSE for license details.

package examples.test

import Chisel.iotesters._
import example.PWM
import org.scalatest.Matchers
import scala.util.Random

class PWMUnitTester(c: PWM, b: Option[Backend] = None) extends PeekPokeTester(c, _backend=b) {

  val gcd = c

  var x = 0

  val r = Random

  for(n <- 1 to 100) {
    val enable: BigInt = BigInt(r.nextInt % 2).abs
    val period: BigInt = BigInt(r.nextInt % (1 << c.w)).abs
    val duty: BigInt  = BigInt(r.nextInt % period.toInt).abs

    val out = enable & (x > duty)

    poke(c.io.enable, enable)
    poke(c.io.period, period)
    poke(c.io.duty,   duty)
    expect(c.io.out,  out)

    step(1)

    x = x + 1
    if (x > period) x = 0
  }
}

class PWMTester extends ChiselFlatSpec with Matchers {
  "PWM" should "calculate pulse width modulation" in {
    runPeekPokeTester(() => new PWM(16)) {
      (c, b) => new PWMUnitTester(c, b)
    } should be (true)
  }
}
