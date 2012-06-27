package com.app;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.Mongodb;
import com.twitter.TwitterApi;
import java.util.List;
import twitter4j.GeoLocation;
import twitter4j.Tweet;

public class App {

    public static void main(String[] args) {
        Mongodb.getInstance().createConnection();
        Mongodb.getInstance().removeAll();
        searchTwetts();
        insertTwetts();
        find();
    }

    private static void searchTwetts() {
        TwitterApi.getInstance().setQuery("Siena 'Palio Weekend'", 1000);
    }

    private static void insertTwetts() {
        List<Tweet> tweets = TwitterApi.getInstance().getTweets();
        for (Tweet tweet : tweets) {
            BasicDBObject bdbo = new BasicDBObject();
            bdbo.put("created_at", tweet.getCreatedAt().toString());
            bdbo.put("from_user", tweet.getFromUser());
            bdbo.put("from_user_id", tweet.getFromUserId());
            GeoLocation location = tweet.getGeoLocation();
            if (location != null) {
                bdbo.put("geo_location", new BasicDBObject("latitude", tweet.getGeoLocation().getLatitude()).append("longitude", tweet.getGeoLocation().getLongitude()));
            } else {
                bdbo.put("geo_location", null);
            }
            bdbo.put("id", tweet.getId());
            bdbo.put("iso_language_code", tweet.getIsoLanguageCode());
            bdbo.put("location", tweet.getLocation());
            bdbo.put("place", tweet.getPlace());
            bdbo.put("profile_image_url", tweet.getProfileImageUrl());
            bdbo.put("source", tweet.getSource());
            bdbo.put("text", tweet.getText());
            bdbo.put("to_user", tweet.getToUser());
            bdbo.put("to_user_id", tweet.getToUserId());
            Mongodb.getInstance().insert(bdbo);
        }
    }

    private static void find() {
        countTweets();
        findAllTwetts();
        selectDistinct();
        selectUserAndText();
    }

    private static void findAllTwetts() {
        List<DBObject> objs = Mongodb.getInstance().find(null, null);
        for (DBObject dBObject : objs) {
            System.out.println(dBObject.toString());
        }
    }

    private static void selectDistinct() {
        List<String> list = Mongodb.getInstance().selectDistinct("from_user");
        for (String user : list) {
            System.out.println(user);
        }
    }

    private static void countTweets() {
        System.out.println(Mongodb.getInstance().size());
    }

    private static void selectUserAndText() {
        BasicDBObject keys = new BasicDBObject();
        keys.append("from_user", 1);
        keys.append("text", 1);
        List<DBObject> objs = Mongodb.getInstance().find(null, keys);
        for (DBObject dBObject : objs) {
            System.out.println(dBObject.get("from_user") + " " + dBObject.get("text"));
        }
    }
}