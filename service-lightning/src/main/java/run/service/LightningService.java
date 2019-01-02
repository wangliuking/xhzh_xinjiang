package run.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.bean.Lightning;
import run.mapper.LightningMapper;

import java.util.List;
import java.util.Map;

@Service
public class LightningService {
    @Autowired
    LightningMapper lightningMapper;

    public List<Map<String,Object>> selectAllLightning(int start, int limit, int site_id, int rtu_id){
        return lightningMapper.selectAllLightning(start,limit,site_id,rtu_id);
    }

    public int selectAllLightningCount(int start,int limit,int site_id,int rtu_id){
        return lightningMapper.selectAllLightningCount(start,limit,site_id,rtu_id);
    }


    public int insertLightning(Lightning lightning){
        return lightningMapper.insertLightning(lightning);
    }

    public int updateLightning(Lightning lightning){
        return lightningMapper.updateLightning(lightning);
    }

    public Lightning selectOneLight(Map<String,Object> param){
        return lightningMapper.selectOneLight(param);
    }

    public int deleteLightningBySite(int id){
        return lightningMapper.deleteLightningBySite(id);
    }

    public int deleteLightningByRTU(int id){
        return lightningMapper.deleteLightningByRTU(id);
    }

    public int deleteLightning(Map<String,Object> param){
        return lightningMapper.deleteLightning(param);
    }

    public List<Map<String,Object>> selectLightningHistory(int start, int limit, int site_id, int rtu_id, int ltn_id, String ltn_location,String startTime,String endTime){
        return lightningMapper.selectLightningHistory(start,limit,site_id,rtu_id,ltn_id,ltn_location,startTime,endTime);
    }

    public int selectLightningHistoryCount(int start, int limit, int site_id, int rtu_id, int ltn_id, String ltn_location,String startTime,String endTime){
        return lightningMapper.selectLightningHistoryCount(start,limit,site_id,rtu_id,ltn_id,ltn_location,startTime,endTime);
    }

    public List<Map<String,Object>> selectLightningByRTU(int rtu_id){
        return lightningMapper.selectLightningByRTU(rtu_id);
    }

}
