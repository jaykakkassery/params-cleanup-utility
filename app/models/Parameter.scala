package models

import com.google.inject.Inject
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.MySQLProfile.api._

case class Parameter(id: Long, name: String)


class ParameterTableDef(tag: Tag) extends Table[Parameter](tag, "parameter") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def isComplete = column[Boolean]("isComplete")

  override def * = (id, name) <> (Parameter.tupled, Parameter.unapply)
}

class ParameterList @Inject()(
                          protected val dbConfigProvider: DatabaseConfigProvider
                        )(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  var parameterList = TableQuery[ParameterTableDef]

  def add(parameterItem: Seq[Parameter]): Future[String] = {
    dbConfig.db
      .run(parameterList ++= parameterItem)
      .map(res => "TodoItem successfully added")
      .recover {
        case ex: Exception => {
          printf(ex.getMessage())
          ex.getMessage
        }
      }
  }
}

