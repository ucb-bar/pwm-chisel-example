// See LICENSE for license details.

package example

import Chisel._

class Toggle extends Module {
  val io = new Bundle {
    val i  = Bool(INPUT)
    val o  = Bool(OUTPUT)
  }
  val x = Reg(init = Bool(false))
  when (io.i) { x := ! x }
  io.o := x
}
