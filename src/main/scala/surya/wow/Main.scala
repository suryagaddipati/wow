package surya.wow


import scala.io.Source
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox
import sbt._
import java.io.{File, PrintWriter}

import surya.wow.aws.AWS

object Main extends xsbti.AppMain {
  def main(args: Array[String]) {
    val toolbox = currentMirror.mkToolBox()

    val fileContents = Source.fromFile("/home/surya/code/wow/src/main/examples/oneInstanceWithEip.scala").getLines.mkString("\n")
    val tree = toolbox.parse(fileContents)
    toolbox.compile(tree)
    val k = toolbox.eval(tree)
    AWS.create

  }

  /** Defines the entry point for the application.
    * The call to `initialState` sets up the application.
    * The call to runLogged starts command processing. */
  def run(configuration: xsbti.AppConfiguration): xsbti.MainResult =
    MainLoop.runLogged(initialState(configuration))

  /** Sets up the application by constructing an initial State instance with the supported commands
    * and initial commands to run.  See the State API documentation for details. */
  def initialState(configuration: xsbti.AppConfiguration): sbt.State = {
    val commandDefinitions = hello +: BasicCommands.allBasicCommands
    val commandsToRun = Hello +: "iflast shell" +: configuration.arguments.map(_.trim)
    sbt.State(configuration, commandDefinitions, Set.empty, None, commandsToRun, sbt.State.newHistory,
      AttributeMap.empty, initialGlobalLogging, sbt.State.Continue)
  }

  // defines an example command.  see the Commands page for details.
  val Hello = "hello"
  val hello = Command.command(Hello) { s =>
    s.log.info("Hello!")
    s
  }

  /** Configures logging to log to a temporary backing file as well as to the console.
    * An application would need to do more here to customize the logging level and
    * provide access to the backing file (like sbt's last command and logLevel setting). */
  def initialGlobalLogging: GlobalLogging =
    GlobalLogging.initial(MainLogging.globalDefault(ConsoleOut.systemOut), File.createTempFile("hello", "log"), ConsoleOut.systemOut)

}