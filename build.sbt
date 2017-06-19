name := "virtualstack"

lazy val commonSettings = Seq(
  organization := "shoma.me",
  version := "0.1.0",
  scalaVersion := "2.11.11"
)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .aggregate(gitbucket)
  .dependsOn(gitbucket)
  .settings(
    commonSettings
  )

lazy val gitbucket = (project in file("modules/gitbucket-scala-client"))
  .settings(commonSettings)

libraryDependencies ++= Seq(
  cache,
  ws,
  filters,
  "com.typesafe.play"       %% "play-slick"               % "2.0.0",
  "com.typesafe.play"       %% "play-slick-evolutions"    % "2.0.0",
  "org.postgresql"          %  "postgresql"               % "42.1.1",
  "com.mohiva"              %% "play-silhouette"          % "4.0.0",
  "com.mohiva"              %% "play-silhouette-testkit"  % "4.0.0"   % "test",
  "org.scalatestplus.play"  %% "scalatestplus-play"       % "1.5.1"   % Test
)

resolvers := ("Atlassian Releases" at "https://maven.atlassian.com/public/") +: resolvers.value

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
