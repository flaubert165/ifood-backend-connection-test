package presentation;

import domain.entities.User;
import play.Play;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Security;

import java.util.Date;

public class Secured extends Security.Authenticator {

    /*public static final String UNAUTHENTICATED = "unauthenticated";

    public static User getLoggedInUser() {
        if ( Controller.session("userId"); == null)
            return null;
        return User.findById(Long.parseLong(Controller.session("userId")));
    }

    public static String getLoggedInUsername() {
        if (Controller.session("userId") == null)
            return null;
        return User.findById(Long.parseLong(Controller.session("userId"))).getUsername();
    }


    @Override
    public String getUsername(Http.Context ctx) {

        // see if user is logged in
        if (Controller.session("userId") == null)
            return null;

        // see if the session is expired
        String previousTick = Controller.session("userTime");
        if (previousTick != null && !previousTick.equals("")) {
            long previousT = Long.valueOf(previousTick);
            long currentT = new Date().getTime();
            long timeout = Long.valueOf(Play.application().configuration().getString("sessionTimeout")) * 1000 * 60;
            if ((currentT - previousT) > timeout) {
                // session expired
                Controller.session().clear();
                return null;
            }
        }

        // update time in session
        String tickString = Long.toString(new Date().getTime());
        Controller.session("userTime", tickString);

        return User.findById(Long.parseLong(Controller.session("userId"))).getUsername();
    }*/
}
