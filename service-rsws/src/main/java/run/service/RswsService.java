package run.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.bean.Rsws;
import run.mapper.RswsMapper;

import java.util.List;
import java.util.Map;

@Service
public class RswsService {
    @Autowired
    RswsMapper rswsMapper;

    public List<Map<String,Object>> selectAllRsws(int start, int limit, int site_id, int rtu_id){
        return rswsMapper.selectAllRsws(start,limit,site_id,rtu_id);
    }

    public int selectAllRswsCount(int start,int limit,int site_id,int rtu_id){
        return rswsMapper.selectAllRswsCount(start,limit,site_id,rtu_id);
    }


    public int insertRsws(Rsws rsws){
        return rswsMapper.insertRsws(rsws);
    }

    public int updateRsws(Rsws rsws){
        return rswsMapper.updateRsws(rsws);
    }

    public Rsws selectOneLight(Map<String,Object> param){
        return rswsMapper.selectOneRsws(param);
    }

    public int deleteRswsBySite(int id){
        return rswsMapper.deleteRswsBySite(id);
    }

    public int deleteRswsByRTU(int id){
        return rswsMapper.deleteRswsByRTU(id);
    }

    public int deleteRsws(Map<String,Object> param){
        return rswsMapper.deleteRsws(param);
    }

    public List<Map<String,Object>> selectRswsHistory(int start, int limit, int site_id, int rtu_id, int hmt_id, String hmt_location,String startTime,String endTime){
        return rswsMapper.selectRswsHistory(start,limit,site_id,rtu_id,hmt_id,hmt_location,startTime,endTime);
    }

    public int selectRswsHistoryCount(int start, int limit, int site_id, int rtu_id, int hmt_id, String hmt_location,String startTime,String endTime){
        return rswsMapper.selectRswsHistoryCount(start,limit,site_id,rtu_id,hmt_id,hmt_location,startTime,endTime);
    }

    public List<Map<String,Object>> selectRswsByRTU(int rtu_id){
        return rswsMapper.selectRswsByRTU(rtu_id);
    }

}
