ThisBuild / scalaVersion := "3.3.0"
ThisBuild / organization := "com.chidiNweke"

lazy val tictactoe = project
    .in(file("."))
    .settings(
      name := "tictactoe",
      libraryDependencies += "org.scala-lang" %% "toolkit-test" % "0.1.7" % Test,
      libraryDependencies += "org.typelevel" %% "cats-effect" % "3.5.2"
    )
