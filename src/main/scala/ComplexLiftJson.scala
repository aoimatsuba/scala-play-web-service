import net.liftweb.json.DefaultFormats
import net.liftweb.json.JsonDSL._
import net.liftweb.json.prettyRender

object ComplexLiftJson extends App {

  // When scala object that needs to be converted to JSON gets more complex,
  // especially when they start to contain collections,
  // The Lift json library uses its own DSL

  implicit val formats = DefaultFormats

  val noot = Human("Noot", Address2("Bed", 81541))
  val baj = Human("Baj", Address2("Santa Clara", 85054))

  val kyupio = Human("Yori", Address2("Sendagaya", 1510051))
  val friends = List(noot, baj)
  kyupio.friends = friends

  // Needs to define the json output myself
  val json = ("human" ->
    ("name" -> kyupio.name) ~
      ("address" -> ("city" -> kyupio.address.city) ~
        ("ZIP" -> kyupio.address.zip)) ~
      ("friends" -> friends.map { f=>
        ("name" -> f.name) ~
          ("address" ->
            ("city" -> f.address.city) ~
            ("zip" -> f.address.zip)
          )
      })
    )


 // println(prettyRender(json))

  // out put result
  /*
  {
  "human":{
    "name":"Yori",
    "address":{
      "city":"Sendagaya",
      "ZIP":1510051
    },
    "friends":[
      {
        "name":"Noot",
        "address":{
          "city":"Bed",
          "zip":81541
        }
      },
      {
        "name":"Baj",
        "address":{
          "city":"Santa Clara",
          "zip":85054
        }
      }
    ]
  }
}
   */


  // This can be done more easily by breaking down the generating code to
  // some small methods
  val easierJson = ("personEASY" ->
    ("name" -> kyupio.name) ~
    getAddress(kyupio.address) ~
    getFriends(kyupio)
    )


  def getFriends(h: Human) = {
    ("friends" ->
      h.friends.map { f =>
        ("name" -> f.name) ~
        getAddress(f.address)
      })
  }

  def getAddress(a: Address2) = {
    ("address" ->
      ("city" -> a.city) ~
        ("zip" -> a.zip)
      )
  }

  println(prettyRender(easierJson))
}

case class Human(name: String, address: Address2) {
  var friends = List[Human]()
}

case class Address2(city: String, zip: Int)