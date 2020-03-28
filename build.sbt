name := "TwitterSpark"

version := "1.0"

scalaVersion := "2.10.6"

val sparkVersion = "1.6.0"

assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case x => MergeStrategy.last
}

resolvers ++= Seq(
  "apache-snapshots" at "http://repository.apache.org/snapshots/"
)

libraryDependencies ++= Seq(
  "org.twitter4j" % "twitter4j-core" % "4.0.7",
  "org.twitter4j" % "twitter4j-stream" % "3.0.3",
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming-twitter" % sparkVersion,
  "org.joda" % "joda-convert" % "1.8",
  "joda-time" % "joda-time" % "2.9.3"
)
