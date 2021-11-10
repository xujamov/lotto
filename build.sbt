import Dependencies.Libraries._
import Dependencies.{Versions, commonDependencies, rootDependencies}

lazy val projectSettings = scala.Seq(version := "1.0", scalaVersion := "2.13.6")

lazy val common = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("common"))
  .settings(libraryDependencies ++= commonDependencies)
  .settings(projectSettings: _*)

lazy val client = (project in file("client"))
  .settings(projectSettings: _*)
  .settings(
    name                            := "client",
    scalaJSUseMainModuleInitializer := true,
    resolvers += Resolver.sonatypeRepo("releases"),
    libraryDependencies ++= Seq(
      "io.github.chronoscala"             %%% "chronoscala"   % "2.0.2",
      "com.github.japgolly.scalajs-react" %%% "core"          % Versions.scalaJsReactV,
      "com.github.japgolly.scalajs-react" %%% "extra"         % Versions.scalaJsReactV,
      "com.github.japgolly.scalacss"      %%% "ext-react"     % Versions.scalaCssV,
      "io.circe"                          %%% "circe-core"    % Versions.circeVersion,
      "io.circe"                          %%% "circe-parser"  % Versions.circeVersion,
      "io.circe"                          %%% "circe-generic" % Versions.circeVersion),
    webpackEmitSourceMaps := false,
    Compile / npmDependencies ++= npmLibs)
  .enablePlugins(ScalaJSBundlerPlugin)
  .dependsOn(common.js)

lazy val server = project
  .in(file("server"))
  .dependsOn(common.jvm)
  .settings(projectSettings: _*)
  .settings(
    name                    := "real-lotto",
    scalaJSProjects         := scala.Seq(client),
    Assets / pipelineStages := scala.Seq(scalaJSPipeline),
    pipelineStages          := scala.Seq(digest, gzip),
    Compile / compile       := ((Compile / compile) dependsOn scalaJSPipeline).value,
    resolvers += Resolver.sonatypeRepo("snapshots"),
    scalacOptions ++= scala.Seq("-deprecation", "-encoding", "UTF-8", "-feature", "-unchecked"),
    libraryDependencies ++= rootDependencies ++ Seq(guice)
  )
  .enablePlugins(WebScalaJSBundlerPlugin, PlayScala)

lazy val `real-lotto` = (project in file("."))
  .aggregate(server, client)

Global / onLoad := (Global / onLoad).value.andThen(state => "project server" :: state)
