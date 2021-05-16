package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import scala.concurrent.duration._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext

import play.api.libs.json._

import play.api.libs.ws._
import models.Parameter

import service.ParameterService


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                               val ws: WSClient,
                               ec: ExecutionContext,
                               parameterService: ParameterService) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def allKeys(json: JsValue): collection.Set[String] = json match {
    case o: JsObject => o.keys ++ o.values.flatMap(allKeys)
    case JsArray(as) => as.flatMap(allKeys).toSet
    case _ => Set()
  }

}
