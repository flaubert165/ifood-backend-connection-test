
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/italosantana/Desktop/ifood-backend-connection-test/ifood-backend-connection-test/conf/routes
// @DATE:Tue Dec 26 10:38:28 BRT 2017

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  AuthenticationController_1: presentation.controllers.AuthenticationController,
  // @LINE:10
  UserController_3: presentation.controllers.UserController,
  // @LINE:14
  UnavalabilityScheduleController_0: presentation.controllers.UnavalabilityScheduleController,
  // @LINE:19
  Assets_2: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    AuthenticationController_1: presentation.controllers.AuthenticationController,
    // @LINE:10
    UserController_3: presentation.controllers.UserController,
    // @LINE:14
    UnavalabilityScheduleController_0: presentation.controllers.UnavalabilityScheduleController,
    // @LINE:19
    Assets_2: controllers.Assets
  ) = this(errorHandler, AuthenticationController_1, UserController_3, UnavalabilityScheduleController_0, Assets_2, "/")

  import ReverseRouteContext.empty

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, AuthenticationController_1, UserController_3, UnavalabilityScheduleController_0, Assets_2, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """auth/login""", """presentation.controllers.AuthenticationController.login()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """auth/""", """presentation.controllers.AuthenticationController.logout()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """user""", """presentation.controllers.UserController.getAll()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """user/register""", """presentation.controllers.UserController.create()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """scheduling/register""", """presentation.controllers.UnavalabilityScheduleController.create()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """scheduling/""" + "$" + """id<[^/]+>""", """presentation.controllers.UnavalabilityScheduleController.getByUserId(id:Long)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """scheduling/""" + "$" + """id<[^/]+>""", """presentation.controllers.UnavalabilityScheduleController.delete(id:Long)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """ui""", """controllers.Assets.at(path:String = "/public", file:String = "index.html")"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.at(path:String = "/public", file:String)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val presentation_controllers_AuthenticationController_login0_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("auth/login")))
  )
  private[this] lazy val presentation_controllers_AuthenticationController_login0_invoker = createInvoker(
    AuthenticationController_1.login(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "presentation.controllers.AuthenticationController",
      "login",
      Nil,
      "POST",
      """AUTH ROUTES""",
      this.prefix + """auth/login"""
    )
  )

  // @LINE:7
  private[this] lazy val presentation_controllers_AuthenticationController_logout1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("auth/")))
  )
  private[this] lazy val presentation_controllers_AuthenticationController_logout1_invoker = createInvoker(
    AuthenticationController_1.logout(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "presentation.controllers.AuthenticationController",
      "logout",
      Nil,
      "GET",
      """""",
      this.prefix + """auth/"""
    )
  )

  // @LINE:10
  private[this] lazy val presentation_controllers_UserController_getAll2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("user")))
  )
  private[this] lazy val presentation_controllers_UserController_getAll2_invoker = createInvoker(
    UserController_3.getAll(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "presentation.controllers.UserController",
      "getAll",
      Nil,
      "GET",
      """USER ROUTES""",
      this.prefix + """user"""
    )
  )

  // @LINE:11
  private[this] lazy val presentation_controllers_UserController_create3_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("user/register")))
  )
  private[this] lazy val presentation_controllers_UserController_create3_invoker = createInvoker(
    UserController_3.create(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "presentation.controllers.UserController",
      "create",
      Nil,
      "POST",
      """""",
      this.prefix + """user/register"""
    )
  )

  // @LINE:14
  private[this] lazy val presentation_controllers_UnavalabilityScheduleController_create4_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("scheduling/register")))
  )
  private[this] lazy val presentation_controllers_UnavalabilityScheduleController_create4_invoker = createInvoker(
    UnavalabilityScheduleController_0.create(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "presentation.controllers.UnavalabilityScheduleController",
      "create",
      Nil,
      "POST",
      """SCHEDULE ROUTES""",
      this.prefix + """scheduling/register"""
    )
  )

  // @LINE:15
  private[this] lazy val presentation_controllers_UnavalabilityScheduleController_getByUserId5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("scheduling/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val presentation_controllers_UnavalabilityScheduleController_getByUserId5_invoker = createInvoker(
    UnavalabilityScheduleController_0.getByUserId(fakeValue[Long]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "presentation.controllers.UnavalabilityScheduleController",
      "getByUserId",
      Seq(classOf[Long]),
      "GET",
      """""",
      this.prefix + """scheduling/""" + "$" + """id<[^/]+>"""
    )
  )

  // @LINE:16
  private[this] lazy val presentation_controllers_UnavalabilityScheduleController_delete6_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("scheduling/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val presentation_controllers_UnavalabilityScheduleController_delete6_invoker = createInvoker(
    UnavalabilityScheduleController_0.delete(fakeValue[Long]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "presentation.controllers.UnavalabilityScheduleController",
      "delete",
      Seq(classOf[Long]),
      "DELETE",
      """""",
      this.prefix + """scheduling/""" + "$" + """id<[^/]+>"""
    )
  )

  // @LINE:19
  private[this] lazy val controllers_Assets_at7_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("ui")))
  )
  private[this] lazy val controllers_Assets_at7_invoker = createInvoker(
    Assets_2.at(fakeValue[String], fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "at",
      Seq(classOf[String], classOf[String]),
      "GET",
      """OTHERS""",
      this.prefix + """ui"""
    )
  )

  // @LINE:20
  private[this] lazy val controllers_Assets_at8_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_at8_invoker = createInvoker(
    Assets_2.at(fakeValue[String], fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "at",
      Seq(classOf[String], classOf[String]),
      "GET",
      """""",
      this.prefix + """assets/""" + "$" + """file<.+>"""
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case presentation_controllers_AuthenticationController_login0_route(params) =>
      call { 
        presentation_controllers_AuthenticationController_login0_invoker.call(AuthenticationController_1.login())
      }
  
    // @LINE:7
    case presentation_controllers_AuthenticationController_logout1_route(params) =>
      call { 
        presentation_controllers_AuthenticationController_logout1_invoker.call(AuthenticationController_1.logout())
      }
  
    // @LINE:10
    case presentation_controllers_UserController_getAll2_route(params) =>
      call { 
        presentation_controllers_UserController_getAll2_invoker.call(UserController_3.getAll())
      }
  
    // @LINE:11
    case presentation_controllers_UserController_create3_route(params) =>
      call { 
        presentation_controllers_UserController_create3_invoker.call(UserController_3.create())
      }
  
    // @LINE:14
    case presentation_controllers_UnavalabilityScheduleController_create4_route(params) =>
      call { 
        presentation_controllers_UnavalabilityScheduleController_create4_invoker.call(UnavalabilityScheduleController_0.create())
      }
  
    // @LINE:15
    case presentation_controllers_UnavalabilityScheduleController_getByUserId5_route(params) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        presentation_controllers_UnavalabilityScheduleController_getByUserId5_invoker.call(UnavalabilityScheduleController_0.getByUserId(id))
      }
  
    // @LINE:16
    case presentation_controllers_UnavalabilityScheduleController_delete6_route(params) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        presentation_controllers_UnavalabilityScheduleController_delete6_invoker.call(UnavalabilityScheduleController_0.delete(id))
      }
  
    // @LINE:19
    case controllers_Assets_at7_route(params) =>
      call(Param[String]("path", Right("/public")), Param[String]("file", Right("index.html"))) { (path, file) =>
        controllers_Assets_at7_invoker.call(Assets_2.at(path, file))
      }
  
    // @LINE:20
    case controllers_Assets_at8_route(params) =>
      call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        controllers_Assets_at8_invoker.call(Assets_2.at(path, file))
      }
  }
}