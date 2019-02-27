package run.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import run.bean.Svt;

import java.util.List;
import java.util.Map;

@Repository
public interface SvtMapper {

    @Select("<script>" +
            "select a.*,b.tilt_state from tilt_config as a left join tilt_now_data as b on a.rtu_id=b.rtu_id and a.rtu_port=b.rtu_channel and a.tilt_id=b.tilt_id where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and a.site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != -1\">" +
            "and a.rtu_id =#{rtu_id}"+
            "</if>"+
            "group by a.rtu_id,a.rtu_port,a.tilt_id"+
            "<if test=\"start !=null and limit != null and start != -1 and limit != -1\">" +
            "limit #{start},#{limit}"+
            "</if>"+
            "</script>")
    List<Map<String,Object>> selectAllSvt(@Param("start") int start, @Param("limit") int limit, @Param("site_id") int site_id, @Param("rtu_id") int rtu_id);

    @Select("<script>" +
            "select count(*) from ("+
            "select * from tilt_config where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != -1\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port,tilt_id"+
            ") as t"+
            "</script>")
    int selectAllSvtCount(@Param("start") int start, @Param("limit") int limit, @Param("site_id") int site_id, @Param("rtu_id") int rtu_id);

    @Insert("insert into tilt_config(site_id,rtu_id,rtu_port,rtu_baud_rate,tilt_id,tilt_name,tilt_model,tilt_location,tilt_threshold1,tilt_threshold2,tilt_space,tilt_ospace) values(#{site_id},#{rtu_id},#{rtu_port},#{rtu_baud_rate},#{tilt_id},#{tilt_name},#{tilt_model},#{tilt_location},#{tilt_threshold1},#{tilt_threshold2},#{tilt_space},#{tilt_ospace})")
    int insertSvt(Svt Svt);

    @Delete("delete from tilt_config where site_id = #{id}")
    int deleteSvtBySite(int id);

    @Delete("delete from tilt_config where rtu_id = #{id}")
    int deleteSvtByRTU(int id);

    @Delete("delete from tilt_config where rtu_id = #{rtu_id} and tilt_id = #{tilt_id} and rtu_port=#{rtu_port}")
    int deleteSvt(Map<String, Object> param);

    @Update("update tilt_config set site_id=#{site_id},rtu_baud_rate=#{rtu_baud_rate},tilt_name=#{tilt_name},tilt_model=#{tilt_model},tilt_location=#{tilt_location},tilt_threshold1=#{tilt_threshold1},tilt_threshold2=#{tilt_threshold2},tilt_space=#{tilt_space},tilt_ospace=#{tilt_ospace} where rtu_id=#{rtu_id} and rtu_port=#{rtu_port} and tilt_id=#{tilt_id}")
    int updateSvt(Svt Svt);

    @Select("select * from tilt_config where rtu_id=#{rtu_id} and rtu_port=#{rtu_port} and tilt_id=#{tilt_id}")
    Svt selectOneSvt(Map<String, Object> param);

    @Select("select * from tilt_now_data where rtu_id=#{rtu_id}")
    List<Map<String,Object>> selectSvtByRTU(int rtu_id);

    @Select("<script>" +
            "select a.*,b.tilt_location,b.tilt_name,b.tilt_model,c.* from tilt_old_data as a left join tilt_config as b on a.rtu_id=b.rtu_id and a.tilt_id=b.tilt_id and a.rtu_channel=b.rtu_port left join site_config as c on b.site_id=c.site_id where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and b.site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != -1\">" +
            "and b.rtu_id =#{rtu_id}"+
            "</if>"+
            "<if test=\"tilt_id != null and tilt_id != -1\">" +
            "and b.tilt_id =#{tilt_id}"+
            "</if>"+
            "<if test=\"tilt_location != null and tilt_location != ''\">" +
            "and tilt_location like concat('%',#{tilt_location},'%')"+
            "</if>"+
            "<if test=\"startTime != null and startTime != ''\">" +
            "and write_time between #{startTime} and #{endTime}"+
            "</if>"+
            "order by write_time desc"+
            "<if test=\"start !=null and limit != null and start != -1 and limit != -1\">" +
            "limit #{start},#{limit}"+
            "</if>"+
            "</script>")
    List<Map<String,Object>> selectSvtHistory(@Param("start") int start, @Param("limit") int limit, @Param("site_id") int site_id, @Param("rtu_id") int rtu_id, @Param("tilt_id") int tilt_id, @Param("tilt_location") String tilt_location, @Param("startTime") String startTime, @Param("endTime") String endTime);

    @Select("<script>" +
            "select count(*) from tilt_old_data as a left join tilt_config as b on a.rtu_id=b.rtu_id and a.tilt_id=b.tilt_id and a.rtu_channel=b.rtu_port left join site_config as c on b.site_id=c.site_id where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and b.site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != -1\">" +
            "and b.rtu_id =#{rtu_id}"+
            "</if>"+
            "<if test=\"tilt_id != null and tilt_id != -1\">" +
            "and b.tilt_id =#{tilt_id}"+
            "</if>"+
            "<if test=\"tilt_location != null and tilt_location != ''\">" +
            "and tilt_location like concat('%',#{tilt_location},'%')"+
            "</if>"+
            "<if test=\"startTime != null and startTime != ''\">" +
            "and write_time between #{startTime} and #{endTime}"+
            "</if>"+
            "</script>")
    int selectSvtHistoryCount(@Param("start") int start, @Param("limit") int limit, @Param("site_id") int site_id, @Param("rtu_id") int rtu_id, @Param("tilt_id") int tilt_id, @Param("tilt_location") String tilt_location, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 删除设备时同步删除rtu_alarm_data表相关信息
     * @param
     * @return
     */
    @Delete("delete from rtu_alarm_data where rtu_id=#{rtu_id} and rtu_channel=#{rtu_port} and devieceId=#{tilt_id}")
    int deleteRTUAlarmData(Map<String,Object> param);
}
