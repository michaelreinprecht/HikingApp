package com.example.myhike;

import facade.JPAFacade;
import models.Hike;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class Database {
    //TODO: return Hike-Object, just returning single String for testing purposes.
    public static String LoadData() {
        //Hibernate Test get all
        JPAFacade facade = new JPAFacade();
        List<Hike> hikes= facade.getAllHikes();
        for (Hike hike : hikes) {
            return hike.getHikeName();
        }
        return null;
    }
}