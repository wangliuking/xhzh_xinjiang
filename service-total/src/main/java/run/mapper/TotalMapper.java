package run.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TotalMapper {
    /*@Select("<script>" +
            "select a.*,b.site_id from rtu_alarm_data as a left join rtu_config as b on a.rtu_id = b.rtu_id where type=2 and alarmStatus=1 "+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "group by a.rtu_id"+
            "</script>")
    List<Map<String,Object>> selectRTUOff(Map<String, Object> param);*/

    @Select("<script>" +
            "select a.*,b.site_id from rtu_now_data as a left join rtu_config as b on a.rtu_id = b.rtu_id where rtu_state = 1 "+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and b.site_id =#{site_id}"+
            "</if>"+
            "group by a.rtu_id"+
            "</script>")
    List<Map<String,Object>> selectRTUOff(Map<String, Object> param);

    @Select("<script>" +
            "select a.*,b.site_id from rtu_alarm_data as a left join rtu_config as b on a.rtu_id = b.rtu_id where type=3 and alarmStatus=1 "+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "group by a.rtu_id"+
            "</script>")
    List<Map<String,Object>> selectRTUWarning(Map<String, Object> param);

    @Select("<script>" +
            "select a.* from rtu_alarm_data as a left join rtu_config as b on a.rtu_id=b.rtu_id where type=3 and alarmStatus=1"+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "group by a.rtu_id,rtu_channel,devieceId"+
            "</script>")
    List<Map<String,Object>> selectDeviceWarning(Map<String, Object> param);

    @Select("<script>" +
            "select a.* from rtu_alarm_data as a left join rtu_config as b on a.rtu_id=b.rtu_id where type=1 and alarmStatus=1"+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "group by a.rtu_id,rtu_channel,devieceId"+
            "</script>")
    List<Map<String,Object>> selectDeviceOff(Map<String, Object> param);

    @Select("<script>" +
            "select * from site_config where 1=1 "+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "</script>")
    List<Map<String,Object>> selectSiteById(Map<String, Object> param);

    @Select("<script>" +
            "select * from rtu_config where 1=1 "+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "</script>")
    Map<String,Object> selectRTUById(@Param("rtu_id")int rtu_id);

    @Select("<script>" +
            "select count(*) from rtu_config where 1=1 "+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "</script>")
    int selectRTUNumBySiteId(Map<String, Object> param);

    @Select("<script>" +
            "select * from spd_config where 1=1 "+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "</script>")
    List<Map<String,Object>> selectSPDCount(Map<String, Object> param);

    @Select("<script>" +
            "select spd_number from spd_config where 1=1 "+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "</script>")
    List<Integer> selectSPDPort(Map<String, Object> param);

    @Select("<script>" +
            "select * from resistance_config where 1=1 "+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port,rst_id"+
            "</script>")
    List<Map<String,Object>> selectETCRCount(Map<String, Object> param);

    @Select("<script>" +
            "select rtu_port from resistance_config where rst_id!=0"+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port"+
            "</script>")
    List<Integer> selectETCRPort(Map<String, Object> param);

    @Select("<script>" +
            "select * from lightning_config where 1=1 "+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port,ltn_id"+
            "</script>")
    List<Map<String,Object>> selectLightningCount(Map<String, Object> param);

    @Select("<script>" +
            "select rtu_port from lightning_config where ltn_id!=0"+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port,ltn_id"+
            "</script>")
    List<Integer> selectLightningPort(Map<String, Object> param);

    @Select("<script>" +
            "select * from static_electricity_config where 1=1 "+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port,staet_id"+
            "</script>")
    List<Map<String,Object>> selectStaticCount(Map<String, Object> param);

    @Select("<script>" +
            "select rtu_port from static_electricity_config where staet_id!=0 "+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port"+
            "</script>")
    List<Integer> selectStaticPort(Map<String, Object> param);

    @Select("<script>" +
            "select * from humiture_config where 1=1 "+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port,hmt_id"+
            "</script>")
    List<Map<String,Object>> selectRswsCount(Map<String, Object> param);

    @Select("<script>" +
            "select rtu_port from humiture_config where hmt_id!=0 "+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port"+
            "</script>")
    List<Integer> selectRswsPort(Map<String, Object> param);

    @Select("<script>" +
            "select * from tilt_config where 1=1 "+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port,tilt_id"+
            "</script>")
    List<Map<String,Object>> selectSvtCount(Map<String, Object> param);

    @Select("<script>" +
            "select rtu_port from tilt_config where tilt_id!=0 "+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port"+
            "</script>")
    List<Integer> selectSvtPort(Map<String, Object> param);

    @Select("<script>" +
            "select * from electrical_safety_config where 1=1 "+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port,es_id"+
            "</script>")
    List<Map<String,Object>> selectHcCount(Map<String, Object> param);

    @Select("<script>" +
            "select rtu_port from electrical_safety_config where es_id!=0 "+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port"+
            "</script>")
    List<Integer> selectHcPort(Map<String, Object> param);

    @Select("<script>" +
            "select * from stray_electricity_config where 1=1 "+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port,stret_id"+
            "</script>")
    List<Map<String,Object>> selectStrayCount(Map<String, Object> param);

    @Select("<script>" +
            "select rtu_port from stray_electricity_config where stret_id!=0 "+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port"+
            "</script>")
    List<Integer> selectStray485Port(Map<String, Object> param);

    @Select("<script>" +
            "select rtu_port from stray_electricity_config where stret_id=0 "+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port"+
            "</script>")
    List<Integer> selectStrayTestPort(Map<String, Object> param);

    @Select("<script>" +
            "select * from cathode_config where 1=1 "+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port,cathode_id"+
            "</script>")
    List<Map<String,Object>> selectCatCount(Map<String, Object> param);

    @Select("<script>" +
            "select rtu_port from cathode_config where cathode_id!=0 "+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port"+
            "</script>")
    List<Integer> selectCat485Port(Map<String, Object> param);

    @Select("<script>" +
            "select rtu_port from cathode_config where cathode_id=0 "+
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port"+
            "</script>")
    List<Integer> selectCatTestPort(Map<String, Object> param);

    @Select("<script>" +
            "select tempDate,count(tempDate) as num from " +
            "(select udpateTime,date_format(udpateTime,'%Y-%m-%d') as tempDate from rtu_alarm_history as a left join rtu_config as b on a.rtu_id=b.rtu_id where type=1 " +
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and a.rtu_id =#{rtu_id}"+
            "</if>"+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "and udpateTime between #{startTime} and #{endTime}) " +
            "as t group by tempDate"+
            "</script>")
    List<Map<String,Object>> selectDeviceOffByMonth(Map<String, Object> param);

    @Select("<script>" +
            "select tempDate,count(tempDate) as num from " +
            "(select udpateTime,date_format(udpateTime,'%Y-%m-%d') as tempDate from rtu_alarm_history as a left join rtu_config as b on a.rtu_id=b.rtu_id where type=3 " +
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and a.rtu_id =#{rtu_id}"+
            "</if>"+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "and udpateTime between #{startTime} and #{endTime}) " +
            "as t group by tempDate"+
            "</script>")
    List<Map<String,Object>> selectDeviceWarningByMonth(Map<String, Object> param);

    @Select("<script>" +
            "select tempDate,count(tempDate) as num from " +
            "(select udpateTime,date_format(udpateTime,'%Y-%m-%d') as tempDate from rtu_alarm_history as a left join rtu_config as b on a.rtu_id=b.rtu_id where type=2 " +
            "<if test=\"rtu_id != null and rtu_id != ''\">" +
            "and a.rtu_id =#{rtu_id}"+
            "</if>"+
            "<if test=\"site_id != null and site_id != ''\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "and udpateTime between #{startTime} and #{endTime}) " +
            "as t group by tempDate"+
            "</script>")
    List<Map<String,Object>> selectRTUOffByMonth(Map<String, Object> param);


    @Select("<script>" +
            "select spd_state,write_time from spd_old_data as a left join spd_config as b on a.rtu_id=b.rtu_id and a.spd_number=b.spd_number left join site_config as c on b.site_id=c.site_id where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and b.site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != -1\">" +
            "and b.rtu_id =#{rtu_id}"+
            "</if>"+
            "<if test=\"spd_number != null and spd_number != -1\">" +
            "and b.spd_number =#{spd_number}"+
            "</if>"+
            "<if test=\"startTime != null and startTime != ''\">" +
            "and write_time between #{startTime} and #{endTime}"+
            "</if>"+
            "order by write_time"+
            "</script>")
    List<Map<String,Object>> selectSPDHistory(Map<String,Object> param);

    @Select("<script>" +
            "select rst_value,b.rst_type,a.relayno,write_time from resistance_old_data as a left join resistance_config as b on a.rtu_id=b.rtu_id and a.rst_id=b.rst_id and a.relayno=b.relayno left join site_config as c on b.site_id=c.site_id where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and b.site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != -1\">" +
            "and b.rtu_id =#{rtu_id}"+
            "</if>"+
            "<if test=\"rst_id != null and rst_id != -1\">" +
            "and a.rst_id =#{rst_id}"+
            "</if>"+
            "<if test=\"startTime != null and startTime != ''\">" +
            "and write_time between #{startTime} and #{endTime}"+
            "</if>"+
            "order by write_time"+
            "</script>")
    List<Map<String,Object>> selectETCRHistory(Map<String,Object> param);

    @Select("<script>" +
            "select ltn_value,write_time from lightning_old_data as a left join lightning_config as b on a.rtu_id=b.rtu_id and a.ltn_id=b.ltn_id and a.rtu_channel=b.rtu_port left join site_config as c on b.site_id=c.site_id where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and b.site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != -1\">" +
            "and b.rtu_id =#{rtu_id}"+
            "</if>"+
            "<if test=\"ltn_id != null and ltn_id != -1\">" +
            "and b.ltn_id =#{ltn_id}"+
            "</if>"+
            "<if test=\"startTime != null and startTime != ''\">" +
            "and write_time between #{startTime} and #{endTime}"+
            "</if>"+
            "order by write_time"+
            "</script>")
    List<Map<String,Object>> selectLightningHistory(Map<String,Object> param);

    @Select("<script>" +
            "select staet_value,write_time from static_electricity_old_data as a left join static_electricity_config as b on a.rtu_id=b.rtu_id and a.staet_id=b.staet_id and a.rtu_channel=b.rtu_port left join site_config as c on b.site_id=c.site_id where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and b.site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != -1\">" +
            "and b.rtu_id =#{rtu_id}"+
            "</if>"+
            "<if test=\"staet_id != null and staet_id != -1\">" +
            "and b.staet_id =#{staet_id}"+
            "</if>"+
            "<if test=\"startTime != null and startTime != ''\">" +
            "and write_time between #{startTime} and #{endTime}"+
            "</if>"+
            "order by write_time"+
            "</script>")
    List<Map<String,Object>> selectStaticHistory(Map<String,Object> param);

    @Select("<script>" +
            "select hmt_temp,hmt_hm,write_time from humiture_old_data as a left join humiture_config as b on a.rtu_id=b.rtu_id and a.hmt_id=b.hmt_id and a.rtu_channel=b.rtu_port left join site_config as c on b.site_id=c.site_id where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and b.site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != -1\">" +
            "and b.rtu_id =#{rtu_id}"+
            "</if>"+
            "<if test=\"hmt_id != null and hmt_id != -1\">" +
            "and b.hmt_id =#{hmt_id}"+
            "</if>"+
            "<if test=\"startTime != null and startTime != ''\">" +
            "and write_time between #{startTime} and #{endTime}"+
            "</if>"+
            "order by write_time"+
            "</script>")
    List<Map<String,Object>> selectRswsHistory(Map<String,Object> param);

    @Select("<script>" +
            "select tilt_value1,tilt_value2,tilt_gx,tilt_gy,tilt_gs,write_time from tilt_old_data as a left join tilt_config as b on a.rtu_id=b.rtu_id and a.tilt_id=b.tilt_id and a.rtu_channel=b.rtu_port left join site_config as c on b.site_id=c.site_id where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and b.site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != -1\">" +
            "and b.rtu_id =#{rtu_id}"+
            "</if>"+
            "<if test=\"tilt_id != null and tilt_id != -1\">" +
            "and b.tilt_id =#{tilt_id}"+
            "</if>"+
            "<if test=\"startTime != null and startTime != ''\">" +
            "and write_time between #{startTime} and #{endTime}"+
            "</if>"+
            "order by write_time"+
            "</script>")
    List<Map<String,Object>> selectSvtHistory(Map<String,Object> param);

    @Select("<script>" +
            "select es_i_value,es_temp_value,write_time from electrical_safety_old_data as a left join electrical_safety_config as b on a.rtu_id=b.rtu_id and a.es_id=b.es_id and a.rtu_channel=b.rtu_port left join site_config as c on b.site_id=c.site_id where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and b.site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != -1\">" +
            "and b.rtu_id =#{rtu_id}"+
            "</if>"+
            "<if test=\"es_id != null and es_id != -1\">" +
            "and b.es_id =#{es_id}"+
            "</if>"+
            "<if test=\"startTime != null and startTime != ''\">" +
            "and write_time between #{startTime} and #{endTime}"+
            "</if>"+
            "order by write_time"+
            "</script>")
    List<Map<String,Object>> selectHcHistory(Map<String,Object> param);

    @Select("<script>" +
            "select stret_value,a.portId,write_time from stray_electricity_old_data as a left join stray_electricity_config as b on a.rtu_id=b.rtu_id and a.stret_id=b.stret_id and a.rtu_channel=b.rtu_port and a.portId=b.portId left join site_config as c on b.site_id=c.site_id where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and b.site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != -1\">" +
            "and b.rtu_id =#{rtu_id}"+
            "</if>"+
            "<if test=\"stret_id != null and stret_id != -1\">" +
            "and b.stret_id =#{stret_id}"+
            "</if>"+
            "<if test=\"startTime != null and startTime != ''\">" +
            "and write_time between #{startTime} and #{endTime}"+
            "</if>"+
            "order by write_time"+
            "</script>")
    List<Map<String,Object>> selectStrayHistory(Map<String,Object> param);

    @Select("<script>" +
            "select cathode_value,write_time from cathode_old_data as a left join cathode_config as b on a.rtu_id=b.rtu_id and a.cathode_id=b.cathode_id and a.rtu_channel=b.rtu_port left join site_config as c on b.site_id=c.site_id where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and b.site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != -1\">" +
            "and b.rtu_id =#{rtu_id}"+
            "</if>"+
            "<if test=\"cathode_id != null and cathode_id != -1\">" +
            "and b.cathode_id =#{cathode_id}"+
            "</if>"+
            "<if test=\"startTime != null and startTime != ''\">" +
            "and write_time between #{startTime} and #{endTime}"+
            "</if>"+
            "order by write_time"+
            "</script>")
    List<Map<String,Object>> selectCatHistory(Map<String,Object> param);
}
