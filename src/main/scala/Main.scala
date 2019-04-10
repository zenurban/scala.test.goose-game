import java.io.File

import Display.{ARROW, styleHighlight => hl, stylePrompt => prm}
import sbt.complete.Parser

import scala.util.Try

object Main extends App {
  val prompt = s"${prm("goose-game")} $ARROW "

  val parser = CommandParser.parser

  def loop(): Unit = {
    Try(readLine(parser) match {
      case Some(Exit) => Exit.run
      case None => loop()
      case Some(cmd) =>
        cmd.run()
        loop()
    }).recover {
      case _ => Display.error("Ooops! Something went wrong, let's try again")
        loop()
    }
  }

  Display.head(s"Welcome to the ${hl("The Goose Game Kata")}")
  loop()

  private def readLine[U](parser: Parser[U]): Option[U] = {
    val reader = new sbt.FullReader(
      Some(new File("/tmp/scala-goose-game.history")),
      parser
    )
    reader
      .readLine(prompt = prompt)
      .flatMap {
        line => Parser.parse(line, parser).fold(_ => None, Some(_))
      }
  }
}
