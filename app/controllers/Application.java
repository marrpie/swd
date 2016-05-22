package controllers;

import models.trainConnectionModel;
import services.trainConnectionService;
import play.*;
import play.mvc.*;

import views.html.*;

import java.util.List;

public class Application extends Controller {

    public static Result index() {
        List<trainConnectionModel> models = trainConnectionService.createSampleConnection();
        return ok(index.render("ko", models));
    }

}
