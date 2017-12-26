
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/italosantana/Desktop/ifood-backend-connection-test/ifood-backend-connection-test/conf/routes
// @DATE:Sun Dec 24 18:52:55 BRT 2017


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
