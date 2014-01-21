package le.liu


/**
 * Created by IntelliJ IDEA.
 * User: liu
 * Date: 13-5-14
 * Time: 下午5:30
 *
 */

object CurryTest extends App {

  self =>

  def filter(xs: List[Int], p: Int => Boolean): List[Int] =
    if (xs.isEmpty) xs
    else if (p(xs.head)) xs.head :: filter(xs.tail, p)
    else filter(xs.tail, p)

  def modN(n: Int)(x: Int) = (x % n) == 0


  val nums = List(1, 2, 3, 4, 5, 6, 7, 8)

  println(filter(nums, modN(4)))
  println(filter(nums, modN(3)))

}