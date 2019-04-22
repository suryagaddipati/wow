package surya.wow


import java.io.File

import sbt._

final class Main extends xsbti.AppMain {

  class Exit(val code: Int) extends xsbti.Exit

  def run(configuration: xsbti.AppConfiguration): xsbti.MainResult = {
    MainLoop.runLogged(initialState(configuration))
  }

  def initialState(configuration: xsbti.AppConfiguration): sbt.State = {
    val commandDefinitions = create +: BasicCommands.allBasicCommands
    val commandsToRun = "iflast shell" +: configuration.arguments.map(_.trim)
    sbt.State(configuration, commandDefinitions, Set.empty, None, commandsToRun, sbt.State.newHistory,
      AttributeMap.empty, initialGlobalLogging, sbt.State.Continue)
  }

  val create = Command.args("create", "fileName") { (s, args) =>
    Commands.create(s.log, args(0))
    s
  }

  def initialGlobalLogging: GlobalLogging =
    GlobalLogging.initial(MainLogging.globalDefault(ConsoleOut.systemOut), File.createTempFile("hello", "log"), ConsoleOut.systemOut)
}