package controllers;

import dynamicprogramming.DynamicProgramming;
import play.mvc.*;

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

        return ok(views.html.index.render("ko", DynamicProgramming.getInstance().getCities()));
    }



}
