package services;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Singleton;
import models.trainConnectionModel;


public class trainConnectionService {

    public static List<trainConnectionModel> createSampleConnection() {
        List<trainConnectionModel> lists = new ArrayList<trainConnectionModel>();
        lists.add(new trainConnectionModel("Wroclaw", "Poznan", 1463911200000L, 1463911700000L));
        lists.add(new trainConnectionModel("Warszawa", "Krakow", 1463912100000L, 1463912700000L));
        lists.add(new trainConnectionModel("Gdansk", "Suwalki", 1463911200000L, 1463911700000L));
        lists.add(new trainConnectionModel("Wroclaw", "Katowice", 1463911200000L, 1463911700000L));
        lists.add(new trainConnectionModel("Wroclaw", "Lodz", 1463911200000L, 1463911700000L));
        lists.add(new trainConnectionModel("Poznan", "Lodz", 1463911200000L, 1463911700000L));
        lists.add(new trainConnectionModel("Katowice", "Poznan", 1463911200000L, 1463911700000L));
        lists.add(new trainConnectionModel("Katowice", "Lodz", 1463911200000L, 1463911700000L));
        return lists;
    }
}