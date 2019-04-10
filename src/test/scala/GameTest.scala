import Game.{GOAL_LABEL, THE_BRIDGE, THE_BRIDGE_JUMP, THE_BRIDGE_LABEL, WIN_LABEL, THE_GOOSES, label}
import org.scalatest.FunSuite

class GameTest extends FunSuite {
  test("analyze traps") {
    var pos = 22
    val user = "zen"
    val roll1 = 2
    val roll2 = 3
    assert(Game.analyzeTraps(user, pos, roll1, roll2) === (pos, label(pos)))

    pos = Game.GOAL
    assert(Game.analyzeTraps(user, pos, roll1, 4) === (pos, s"$GOAL_LABEL. $user $WIN_LABEL"))

    pos = THE_BRIDGE
    assert(Game.analyzeTraps(user, pos, roll1, roll2) ===
      (THE_BRIDGE_JUMP, s"$THE_BRIDGE_LABEL. $user jumps to ${label(THE_BRIDGE_JUMP)}"))

    pos = THE_GOOSES.head
    def finalPos = pos + roll1 + roll2
    assert(Game.analyzeTraps(user, pos, roll1, roll2) ===
      (finalPos, s"${label(pos)}. $user moves again and goes to ${label(finalPos)}"))
  }

}

