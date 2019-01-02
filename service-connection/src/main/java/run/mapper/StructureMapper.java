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

    @Insert("insert into structure(pId,level,name) values(#{id},#{level}+1,#{name})")
    int insert(Node node);

    @Delete("delete from structure where id = #{id} or pId = #{id}")
    int delete(int id);

    @Update("update structure set name=#{name} where id=#{id}")
    int update(Map<String,Object> param);
}
