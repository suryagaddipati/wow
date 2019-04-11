package wow

import wow.aws.AWS

import scala.collection.mutable

case class Plan(resources: mutable.MutableList[Resource]) {

  override def toString() = s"creating ${roots}"

  implicit val awsProvider = AWS


  def saveState(state: State) = writeState(JsonUtil.toJson(state))

  def create() = saveState(roots.foldLeft(currentState) { (s, r) =>
    if (s.has(r)) {
      println(s"Resource ${r} exists. Skipping..")
      s
    } else {
      val resp = r.create
      s :+ r
    }
  })

  def currentState() = {
    readState() match {
      case Some(s) => s
      case None => State(List())
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

  def writeState(state: String) = {
    import java.io.PrintWriter
    new PrintWriter("state.json") {
      write(state);
      close
    }
  }

  def roots: List[Resource] = {
    var rList = resources.toList
    resources.foreach(r => {
      rList = rList.filter(rr => !r.dependencies.contains(rr))
    })
    rList
  }

}
