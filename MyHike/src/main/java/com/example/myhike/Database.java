package com.example.myhike;

import facade.JPAFacade;
import models.Hike;
import java.util.List;

public class Database {
    //TODO: return Hike-Object, just returning single String for testing purposes.

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
}