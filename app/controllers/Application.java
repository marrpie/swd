package controllers;

import models.trainConnectionModel;
import services.trainConnectionService;
import dynamicprogramming.DynamicProgramming;
import play.*;
import play.mvc.*;

import views.html.*;

import javax.inject.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Application extends Controller {


    public Result search(String from, String to) {
        DynamicProgramming.getInstance().dynamicProgrammingAlgorithm(from, to);
        long milis = DynamicProgramming.getInstance().printTripTime();
        long hour = 1000*60*60;
        long min = 1000*60;
        Long mins = (milis%hour)/min;
        Long hours = (milis - (milis%hour))/hour;
        return ok(views.html.result.render(from, to, DynamicProgramming.getInstance().getRoute(), mins, hours));
    }

    public static Result index() {
        DynamicProgramming.getInstance().readGraph("train.txt");
/*        alg.readGraph("train.txt");
        alg.dynamicProgrammingAlgorith("Olsztyn", "ZielonaGora");
        alg.printTripTime();*/

        return ok(views.html.index.render("ko", DynamicProgramming.getInstance().getCities()));
    }



}
