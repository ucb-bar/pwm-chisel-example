// See LICENSE for license details.

package examples.test

import Chisel.iotesters._
import example.PWM
import org.scalatest.Matchers

class PWMUnitTester(c: PWM, b: Option[Backend] = None) extends PeekPokeTester(c, _backend=b) {
  /**
    * compute the gcd and the number of steps it should take to do it
    *
    * @param a positive integer
    * @param b positive integer
    * @return the PWM of a and b
    */
  def computeGcd(a: Int, b: Int): (Int, Int) = {
    var x = a
    var y = b
    var depth = 1
    while(y > 0 ) {
      if (x > y) {
        x -= y
      }
      else {
        y -= x
      }
      depth += 1
    }
    (x, depth)
  }

  val gcd = c

  for(i <- 1 to 100) {
    for (j <- 1 to 100) {
      val (a, b, z) = (64, 48, 16)

      poke(gcd.io.a, a)
      poke(gcd.io.b, b)
      poke(gcd.io.e, 1)
      step(1)
      poke(gcd.io.e, 0)

      val (expected_gcd, steps) = computeGcd(a, b)

      step(steps - 1) // -1 is because we step(1) already to toggle the enable
      expect(gcd.io.z, expected_gcd)
      expect(gcd.io.v, 1)
    }
  }
}

class PWMTester extends ChiselFlatSpec with Matchers {
  "PWM" should "calculate proper greatest common denominator" in {
    runPeekPokeTester(() => new PWM) {
      (c, b) => new PWMUnitTester(c, b)
    } should be (true)
  }
}
