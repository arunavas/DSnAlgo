package test.arunava.scala.graph

object DiGraphTest {
  def main(args: Array[String]): Unit = {
    val graph = DiGraph(Nil).addVertex(1).addVertex(2).addVertex(3).addVertex(4).addVertex(5)
                            .addEdge(1, 2).addEdge(2, 3).addEdge(3, 4).addEdge(4, 5).addEdge(5, 1)
                            .addEdge(2, 4).addEdge(2, 5)
    println(graph)
    graph dfs(2)
    graph bfs(2)
  }
}