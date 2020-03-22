name := "TwitterSpark"

version := "1.0"

scalaVersion := "2.11.12"

val sparkVersion = "1.5.2"


resolvers ++= Seq(
  "apache-snapshots" at "http://repository.apache.org/snapshots/"
)

libraryDependencies ++= Seq(
  "commons-logging" % "commons-logging" % "1.2",
  "org.twitter4j" % "twitter4j-core" % "4.0.7",
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-twitter" % "1.6.3"
)

libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.5.1" 
libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.5.1" classifier "models"
