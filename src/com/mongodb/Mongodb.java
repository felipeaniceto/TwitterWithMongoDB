package com.mongodb;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mongodb {

    private static final String URL = "localhost";
    private static final int PORT = 27017;
    private static final String DATA_BASE = "twitter";
    private static final String COLLECTION = "twitterCollection";
    private static Mongodb instance;
    private Mongo mongo;
    private DBCollection collection;

    static {
        instance = new Mongodb();
    }

    private Mongodb() {
    }

    public static Mongodb getInstance() {
        return instance;
    }

    public void createConnection() {
        try {
            mongo = new Mongo(URL, PORT);
            DB db = mongo.getDB(DATA_BASE);
            collection = db.getCollection(COLLECTION);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Mongodb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MongoException ex) {
            Logger.getLogger(Mongodb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insert(DBObject dbos) {
        collection.insert(dbos);
    }

    public void remove(DBObject dbos) {
        collection.remove(dbos);
    }

    public void removeAll() {
        collection.drop();
    }

    public List<DBObject> find() {
        List<DBObject> dbObjects = new ArrayList<DBObject>();
        DBCursor cur = collection.find();
        while (cur.hasNext()) {
            dbObjects.add(cur.next());
        }
        return dbObjects;
    }

    public List<DBObject> find(DBObject obj) {
        List<DBObject> dbObjects = new ArrayList<DBObject>();
        DBCursor cur = collection.find(obj);
        while (cur.hasNext()) {
            dbObjects.add(cur.next());
        }
        return dbObjects;
    }

    public List<DBObject> find(DBObject obj, DBObject keys) {
        List<DBObject> dbObjects = new ArrayList<DBObject>();
        DBCursor cur = collection.find(obj, keys);
        while (cur.hasNext()) {
            dbObjects.add(cur.next());
        }
        return dbObjects;
    }
}
