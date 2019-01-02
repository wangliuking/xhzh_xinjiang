package run.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.mapper.LoginMapper;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    LoginMapper loginMapper;

    public List<Map<String,Object>> selectUser(Map<String,Object> param){
        return loginMapper.selectUser(param);
    }
}
