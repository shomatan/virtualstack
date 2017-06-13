name := "virtualstack"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  cache,
  ws,
  filters,
  "org.scalatestplus.play"  %% "scalatestplus-play"     % "1.5.1" % Test,
  "com.typesafe.play"       %% "play-slick"             % "2.0.0",
  "com.typesafe.play"       %% "play-slick-evolutions"  % "2.0.0",
  "mysql"                   %  "mysql-connector-java"   % "5.1.36",
  "net.databinder.dispatch" %% "dispatch-core"          % "0.11.2",
  "io.circe"                %% "circe-core"             % "0.8.0",
  "io.circe"                %% "circe-generic"          % "0.8.0",
  "io.circe"                %% "circe-parser"           % "0.8.0",
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"