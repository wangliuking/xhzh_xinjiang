package run.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import run.bean.Cat;
import run.bean.RTU;
import run.service.CatService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CatController {
    @Autowired
    private CatService catService;
    @Autowired
    private FeignForMQ feignForMQ;
    @Autowired
    private FeignForRTU feignForRTU;

    @RequestMapping(value = "/selectAllCat",method = RequestMethod.GET)
    public Map<String,Object> selectAllCat (HttpServletRequest req, HttpServletResponse resp){
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
        List<Map<String,Object>> CatList = catService.selectAllCat(start,limit,site_id,rtu_id);
        int count = catService.selectAllCatCount(start,limit,site_id,rtu_id);
        Map<String,Object> CatListMap = new HashMap<>();
        CatListMap.put("items",CatList);
        CatListMap.put("totals",count);
        return CatListMap;

    }

    @RequestMapping(value = "/insertCat", method = RequestMethod.POST)
    public Map<String, Object> insertCat (@RequestBody Cat Cat){
        System.out.println("进入添加方法！！！");
        RTU rtu = feignForRTU.selectRTUById(Cat.getRtu_id());
        System.out.println(Cat);

        Map<String,Object> tempParams = new HashMap<>();
        tempParams.put("rtu",rtu);
        tempParams.put("cat",Cat);
        tempParams.put("op",0);
        /*int i = catService.insertCat(Cat);
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
        //feignForMQ.sendCatConfForRTU(tempParams);
        //feignForMQ.sendCatConf(tempParams);
        String res = feignForMQ.sendCatConfForRTU(tempParams);
        Map<String,Object> map = new HashMap<>();
        if("配置成功".equals(res)){
            catService.insertCat(Cat);
            feignForMQ.sendCatConf(tempParams);

            map.put("success",true);
            map.put("message",res);
            return map;
        }else{
            map.put("success",false);
            map.put("message",res);
            return map;
        }

    }


    @RequestMapping(value = "/updateCat", method = RequestMethod.POST)
    public Map<String, Object> updateCat (@RequestBody Cat Cat){
        System.out.println("进入修改方法！！！");
        RTU rtu = feignForRTU.selectRTUById(Cat.getRtu_id());
        System.out.println(Cat);

        /*int i = catService.updateCat(Cat);
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
        tempParams.put("cat",Cat);
        tempParams.put("op",0);
        String res = feignForMQ.sendCatConfForRTU(tempParams);
        Map<String,Object> map = new HashMap<>();
        if("配置成功".equals(res)){
            catService.updateCat(Cat);
            feignForMQ.sendCatConf(tempParams);

            map.put("success",true);
            map.put("message",res);
            return map;
        }else{
            map.put("success",false);
            map.put("message",res);
            return map;
        }


    }


    @RequestMapping(value = "/deleteCat", method = RequestMethod.GET)
    public Map<String, Object> deleteCat(HttpServletRequest req, HttpServletResponse resp){
        int rtu_id = Integer.parseInt(req.getParameter("rtu_id"));
        int cathode_id = Integer.parseInt(req.getParameter("cathode_id"));
        int rtu_port = Integer.parseInt(req.getParameter("rtu_port"));
        RTU rtu = feignForRTU.selectRTUById(rtu_id);

        Map<String,Object> params = new HashMap<>();
        params.put("rtu_id",rtu_id);
        params.put("cathode_id",cathode_id);
        params.put("rtu_port",rtu_port);

        Cat Cat = catService.selectOneLight(params);
        System.out.println("查询后的Cat为 ： "+Cat);

        /*int i = catService.deleteCat(params);
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
        tempParams.put("cat",Cat);
        tempParams.put("op",1);
        String res = feignForMQ.sendCatConfForRTU(tempParams);
        Map<String,Object> map = new HashMap<>();
        if("配置成功".equals(res)){
            catService.deleteCat(params);
            feignForMQ.sendCatConf(tempParams);
            map.put("success",true);
            map.put("message",res);
            return map;
        }else{
            map.put("success",false);
            map.put("message",res);
            return map;
        }
    }

    @RequestMapping(value = "/deleteCatBySite", method = RequestMethod.GET)
    public void deleteCatBySite(HttpServletRequest req){
        int site_id = Integer.parseInt(req.getParameter("site_id"));
        int res = catService.deleteCatBySite(site_id);
    }

    @RequestMapping(value = "/deleteCatByRTU", method = RequestMethod.GET)
    public void deleteCatByRTU(HttpServletRequest req){
        int rtu_id = Integer.parseInt(req.getParameter("rtu_id"));
        int res = catService.deleteCatByRTU(rtu_id);
    }


    @RequestMapping(value = "/selectAllCatHistory",method = RequestMethod.GET)
    public Map<String,Object> selectAllCatHistory (HttpServletRequest req, HttpServletResponse resp){
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
        int cathode_id;
        if(req.getParameter("cathode_id") != null && req.getParameter("cathode_id") != ""){
            cathode_id = Integer.parseInt(req.getParameter("cathode_id"));
        }else {
            cathode_id = -1;
        }
        String cathode_location = req.getParameter("location");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");

        //System.out.println(start+"=="+limit+"=="+site_id+"=="+rtu_id+"=="+spd_number+"=="+spd_location);
        List<Map<String,Object>> CatList = catService.selectCatHistory(start,limit,site_id,rtu_id,cathode_id,cathode_location,startTime,endTime);
        int count = catService.selectCatHistoryCount(start,limit,site_id,rtu_id,cathode_id,cathode_location,startTime,endTime);
        Map<String,Object> CatListMap = new HashMap<>();
        CatListMap.put("items",CatList);
        CatListMap.put("totals",count);
        return CatListMap;

    }

    @RequestMapping(value = "/selectCatByRTU",method = RequestMethod.GET)
    public List<Map<String,Object>> selectCatByRTU(HttpServletRequest req){
        String str = req.getParameter("rtu_id");
        if(str != null && !"".equals(str)){
            int rtu_id = Integer.parseInt(str);
            return catService.selectCatByRTU(rtu_id);
        }else{
            return null;
        }
    }

}
