import scala.collection.mutable.ArrayBuffer

class Product(val productID: Int,val productGroup:Int, val productYear:Int, val arrPurchases:ArrayBuffer[Int]) {
  private val idX = productID
  private val groupX = productGroup
  private val purchasesX = arrPurchases
  private val yearX = productYear
  def id: Int= idX
  def year: Int= yearX
  def group:Int= groupX
  def purchases:ArrayBuffer[Int] = purchasesX
  override def toString: String =
    "(" + idX + ", " + groupX+", "+yearX+")"
}

