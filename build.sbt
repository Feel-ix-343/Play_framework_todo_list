import sbt.Keys.libraryDependencies

name := """play-learning"""
organization := "felix"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.8"

libraryDependencies ++= Seq(
    guice,
    "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
    "com.typesafe.play" %% "play-json" % "2.9.2"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "felix.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "felix.binders._"
