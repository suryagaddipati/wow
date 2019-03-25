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
    val provider = tree.asInstanceOf[Apply]

    println(provider)
    val apps = applies(provider.children(1))
    println(apps)

  }

  def instance(name: String)(f: String => Unit): Unit = {

  }

  instance(name = "meow")(mew => ""

  )

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