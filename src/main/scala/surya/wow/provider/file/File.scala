package surya.wow.provider.file

import surya.wow.{ProviderWithState, Resource}

object File extends ProviderWithState {
  def file(name: String, dir: Dir, content: String): File = :+(File(name, dir, content))

  def dir(name: String): Dir = :+(Dir(name))

  case class Dir(name: String) extends Resource {
    override def create(): Any = ???
  }

  case class File(name: String, dir: Dir, content: String) extends Resource {
    override def create(): Any = ???
  }

}
