package run.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import protobuf.jsonbean.AlarmInfo;
import run.mapper.AlarmInfoMapper;
import java.util.List;
import java.util.Map;

@Service
public class AlarmInfoService {
    @Autowired
    AlarmInfoMapper alarmInfoMapper;

    public List<Map<String,Object>> selectAllAlarmInfo(Map<String,Object> params){
        return alarmInfoMapper.selectAllAlarmInfo(params);
    }

    public int selectAllAlarmInfoCount(Map<String,Object> params){
        return alarmInfoMapper.selectAllAlarmInfoCount(params);
    }

    public int insertAlarmInfo(AlarmInfo alarmInfo){
        return alarmInfoMapper.insertAlarmInfo(alarmInfo);
    }

    public void changeAlarmNow(AlarmInfo alarmInfo){
        if(alarmInfo.getStatus() == 1){
            alarmInfoMapper.insertAlarmNow(alarmInfo);
        }else if(alarmInfo.getStatus() == 0){
            alarmInfoMapper.deleteAlarmNow(alarmInfo);
        }
    }
}
