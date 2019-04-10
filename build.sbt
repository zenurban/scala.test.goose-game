scalaVersion := "2.12.7"

onLoadMessage := ""
showSuccess := false
logLevel in run := Level.Error

name := "scala-goose-game"
version := "1.0.0"

//enablePlugins(UniversalPlugin)

libraryDependencies += "org.scala-sbt" % "sbt" % "1.2.6"
libraryDependencies += "org.scala-sbt" %% "command" % "1.2.6"
libraryDependencies += "com.lihaoyi" %% "fansi" % "0.2.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

