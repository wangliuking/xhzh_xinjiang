package run.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.bean.Hc;
import run.mapper.HcMapper;

import java.util.List;
import java.util.Map;

@Service
public class HcService {
    @Autowired
    HcMapper hcMapper;

    public List<Map<String,Object>> selectAllHc(int start, int limit, int site_id, int rtu_id){
        return hcMapper.selectAllHc(start,limit,site_id,rtu_id);
    }

    public int selectAllHcCount(int start,int limit,int site_id,int rtu_id){
        return hcMapper.selectAllHcCount(start,limit,site_id,rtu_id);
    }


    public int insertHc(Hc Hc){
        return hcMapper.insertHc(Hc);
    }

    public int updateHc(Hc Hc){
        return hcMapper.updateHc(Hc);
    }

    public Hc selectOneLight(Map<String,Object> param){
        return hcMapper.selectOneHc(param);
    }

    public int deleteHcBySite(int id){
        return hcMapper.deleteHcBySite(id);
    }

    public int deleteHcByRTU(int id){
        return hcMapper.deleteHcByRTU(id);
    }

    public int deleteHc(Map<String,Object> param){
        return hcMapper.deleteHc(param);
    }

    public List<Map<String,Object>> selectHcHistory(int start, int limit, int site_id, int rtu_id, int es_id, String es_location,String startTime,String endTime){
        return hcMapper.selectHcHistory(start,limit,site_id,rtu_id,es_id,es_location,startTime,endTime);
    }

    public int selectHcHistoryCount(int start, int limit, int site_id, int rtu_id, int es_id, String es_location,String startTime,String endTime){
        return hcMapper.selectHcHistoryCount(start,limit,site_id,rtu_id,es_id,es_location,startTime,endTime);
    }

    public List<Map<String,Object>> selectHcByRTU(int rtu_id){
        return hcMapper.selectHcByRTU(rtu_id);
    }

}
