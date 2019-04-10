import scala.collection.mutable

object Players {
  private val _db = mutable.HashMap.empty[String, Int]

  def add(name: String): this.type = {
    _db += (name -> 0)
    this
  }

  def getPosition(name: String): Int = {
    _db(name)
  }

  def contains(name: String): Boolean = {
    _db.contains(name)
  }

  def all: Iterable[String] = {
    _db.keys
  }

  def move(name: String, pos: Int): this.type = {
    _db(name) = pos
    this
  }
}
