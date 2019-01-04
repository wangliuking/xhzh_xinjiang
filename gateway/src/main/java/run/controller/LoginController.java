package run.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.EncryptUtil;
import run.redis.RedisTest;
import run.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping("/login")
    public Map<String,Object> login(HttpServletRequest req) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Map<String,Object> param = new HashMap<>();
        System.out.println("username为： "+username+"====="+"password为： "+ EncryptUtil.encrypt(password));
        param.put("username",username);
        param.put("password", EncryptUtil.encrypt(password));
        List<Map<String,Object>> list = loginService.selectUser(param);
        System.out.println("返回的userlist为： "+list);
        Map<String,Object> resultMap = new HashMap<>();
        if(list.size()>0){
            RedisTest.addLoginUser(req.getSession().getId(),username);
            resultMap.put("result","登录成功");
            return resultMap;
        }else{
            resultMap.put("result","登录失败");
            return resultMap;
        }
    }

    @RequestMapping("/loginOut")
    public Map<String,Object> loginOut(HttpServletRequest req) {
        RedisTest.removeLoginUser(req.getSession().getId());
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("result","退出成功");
        return resultMap;
    }
}
