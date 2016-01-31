name := "Butler"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "scalac repo" at "https://raw.githubusercontent.com/ScalaConsultants/mvn-repo/master/"
resolvers += "JAnalyse Repository" at "http://www.janalyse.fr/repository/"

libraryDependencies ++= Seq (
  "io.scalac" %% "slack-scala-bot-core" % "0.2.1",
  "fr.janalyse"   %% "janalyse-ssh" % "0.9.19" % "compile"
)