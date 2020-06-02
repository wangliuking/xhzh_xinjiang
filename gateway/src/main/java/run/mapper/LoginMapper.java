package run.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import run.bean.App;

import java.util.List;
import java.util.Map;

@Repository
public interface LoginMapper {

    @Select("select * from user where username=#{username} and password=#{password}")
    List<Map<String,Object>> selectUser(Map<String,Object> param);

    @Select("select a.username,a.name,b.*,c.id as groupId,c.name as groupName from user as a left join role as b on a.roleId = b.id left join user_group as c on a.groupId=c.id where username=#{userId}")
    Map<String,Object> selectUserPower(Map<String,Object> param);

    @Select("<script>" +
            "select * from system_log where 1=1 " +
            "<if test=\"type != null and type != ''\">" +
            "and type =#{type}"+
            "</if>"+
            "<if test=\"userId != null and userId != ''\">" +
            "and userId =#{userId}"+
            "</if>"+
            "<if test=\"startTime != null and startTime != ''\">" +
            "and createTime between #{startTime} and #{endTime}"+
            "</if>"+
            "order by createTime desc"+
            "<if test=\"start !=null and limit != null\">" +
            "limit #{start},#{limit}"+
            "</if>"+
            "</script>")
    List<Map<String,Object>> selectLogList(Map<String,Object> param);

    @Select("<script>" +
            "select count(*) from system_log where 1=1 " +
            "<if test=\"type != null and type != ''\">" +
            "and type =#{type}"+
            "</if>"+
            "<if test=\"userId != null and userId != ''\">" +
            "and userId =#{userId}"+
            "</if>"+
            "<if test=\"startTime != null and startTime != ''\">" +
            "and createTime between #{startTime} and #{endTime}"+
            "</if>"+
            "</script>")
    int selectLogListCount(Map<String,Object> param);

    @Insert("insert into xhzh.system_log(type,userId,ip,content) values(#{type},#{userId},#{ip},#{content})")
    int insertLog(Map<String,Object> param);



    @Select("<script>" +
            "select * from table_app where 1=1" +
            "<if test=\"appName != null and appName != ''\">" +
            "and appName like concat('%',#{appName},'%')" +
            "</if>" +
            "<if test=\"start != null and start != -1\">" +
            "limit #{start},#{limit}"+
            "</if>" +
            "</script>")
    List<App> selectAppList(Map<String, Object> param);

    @Select("<script>" +
            "select count(*) from table_app where 1=1" +
            "<if test=\"appName != null and appName != ''\">" +
            "and appName like concat('%',#{appName},'%')" +
            "</if>" +
            "</script>")
    int selectAppListCount(Map<String, Object> param);

    @Insert("insert into table_app(appName,appVersion,content,path,uploadTime,fileName) values(#{appName},#{appVersion},#{content},#{path},now(),#{fileName})")
    int insertApp(App app);

    @Delete("delete from table_app where id=#{id}")
    int deleteApp(int id);

}
