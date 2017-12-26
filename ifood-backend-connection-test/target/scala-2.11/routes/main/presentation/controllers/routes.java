
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/italosantana/Desktop/ifood-backend-connection-test/ifood-backend-connection-test/conf/routes
// @DATE:Tue Dec 26 10:38:28 BRT 2017

package presentation.controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final presentation.controllers.ReverseUnavalabilityScheduleController UnavalabilityScheduleController = new presentation.controllers.ReverseUnavalabilityScheduleController(RoutesPrefix.byNamePrefix());
  public static final presentation.controllers.ReverseUserController UserController = new presentation.controllers.ReverseUserController(RoutesPrefix.byNamePrefix());
  public static final presentation.controllers.ReverseAuthenticationController AuthenticationController = new presentation.controllers.ReverseAuthenticationController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final presentation.controllers.javascript.ReverseUnavalabilityScheduleController UnavalabilityScheduleController = new presentation.controllers.javascript.ReverseUnavalabilityScheduleController(RoutesPrefix.byNamePrefix());
    public static final presentation.controllers.javascript.ReverseUserController UserController = new presentation.controllers.javascript.ReverseUserController(RoutesPrefix.byNamePrefix());
    public static final presentation.controllers.javascript.ReverseAuthenticationController AuthenticationController = new presentation.controllers.javascript.ReverseAuthenticationController(RoutesPrefix.byNamePrefix());
  }

}
