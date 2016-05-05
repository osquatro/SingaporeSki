package org.zsoft.ski

/**
  * Created by Denys on 27/4/16.
  */
class TreeNode(val row: Int,
               val column: Int,
               val value: Int,
               val parent: Option[TreeNode],
               val direction: Direction.Value,
               val position: Int) {
  var north: Option[TreeNode] = None
  var south: Option[TreeNode] = None
  var west: Option[TreeNode] = None
  var east: Option[TreeNode] = None
}
