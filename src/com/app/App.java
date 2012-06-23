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
        TwitterApi.getInstance().setQuery("Siena 'Palio Weekend'", 100);
    }

    private static void insertTwetts() {
        List<Tweet> tweets = TwitterApi.getInstance().getTweets();
        for (Tweet tweet : tweets) {
            BasicDBObject bdbo = new BasicDBObject();
            bdbo.put("created_at", tweet.getCreatedAt().toString());
            bdbo.put("from_user", tweet.getFromUser());
            bdbo.put("from_user_id", tweet.getFromUserId());
            GeoLocation location = tweet.getGeoLocation();
            if(location != null){
                bdbo.put("geo_location", new BasicDBObject("latitude", tweet.getGeoLocation().getLatitude()).append("longitude", tweet.getGeoLocation().getLongitude()));
            }else{
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
    
    private static void find(){
        
        System.out.println(Mongodb.getInstance().size());
        
        List<DBObject> objs = Mongodb.getInstance().find(null,null);
        for (DBObject dBObject : objs) {
            System.out.println(dBObject.toString());
        }
        
        List<String> list = Mongodb.getInstance().selectDistinct("from_user");
        for (String value : list) {
            System.out.println(value);
        }
    }
}