package myHikeJava;

import facade.*;
import models.Hike;
import models.Region;

import java.util.List;

@SuppressWarnings("unused")
public class Database {
    private static final JPAFacade facade = new JPAFacade();
    private static final JPAHikeFacade hikeFacade = new JPAHikeFacade();
    private static final JPARegionFacade regionFacade = new JPARegionFacade();

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
    public static Hike getHikeById(String id) {
        return hikeFacade.getHikeById(id);
    }

    public static List<Region> getAllRegions() {
        return regionFacade.getAllRegions();
    }
    public static Region getRegionById(String id) {
        return regionFacade.getRegionById(id);
    }
}