package wow

trait Provider {
  def create(resource: Resource): Any
}


