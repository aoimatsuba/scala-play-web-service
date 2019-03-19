import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write

case class Person(name: String, age: Int, address: Address)
case class Address(zip: Int, city: String)

object  LiftJson extends App {

  // Creating a Json string from a scala object without using
  // outside of a specific framework like Play

  // Lift-JSON
  val aoi = Person("Aoi", 27, Address(81541, "Munich"))

  // create a JSON string from aoi
  // Default json formats from lift-json
  implicit val formats = DefaultFormats
  val aoiJsonString = write(aoi)

  println(aoiJsonString)

  // As a result, printed
  // {"name":"Aoi","age":27,"address":{"zip":81541,"city":"Munich"}}
}