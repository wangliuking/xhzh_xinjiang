package run.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import run.bean.Role;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleMapper {

    @Select("select * from xhzh.role where id = #{id}")
    Role selectRoleById(int id);

    @Select("<script>" +
            "select * from xhzh.role where 1=1 " +
            "<if test=\"param != null and param != ''\">" +
            "and name like concat('%',#{param},'%')" +
            "</if>" +
            "limit #{start},#{limit}"+
            "</script>")
    List<Role> selectRoleList(Map<String, Object> param);

    @Select("<script>" +
            "select count(*) from xhzh.role where 1=1 " +
            "<if test=\"param != null and param != ''\">" +
            "and name like concat('%',#{param},'%')" +
            "</if>" +
            "</script>")
    int selectRoleListCount(Map<String, Object> param);

    @Insert("insert into xhzh.role(name) values(#{name})")
    int insertRole(Role role);

    @Update("update xhzh.role set name=#{name} where id=#{id}")
    int updateRole(Role role);

    @Delete("delete from xhzh.role where id=#{id}")
    int deleteRole(int id);
}
