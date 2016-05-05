package org.zsoft.ski

/**
  * Created by Denys on 9/12/15.
  */


object Main {
  def main(args: Array[String]): Unit = {

    val tree: Tree = new TreeBuilder("input.txt").buildTree;
    for (node : TreeNode <- tree) {
      println(s"x:${node.row}, y:${node.column}, value: ${node.value}")
    }
    println(tree.result)
  }
}
