package run.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import protobuf.jsonbean.AlarmInfo;


import java.util.List;
import java.util.Map;

@Repository
public interface AlarmInfoMapper {

    @Select("<script>" +
            "select a.*,c.site_id,c.site_name from rtu_alarm_history as a left join rtu_config as b on a.rtu_Id=b.rtu_id left join site_config as c on b.site_id=c.site_id where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and c.site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtuId != null and rtuId != -1\">" +
            "and a.rtu_Id =#{rtuId}"+
            "</if>"+
            "<if test=\"type != null and type != -1\">" +
            "and type =#{type}"+
            "</if>"+
            "<if test=\"alarmStatus != null and alarmStatus != -1\">" +
            "and alarmStatus =#{alarmStatus}"+
            "</if>"+
            "<if test=\"startTime != null and startTime != '' and startTime != 'null'\">" +
            "and udpateTime between #{startTime} and #{endTime}"+
            "</if>"+
            "order by udpateTime desc"+
            "<if test=\"start !=null and limit != null and start != -1 and limit != -1\">" +
            "limit #{start},#{limit}"+
            "</if>"+
            "</script>")
    List<Map<String,Object>> selectAllAlarmInfo(Map<String,Object> params);

    @Select("<script>" +
            "select count(*) from rtu_alarm_history as a left join rtu_config as b on a.rtu_Id=b.rtu_id left join site_config as c on b.site_id=c.site_id where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and c.site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtuId != null and rtuId != -1\">" +
            "and a.rtu_Id =#{rtuId}"+
            "</if>"+
            "<if test=\"type != null and type != -1\">" +
            "and type =#{type}"+
            "</if>"+
            "<if test=\"alarmStatus != null and alarmStatus != -1\">" +
            "and alarmStatus =#{alarmStatus}"+
            "</if>"+
            "<if test=\"startTime != null and startTime != '' and startTime != 'null'\">" +
            "and udpateTime between #{startTime} and #{endTime}"+
            "</if>"+
            "</script>")
    int selectAllAlarmInfoCount(Map<String,Object> params);
    
    @Insert("insert into rtu_alarm_history(rtu_id,rtu_channel,devieceId,deviceType,relayNo,alarmStatus,udpateTime,type,alarmStr) values(#{rtuId},#{channo},#{deviceid},#{devicetype},#{relayno},#{status},now(),3,#{alarmStr})")
    int insertAlarmInfo(AlarmInfo alarmInfo);

    @Insert("replace into rtu_alarm_data(rtu_id,rtu_channel,devieceId,deviceType,relayNo,alarmStatus,udpateTime,type,alarmStr) values(#{rtuId},#{channo},#{deviceid},#{devicetype},#{relayno},#{status},now(),3,#{alarmStr})")
    int insertAlarmNow(AlarmInfo alarmInfo);

    @Delete("delete from rtu_alarm_data where channo=#{channo} and deviceid=#{deviceid} and relayno=#{relayno} and rtuId=#{rtuId} and devicetype=#{devicetype} and type=3")
    int deleteAlarmNow(AlarmInfo alarmInfo);
}
