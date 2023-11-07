package myHikeJava;

import facade.JPAFacade;
import models.Hike;
import models.Month;

import java.util.List;

public class Database {
    //TODO: maybe split up this OR facade for multiple objects

    public static List<Hike> getAllHikes(){
        JPAFacade facade = new JPAFacade();
        List<Hike> hikes = facade.getAllHikes();
        return hikes;
    }
    public static Hike getHikeById(int id) {
        JPAFacade facade = new JPAFacade();
        Hike hike = facade.getHikeById(id);
        return hike;
    }


    public static List<Month> getAllMonths() {
        JPAFacade facade = new JPAFacade();
        List<Month> months = facade.getAllMonths();
        return months;
    }

    public static Month getMonthById(int id) {
        JPAFacade facade = new JPAFacade();
        Month month = facade.getMonthById(id);
        return month;
    }
}