package surya.wow

case class State(resources: Resource*) {
  def :+(r: Resource): State = State(resources :+ r: _*)

  def has(r: Resource): Boolean = resources.contains(r)

  def save() = {
    writeState(JsonUtil.toJson(this))
    this
  }

  def writeState(state: String) = {
    import java.io.PrintWriter
    new PrintWriter("state.json") {
      write(state);
      close
    }
  }


}

object State {
  def current() = {
    readState() match {
      case Some(s) => s
      case None => State()
    }
  }

  import java.nio.file.{Paths, Files}

  def readState() = {
    if (Files.exists(Paths.get("state.json"))) {
      val source = scala.io.Source.fromFile("state.json")
      val state = try source.mkString finally source.close()
      try {
        Some(JsonUtil.fromJson[State](state))
      } catch {
        case e => {
          e.printStackTrace();
          None
        }
      }
    } else {
      None
    }
  }
}
