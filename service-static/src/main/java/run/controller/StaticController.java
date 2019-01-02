package run.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import run.bean.RTU;
import run.bean.Static;
import run.service.StaticService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StaticController {
    @Autowired
    private StaticService StaticService;
    @Autowired
    private FeignForMQ feignForMQ;
    @Autowired
    private FeignForRTU feignForRTU;

    @RequestMapping(value = "/selectAllStatic",method = RequestMethod.GET)
    public Map<String,Object> selectAllStatic (HttpServletRequest req, HttpServletResponse resp){
        int start;
        if(req.getParameter("start") != null){
            start = Integer.parseInt(req.getParameter("start"));
        }else {
            start = -1;
        }
        int limit;
        if(req.getParameter("limit") != null){
            limit = Integer.parseInt(req.getParameter("limit"));
        }else {
            limit = -1;
        }
        int site_id;
        if(req.getParameter("site_id") != null && req.getParameter("site_id") != ""){
            site_id = Integer.parseInt(req.getParameter("site_id"));
        }else {
            site_id = -1;
        }
        int rtu_id;
        if(req.getParameter("rtu_id") != null && req.getParameter("rtu_id") != ""){
            rtu_id = Integer.parseInt(req.getParameter("rtu_id"));
        }else {
            rtu_id = -1;
        }

        System.out.println(start+"=="+limit+"=="+site_id+"=="+rtu_id);
        List<Map<String,Object>> StaticList = StaticService.selectAllStatic(start,limit,site_id,rtu_id);
        int count = StaticService.selectAllStaticCount(start,limit,site_id,rtu_id);
        Map<String,Object> StaticListMap = new HashMap<>();
        StaticListMap.put("items",StaticList);
        StaticListMap.put("totals",count);
        return StaticListMap;

    }

    @RequestMapping(value = "/insertStatic", method = RequestMethod.POST)
    public Map<String, Object> insertStatic (@RequestBody Static Static){
        System.out.println("进入添加方法！！！");
        RTU rtu = feignForRTU.selectRTUById(Static.getRtu_id());
        System.out.println(Static);

        Map<String,Object> tempParams = new HashMap<>();
        tempParams.put("rtu",rtu);
        tempParams.put("Static",Static);
        tempParams.put("op",0);
        /*int i = StaticService.insertStatic(Static);
        Map<String,Object> map = new HashMap<>();
        if(i>0){
            map.put("success",true);
            map.put("message","添加成功");
            return map;
        }else{
            map.put("success",true);
            map.put("message","添加失败");
            return map;
        }*/
        //feignForMQ.sendStaticConfForRTU(tempParams);
        //feignForMQ.sendStaticConf(tempParams);
        String res = feignForMQ.sendStaticConfForRTU(tempParams);
        Map<String,Object> map = new HashMap<>();
        if("配置成功".equals(res)){
            StaticService.insertStatic(Static);
            feignForMQ.sendStaticConf(tempParams);

            map.put("success",true);
            map.put("message",res);
            return map;
        }else{
            map.put("success",false);
            map.put("message",res);
            return map;
        }

    }


    @RequestMapping(value = "/updateStatic", method = RequestMethod.POST)
    public Map<String, Object> updateStatic (@RequestBody Static Static){
        System.out.println("进入修改方法！！！");
        RTU rtu = feignForRTU.selectRTUById(Static.getRtu_id());
        System.out.println(Static);

        /*int i = StaticService.updateStatic(Static);
        Map<String,Object> map = new HashMap<>();
        if(i>0){
            map.put("success",true);
            map.put("message","更新成功");
            return map;
        }else{
            map.put("success",true);
            map.put("message","更新失败");
            return map;
        }*/

        Map<String,Object> tempParams = new HashMap<>();
        tempParams.put("rtu",rtu);
        tempParams.put("Static",Static);
        tempParams.put("op",0);
        String res = feignForMQ.sendStaticConfForRTU(tempParams);
        Map<String,Object> map = new HashMap<>();
        if("配置成功".equals(res)){
            StaticService.updateStatic(Static);
            feignForMQ.sendStaticConf(tempParams);

            map.put("success",true);
            map.put("message",res);
            return map;
        }else{
            map.put("success",false);
            map.put("message",res);
            return map;
        }


    }


    @RequestMapping(value = "/deleteStatic", method = RequestMethod.GET)
    public Map<String, Object> deleteStatic(HttpServletRequest req, HttpServletResponse resp){
        int rtu_id = Integer.parseInt(req.getParameter("rtu_id"));
        int staet_id = Integer.parseInt(req.getParameter("staet_id"));
        int rtu_port = Integer.parseInt(req.getParameter("rtu_port"));
        RTU rtu = feignForRTU.selectRTUById(rtu_id);

        Map<String,Object> params = new HashMap<>();
        params.put("rtu_id",rtu_id);
        params.put("staet_id",staet_id);
        params.put("rtu_port",rtu_port);

        Static Static = StaticService.selectOneLight(params);
        System.out.println("查询后的Static为 ： "+Static);

        /*int i = StaticService.deleteStatic(params);
        Map<String,Object> map = new HashMap<>();
        if(i>0){
            map.put("success",true);
            map.put("message","删除成功");
            return map;
        }else{
            map.put("success",true);
            map.put("message","删除失败");
            return map;
        }*/

        Map<String,Object> tempParams = new HashMap<>();
        tempParams.put("rtu",rtu);
        tempParams.put("Static",Static);
        tempParams.put("op",0);
        String res = feignForMQ.sendStaticConfForRTU(tempParams);
        Map<String,Object> map = new HashMap<>();
        if("配置成功".equals(res)){
            StaticService.deleteStatic(params);
            feignForMQ.sendStaticConf(tempParams);
            map.put("success",true);
            map.put("message",res);
            return map;
        }else{
            map.put("success",false);
            map.put("message",res);
            return map;
        }
    }

    @RequestMapping(value = "/deleteStaticBySite", method = RequestMethod.GET)
    public void deleteStaticBySite(HttpServletRequest req){
        int site_id = Integer.parseInt(req.getParameter("site_id"));
        int res = StaticService.deleteStaticBySite(site_id);
    }

    @RequestMapping(value = "/deleteStaticByRTU", method = RequestMethod.GET)
    public void deleteStaticByRTU(HttpServletRequest req){
        int rtu_id = Integer.parseInt(req.getParameter("rtu_id"));
        int res = StaticService.deleteStaticByRTU(rtu_id);
    }


    @RequestMapping(value = "/selectAllStaticHistory",method = RequestMethod.GET)
    public Map<String,Object> selectAllStaticHistory (HttpServletRequest req, HttpServletResponse resp){
        int start;
        if(req.getParameter("start") != null){
            start = Integer.parseInt(req.getParameter("start"));
        }else {
            start = -1;
        }
        int limit;
        if(req.getParameter("limit") != null){
            limit = Integer.parseInt(req.getParameter("limit"));
        }else {
            limit = -1;
        }
        int site_id;
        if(req.getParameter("site_id") != null && req.getParameter("site_id") != ""){
            site_id = Integer.parseInt(req.getParameter("site_id"));
        }else {
            site_id = -1;
        }
        int rtu_id;
        if(req.getParameter("rtu_id") != null && req.getParameter("rtu_id") != ""){
            rtu_id = Integer.parseInt(req.getParameter("rtu_id"));
        }else {
            rtu_id = -1;
        }
        int staet_id;
        if(req.getParameter("staet_id") != null && req.getParameter("staet_id") != ""){
            staet_id = Integer.parseInt(req.getParameter("staet_id"));
        }else {
            staet_id = -1;
        }
        String staet_location = req.getParameter("location");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");

        //System.out.println(start+"=="+limit+"=="+site_id+"=="+rtu_id+"=="+spd_number+"=="+spd_location);
        List<Map<String,Object>> StaticList = StaticService.selectStaticHistory(start,limit,site_id,rtu_id,staet_id,staet_location,startTime,endTime);
        int count = StaticService.selectStaticHistoryCount(start,limit,site_id,rtu_id,staet_id,staet_location,startTime,endTime);
        Map<String,Object> StaticListMap = new HashMap<>();
        StaticListMap.put("items",StaticList);
        StaticListMap.put("totals",count);
        return StaticListMap;

    }

    @RequestMapping(value = "/selectStaticByRTU",method = RequestMethod.GET)
    public List<Map<String,Object>> selectStaticByRTU(HttpServletRequest req){
        String str = req.getParameter("rtu_id");
        if(str != null && !"".equals(str)){
            int rtu_id = Integer.parseInt(str);
            return StaticService.selectStaticByRTU(rtu_id);
        }else{
            return null;
        }
    }

}
