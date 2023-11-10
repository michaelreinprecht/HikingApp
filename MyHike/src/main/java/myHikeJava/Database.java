package myHikeJava;

import facade.*;
import models.Hike;
import models.Month;
import models.Recommended;
import models.Region;

import java.util.List;

public class Database {
    private static JPAFacade facade = new JPAFacade();
    private static JPAHikeFacade hikeFacade = new JPAHikeFacade();
    private static JPAMonthFacade monthFacade = new JPAMonthFacade();
    private static JPARegionFacade regionFacade = new JPARegionFacade();
    private static JPARecommendedFacade recommendedFacade = new JPARecommendedFacade();

    //Insert, update and delete can be used with any model
    public static void insert(Object databaseObject) {
        facade.insert(databaseObject);
    }
    public static void update(Object databaseObject) {
        facade.update(databaseObject);
    }
    public static void delete(Object databaseObject) {
        facade.delete(databaseObject);
    }

    /* Model specific methods */
    public static List<Hike> getAllHikes(){
        return hikeFacade.getAllHikes();
    }
    public static Hike getHikeById(int id) {
        return hikeFacade.getHikeById(id);
    }

    public static List<Month> getAllMonths() {
        return monthFacade.getAllMonths();
    }
    public static Month getMonthById(int id) {
        return monthFacade.getMonthById(id);
    }

    public static List<Region> getAllRegions() {
        return regionFacade.getAllRegions();
    }
    public static Region getRegionById(String id) {
        return regionFacade.getRegionById(id);
    }

    public static List<Recommended> getAllRecommendeds() {
        return recommendedFacade.getAllRecommendeds();
    }
    public static Recommended getRecommendedById(int id) {
        return recommendedFacade.getRecommendedById(id);
    }
}