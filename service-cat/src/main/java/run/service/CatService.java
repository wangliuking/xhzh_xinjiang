package run.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.bean.Cat;
import run.mapper.CatMapper;

import java.util.List;
import java.util.Map;

@Service
public class CatService {
    @Autowired
    CatMapper catMapper;

    public List<Map<String,Object>> selectAllCat(int start, int limit, int site_id, int rtu_id){
        return catMapper.selectAllCat(start,limit,site_id,rtu_id);
    }

    public int selectAllCatCount(int start,int limit,int site_id,int rtu_id){
        return catMapper.selectAllCatCount(start,limit,site_id,rtu_id);
    }


    public int insertCat(Cat Cat){
        return catMapper.insertCat(Cat);
    }

    public int updateCat(Cat Cat){
        return catMapper.updateCat(Cat);
    }

    public Cat selectOneLight(Map<String,Object> param){
        return catMapper.selectOneCat(param);
    }

    public int deleteCatBySite(int id){
        return catMapper.deleteCatBySite(id);
    }

    public int deleteCatByRTU(int id){
        return catMapper.deleteCatByRTU(id);
    }

    public int deleteCat(Map<String,Object> param){
        return catMapper.deleteCat(param);
    }

    public List<Map<String,Object>> selectCatHistory(int start, int limit, int site_id, int rtu_id, int cathode_id, String cathode_location,String startTime,String endTime){
        return catMapper.selectCatHistory(start,limit,site_id,rtu_id,cathode_id,cathode_location,startTime,endTime);
    }

    public int selectCatHistoryCount(int start, int limit, int site_id, int rtu_id, int cathode_id, String cathode_location,String startTime,String endTime){
        return catMapper.selectCatHistoryCount(start,limit,site_id,rtu_id,cathode_id,cathode_location,startTime,endTime);
    }

    public List<Map<String,Object>> selectCatByRTU(int rtu_id){
        return catMapper.selectCatByRTU(rtu_id);
    }

    public int deleteRTUAlarmData(Map<String,Object> param){
        return catMapper.deleteRTUAlarmData(param);
    }
}
