package service
import com.google.inject.Inject
import models.{Parameter, ParameterList}

import scala.concurrent.Future

class ParameterService @Inject()(items: ParameterList) {
  def addItems(item: Seq[Parameter]): Future[String] = {
    items.add(item)
  }
}
