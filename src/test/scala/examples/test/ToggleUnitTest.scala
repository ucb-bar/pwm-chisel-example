// See LICENSE for license details.

package examples.test

import Chisel.iotesters._
import example.Toggle
import org.scalatest.Matchers
import scala.util.Random

class ToggleUnitTester(c: Toggle, b: Option[Backend] = None) extends PeekPokeTester(c, _backend=b) {
  var x = 0

  val r = Random

  for(n <- 1 to 100) {
    val i: BigInt = BigInt(r.nextInt % 2).abs

    println(s"i=$i")

    poke(c.io.i, i)
    expect(c.io.o, x)

    step(1)
    if (i % 2 == 1) {
      x = (1 - x)
    }

    expect(c.io.o, x)
  }
}

class ToggleTester extends ChiselFlatSpec with Matchers {
  "Toggle" should "calculate toggle " in {
    runPeekPokeTester(() => new Toggle) {
      (c, b) => new ToggleUnitTester(c, b)
    } should be (true)
  }
}
