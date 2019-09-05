import java.sql.{Connection, DriverManager}
import java.util
import java.util.Map

import scala.collection.immutable.LinearSeq
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Main
{
  val mapIdGroup = new mutable.HashMap[Int,Int]();
  val mapIdYears  =  new mutable.HashMap[Int,Set[Int]]();
  val seqProduct = ArrayBuffer[Product]()
  val randomID = scala.util.Random
  val randomYear =  scala.util.Random
  val randomGroup = scala.util.Random
  val randomPurchases = scala.util.Random
  val arrSize = 12
  val arrPurchases = new ArrayBuffer[Int](arrSize)
  var i = 0
  val amountRecords = 20000
  val maxNumberGroup = 9
  val minYear = 2015
  val maxYear = 2018
  val maxAmountMoney = 100000
  var counter=0

  def main(args: Array[String]): Unit = {
    generate()
    loadData()
  }

  def loadData(): Unit = {
    var flagClosed = false;
    try {
      val env = System.getenv
      print(env)
      val connectDB = new LoadData(env.getOrDefault("TABLE_NAME","table223123123"));
      connectDB.createSpecificTable()
      connectDB.loadData(seqProduct)
      flagClosed = connectDB.closeConnection()
    }
    catch {
      case ex: Exception => print(ex)
    }
    finally
      if(!flagClosed)
        print("Connection is open")
  }

  def generate(): Unit = {
    while (i < amountRecords) {
      val id = randomID.nextInt(amountRecords / 4)
      val group = randomGroup.nextInt(maxNumberGroup + 1)
      val year = randomYear.nextInt(maxYear - minYear + 1) + minYear
      counter = 0
      while (counter < arrSize) {
        arrPurchases.insert(counter, randomPurchases.nextInt(maxAmountMoney))
        counter += 1
      }
      if (mapIdYears.contains(id)) {
        if (!mapIdYears.getOrElse(id, sys.error(s"unexpected key:")).contains(year)) {
          val rightGroup = mapIdGroup.getOrElse(id, sys.error(s"unexpected key:"))
          val product = new Product(id, rightGroup, year, arrPurchases)
          seqProduct.addOne(product)
          var years = mapIdYears.getOrElse(id, sys.error(s"unexpected key:"))
          years += year
          mapIdYears.update(id, years)
        }
      }
      else {
        val product = new Product(id, group, year, arrPurchases)
        seqProduct.addOne(product)
        mapIdGroup.addOne(id, group)
        val value = Set[Int](year)
        mapIdYears.addOne(id, value)
      }
      i += 1
    }
    seqProduct.foreach {
      println
    }
  }
}
