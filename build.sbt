import org.scalajs.linker.interface.ModuleSplitStyle

ThisBuild / scalaVersion := "3.3.0"
ThisBuild / organization := "io.github.chidirnweke"

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
      scalaJSLinkerConfig ~= {

          _.withModuleKind(ModuleKind.ESModule)
              .withModuleSplitStyle(
                ModuleSplitStyle.SmallModulesFor(List("tictactoe"))
              )

      },
      libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.4.0",
      libraryDependencies += "org.scalablytyped" %%% "scala-tictactoe" % "1.0.0-ea970c"
    )
