import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import twitter4j.conf.ConfigurationBuilder
import twitter4j.auth.OAuthAuthorization
import twitter4j.Status
import org.apache.spark.streaming.twitter.TwitterUtils

object TwitterSparkHashTag {
  def main(args: Array[String]) {
    val appName = "TwitterData"
    val conf = new SparkConf()
    conf.setAppName(appName).setMaster("local[3]")
    val ssc = new StreamingContext(conf, Seconds(5))

    val consumerSecret = "consumerSecret"
    val accessToken = "accessToken"
    val accessTokenSecret = "accessTokenSecret"
    val consumerKey = "consumerKey"

    val cb = new ConfigurationBuilder
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(consumerKey)
      .setOAuthConsumerSecret(consumerSecret)
      .setOAuthAccessToken(accessToken)
      .setOAuthAccessTokenSecret(accessTokenSecret)
    val auth = new OAuthAuthorization(cb.build)
    val stream = TwitterUtils.createStream(ssc, Some(auth))

    val now = org.joda.time.DateTime.now()
    val day = now.getDayOfMonth
    val month = now.getMonthOfYear
    val year = now.getYear
    val name= s"year=$year/month=$month/day=$day"
    val min = now.getSecondOfDay;
    
    val tags = stream.flatMap { status =>
      status.getHashtagEntities.map(_.getText)
    }
    tags
      .countByValue()
      .foreachRDD { rdd =>
        rdd
          .sortBy(_._2)
          .map(x => (x, now))
          .saveAsTextFile(s"output/hashtags/$name/hashtags-$min")
      }

    // val tweets = stream.filter { t =>
    //   val tags = t.getText.split(" ").filter(_.startsWith("#")).map(_.toLowerCase)
    //   tags.contains("#bigdata") && tags.contains("#food")
    // }
    val englishTweets = stream.filter(_.getLang() == "en")
    // englishTweets.print()
    englishTweets.saveAsTextFiles(s"output/tweets/$name/tweets", "json")

    ssc.start()
    ssc.awaitTermination()
  }
}
