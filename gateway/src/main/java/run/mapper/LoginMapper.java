package run.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LoginMapper {

    @Select("select * from user where username=#{username} and password=#{password}")
    List<Map<String,Object>> selectUser(Map<String,Object> param);
}
