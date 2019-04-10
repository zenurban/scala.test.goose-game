import Display.{styleHighlight => hl}

import scala.util.Random

object Game {
  val START: Int = 0
  val GOAL: Int = 63
  val THE_BRIDGE: Int = 6
  val THE_BRIDGE_JUMP: Int = 12
  val THE_GOOSES: Vector[Int] = Vector(5, 9, 14, 18, 23, 27)

  val START_LABEL: String = hl("Start")
  val THE_BRIDGE_LABEL: String = s"${hl("The")} ${hl("Bridge")}"
  val THE_GOOSE_LABEL: String = s"${hl("The")} ${hl("Goose")}"
  val GOAL_LABEL: String = hl(GOAL)
  val WIN_LABEL: String = hl("Wins!!")

  private val _random = Random

  def label(playerPos: Int): String = {
    if (playerPos == START) {
      START_LABEL
    } else if (playerPos == THE_BRIDGE) {
      THE_BRIDGE_LABEL
    } else if (THE_GOOSES.contains(playerPos)) {
      s"${hl(playerPos)}, $THE_GOOSE_LABEL"
    } else {
      hl(playerPos)
    }
  }


  def action(playerName: String, roll1: Int, roll2: Int): String = {
    val playerLabel: String = hl(playerName)
    val playerPosOrig: Int = Players.getPosition(playerName)
    var playerPos: Int = playerPosOrig
    var output: String = s"$playerLabel rolls ${hl(roll1)}, ${hl(roll2)}. $playerLabel moves from ${label(playerPos)} to "
    playerPos += roll1 + roll2

    val result = analyzeTraps(playerLabel, playerPos, roll1, roll2)
    playerPos = result._1
    output += result._2

    Players.move(playerName, playerPos)
    for (otherPlayerName <- Players.all if otherPlayerName != playerName && Players.getPosition(otherPlayerName) == playerPos) {
      output += s". On ${label(playerPos)} there is ${hl(otherPlayerName)}, who returns to ${label(playerPosOrig)}"
      Players.move(otherPlayerName, playerPosOrig)
      return output
    }
    output
  }

  def analyzeTraps(playerLabel: String, playerPos: Int, roll1: Int, roll2: Int): (Int, String) = {
    var playerPos1: Int = playerPos
    var output1: String = ""
    var continueTurn: Boolean = true
    while (continueTurn) {
      if (playerPos1 == THE_BRIDGE) {
        output1 += s"$THE_BRIDGE_LABEL. $playerLabel jumps to "
        playerPos1 = THE_BRIDGE_JUMP
      } else if (THE_GOOSES.contains(playerPos1)) {
        output1 += s"${label(playerPos1)}. $playerLabel moves again and goes to "
        playerPos1 += roll1 + roll2
      } else if (playerPos1 == GOAL) {
        output1 += s"$GOAL_LABEL. $playerLabel $WIN_LABEL"
        continueTurn = false
      } else if (playerPos1 > GOAL) {
        output1 += s"$playerPos1. $playerLabel bounces! $playerLabel returns to "
        playerPos1 = GOAL - (playerPos1 - GOAL)
      } else {
        output1 += label(playerPos1)
        continueTurn = false
      }
    }
    (playerPos1, output1)
  }


  def randomRoll: Int = {
    _random.nextInt(6) + 1
  }
}
