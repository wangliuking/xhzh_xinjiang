package run.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.bean.RTU;
import run.mapper.RTUMapper;


import java.util.List;
import java.util.Map;

@Service
public class RTUService {
    @Autowired
    RTUMapper rtuMapper;

    public List<Map<String,Object>> selectAllRTU(Map<String,Object> param){
        return rtuMapper.selectAllRTU(param);
    }

    public int selectDeviceWarningCount(int rtu_id){
        return rtuMapper.selectDeviceWarningCount(rtu_id);
    }

    public RTU selectRTUById(int id){
        return rtuMapper.selectRTUById(id);
    }

    public int selectRTUCountBySiteId(int id){
        return rtuMapper.selectRTUCountBySiteId(id);
    }

    public int insertRTU(RTU rtu){
        return rtuMapper.insertRTU(rtu);
    }

    public int deleteRTU(int id){
        return rtuMapper.deleteRTU(id);
    }

    public int deleteRTUBySite(int id){
        return rtuMapper.deleteRTUBySite(id);
    }

    public int updateRTU(RTU rtu){
        return rtuMapper.updateRTU(rtu);
    }
}
