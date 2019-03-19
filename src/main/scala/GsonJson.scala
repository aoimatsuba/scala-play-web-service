import com.google.gson.Gson

case class Cat(name: String, age: Int, address: Habitat)
case class Habitat(zip: Int, city: String)

object GsonJson extends App {

  // download Json JAR and place it in lib
  val noot = Cat("Noot", 12, Habitat(81541, "München"))

  val gson = new Gson
  val nootJsonString = gson.toJson(noot)

  println(nootJsonString)
  // prints {"name":"Noot","age":12,"address":{"zip":81541,"city":"München"}}
}