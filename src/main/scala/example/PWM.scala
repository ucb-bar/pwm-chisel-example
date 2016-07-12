// See LICENSE for license details.

package example

import Chisel._

class PWM(val w: Int) extends Module {
  val io = new Bundle {
    val enable = Bool(INPUT)
    val period = UInt(INPUT,  w)
    val duty   = UInt(INPUT,  w)
    val out    = Bool(OUTPUT)
  }
  val x  = Reg(init = UInt(0, w))
  x := x + UInt(1)
  when   (x >= io.period) { x := UInt(0) }
  io.out := io.enable && (x > io.duty)
}
