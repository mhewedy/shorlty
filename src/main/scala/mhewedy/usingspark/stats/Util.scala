package mhewedy.usingspark.stats

import java.util.Date

import scala.collection.JavaConverters._
import org.parse4j.{ParseQuery, ParseObject}

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import ExecutionContext.Implicits.global


object Util {

   def printToFile(fileName: String)(op: java.io.PrintWriter => Unit) {
     val p = new java.io.PrintWriter(new java.io.File(fileName))
     try { op(p) } finally { p.close() }
   }

   def finalAll(className: String): Stream[ParseObject] = {
     val compositeFuture = Future.sequence(findAll0("URLMapping"))
     val ret = (Await.result(compositeFuture, 1 minute) flatten).toStream
     println("data loaded!")
     ret
   }

   def byMonth(date: Date) = new Date(date.getYear, date.getMonth, 1)

   def toString(po: ParseObject) = {
     val kvm = po.keySet().asScala.toList.sorted.map(key => s"$key: '${po.get(key)}'") mkString ", "
     kvm + s", createdAt: '${po.getCreatedAt.toString}'"
   }

   def toString(list: List[ParseObject]): String = {
     (for (po <- list) yield toString(po)) mkString "\n"
   }

   def prettyPrint[T](list: Stream[(T, Int)]): Unit = {
     println(list mkString "\n")
   }

   // -- private

   private def findAll0(className: String): List[Future[Vector[ParseObject]]]= {
     def find(query: ParseQuery[ParseObject], from: Int, limit: Int) = {
       query.skip(from)
       query.limit(limit)
       Future(query.find().asScala.toVector)
     }

     val count = ParseQuery.getQuery(className).count()
     val skip = 1000
     val fromAndLimit = for (from <- 0 to count by skip) yield (from, if (from + skip < count) skip else count - from )
     println("fromAndLimit: " + fromAndLimit)

     (for((from, limit) <- fromAndLimit if limit > 0) yield find(ParseQuery.getQuery(className), from, limit)).toList
   }
 }
