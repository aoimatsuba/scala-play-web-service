import java.util

import com.google.gson.Gson
import org.apache.http.NameValuePair
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.{HttpGet, HttpPost}
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import org.apache.http.message.BasicNameValuePair

case class Dog(name: String, age: Int, owner: String)

object ApacheHttpClient extends App {

  def getRestContent(url: String, connectionTimeout: Int, socketTimeout: Int): String = {

    val httpClient = buildHttpClient(connectionTimeout, socketTimeout)
    val httpResponse = httpClient.execute(new HttpGet(url))

    val entity = httpResponse.getEntity
    var content = "No content: failed"
    if (entity != null) {
      val inputStream = entity.getContent
      content = io.Source.fromInputStream(inputStream).getLines.mkString
      inputStream.close()
    }
    httpClient.close()
    content
  }

  def post(json: String): HttpPost = {
    val post = new HttpPost("http://skimap.org")

    // add name value pairs to a post object
    val nameValuePairs = new util.ArrayList[NameValuePair]()
    nameValuePairs.add(new BasicNameValuePair("JSON", json))
    post.setEntity(new UrlEncodedFormEntity(nameValuePairs))
    post
  }

  // Use Closeable client so that client can be closed ^ getConnectionManager().shutdown() is deprecated
  private def buildHttpClient(connectionTimeout: Int, socketTimeout: Int): CloseableHttpClient = {

    // The way to set request config by getParam is deprecated
    // Create a custom requestconfig and set idt to the client
    val httpConfig = RequestConfig.custom()
      .setConnectionRequestTimeout(connectionTimeout)
      .setSocketTimeout(socketTimeout).build()

    val httpClient = HttpClientBuilder.create().setDefaultRequestConfig(httpConfig).build()
    httpClient
  }

  // Get request
  println(getRestContent("http://skimap.org", 500, 1500))

  // Post request
  // Create json string out of scala object
  val hugo = new Dog("Hugo", 10, "Russell")
  val json = (new Gson).toJson(hugo)

  // create post object
  val httpClient = HttpClientBuilder.create().build()
  val postResponse = httpClient.execute(post(json))

  // getting headers
  println("======HEADERS=======")
  postResponse.getAllHeaders.foreach(arg => println(arg))

  // Setting header when sending GET request
  val httpGet = new HttpGet("http://skimap.org")
  httpGet.setHeader("header_key", "value")
  httpGet.setHeader("another_key", "another value")

  println("======HEADERS GET REQUEST=======")
  println(httpGet.getAllHeaders.foreach(println))
  httpClient.close()
}