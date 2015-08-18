
package mhewedy.usingspark.stats

import java.text.SimpleDateFormat

import org.parse4j.ParseObject

object Stats{

  def getMonthStats(xs: Stream[ParseObject]) =
    (groupBy (xs) (o => Util.byMonth(o.getCreatedAt)) sortBy (_._1)) map {
      e => (new SimpleDateFormat("MMM YYYY").format(e._1), e._2)
    }

  def getSourceStats(xs: Stream[ParseObject]) = groupBy (xs) (_.getString("source")).filterNot(_._1 == null)

  def getOwnerByUsage(xs: Stream[ParseObject]) = {
    val usageList = countByOwnerId(xs) map (p => p._2)
    (for {
      (n, l) <- usageList groupBy (p => usageList.count(_ == p))
      e <- l
    }yield (e, n)).toStream.sortBy(_._1)
  }

  // ---- Private Staff

  private def countByOwnerId(xs: Stream[ParseObject]): Stream[(String, Int)] =  groupBy (xs) (_.getString("owner_id"))

  private def groupBy[T](xs: Stream[ParseObject]) (g: ParseObject => T): Stream[(T, Int)] =
    (xs groupBy g) map(p => (p._1, p._2.length)) toStream

}
