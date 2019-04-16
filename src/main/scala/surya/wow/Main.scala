package surya.wow


import scala.io.Source
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox
import java.io.{File, PrintWriter}

import surya.wow.aws.AWS
import xsbti.Exit

object Main extends xsbti.AppMain {
  def main(args: Array[String]) {
    val toolbox = currentMirror.mkToolBox()

    val fileContents = Source.fromFile("/home/surya/code/wow/src/main/examples/oneInstanceWithEip.scala").getLines.mkString("\n")
    val tree = toolbox.parse(fileContents)
    toolbox.compile(tree)
    val k = toolbox.eval(tree)
    AWS.create

  }

  def run(configuration: xsbti.AppConfiguration) = {
    // get the version of Scala used to launch the application
    val scalaVersion = configuration.provider.scalaProvider.version

    // Print a message and the arguments to the application
    println("Hello world!  Running Scala " + scalaVersion)
    configuration.arguments.foreach(println)

    // demonstrate the ability to reboot the application into different versions of Scala
    // and how to return the code to exit with
    scalaVersion match {
      case "2.10.6" =>
        new xsbti.Reboot {
          def arguments = configuration.arguments

          def baseDirectory = configuration.baseDirectory

          def scalaVersion = "2.11.8"

          def app = configuration.provider.id
        }
      case "2.11.8" => new Exit(1)
      case _ => new Exit(0)
    }
  }

  class Exit(val code: Int) extends xsbti.Exit


}