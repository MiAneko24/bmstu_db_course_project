package com.fuzzy.fuzzyexpertsystemstool.repositories;

import com.fuzzy.fuzzyexpertsystemstool.cache.OutputCacheDao;
import com.fuzzy.fuzzyexpertsystemstool.dao.Dao;
import com.fuzzy.fuzzyexpertsystemstool.dao.OutputResultDao;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.OutputResult;

import java.util.List;

public class OutputRepository {
    private final Dao<OutputResult> outputResultDao = new OutputResultDao();

    private final OutputCacheDao outputCacheDao = new OutputCacheDao();
//    public List<OutputResult> getOutput(int sId, boolean cached) {
//        List<OutputResult> result = new ArrayList<>();
////        for (int j = 0; j < 10; j ++) {
////            if (j % 2 == 1) {
////                cached = false;
////            } else
////                cached = true;
//            long duration = 0L;
//            for (int i = 0; i < 50; i ++) {
//                Instant start = Instant.now();
//                result = getOutputInner(sId, cached);
//                Instant end = Instant.now();
//                duration += Duration.between(start, end).toMillis();
//
//                System.out.println("iter inner out\n");
//            }
//            duration /= 50;
//            try (FileWriter f = new FileWriter("/home/mianeko/university_stuff/bmstu_db_course_project/" +
//                    "not_cached.txt", true)) {
//                f.write(duration + "\n");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println("iter upper out\n");
////        }
//        return result;
//    }
    public List<OutputResult> getOutput(int sId, boolean cached) {
        List<OutputResult> result;
        if (cached) {
            result = outputCacheDao.getOutputResult(sId+"");
            if (result != null) {
                return result;
            }

        }
        result = outputResultDao.getAll(sId);
        outputCacheDao.setOutputResult(result, sId+"");
        return result;
    }
}
