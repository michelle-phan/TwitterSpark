import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import twitter4j.conf.ConfigurationBuilder
import twitter4j.auth.OAuthAuthorization
import twitter4j.Status
import org.apache.spark.streaming.twitter.TwitterUtils

object TwitterSpark {
  def main1(args: Array[String]) {
    val appName = "TwitterData"
    val conf = new SparkConf()
    conf.setAppName(appName).setMaster("local[3]")
    val ssc = new StreamingContext(conf, Seconds(5))

    val consumerSecret = "consumerSecret"
    val accessToken = "accessToken"
    val accessTokenSecret = "accessTokenSecret"
    val consumerKey = "consumerKey"

    val cb = new ConfigurationBuilder
    cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
      .setOAuthConsumerSecret(consumerSecret)
      .setOAuthAccessToken(accessToken)
      .setOAuthAccessTokenSecret(accessTokenSecret)
    val auth = new OAuthAuthorization(cb.build)
    val stream = TwitterUtils.createStream(ssc, Some(auth))

    val englishTweets = stream.filter(_.getLang() == "en")
    // englishTweets.print()
    englishTweets.saveAsTextFiles("tweets/tweets", "json")

    ssc.start()
    ssc.awaitTermination()
  }
}

