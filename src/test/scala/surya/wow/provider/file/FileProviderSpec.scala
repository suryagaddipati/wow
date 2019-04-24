package surya.wow.provider.file

import org.scalatest.FlatSpec

class FileProviderSpec extends FlatSpec {

  "FileProvider" should "manage filesystem" in {
    val dir = File.dir("/tmp/meow")
    val file = File.file(name = "meow.txt ", dir = dir, content = "content")
    println(File.changes)
  }

}
