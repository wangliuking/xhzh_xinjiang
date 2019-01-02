package run.mapper;

import org.apache.ibatis.annotations.*;
import run.bean.Site;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SiteMapper {

    @Select("<script>" +
            "select * from site_config where 1=1" +
            "<if test=\"site_name != null and site_name != '' and site_name != 'null'\">" +
            "and site_name =#{site_name}"+
            "</if>"+
            "<if test=\"site_industry != null and site_industry != '' and site_industry != 'null'\">" +
            "and site_industry =#{site_industry}"+
            "</if>"+
            "<if test=\"site_province != null and site_province != '' and site_province != 'null'\">" +
            "and site_province =#{site_province}"+
            "</if>"+
            "<if test=\"site_city != null and site_city != '' and site_city != 'null'\">" +
            "and site_city =#{site_city}"+
            "</if>"+
            "<if test=\"site_county != null and site_county != '' and site_county != 'null'\">" +
            "and site_county =#{site_county}"+
            "</if>"+
            "<if test=\"site_company != null and site_company != '' and site_company != 'null'\">" +
            "and site_company =#{site_company}"+
            "</if>"+
            "order by site_id"+
            "</script>")
    List<Map<String,Object>> selectAllSite(Map<String,Object> param);

    @Select("select * from site_config where site_id = #{id}")
    Site selectSiteById(int id);

    @Insert("insert into site_config(site_name,site_industry,site_province,site_city,site_county,site_company,site_lat,site_lng,createTime) values(#{site_name},#{site_industry},#{site_province},#{site_city},#{site_county},#{site_company},#{site_lat},#{site_lng},now())")
    int insertSite(Site site);

    @Delete("delete from site_config where site_id = #{id}")
    int deleteSite(int id);

    @Update("update site_config set site_name=#{site_name},site_industry=#{site_industry},site_province=#{site_province},site_city=#{site_city},site_county=#{site_county},site_company=#{site_company},site_lat=#{site_lat},site_lng=#{site_lng},createTime=now() where site_id=#{site_id}")
    int updateSite(Site site);
}
