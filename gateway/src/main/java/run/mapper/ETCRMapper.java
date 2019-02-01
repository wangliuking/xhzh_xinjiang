package run.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ETCRMapper {

    @Select("<script>" +
            "select a.*,b.rst_location,b.rst_name,b.rst_model,c.* from lightingdetection.resistance_old_data as a left join lightingdetection.resistance_config as b on a.rtu_id=b.rtu_id and a.rst_id=b.rst_id and a.relayno=b.relayno left join lightingdetection.site_config as c on b.site_id=c.site_id where 1=1 " +
            "<if test=\"site_id != null and site_id != -1\">" +
            "and b.site_id =#{site_id}"+
            "</if>"+
            "<if test=\"rtu_id != null and rtu_id != -1\">" +
            "and b.rtu_id =#{rtu_id}"+
            "</if>"+
            "<if test=\"rst_id != null and rst_id != -1\">" +
            "and a.rst_id =#{rst_id}"+
            "</if>"+
            "<if test=\"rst_location != null and rst_location != ''\">" +
            "and rst_location like concat('%',#{rst_location},'%')"+
            "</if>"+
            "<if test=\"startTime != null and startTime != ''\">" +
            "and write_time between #{startTime} and #{endTime}"+
            "</if>"+
            "order by write_time desc"+
            "</script>")
    List<Map<String,Object>> exportAllETCRHistoryExcel(@Param("site_id") int site_id, @Param("rtu_id") int rtu_id, @Param("rst_id") int rst_id, @Param("rst_location") String rst_location, @Param("startTime") String startTime, @Param("endTime") String endTime);
}
