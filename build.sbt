name := "Butler"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "scalac repo" at "https://raw.githubusercontent.com/ScalaConsultants/mvn-repo/master/"

libraryDependencies ++= Seq (
  "io.scalac" %% "slack-scala-bot-core" % "0.2.1",
  "com.decodified" %% "scala-ssh" % "0.7.0",
  "org.bouncycastle" % "bcprov-jdk16" % "1.46",
  "com.jcraft" % "jzlib" % "1.1.3"
)