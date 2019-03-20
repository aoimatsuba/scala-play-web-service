import net.liftweb.json.DefaultFormats
import net.liftweb.json._

// case class that matches the json string
case class EmailAccounts(
                          accountName: String,
                          username: String,
                          password: String,
                          url: String,
                          minutesBetweenChecks: Int,
                          usersOfInterest: List[String]
                        )

object ComplexJsonToScalaObjects extends App {

  implicit val formats = DefaultFormats

  val jsonString = """
    {
      "accounts": [
      { "emailAccount": {
        "accountName": "Yahoo mail",
        "username": "aoimatsuba",
        "password": "yahoooo",
        "url": "imap.yahoo.com",
        "minutesBetweenChecks": 1,
        "usersOfInterest": ["baj", "noonoo", "giraffe"]
      }},
      { "emailAccount": {
        "accountName": "Gmail",
        "username": "amatsuba",
        "password": "kajigaya",
        "url": "imap.gmail.com",
        "minutesBetweenChecks": 1,
        "usersOfInterest": ["raseru", "lichin san"]
      }}
      ]
    }
  """

  // parse string to Jvalue
  val json = parse(jsonString)

  // search for all elements named emailAccount using \\ method
  val elements = (json \\ "emailAccount").children
  println((json \\ "emailAccount")) // JObject(List[JField(emailAccount, ...), Jfield(emailAccount, ...)])
  println(elements) // List(JObject, JObject)

  for(account <- elements) {
    val m = account.extract[EmailAccounts]
    println(s"Account: ${m.accountName}, ${m.username}, ${m.password}")
    println(s"User is friends with ${m.usersOfInterest.mkString(",")}")
  }

}