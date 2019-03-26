package wow


import scala.io.Source
import scala.reflect.runtime.currentMirror
import scala.reflect.runtime.universe._
import scala.tools.reflect.ToolBox


object Main {
  def main(args: Array[String]) {

    val toolbox = currentMirror.mkToolBox()

    val fileContents = Source.fromFile("/home/surya/code/wow/src/main/examples/ec2.scala").getLines.mkString("\n")
    val tree = toolbox.parse("import wow._ ; " + fileContents)
    toolbox.compile(tree)
    //    val provider = tree.children(0).asInstanceOf[Apply]
    //   println(provider)


    val apps = applies(tree.children(1))
    println(apps)

  }


  def applies(tree: Tree) = {
    traverser.traverse(tree)
    traverser.applies
  }

  object traverser extends Traverser {
    var applies = List[Apply]()

    override def traverse(tree: Tree): Unit = tree match {
      case app@Apply(fun, args) =>
        applies = app :: applies
        super.traverse(fun)
        super.traverseTrees(args)
      case _ => super.traverse(tree)
    }

  }

}