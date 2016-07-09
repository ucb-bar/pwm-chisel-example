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
    val i0 = r.nextInt
    val i1 = if (i0 < 0) -i0 else i0
    val i  = i1 % 2
    println(s"i=$i")
    val o = x
    poke(c.io.i, i)
    step(1)
    expect(c.io.o, o)
    if (i % 2 == 1) 
      x = (1 - x)
  }
}

class ToggleTester extends ChiselFlatSpec with Matchers {
  "Toggle" should "calculate toggle " in {
    runPeekPokeTester(() => new Toggle) {
      (c, b) => new ToggleUnitTester(c, b)
    } should be (true)
  }
}
