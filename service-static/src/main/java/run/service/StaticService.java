package run.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.bean.Static;
import run.mapper.StaticMapper;

import java.util.List;
import java.util.Map;

@Service
public class StaticService {
    @Autowired
    StaticMapper StaticMapper;

    public List<Map<String,Object>> selectAllStatic(int start, int limit, int site_id, int rtu_id){
        return StaticMapper.selectAllStatic(start,limit,site_id,rtu_id);
    }

    public int selectAllStaticCount(int start,int limit,int site_id,int rtu_id){
        return StaticMapper.selectAllStaticCount(start,limit,site_id,rtu_id);
    }


    public int insertStatic(Static Static){
        return StaticMapper.insertStatic(Static);
    }

    public int updateStatic(Static Static){
        return StaticMapper.updateStatic(Static);
    }

    public Static selectOneLight(Map<String,Object> param){
        return StaticMapper.selectOneStatic(param);
    }

    public int deleteStaticBySite(int id){
        return StaticMapper.deleteStaticBySite(id);
    }

    public int deleteStaticByRTU(int id){
        return StaticMapper.deleteStaticByRTU(id);
    }

    public int deleteStatic(Map<String,Object> param){
        return StaticMapper.deleteStatic(param);
    }

    public List<Map<String,Object>> selectStaticHistory(int start, int limit, int site_id, int rtu_id, int staet_id, String staet_location,String startTime,String endTime){
        return StaticMapper.selectStaticHistory(start,limit,site_id,rtu_id,staet_id,staet_location,startTime,endTime);
    }

    public int selectStaticHistoryCount(int start, int limit, int site_id, int rtu_id, int staet_id, String staet_location,String startTime,String endTime){
        return StaticMapper.selectStaticHistoryCount(start,limit,site_id,rtu_id,staet_id,staet_location,startTime,endTime);
    }

    public List<Map<String,Object>> selectStaticByRTU(int rtu_id){
        return StaticMapper.selectStaticByRTU(rtu_id);
    }

}
