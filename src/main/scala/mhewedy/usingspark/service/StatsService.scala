package mhewedy.usingspark.service

import mhewedy.usingspark.stats.{Util, Stats}
import scala.collection.JavaConverters._
import spark.{Request, Response}

@org.springframework.stereotype.Service
class StatsService extends JsonService[Any]{

  override def getObject(req: Request, res: Response): Any = {

    val xs = Util.finalAll("URLMapping")

    val month = Stats.getMonthStats(xs)
    val owner = Stats.getTop20OwnerIdStats(xs)

    val monthData = Array(month._1 asJava, month._2 asJava)
    val sourceData = Stats.getSourceStats(xs) asJava
    val ownerData = Array(owner._1 asJava, owner._2 asJava)

    Array(monthData, sourceData, ownerData)
  }
}
