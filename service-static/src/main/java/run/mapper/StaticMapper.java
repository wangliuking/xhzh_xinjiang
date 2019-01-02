package run.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import run.bean.Static;

import java.util.List;
import java.util.Map;

@Repository
public interface StaticMapper {

    @Select("<script>" +
            "select a.*,b.staet_state from static_electricity_config as a left join static_electricity_now_data as b on a.rtu_id=b.rtu_id and a.rtu_port=b.rtu_channel and a.staet_id=b.staet_id where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and a.site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != -1\">" +
            "and a.rtu_id =#{rtu_id}"+
            "</if>"+
            "group by a.rtu_id,a.rtu_port,a.staet_id"+
            "<if test=\"start !=null and limit != null and start != -1 and limit != -1\">" +
            "limit #{start},#{limit}"+
            "</if>"+
            "</script>")
    List<Map<String,Object>> selectAllStatic(@Param("start") int start, @Param("limit") int limit, @Param("site_id") int site_id, @Param("rtu_id") int rtu_id);

    @Select("<script>" +
            "select count(*) from ("+
            "select * from static_electricity_config where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != -1\">" +
            "and rtu_id =#{rtu_id}"+
            "</if>"+
            "group by rtu_id,rtu_port,staet_id"+
            ") as t"+
            "</script>")
    int selectAllStaticCount(@Param("start") int start, @Param("limit") int limit, @Param("site_id") int site_id, @Param("rtu_id") int rtu_id);

    @Insert("insert into static_electricity_config(site_id,rtu_id,rtu_port,rtu_baud_rate,staet_id,staet_name,staet_model,staet_location,staet_threshold1,staet_threshold2,staet_k,staet_v0,staet_space,staet_ospace) values(#{site_id},#{rtu_id},#{rtu_port},#{rtu_baud_rate},#{staet_id},#{staet_name},#{staet_model},#{staet_location},#{staet_threshold1},#{staet_threshold2},#{staet_k},#{staet_v0},#{staet_space},#{staet_ospace})")
    int insertStatic(Static Static);

    @Delete("delete from static_electricity_config where site_id = #{id}")
    int deleteStaticBySite(int id);

    @Delete("delete from static_electricity_config where rtu_id = #{id}")
    int deleteStaticByRTU(int id);

    @Delete("delete from static_electricity_config where rtu_id = #{rtu_id} and staet_id = #{staet_id} and rtu_port=#{rtu_port}")
    int deleteStatic(Map<String, Object> param);

    @Update("update static_electricity_config set site_id=#{site_id},rtu_baud_rate=#{rtu_baud_rate},staet_name=#{staet_name},staet_model=#{staet_model},staet_location=#{staet_location},staet_threshold1=#{staet_threshold1},staet_threshold2=#{staet_threshold2},staet_k=#{staet_k},staet_v0=#{staet_v0},staet_space=#{staet_space},staet_ospace=#{staet_ospace} where rtu_id=#{rtu_id} and rtu_port=#{rtu_port} and staet_id=#{staet_id}")
    int updateStatic(Static Static);

    @Select("select * from static_electricity_config where rtu_id=#{rtu_id} and rtu_port=#{rtu_port} and staet_id=#{staet_id}")
    Static selectOneStatic(Map<String, Object> param);

    @Select("select * from static_electricity_now_data where rtu_id=#{rtu_id}")
    List<Map<String,Object>> selectStaticByRTU(int rtu_id);

    @Select("<script>" +
            "select a.*,b.staet_location,b.staet_name,b.staet_model,c.* from static_electricity_old_data as a left join static_electricity_config as b on a.rtu_id=b.rtu_id and a.staet_id=b.staet_id and a.rtu_channel=b.rtu_port left join site_config as c on b.site_id=c.site_id where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and b.site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != -1\">" +
            "and b.rtu_id =#{rtu_id}"+
            "</if>"+
            "<if test=\"staet_id != null and staet_id != -1\">" +
            "and b.staet_id =#{staet_id}"+
            "</if>"+
            "<if test=\"staet_location != null and staet_location != ''\">" +
            "and staet_location like concat('%',#{staet_location},'%')"+
            "</if>"+
            "<if test=\"startTime != null and startTime != ''\">" +
            "and write_time between #{startTime} and #{endTime}"+
            "</if>"+
            "order by write_time desc"+
            "<if test=\"start !=null and limit != null and start != -1 and limit != -1\">" +
            "limit #{start},#{limit}"+
            "</if>"+
            "</script>")
    List<Map<String,Object>> selectStaticHistory(@Param("start") int start, @Param("limit") int limit, @Param("site_id") int site_id, @Param("rtu_id") int rtu_id, @Param("staet_id") int staet_id, @Param("staet_location") String staet_location, @Param("startTime") String startTime, @Param("endTime") String endTime);

    @Select("<script>" +
            "select count(*) from static_electricity_old_data as a left join static_electricity_config as b on a.rtu_id=b.rtu_id and a.staet_id=b.staet_id and a.rtu_channel=b.rtu_port left join site_config as c on b.site_id=c.site_id where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and b.site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != -1\">" +
            "and b.rtu_id =#{rtu_id}"+
            "</if>"+
            "<if test=\"staet_id != null and staet_id != -1\">" +
            "and b.staet_id =#{staet_id}"+
            "</if>"+
            "<if test=\"staet_location != null and staet_location != ''\">" +
            "and staet_location like concat('%',#{staet_location},'%')"+
            "</if>"+
            "<if test=\"startTime != null and startTime != ''\">" +
            "and write_time between #{startTime} and #{endTime}"+
            "</if>"+
            "</script>")
    int selectStaticHistoryCount(@Param("start") int start, @Param("limit") int limit, @Param("site_id") int site_id, @Param("rtu_id") int rtu_id, @Param("staet_id") int staet_id, @Param("staet_location") String staet_location, @Param("startTime") String startTime, @Param("endTime") String endTime);
}
