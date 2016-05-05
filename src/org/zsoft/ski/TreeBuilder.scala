package org.zsoft.ski

import scala.annotation.tailrec
import scala.io.Source

/**
  * Created by Denys on 19/4/16.
  *




/Library/Java/JavaVirtualMachines/jdk1.8.0_65.jdk/Contents/Home/jre/bin/java -cp /Users/Denys/Documents/CLIENT/lib/castor-0.9.7.jar:/Users/Denys/Documents/CLIENT/lib/commons-codec-1.3.jar:/Users/Denys/Documents/CLIENT/lib/commons-httpclient-3.0-rc3.jar:/Users/Denys/Documents/CLIENT/lib/commons-lang.jar:/Users/Denys/Documents/CLIENT/lib/commons-logging.jar:/Users/Denys/Documents/CLIENT/lib/cryptix-jce-api.jar:/Users/Denys/Documents/CLIENT/lib/cryptix-jce-provider.jar:/Users/Denys/Documents/CLIENT/lib/cryptix-message-api.jar:/Users/Denys/Documents/CLIENT/lib/cryptix-openpgp-provider.jar:/Users/Denys/Documents/CLIENT/lib/cryptix-pki-api.jar:/Users/Denys/Documents/CLIENT/lib/dom4j.jar:/Users/Denys/Documents/CLIENT/lib/log4j-1.2.9.jar:/Users/Denys/Documents/CLIENT/lib/umapi_v1.5.jar:/Users/Denys/Documents/CLIENT/lib/xerces.jar com.wiz.enets2.transaction.util.PGPGeneratorApp
  */
class MatrixEntry(val value: Int, var used: Boolean = false)

class TreeBuilder(val fileName: String) {
  val directions: Array[Direction.Value] = Array(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)

  val (xDimension, yDimension, matrix) = {
    val strToIntArray = (s: String) => s.split("\\s").map(_.toInt);
    val strToMatrixEntryArray = (s: String) => s.split("\\s").map((v: String) => new MatrixEntry(v.toInt))

    val fileLines = Source.fromFile(fileName)
    val sizes: Array[Int] = fileLines.getLines().take(1).map(strToIntArray).toList.head
    require(sizes.length == 2)

    (sizes(0), sizes(1), fileLines.getLines().map(strToMatrixEntryArray).toArray)
  }

  def buildTree: Tree = {

    var maxTree: Tree = ???

    for (x <- 0 to xDimension - 1;
         y <- 0 to yDimension - 1;
         if (!matrix(x)(y).used);
         localTree: Tree = buildTree(x, y)) {
       if (maxTree < localTree) maxTree = localTree
    }

    maxTree
  }

  private def buildTree(row: Int, column: Int): Tree = {
    val root: TreeNode = new TreeNode(row, column, matrix(row)(column).value, None, Direction.NONE, 1)
    val tree: Tree = new Tree(root)
    buildNextNode(root)
    findTop(tree, Some(tree.root))
    if (!tree.isSingleStep) groomTree(tree.top)

    tree
  }

  private def buildNextNode(node: TreeNode) {
    directions.foreach(createNode(node, _) match {
      case Some(n) => buildNextNode(n)
      case None => None
    })
  }

  private def createNode(parent: TreeNode, direction: Direction.Value): Option[TreeNode] = {
    val tuple = direction match {
      case (Direction.NORTH) => (parent.row - 1, parent.column, parent.row - 1 >= 0)
      case (Direction.SOUTH) => (parent.row + 1, parent.column, parent.row + 1 < xDimension)
      case (Direction.EAST) => (parent.row, parent.column + 1, parent.column + 1 < yDimension)
      case (Direction.WEST) => (parent.row, parent.column - 1, parent.column - 1 >= 0)
    }
    val value: Option[Int] = {
      if (tuple._3 && matrix(tuple._1)(tuple._2).value < parent.value)
        Some(matrix(tuple._1)(tuple._2).value)
      else
        None
    }

    value match {
      case Some(valueNumber) => {
        val newNode = new TreeNode(tuple._1, tuple._2, valueNumber, Some(parent), direction, parent.position + 1)

        direction match {
          case (Direction.NORTH) => parent.north = Some(newNode)
          case (Direction.SOUTH) => parent.south = Some(newNode)
          case (Direction.EAST) => parent.east = Some(newNode)
          case (Direction.WEST) => parent.west = Some(newNode)
        }

        matrix(tuple._1)(tuple._2).used = true
        Some(newNode)
      }
      case None => None
    }
  }

  @tailrec
  private def groomTree(current: Option[TreeNode]): Option[TreeNode] = current match {
    case None => None
    case Some(node) => {
      node.direction match {
        case (Direction.NORTH) => {
          node.south = None
          node.east = None
          node.west = None
        }
        case (Direction.SOUTH) => {
          node.north = None
          node.east = None
          node.west = None
        }
        case (Direction.EAST) => {
          node.south = None
          node.north = None
          node.west = None
        }
        case (Direction.WEST) => {
          node.south = None
          node.east = None
          node.north = None
        }
      }
      node.parent match {
        case None => None
        case Some(p) => if (p.parent.isDefined) groomTree(node.parent) else None
      }
    }
  }

  private def findTop(tree: Tree, node: Option[TreeNode]) {
    node.map((nodeVal: TreeNode) => {
      tree.top match {
        case None => tree.top = Some(nodeVal)
        case Some(topVal) => {
          if (topVal.position < nodeVal.position) {
            tree.top = Some(nodeVal)
          }
          else if (topVal.position == nodeVal.position
            && (tree.root.value - topVal.value) < (tree.root.value - nodeVal.value)) {
            tree.top = Some(nodeVal)
          }
        }
      }
      Array(nodeVal.north, nodeVal.south, nodeVal.east, nodeVal.west).foreach(findTop(tree, _))
    })
  }
}