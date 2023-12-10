package database;

import facade.*;
import models.*;

import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("unused")
public class Database {
    public static JPAFacade facade;
    public static JPAHikeFacade hikeFacade = new JPAHikeFacade();
    public static JPARegionFacade regionFacade = new JPARegionFacade();
    public static JPAPointOfInterestFacade pointOfInterestFacade = new JPAPointOfInterestFacade();
    public static JPAUserFacade userFacade = new JPAUserFacade();
    public static JPACommentFacade commentFacade = new JPACommentFacade();

    //Insert, update and delete can be used with any model
    public static void insert(Object databaseObject) throws SQLException {
        if (facade == null) {
            facade = new JPAFacade();
        }
        facade.insert(databaseObject);
    }
    public static void update(Object databaseObject) throws SQLException {
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

    public static List<User> getAllUsers() {
        return userFacade.getAllUsers();
    }
    public static User getUserById(String id) {
        return userFacade.getUserById(id);
    }

    public static List<Comment> getAllComments() {
        return commentFacade.getAllComments();
    }
    public static Comment getCommentById(String id) {
        return commentFacade.getCommentById(id);
    }
}