package run.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.bean.Stray;
import run.mapper.StrayMapper;

import java.util.List;
import java.util.Map;

@Service
public class StrayService {
    @Autowired
    StrayMapper strayMapper;

    public List<Map<String,Object>> selectAllStray(int start, int limit, int site_id, int rtu_id){
        return strayMapper.selectAllStray(start,limit,site_id,rtu_id);
    }

    public int selectAllStrayCount(int start,int limit,int site_id,int rtu_id){
        return strayMapper.selectAllStrayCount(start,limit,site_id,rtu_id);
    }


    public int insertStray(Stray Stray){
        return strayMapper.insertStray(Stray);
    }

    public int updateStray(Stray Stray){
        return strayMapper.updateStray(Stray);
    }

    public List<Stray> selectStray(Map<String,Object> param){
        return strayMapper.selectStray(param);
    }

    public int deleteStrayBySite(int id){
        return strayMapper.deleteStrayBySite(id);
    }

    public int deleteStrayByRTU(int id){
        return strayMapper.deleteStrayByRTU(id);
    }

    public int deleteStray(Map<String,Object> param){
        return strayMapper.deleteStray(param);
    }

    public List<Map<String,Object>> selectStrayHistory(int start, int limit, int site_id, int rtu_id, int stret_id, String stret_location,String startTime,String endTime){
        return strayMapper.selectStrayHistory(start,limit,site_id,rtu_id,stret_id,stret_location,startTime,endTime);
    }

    public int selectStrayHistoryCount(int start, int limit, int site_id, int rtu_id, int stret_id, String stret_location,String startTime,String endTime){
        return strayMapper.selectStrayHistoryCount(start,limit,site_id,rtu_id,stret_id,stret_location,startTime,endTime);
    }

    public List<Map<String,Object>> selectStrayByRTU(int rtu_id){
        return strayMapper.selectStrayByRTU(rtu_id);
    }

    public int deleteRTUAlarmData(Map<String,Object> param){
        return strayMapper.deleteRTUAlarmData(param);
    }
}
