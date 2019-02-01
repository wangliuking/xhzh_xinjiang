package run.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import run.bean.Node;
import run.bean.RTU;

import java.util.List;
import java.util.Map;

@Repository
public interface StructureMapper {

    @Select("select * from structure")
    List<Node> selectAll();

    @Select("<script>" +
            "select * from structure where level!=0" +
            "<if test=\"industry != null and industry != '' and industry != 'null'\">" +
            "and industry =#{industry}"+
            "</if>"+
            "order by id"+
            "</script>")
    List<Map<String,Object>> selectStructureList(Map<String,Object> param);

    @Insert("insert into structure(pId,level,name) values(#{id},#{level}+1,#{name})")
    int insert(Node node);

    @Delete("delete from structure where id = #{id} or pId = #{id}")
    int delete(int id);

    @Update("update structure set name=#{name} where id=#{id}")
    int update(Map<String,Object> param);
}
