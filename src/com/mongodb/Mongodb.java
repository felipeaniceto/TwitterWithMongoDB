package com.mongodb;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author felipe Aniceto
 * @author Mirlane Ribeiro
 */

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
    
    public long size(){
        return collection.count();
    }
    
    public List<String> selectDistinct(String key){
        return selectDistinct(key, null);
    }
    
    public List<String> selectDistinct(String key, DBObject query){
        return collection.distinct(key, query);
    }

    public List<DBObject> find() {
        return find(null, null);
    }

    public List<DBObject> find(DBObject obj) {
        return find(obj, null);
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
