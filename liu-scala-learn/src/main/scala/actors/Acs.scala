package actors

import scala.actors.Actor._
import scala.actors.Actor

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-6-7
 * Time: 上午11:23
 */
object Acs {
  val caller = self

  def isPrime(number: Int) = {
    println("Going to find if " + number + " is prime")
    var result = true
    if (number == 2 || number == 3) result = true
    for (i <- 2 to Math.sqrt(number.toDouble).floor.toInt; if result) {
      if (number % i == 0) result = false
    }

    println("done finding if " + number + " is prime")
    result
  }


  val primeTeller = actor {
    var continue = true
    while (continue) {
      receive {
        //        case (caller: Actor, number: Int) => caller !(number, isPrime(number))
        case (caller: Actor, number: Int) => actor {
          caller !(number, isPrime(number))
        }
        case "quit" => continue = false
      }
    }
  }

  def main(args: Array[String]) {
    primeTeller !(self, 2)
    primeTeller !(self, 131)
    primeTeller !(self, 132)
    primeTeller !(self, 10)
    for (i <- 1 to 4) {
      receive {
        case (number, result) => println(number + " is prime? " + result)
      }
    }
    primeTeller ! "quit"
  }

}
