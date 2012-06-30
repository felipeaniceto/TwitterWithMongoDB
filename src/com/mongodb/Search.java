package com.mongodb;

import java.util.List;

/**
 *
 * @author felipe Aniceto
 * @author Mirlane Ribeiro
 */
public class Search {

    public static void findAllTwetts() {
        List<DBObject> objs = Mongodb.getInstance().find(null, null);
        for (DBObject dBObject : objs) {
            System.out.println(dBObject.toString());
        }
    }

    public static void selectDistinct(String key) {
        List<String> list = Mongodb.getInstance().selectDistinct(key);
        for (String user : list) {
            System.out.println(user);
        }
    }

    public static void countTweets() {
        System.out.println(Mongodb.getInstance().size());
    }

    public static void selectUserAndText() {
        BasicDBObject keys = new BasicDBObject();
        keys.append("from_user", 1);
        keys.append("text", 1);
        List<DBObject> objs = Mongodb.getInstance().find(null, keys);
        for (DBObject dBObject : objs) {
            System.out.println(dBObject.toString());
        }
    }

    public static void selectTextByKey(String key, String user) {
        BasicDBObject keys = new BasicDBObject();
        keys.append("from_user", 1);
        keys.append("text", 1);

        List<DBObject> objs = Mongodb.getInstance().find(new BasicDBObject(key, user), keys);
        for (DBObject dBObject : objs) {
            System.out.println(dBObject.toString());
        }
    }
}
