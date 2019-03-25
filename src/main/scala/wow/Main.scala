package wow


import scala.io.Source
import scala.reflect.runtime.currentMirror
import scala.reflect.runtime.universe._
import scala.tools.reflect.ToolBox


object Main {
  def main(args: Array[String]) {

    val toolbox = currentMirror.mkToolBox()

    val fileContents = Source.fromFile("/home/surya/code/devops/external.scala").getLines.mkString("\n")
    val tree = toolbox.parse(fileContents)
    traverser.traverse(tree)
    println(traverser.applies)
  }

  def instance(name: String)(f: String => Unit): Unit = {

  }

  instance(name = "meow") { meow =>

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