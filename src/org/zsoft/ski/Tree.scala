package org.zsoft.ski

/**
  * Created by Denys on 27/4/16.
  */
class Tree(val root: TreeNode) extends Ordered[Tree] with Iterable[TreeNode] {
  var top: Option[TreeNode] = None

  override def compare(that: Tree): Int = {
    val topNodeLocal = getTop
    val topNodeOther = that.getTop

    if (topNodeLocal.position != topNodeOther.position) {
      topNodeLocal.position - topNodeOther.position
    }
    else {
      (root.value - topNodeLocal.value) - (that.root.value - topNodeOther.value)
    }
  }

  def getTop : TreeNode = {
    top.getOrElse(throw new IllegalStateException("Top is not ready"))
  }

  def result: String = {
    s"${getPathLength}${getDrop}@redmart.com"
  }

  def getDrop: Int = {
    root.value - getTop.value
  }

  def getPathLength: Int = {
    getTop.position
  }

  def isSingleStep : Boolean = {
    root == getTop
  }

  override def iterator: Iterator[TreeNode] = new Iterator[TreeNode] {
    var current = getTop

    def hasNext = current.parent.isDefined
    def next = {
      val ret = current
      current = current.parent.getOrElse(throw new NoSuchElementException)
      ret
    }
  }
}
