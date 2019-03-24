import java.nio.charset.Charset

object HttpClient extends App {

  // A simple use of fromURL

  implicit val encode = Charset.defaultCharset()
  @throws(classOf[java.io.IOException])
  def get(url: String)(implicit encode: Charset) = scala.io.Source.fromURL(url).mkString

  val url = "https://www.skimap.org/"
  //println(get(url))

  // setting timeout while using fromURL
  def getWithTimeout(url: String, connectionTimeout: Int = 5000, readTimeout: Int = 5000, requestMethod: String = "GET") = {

    import java.net.{URL, HttpURLConnection}
    val connection = (new URL(url)).openConnection.asInstanceOf[HttpURLConnection]
    connection.setConnectTimeout(connectionTimeout)
    connection.setReadTimeout(readTimeout)
    connection.setRequestMethod(requestMethod)

    val inputStream = connection.getInputStream
    val content = io.Source.fromInputStream(inputStream).mkString

    if(inputStream != null) inputStream.close

    content
  }

  //println(getWithTimeout(url))

  // or use try catch
  try {
    val content2 = get("http://localhost:8080/waitForever")
    println(content2)
  } catch {
    case ioe: java.io.IOException => println("IOException: "+ioe.getMessage)
    case ste: java.net.SocketTimeoutException => println("Got timeout!")
  }

}