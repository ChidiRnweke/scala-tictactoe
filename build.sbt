import org.scalajs.linker.interface.ModuleSplitStyle
import scala.scalanative.build._

ThisBuild / scalaVersion := "3.3.0"
ThisBuild / organization := "io.github.chidirnweke"

lazy val root = project
    .in(file("."))
    .aggregate(tictactoe.js, tictactoe.jvm, tictactoe.native)
    .settings(
      publish := {},
      publishLocal := {}
    )

lazy val tictactoe = crossProject(JSPlatform, JVMPlatform, NativePlatform)
    .in(file("."))
    .settings(
      name := "tictactoe",
      version := "0.1-SNAPSHOT"
    )
    .settings(
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
      libraryDependencies += "org.scalablytyped" %%% "scala-tictactoe" % "1.0.0-ea970c"
    )
    .nativeSettings(
      libraryDependencies += "org.typelevel" %%% "cats-effect" % "3.5.2",
      nativeConfig ~= {
          _.withLTO(LTO.thin)
              .withMode(Mode.releaseFast)
              .withGC(GC.commix)
      },
      Compile / unmanagedSourceDirectories += baseDirectory.value / "jvm" / "src" / "main" / "scala" / "tictactoe"
    )
