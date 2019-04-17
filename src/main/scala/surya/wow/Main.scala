package surya.wow


import java.io.{File, PrintWriter}

import xsbti.Exit
import sbt._

final class Main extends xsbti.AppMain {

  class Exit(val code: Int) extends xsbti.Exit

  def run(configuration: xsbti.AppConfiguration): xsbti.MainResult = {
    val scalaVersion = configuration.provider.scalaProvider.version

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
    MainLoop.runLogged(initialState(configuration))
  }

  def initialState(configuration: xsbti.AppConfiguration): sbt.State = {
    val commandDefinitions = hello +: BasicCommands.allBasicCommands
    val commandsToRun = Hello +: "iflast shell" +: configuration.arguments.map(_.trim)
    sbt.State(configuration, commandDefinitions, Set.empty, None, commandsToRun, sbt.State.newHistory,
      AttributeMap.empty, initialGlobalLogging, sbt.State.Continue)
  }

  val Hello = "hello"
  val hello = Command.command(Hello) { s =>
    s.log.info("Hello!")
    s
  }

  def initialGlobalLogging: GlobalLogging =
    GlobalLogging.initial(MainLogging.globalDefault(ConsoleOut.systemOut), File.createTempFile("hello", "log"), ConsoleOut.systemOut)
}