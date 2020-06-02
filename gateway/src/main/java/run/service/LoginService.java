package run.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.bean.App;
import run.mapper.LoginMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    LoginMapper loginMapper;

    public List<Map<String,Object>> selectUser(Map<String,Object> param){
        return loginMapper.selectUser(param);
    }

    public Map<String,Object> selectUserPower(Map<String,Object> param){
        return loginMapper.selectUserPower(param);
    }

    public List<Map<String,Object>> selectLogList(Map<String,Object> param){
        return loginMapper.selectLogList(param);
    }

    public int selectLogListCount(Map<String,Object> param){
        return loginMapper.selectLogListCount(param);
    }

    public int insertLog(Map<String,Object> param){
        return loginMapper.insertLog(param);
    }


    public void deleteApp(int id) {
        loginMapper.deleteApp(id);
    }

    public void addApp(App app) {
        loginMapper.insertApp(app);
    }

    public Map<String, Object> selectAppList(Map<String, Object> param) {
        List<App> list = loginMapper.selectAppList(param);
        int total = loginMapper.selectAppListCount(param);
        Map<String, Object> resultMap = new HashMap<String, Object>() {{
            put("items", list);
            put("total", total);
        }};
        return resultMap;
    }
}
