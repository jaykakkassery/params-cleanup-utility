package tasks

import akka.actor.ActorSystem
import models.Parameter
import play.api.libs.json.{JsArray, JsObject, JsValue}
import play.api.libs.ws
import play.api.libs.ws.WSRequest
import service.ParameterService

import javax.inject.Inject
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import play.api.libs.ws._


class CodeBlockTask @Inject() (actorSystem: ActorSystem,
                               parameterService: ParameterService,
                               ws: WSClient)(implicit executionContext: ExecutionContext) {
  actorSystem.scheduler.scheduleAtFixedRate(initialDelay = 10.seconds, interval = 1.minute) { () =>
    paraReadWrite
    actorSystem.log.info("Executing something...")
  }
  private def paraReadWrite = {
    println("inside param write")
    val url = "http://localhost:10000/parameter/statistics"
    val request: WSRequest = ws.url(url)
    val complexRequest: WSRequest =
      request
        .addHttpHeaders("Accept" -> "application/json")
        .addQueryStringParameters("search" -> "play")
        .withRequestTimeout(10000.millis)

    complexRequest.get().map{response =>
      val c = allKeys(response.json)
      println("All Keys.."+ c)
      val parameter = c.map(a => Parameter(0, a)).toSeq
      parameterService.addItems(parameter)

    }
  }

  def allKeys(json: JsValue): collection.Set[String] = json match {
    case o: JsObject => o.keys ++ o.values.flatMap(allKeys)
    case JsArray(as) => as.flatMap(allKeys).toSet
    case _ => Set()
  }
}