
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/italosantana/Desktop/ifood-backend-connection-test/ifood-backend-connection-test/conf/routes
// @DATE:Thu Dec 28 10:18:55 BRT 2017

import play.api.routing.JavaScriptReverseRoute
import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package presentation.controllers.javascript {
  import ReverseRouteContext.empty

  // @LINE:14
  class ReverseUnavalabilityScheduleController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:14
    def create: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "presentation.controllers.UnavalabilityScheduleController.create",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "scheduling/register"})
        }
      """
    )
  
    // @LINE:16
    def delete: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "presentation.controllers.UnavalabilityScheduleController.delete",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "scheduling/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id0)})
        }
      """
    )
  
    // @LINE:15
    def getByUserId: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "presentation.controllers.UnavalabilityScheduleController.getByUserId",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "scheduling/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id0)})
        }
      """
    )
  
  }

  // @LINE:10
  class ReverseUserController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def getAll: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "presentation.controllers.UserController.getAll",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "user"})
        }
      """
    )
  
    // @LINE:11
    def create: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "presentation.controllers.UserController.create",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "user/register"})
        }
      """
    )
  
  }

  // @LINE:6
  class ReverseAuthenticationController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:7
    def status: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "presentation.controllers.AuthenticationController.status",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "auth/status"})
        }
      """
    )
  
    // @LINE:6
    def login: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "presentation.controllers.AuthenticationController.login",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "auth/login"})
        }
      """
    )
  
  }


}
