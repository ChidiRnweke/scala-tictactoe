ThisBuild / scalaVersion := "3.3.0"
ThisBuild / organization := "com.chidiNweke"

lazy val root = project
    .in(file("."))
    .aggregate(tictactoe.js, tictactoe.jvm)
    .settings(
      publish := {},
      publishLocal := {}
    )

lazy val tictactoe = crossProject(JSPlatform, JVMPlatform)
    .in(file("."))
    .settings(
      name := "tictactoe",
      version := "0.1-SNAPSHOT"
    )
    .settings(
      libraryDependencies += "org.typelevel" %%% "cats-core" % "2.10.0",
      libraryDependencies += "org.scala-lang" %%% "toolkit-test" % "0.1.7" % Test
    )
    .jvmSettings(
      libraryDependencies += "org.typelevel" %% "cats-effect" % "3.5.2",
      Compile / run / fork := true,
      run / connectInput := true
    )
    .jsSettings(
      scalaJSUseMainModuleInitializer := true
    )
