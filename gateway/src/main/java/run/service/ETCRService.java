package run.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.mapper.ETCRMapper;

import java.util.List;
import java.util.Map;

@Service
public class ETCRService {
    @Autowired
    ETCRMapper ETCRMapper;

    public List<Map<String,Object>> exportAllETCRHistoryExcel(int site_id, int rtu_id, int rst_id, String rst_location,String startTime,String endTime){
        return ETCRMapper.exportAllETCRHistoryExcel(site_id,rtu_id,rst_id,rst_location,startTime,endTime);
    }
}
