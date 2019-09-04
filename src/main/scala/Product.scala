import scala.collection.mutable.ArrayBuffer

class Product(val productID: ProductKey,val productGroup: Int, val arrPurchases:ArrayBuffer[Int]) {
  private var idX = productID
  private var groupX = productGroup
  private val purchasesX = arrPurchases
  def id:ProductKey= id
  def group:Int = group
  def purchases:ArrayBuffer[Int] = purchasesX
  def id_= (newValue: ProductKey): Unit = {
    idX = newValue
  }
  def group_= (newValuee: Int): Unit = {
  groupX = newValuee
  }
  override def toString: String =
    "(" + idX + ", " + groupX+", "+purchasesX+")"
}

