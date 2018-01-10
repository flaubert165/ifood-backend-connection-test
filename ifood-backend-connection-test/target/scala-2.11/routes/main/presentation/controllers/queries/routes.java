
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/italosantana/Desktop/ifood-backend-connection-test/ifood-backend-connection-test/conf/routes
// @DATE:Wed Jan 10 10:38:33 BRT 2018

package presentation.controllers.queries;

import router.RoutesPrefix;

public class routes {
  
  public static final presentation.controllers.queries.ReverseUserQueryController UserQueryController = new presentation.controllers.queries.ReverseUserQueryController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final presentation.controllers.queries.javascript.ReverseUserQueryController UserQueryController = new presentation.controllers.queries.javascript.ReverseUserQueryController(RoutesPrefix.byNamePrefix());
  }

}
