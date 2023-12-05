package myHikeJava;

import facade.JPAFacade;
import facade.JPAHikeFacade;
import facade.JPAPointOfInterestFacade;
import facade.JPARegionFacade;
import models.Hike;
import models.PointOfInterest;
import models.Region;

import java.util.List;

@SuppressWarnings("unused")
public class Database {
    public static JPAFacade facade = new JPAFacade();
    public static JPAHikeFacade hikeFacade = new JPAHikeFacade();
    public static JPARegionFacade regionFacade = new JPARegionFacade();
    public static JPAPointOfInterestFacade pointOfInterestFacade = new JPAPointOfInterestFacade();

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

    public static List<PointOfInterest> getAllPointsOfInterest(){
        return pointOfInterestFacade.getAllPointsOfInterest();
    }
    public static PointOfInterest getPointOfInterestById(String id) {
        return pointOfInterestFacade.getPointOfInterestById(id);
    }
}