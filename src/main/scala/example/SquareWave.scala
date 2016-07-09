// See LICENSE for license details.

package example

import Chisel._

class SquareWave(val w:Int) extends Module {
  val io = new Bundle {
    val period = UInt(INPUT, w)
    val enable = Bool(INPUT)
    val out    = UInt(OUTPUT, w)
  }
  val counter = Module(new Counter(w))
  counter.io.period := io.period
  counter.io.enable := io.enable
  val tog = Module(new Toggle())
  tog.io.i := counter.io.out === UInt(0)
  io.out := tog.io.o
}

