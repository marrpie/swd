package controllers;

import dynamicprogramming.DynamicProgramming;
import play.mvc.*;

import java.util.List;

public class Application extends Controller {


    public Result search(String from, String to) {
        DynamicProgramming.getInstance().dynamicProgrammingAlgorithm(from, to);
        if(DynamicProgramming.getInstance().printTripTime()!=null)
        {
            long milis = DynamicProgramming.getInstance().printTripTime();
            long hour = 1000 * 60 * 60;
            long min = 1000 * 60;
            Long mins = (milis % hour) / min;
            Long hours = (milis - (milis % hour)) / hour;
            return ok(views.html.result.render(from, to, DynamicProgramming.getInstance().getRoute(), mins, hours));
        } else {
            return ok(views.html.result.render(from, to, DynamicProgramming.getInstance().getRoute(), 0L, 0L));
        }
    }

    public static Result trainsConnection() {
        ;
        return ok(views.html.timetable.render(DynamicProgramming.getInstance().getTrainConnections()));
    }

    public static Result index() {
        DynamicProgramming.getInstance().readGraph("train.txt");

        return ok(views.html.index.render("ko", DynamicProgramming.getInstance().getCities()));
    }



}
