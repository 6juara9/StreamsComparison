name := "StreamsComparison"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.6.10"

libraryDependencies += "io.monix" %% "monix" % "3.2.2+44-ab4c068c"

libraryDependencies += "dev.zio" %% "zio-streams" % "1.0.3"