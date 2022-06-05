package com.fuzzy.fuzzyexpertsystemstool.cache;

import com.fuzzy.fuzzyexpertsystemstool.dbmodel.OutputResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OutputCacheDao {
    Jedis jedis = new Jedis();
//    private RedissonClient client;
//    public OutputCacheDao() {
//        Config config = new Config();
//        config.useSingleServer()
//                .setAddress("redis://127.0.0.1:6379");
//        client = Redisson.create(config);
//    }
//
//    public List<OutputResult> getOutputResult(String sId) {
//        RBucket<List<OutputResult>> bucket = client.getBucket(sId);
//        return bucket.get();
//    }
//
//    public void setOutputResult(List<OutputResult> outputResult, String sId) {
//        RBucket<List<OutputResult>> bucket = client.getBucket(sId);
//        bucket.set(outputResult);
//    }
    public List<OutputResult> getOutputResult(String sId) {
        String json = jedis.get(sId);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<OutputResult>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    public void setOutputResult(List<OutputResult> outputResult, String sId) {
        Gson gson = new Gson();
        String json = gson.toJson(outputResult);
        jedis.set(sId, json, new SetParams().ex(300));
    }
}
