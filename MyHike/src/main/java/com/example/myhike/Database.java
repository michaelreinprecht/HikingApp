package com.example.myhike;

import facade.JPAFacade;
import models.Hike;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class Database {
    //TODO: return Hike-Object, just returning single String for testing purposes.
    public static Hike getHikeById(int id) {
        JPAFacade facade = new JPAFacade();
        Hike hike = facade.getHikeById(id);
        return hike;
    }
}