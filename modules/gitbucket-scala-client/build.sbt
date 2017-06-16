name := "gitbucket-client"

libraryDependencies ++= Seq(
  "net.databinder.dispatch" %% "dispatch-core"          % "0.11.2",
  "io.circe"                %% "circe-core"             % "0.7.0",
  "io.circe"                %% "circe-generic"          % "0.7.0",
  "io.circe"                %% "circe-parser"           % "0.7.0",
  "io.circe"                %% "circe-generic-extras"   % "0.7.0",
  "org.scalatest"           % "scalatest_2.11"          % "3.0.1"     % "test",
  "org.mockito"             % "mockito-core"            % "1.10.19"   % "test"
)