import sbt._
import com.github.siasia._
import com.typesafe.startscript.StartScriptPlugin
import PluginKeys._
import Keys._

object Build extends sbt.Build {
  import Dependencies._

  lazy val myProject = Project("spray-template-heroku", file("."))
    .settings(StartScriptPlugin.startScriptForClassesSettings: _*)
    .settings(
      organization  := "com.example",
      version       := "0.0.1",
      scalaVersion  := "2.9.1",
      scalacOptions := Seq("-deprecation", "-encoding", "utf8"),
      resolvers     ++= Dependencies.resolutionRepos,
      libraryDependencies ++= Seq(
        Compile.akkaActor,
        Compile.parboiled,
        Compile.mimepull,
        Compile.sprayServer,
        Compile.sprayCan,
        Test.specs2,
        Container.akkaSlf4j,
        Container.slf4j,
        Container.logback
      )
    )
}

object Dependencies {
  val resolutionRepos = Seq(
    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
    ScalaToolsSnapshots
  )

  object V {
    val akka      = "1.2"
    val spray     = "0.9.0-SNAPSHOT"
    val sprayCan  = "0.9.2-SNAPSHOT"
    val specs2    = "1.6.1"
    val slf4j     = "1.6.1"
    val logback   = "0.9.29"
  }

  object Compile {
    val akkaActor   = "se.scalablesolutions.akka" %  "akka-actor"      % V.akka      % "compile"
    val parboiled   = "org.parboiled"             %   "parboiled-scala" % "1.0.2"     % "compile"
    val mimepull    = "org.jvnet"                 %   "mimepull"       % "1.6"       % "compile"
    val sprayServer = "cc.spray"                  %  "spray-server"    % V.spray     % "compile"
    val sprayCan    = "cc.spray.can"              %   "spray-can"      % V.sprayCan  % "compile"
  }

  object Test {
    val specs2      = "org.specs2"                %% "specs2"          % V.specs2  % "test"
  }

  object Container {
    val akkaSlf4j   = "se.scalablesolutions.akka" %  "akka-slf4j"      % V.akka
    val slf4j       = "org.slf4j"                 %  "slf4j-api"       % V.slf4j
    val logback     = "ch.qos.logback"            %  "logback-classic" % V.logback
  }
}
