
package mhewedy.usingspark.stats

import org.parse4j.ParseObject

object Stats extends App{

  val xs = Util.finalAll("URLMapping")

  println("xs length: " + xs.length)

  def getMonthStats = groupBy (xs) (o => Util.byMonth(o.getCreatedAt)) sortBy (_._1)

  def getSourceStats = groupBy (xs) (_.get("source"))

  def getTop20OwnerIdStats = groupBy (xs) (_.get("owner_id")) filterNot(_._2 == 1) sortBy(- _._2) take 20


  def groupBy[T](xs: Stream[ParseObject]) (g: ParseObject => T): Stream[(T, Int)] = {
    (xs groupBy g).toStream.map(p => (p._1, p._2.length))
  }
}
