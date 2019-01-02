package run.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import run.bean.RTU;
import run.bean.SPD;
import run.service.SPDService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SpdController {
    @Autowired
    private FeignForRTU feignForRTU;
    @Autowired
    private SPDService spdService;
    @Autowired
    private FeignForMQ feignForMQ;

    @RequestMapping(value = "/selectAllSPD",method = RequestMethod.GET)
    public Map<String,Object> selectAllSPD (HttpServletRequest req, HttpServletResponse resp){
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
            if(rtu_id == 2){
                rtu_id = -1;
            }
        }else {
            rtu_id = -1;
        }

        System.out.println(start+"=="+limit+"=="+site_id+"=="+rtu_id);
        List<Map<String,Object>> spdList = spdService.selectAllSPD(start,limit,site_id,rtu_id);
        int count = spdService.selectAllSPDCount(start,limit,site_id,rtu_id);
        Map<String,Object> spdListMap = new HashMap<>();
        spdListMap.put("items",spdList);
        spdListMap.put("totals",count);
        return spdListMap;

    }

    @RequestMapping(value = "/selectBySPD",method = RequestMethod.GET)
    public String selectBySPD (HttpServletRequest req, HttpServletResponse resp){
        int site_id = Integer.parseInt(req.getParameter("site_id"));
        int rtu_id = Integer.parseInt(req.getParameter("rtu_id"));
        int spd_number = Integer.parseInt(req.getParameter("spd_number"));
        return spdService.selectBySPD(site_id,rtu_id,spd_number).toString();
    }

    public static void main(String[] args) {
        StringBuilder spd_map = new StringBuilder("01111111");
        int i = Integer.parseInt(spd_map.toString(), 2);
        System.out.println(i);
    }

    @RequestMapping(value = "/insertSPD", method = RequestMethod.POST)
    public Map<String, Object> insertSPD (@RequestBody SPD spd){
        System.out.println("进入添加方法！！！");
        RTU rtu = feignForRTU.selectRTUById(spd.getRtu_id());

        List<Integer> list = spdService.selectByRTU(spd.getRtu_id());
        list.add(spd.getSpd_number());
        StringBuilder spd_map = new StringBuilder("00000000");
        for(int i=0;i<list.size();i++){
            int res = list.get(i);
            spd_map.replace(8-res,9-res,"1");
        }
        System.out.println(" spd_map : "+spd_map);

        Map<String,Object> params = new HashMap<>();
        params.put("spd_map",Integer.parseInt(spd_map.toString(), 2));
        params.put("rtu_id",spd.getRtu_id());
        spdService.updateSPDByRTU(params);

        Map<String,Object> tempMap = new HashMap<>();
        tempMap.put("rtu",rtu);
        spd.setSpd_map(Integer.parseInt(spd_map.toString(), 2));
        tempMap.put("spd",spd);
        String res = feignForMQ.sendSPDConfForRTU(tempMap);
        Map<String,Object> map = new HashMap<>();
        if("配置成功".equals(res)){
            int result = spdService.insertSPD(spd);
            feignForMQ.sendSPDConf(tempMap);
            if(result>0){
                map.put("success",true);
                map.put("message",res);
            }else {
                map.put("success",false);
                map.put("message","添加SPD失败");
            }
            return map;
        }else{
            map.put("success",false);
            map.put("message",res);
            return map;
        }

    }

    @RequestMapping(value = "/updateSPD", method = RequestMethod.POST)
    public Map<String, Object> updateSPD(@RequestBody SPD spd){
        RTU rtu = feignForRTU.selectRTUById(spd.getRtu_id());

        List<Integer> list = spdService.selectByRTU(spd.getRtu_id());
        list.add(spd.getSpd_number());
        StringBuilder spd_map = new StringBuilder("00000000");
        for(int i=0;i<list.size();i++){
            int res = list.get(i);
            spd_map.replace(8-res,9-res,"1");
        }
        System.out.println(" spd_map : "+spd_map);

        Map<String,Object> tempMap = new HashMap<>();
        tempMap.put("rtu",rtu);
        spd.setSpd_map(Integer.parseInt(spd_map.toString(), 2));
        tempMap.put("spd",spd);
        String res = feignForMQ.sendSPDConfForRTU(tempMap);

        Map<String,Object> map = new HashMap<>();

        if("配置成功".equals(res)){
            int result = spdService.updateSPD(spd);
            feignForMQ.sendSPDConf(tempMap);
            if(result>0){
                map.put("success",true);
                map.put("message",res);
            }else {
                map.put("success",false);
                map.put("message","更新SPD失败");
            }
            return map;
        }else{
            map.put("success",false);
            map.put("message",res);
            return map;
        }

    }

    @RequestMapping(value = "/deleteSPD", method = RequestMethod.GET)
    public Map<String, Object> deleteSPD(HttpServletRequest req, HttpServletResponse resp){
        int site_id = Integer.parseInt(req.getParameter("site_id"));
        int rtu_id = Integer.parseInt(req.getParameter("rtu_id"));
        int spd_number = Integer.parseInt(req.getParameter("spd_number"));

        RTU rtu = feignForRTU.selectRTUById(rtu_id);

        List<Integer> list = spdService.selectByRTU(rtu_id);
        StringBuilder spd_map = new StringBuilder("00000000");
        for(int i=0;i<list.size();i++){
            int res = list.get(i);
            if(res != spd_number){
                spd_map.replace(8-res,9-res,"1");
            }
        }
        System.out.println(" spd_map : "+spd_map);

        Map<String,Object> tempMap = new HashMap<>();
        SPD spd = new SPD();
        spd.setRtu_id(rtu_id);
        tempMap.put("rtu",rtu);
        spd.setSpd_map(Integer.parseInt(spd_map.toString(), 2));
        tempMap.put("spd",spd);

        int count = spdService.selectSPDNumByRTU(rtu_id);
        if(count == 1){
            //为最后一条spd需要发送消息给底端
            String res = feignForMQ.sendSPDConfForRTU(tempMap);
            Map<String,Object> map = new HashMap<>();
            if("配置成功".equals(res)){
                int result = spdService.deleteSPD(site_id,rtu_id,spd_number);
                feignForMQ.sendSPDConf(tempMap);
                if(result>0){
                    map.put("success",true);
                    map.put("message",res);
                }else {
                    map.put("success",false);
                    map.put("message","更新SPD失败");
                }
                return map;
            }else{
                map.put("success",false);
                map.put("message",res);
                return map;
            }
        }else{
            Map<String,Object> map = new HashMap<>();
            int result = spdService.deleteSPD(site_id,rtu_id,spd_number);
            feignForMQ.sendSPDConf(tempMap);
            if(result>0){
                map.put("success",true);
                map.put("message","删除SPD成功");
            }else {
                map.put("success",false);
                map.put("message","删除SPD失败");
            }
            return map;
        }

    }

    @RequestMapping(value = "/deleteSPDBySite", method = RequestMethod.GET)
    public void deleteSPDBySite(HttpServletRequest req){
        int site_id = Integer.parseInt(req.getParameter("site_id"));
        int res = spdService.deleteSPDBySite(site_id);
    }

    @RequestMapping(value = "/deleteSPDByRTU", method = RequestMethod.GET)
    public void deleteSPDByRTU(HttpServletRequest req){
        int rtu_id = Integer.parseInt(req.getParameter("rtu_id"));
        int res = spdService.deleteSPDByRTU(rtu_id);
    }


    @RequestMapping(value = "/selectAllSPDHistory",method = RequestMethod.GET)
    public Map<String,Object> selectAllSPDHistory (HttpServletRequest req, HttpServletResponse resp){
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
            if(rtu_id == 2){
                rtu_id = -1;
            }
        }else {
            rtu_id = -1;
        }
        int spd_number;
        if(req.getParameter("spd_number") != null && req.getParameter("spd_number") != ""){
            spd_number = Integer.parseInt(req.getParameter("spd_number"));
            if(spd_number == 2){
                spd_number = -1;
            }
        }else {
            spd_number = -1;
        }
        String spd_location = req.getParameter("location");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");

        //System.out.println(start+"=="+limit+"=="+site_id+"=="+rtu_id+"=="+spd_number+"=="+spd_location);
        List<Map<String,Object>> spdList = spdService.selectSPDHistory(start,limit,site_id,rtu_id,spd_number,spd_location,startTime,endTime);
        System.out.println("spdList : "+spdList);
        int count = spdService.selectSPDHistoryCount(start,limit,site_id,rtu_id,spd_number,spd_location,startTime,endTime);
        Map<String,Object> spdListMap = new HashMap<>();
        spdListMap.put("items",spdList);
        spdListMap.put("totals",count);
        return spdListMap;

    }

    @RequestMapping(value = "/selectAllSPDByRTU",method = RequestMethod.GET)
    public List<Map<String,Object>> selectAllSPDByRTU(HttpServletRequest req){
        String str = req.getParameter("rtu_id");
        int rtu_id;
        if(str != null && !"".equals(str)){
            rtu_id = Integer.parseInt(str);
            return spdService.selectAllSPDByRTU(rtu_id);
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/selectSPDCountBySite",method = RequestMethod.GET)
    public int selectSPDCountBySite(HttpServletRequest req){
        String str = req.getParameter("site_id");
        int site_id;
        if(str != null && !"".equals(str)){
            site_id = Integer.parseInt(str);
            return spdService.selectSPDCountBySite(site_id);
        }else{
            return 0;
        }
    }
}
