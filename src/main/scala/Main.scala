import scala.collection.immutable.LinearSeq
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Main {
  def main(args: Array[String]): Unit = {
    var oif = new mutable.HashSet[ProductID]()
    var seqProduct = ArrayBuffer[Product]()
    val randomID = scala.util.Random
    val randomYear =  scala.util.Random
    val randomGroup = scala.util.Random
    val randomPurchases = scala.util.Random
    val arrSize = 12
    var i = 0
    var counter = 0
    while(i<20000)
    {
      val productID = ProductID(randomID.nextInt(20000),randomYear.nextInt(4)+2015)
      if(oif.contains(productID))
        println("contains")
      else {
        val arrPurchases = new ArrayBuffer[Int](arrSize)
        counter = 0
        while(counter<12) {
          arrPurchases.addOne(randomPurchases.nextInt(100000))
          counter+=1
        }
        val product = new Product(productID,randomGroup.nextInt(10),arrPurchases)
        oif.add(productID)
        seqProduct.addOne(product)
        i+=1
        println(i)
      }
    };
    //oif.foreach { println }
    seqProduct.foreach{println}
    println(seqProduct.length +"size common all")

    println("Hello, world!")
  }
}
