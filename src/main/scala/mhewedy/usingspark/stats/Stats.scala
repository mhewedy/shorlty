
package mhewedy.usingspark.stats

import java.text.SimpleDateFormat

import org.parse4j.ParseObject

object Stats{

  def getMonthStats(xs: Stream[ParseObject]) = {(groupBy (xs) (o => Util.byMonth(o.getCreatedAt)) sortBy (_._1)) map {
          e => (new SimpleDateFormat("MMM YYYY").format(e._1), e._2)
  }toList} unzip

  def getSourceStats(xs: Stream[ParseObject]) = groupBy (xs) (_.get("source")).toMap map(e => SourceObject(e._1, e._2))

  def getTop20OwnerIdStats(xs: Stream[ParseObject]) = (groupBy (xs) (_.get("owner_id")) filterNot(_._2 == 1) sortBy(- _._2) take 20 toList) unzip

  def groupBy[T](xs: Stream[ParseObject]) (g: ParseObject => T): Stream[(T, Int)] = {
    (xs groupBy g).toStream.map(p => (p._1, p._2.length))
  }

  case class SourceObject(n: AnyRef, y: Double){
    val name = if (n == null) "Unknown" else n
  }
}
