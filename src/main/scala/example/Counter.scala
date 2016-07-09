// See LICENSE for license details.

package example

import Chisel._

class Counter (val w:Int) extends Module {
  val io = new Bundle {
    val period = UInt(INPUT, w)
    val enable = Bool(INPUT)
    val out    = UInt(OUTPUT, w)
  }
  val x = Reg(UInt(width = w))
  def wrap (y: UInt, limit: UInt) = Mux(y > limit, UInt(0), y)
  when (io.enable) {
    x := wrap(x + UInt(1), io.period)
  }
  io.out := x
}
