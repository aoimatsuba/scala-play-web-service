import net.liftweb.json.DefaultFormats
import net.liftweb.json._

case class MailServer(url: String, username: String, password: String)

object JsonToScalaObject extends App {

  implicit val formats = DefaultFormats

  // simulate a json string
  val jsonString =
    """
      |{
      |  "url": "gmail.com",
      |  "username": "amatsuba",
      |  "password": "something"
      |}
    """.stripMargin

  // convert a string to a jvalue object
  val jValue = parse(jsonString)

  // create a scala object from the string
  val mailServer = jValue.extract[MailServer]
  println(mailServer.url)
  println(mailServer.username)
  println(mailServer.password)


  // if there is no class field in the json String, it will give
  /* Exception in thread "main" net.liftweb.json.MappingException: No usable value for password
     Did not find value which can be converted into java.lang.String
  */
}