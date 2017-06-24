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

val silhouetteVer = "4.0.0"

libraryDependencies ++= Seq(
  cache,
  ws,
  filters,
  "com.typesafe.play"       %% "play-slick"                       % "2.0.0",
  "com.typesafe.play"       %% "play-slick-evolutions"            % "2.0.0",
  "org.postgresql"          %  "postgresql"                       % "42.1.1",
  "net.codingwell"          %% "scala-guice"                      % "4.1.0",
  "com.iheart"              %% "ficus"                            % "1.2.6",        // config lib, used by Silhouette,
  "com.mohiva"              %% "play-silhouette"                  % silhouetteVer,
  "com.mohiva"              %% "play-silhouette"                  % silhouetteVer,
  "com.mohiva"              %% "play-silhouette-password-bcrypt"  % silhouetteVer,
  "com.mohiva"              %% "play-silhouette-crypto-jca"       % silhouetteVer,
  "com.mohiva"              %% "play-silhouette-persistence"      % silhouetteVer,
  "com.mohiva"              %% "play-silhouette-testkit"          % silhouetteVer   % "test",
  "org.scalatestplus.play"  %% "scalatestplus-play"               % "1.5.1"         % Test,
  "org.slf4j"               %  "slf4j-nop"                        % "1.6.4"
)

resolvers += Resolver.jcenterRepo

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
