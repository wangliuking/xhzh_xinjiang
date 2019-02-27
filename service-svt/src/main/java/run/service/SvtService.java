package run.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.bean.Svt;
import run.mapper.SvtMapper;

import java.util.List;
import java.util.Map;

@Service
public class SvtService {
    @Autowired
    SvtMapper svtMapper;

    public List<Map<String,Object>> selectAllSvt(int start, int limit, int site_id, int rtu_id){
        return svtMapper.selectAllSvt(start,limit,site_id,rtu_id);
    }

    public int selectAllSvtCount(int start,int limit,int site_id,int rtu_id){
        return svtMapper.selectAllSvtCount(start,limit,site_id,rtu_id);
    }


    public int insertSvt(Svt Svt){
        return svtMapper.insertSvt(Svt);
    }

    public int updateSvt(Svt Svt){
        return svtMapper.updateSvt(Svt);
    }

    public Svt selectOneLight(Map<String,Object> param){
        return svtMapper.selectOneSvt(param);
    }

    public int deleteSvtBySite(int id){
        return svtMapper.deleteSvtBySite(id);
    }

    public int deleteSvtByRTU(int id){
        return svtMapper.deleteSvtByRTU(id);
    }

    public int deleteSvt(Map<String,Object> param){
        return svtMapper.deleteSvt(param);
    }

    public List<Map<String,Object>> selectSvtHistory(int start, int limit, int site_id, int rtu_id, int tilt_id, String tilt_location,String startTime,String endTime){
        return svtMapper.selectSvtHistory(start,limit,site_id,rtu_id,tilt_id,tilt_location,startTime,endTime);
    }

    public int selectSvtHistoryCount(int start, int limit, int site_id, int rtu_id, int tilt_id, String tilt_location,String startTime,String endTime){
        return svtMapper.selectSvtHistoryCount(start,limit,site_id,rtu_id,tilt_id,tilt_location,startTime,endTime);
    }

    public List<Map<String,Object>> selectSvtByRTU(int rtu_id){
        return svtMapper.selectSvtByRTU(rtu_id);
    }

    public int deleteRTUAlarmData(Map<String,Object> param){
        return svtMapper.deleteRTUAlarmData(param);
    }
}
