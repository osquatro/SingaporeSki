package org.zsoft.ski

import org.zsoft.ski.CountDiv

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
//    println(BinaryGap.gap( 2345 ))
//    println(BinaryGap.gap( 10012034 ))
//    println(BinaryGap.gap( -1 ))
//    println(BinaryGap.gap( 0 ))
//    println(BinaryGap.gap( 99999999 ))
//    print(FrogRiverOne.solution(5, Array(1, 3, 1, 4, 2, 3, 5, 4)))
//    println(CountDiv.solution(A = 6, B = 11, K = 2))
//    println( CountDiv.solution(A = 11, B = 345, K = 17)         )
//    println( CountDiv.solution( A = 10, B = 10, K=5))// in {5,7,20}
//     println( CountDiv.solution( A = 0, B = Integer.MAX_VALUE, K=Integer.MAX_VALUE))// in {5,7,20}
     //println(PermCheck.solution(Array(4, 1, 3)))

    //println(MissingInteger.solution(Array(1, 3, 6, 4, 1, 2)))
    //assert(MaxCounters.solution(5, Array(3, 4, 4, 6, 1, 4, 4)).deep == Array(3, 2, 2, 4, 2).deep    )
    //MaxCounters.solution(5, Array(3, 4, 4, 6, 1, 4, 4)).foreach((x:Int) => println(x))
    //Triangle.solution(Array(10, 2, 5, 1, 8, 20))
    println(7.0/2)
  }
}
