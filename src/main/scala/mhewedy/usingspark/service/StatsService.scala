package mhewedy.usingspark.service

import java.util

import mhewedy.usingspark.stats.{Util, Stats}
import scala.collection.JavaConverters._
import spark.{Request, Response}

@org.springframework.stereotype.Service
class StatsService extends JsonService[Any]{

  override def getObject(req: Request, res: Response): Any = {

    val xs = Util.finalAll("URLMapping")

    val monthData = asLineChart[String](Stats.getMonthStats(xs))
    val sourceData = asPieChart[String](Stats.getSourceStats(xs))
    val ownerData = asPieChart[Int](Stats.getOwnerByUsage(xs))

    Array(monthData, sourceData, ownerData)
  }

  private def asLineChart[T](xs: Stream[(T, Int)]) = {
    val zippedList = xs.unzip
     Array(zippedList._1 asJava, zippedList._2 asJava)
  }

  private def asPieChart[T](xs: Stream[(T, Int)]) = xs map(p => SourceObject(p._1, p._2)) asJava

  case class SourceObject[T](name: T, y: Double)
}
