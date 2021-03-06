
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/italosantana/Desktop/ifood-backend-connection-test/ifood-backend-connection-test/conf/routes
// @DATE:Wed Jan 10 10:38:33 BRT 2018

import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package presentation.controllers {

  // @LINE:15
  class ReverseUnavalabilityScheduleController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:15
    def create(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "scheduling/register")
    }
  
    // @LINE:17
    def delete(id:Long): Call = {
      import ReverseRouteContext.empty
      Call("DELETE", _prefix + { _defaultPrefix } + "scheduling/" + implicitly[PathBindable[Long]].unbind("id", id))
    }
  
    // @LINE:16
    def getByUserId(id:Long): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "scheduling/" + implicitly[PathBindable[Long]].unbind("id", id))
    }
  
  }

  // @LINE:10
  class ReverseUserController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def getAll(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "user")
    }
  
    // @LINE:12
    def create(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "user/register")
    }
  
  }

  // @LINE:6
  class ReverseAuthenticationController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:7
    def status(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "auth/status")
    }
  
    // @LINE:6
    def login(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "auth/login")
    }
  
  }


}
