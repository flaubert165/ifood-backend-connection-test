package presentation.controllers;

import play.mvc.Controller;
import play.mvc.Result;

import java.io.InputStream;

public class AssetController extends Controller {

    private Class<? extends AssetController> assetControllerType;

    public AssetController() {
        assetControllerType = this.getClass();
    }

    public Result get(String file) {
        InputStream stream = assetControllerType.getResourceAsStream(String.format("/public/%s", file));

        if (stream != null) {
            return ok(stream);
        }

        return redirect("/ui");
    }
}
