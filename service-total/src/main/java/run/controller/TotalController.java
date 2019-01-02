package run.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import run.bean.RTU;
import run.service.TotalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class TotalController {
    @Autowired
    private TotalService totalService;

    @RequestMapping(value = "/selectSiteAllStatus",method = RequestMethod.GET)
    public Map<String,Object> selectSiteAllStatus(HttpServletRequest req){
        String siteId = req.getParameter("site_id");
        Map<String,Object> param = new HashMap<>();
        if(siteId != null && !"".equals(siteId)){
            int site_id = Integer.parseInt(siteId);
            param.put("rtu_id","");
            param.put("site_id",site_id);
        }else{
            param.put("rtu_id","");
            param.put("site_id","");
        }
        System.out.println("param : "+param);
        List<Map<String,Object>> siteInfo = totalService.selectSiteById(param);
        List<Map<String,Object>> rtuOffNum = totalService.selectRTUOff(param);
        List<Map<String,Object>> rtuWarningNum = totalService.selectRTUWarning(param);
        int rtuNum = totalService.selectRTUNumBySiteId(param);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("siteInfo",siteInfo);
        System.out.println(" siteInfo : "+siteInfo);
        resultMap.put("rtuOffNum",rtuOffNum.size());
        resultMap.put("rtuWarningNum",rtuWarningNum.size());
        resultMap.put("rtuNum",rtuNum);
        return resultMap;
    }

    @RequestMapping(value = "/selectSiteAllInfo",method = RequestMethod.GET)
    public Map<String,Object> selectSiteAllInfo(HttpServletRequest req){
        String rtuId = req.getParameter("rtu_id");
        String siteId = req.getParameter("site_id");
        Map<String,Object> param = new HashMap<>();
        if(rtuId != null && !"".equals(rtuId)){
            int rtu_id = Integer.parseInt(rtuId);
            param.put("rtu_id",rtu_id);
            param.put("site_id","");
        }else if(siteId != null && !"".equals(siteId)){
            int site_id = Integer.parseInt(siteId);
            param.put("rtu_id","");
            param.put("site_id",site_id);
        }else{
            param.put("rtu_id","");
            param.put("site_id","");
        }
        System.out.println("param : "+param);
        List<Map<String,Object>> siteInfo = totalService.selectSiteById(param);
        List<Map<String,Object>> deviceOff = totalService.selectDeviceOff(param);
        List<Map<String,Object>> deviceWarning = totalService.selectDeviceWarning(param);
        int rtuNum = totalService.selectRTUNumBySiteId(param);
        List<Map<String,Object>> rtuOffNum = totalService.selectRTUOff(param);
        List<Map<String,Object>> rtuWarningNum = totalService.selectRTUWarning(param);
        List<Map<String,Object>> spdNum = totalService.selectSPDCount(param);
        List<Map<String,Object>> etcrNum = totalService.selectETCRCount(param);
        List<Map<String,Object>> lightningNum = totalService.selectLightningCount(param);
        List<Map<String,Object>> staticNum = totalService.selectStaticCount(param);
        List<Map<String,Object>> rswsNum = totalService.selectRswsCount(param);
        List<Map<String,Object>> svtNum = totalService.selectSvtCount(param);
        List<Map<String,Object>> hcNum = totalService.selectHcCount(param);
        List<Map<String,Object>> strayNum = totalService.selectStrayCount(param);
        List<Map<String,Object>> catNum = totalService.selectCatCount(param);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("siteInfo",siteInfo);
        System.out.println(" siteInfo : "+siteInfo);
        resultMap.put("deviceOffCount",deviceOff.size());
        resultMap.put("deviceWarningCount",deviceWarning.size());
        resultMap.put("rtuNum",rtuNum);
        resultMap.put("rtuOffNum",rtuOffNum.size());
        resultMap.put("rtuWarningNum",rtuWarningNum.size());
        resultMap.put("spdNum",spdNum.size());
        resultMap.put("etcrNum",etcrNum.size());
        resultMap.put("lightningNum",lightningNum.size());
        resultMap.put("staticNum",staticNum.size());
        resultMap.put("rswsNum",rswsNum.size());
        resultMap.put("svtNum",svtNum.size());
        resultMap.put("hcNum",hcNum.size());
        resultMap.put("strayNum",strayNum.size());
        resultMap.put("catNum",catNum.size());
        return resultMap;
    }

    @RequestMapping(value = "/selectDeviceNum",method = RequestMethod.GET)
    public Map<String,Object> selectDeviceNum(HttpServletRequest req){
        String rtuId = req.getParameter("rtu_id");
        String siteId = req.getParameter("site_id");
        Map<String,Object> param = new HashMap<>();
        if(rtuId != null && !"".equals(rtuId)){
            int rtu_id = Integer.parseInt(rtuId);
            Map<String,Object> tMap = totalService.selectRTUById(rtu_id);
            int site_id = Integer.parseInt(tMap.get("site_id")+"");
            param.put("rtu_id",rtu_id);
            param.put("site_id",site_id);
        }else if(siteId != null && !"".equals(siteId)){
            int site_id = Integer.parseInt(siteId);
            param.put("rtu_id","");
            param.put("site_id",site_id);
        }else{
            param.put("rtu_id","");
            param.put("site_id","");
        }
        Map<String,Object> siteInfo = totalService.selectSiteById(param).get(0);
        List<Map<String,Object>> deviceOff = totalService.selectDeviceOff(param);
        List<Map<String,Object>> deviceWarning = totalService.selectDeviceWarning(param);
        int rtuNum = totalService.selectRTUNumBySiteId(param);
        List<Map<String,Object>> spdNum = totalService.selectSPDCount(param);
        List<Map<String,Object>> etcrNum = totalService.selectETCRCount(param);
        List<Map<String,Object>> lightningNum = totalService.selectLightningCount(param);
        List<Map<String,Object>> staticNum = totalService.selectStaticCount(param);
        List<Map<String,Object>> rswsNum = totalService.selectRswsCount(param);
        List<Map<String,Object>> svtNum = totalService.selectSvtCount(param);
        List<Map<String,Object>> hcNum = totalService.selectHcCount(param);
        List<Map<String,Object>> strayNum = totalService.selectStrayCount(param);
        List<Map<String,Object>> catNum = totalService.selectCatCount(param);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("siteInfo",siteInfo);
        resultMap.put("deviceOffCount",deviceOff.size());
        resultMap.put("deviceWarningCount",deviceWarning.size());
        resultMap.put("rtuNum",rtuNum);
        resultMap.put("spdNum",spdNum.size());
        resultMap.put("etcrNum",etcrNum.size());
        resultMap.put("lightningNum",lightningNum.size());
        resultMap.put("staticNum",staticNum.size());
        resultMap.put("rswsNum",rswsNum.size());
        resultMap.put("svtNum",svtNum.size());
        resultMap.put("hcNum",hcNum.size());
        resultMap.put("strayNum",strayNum.size());
        resultMap.put("catNum",catNum.size());
        return resultMap;
    }

    @RequestMapping(value = "/selectRTUPort",method = RequestMethod.GET)
    public Map<String,Object> selectRTUPort(HttpServletRequest req){
        String rtuId = req.getParameter("rtu_id");
        Map<String,Object> param = new HashMap<>();
        if(rtuId != null && !"".equals(rtuId)){
            int rtu_id = Integer.parseInt(rtuId);
            param.put("rtu_id",rtu_id);
        }
        Map<String,Object> resultMap = new HashMap<>();
        //数字量
        List<Integer> spdPort = totalService.selectSPDPort(param);
        resultMap.put("spdPort",spdPort);
        //模拟量
        List<Map<Integer,String>> testList = new ArrayList<>();
        List<Integer> staryTest = totalService.selectStrayTestPort(param);
        if(staryTest != null){
            for(int i=0;i<staryTest.size();i++){
                int temp = staryTest.get(i);
                Map<Integer,String> tempMap = new HashMap<>();
                tempMap.put(temp,"杂散电流");
                testList.add(tempMap);
            }
        }
        List<Integer> catTest = totalService.selectCatTestPort(param);
        if(catTest != null){
            for(int i=0;i<catTest.size();i++){
                int temp = catTest.get(i);
                Map<Integer,String> tempMap = new HashMap<>();
                tempMap.put(temp,"阴极保护");
                testList.add(tempMap);
            }
        }
        resultMap.put("testList",testList);
        //485
        List<Map<Integer,String>> rs485List = new ArrayList<>();
        List<Integer> etcrPort = totalService.selectETCRPort(param);
        if(etcrPort != null){
            for(int i=0;i<etcrPort.size();i++){
                int temp = etcrPort.get(i);
                Map<Integer,String> tempMap = new HashMap<>();
                tempMap.put(temp,"接地电阻");
                rs485List.add(tempMap);
            }
        }
        List<Integer> lightningPort = totalService.selectLightningPort(param);
        if(lightningPort != null){
            for(int i=0;i<lightningPort.size();i++){
                int temp = lightningPort.get(i);
                Map<Integer,String> tempMap = new HashMap<>();
                tempMap.put(temp,"雷电流");
                rs485List.add(tempMap);
            }
        }
        List<Integer> staticPort = totalService.selectStaticPort(param);
        if(staticPort != null){
            for(int i=0;i<staticPort.size();i++){
                int temp = staticPort.get(i);
                Map<Integer,String> tempMap = new HashMap<>();
                tempMap.put(temp,"静电");
                rs485List.add(tempMap);
            }
        }
        List<Integer> rswsPort = totalService.selectRswsPort(param);
        if(rswsPort != null){
            for(int i=0;i<rswsPort.size();i++){
                int temp = rswsPort.get(i);
                Map<Integer,String> tempMap = new HashMap<>();
                tempMap.put(temp,"温湿度");
                rs485List.add(tempMap);
            }
        }
        List<Integer> svtPort = totalService.selectSvtPort(param);
        if(svtPort != null){
            for(int i=0;i<svtPort.size();i++){
                int temp = svtPort.get(i);
                Map<Integer,String> tempMap = new HashMap<>();
                tempMap.put(temp,"倾斜度");
                rs485List.add(tempMap);
            }
        }
        List<Integer> hcPort = totalService.selectHcPort(param);
        if(hcPort != null){
            for(int i=0;i<hcPort.size();i++){
                int temp = hcPort.get(i);
                Map<Integer,String> tempMap = new HashMap<>();
                tempMap.put(temp,"电气安全");
                rs485List.add(tempMap);
            }
        }
        List<Integer> strayPort = totalService.selectStray485Port(param);
        if(strayPort != null){
            for(int i=0;i<strayPort.size();i++){
                int temp = strayPort.get(i);
                Map<Integer,String> tempMap = new HashMap<>();
                tempMap.put(temp,"杂散电流");
                rs485List.add(tempMap);
            }
        }
        List<Integer> catPort = totalService.selectCat485Port(param);
        if(catPort != null){
            for(int i=0;i<catPort.size();i++){
                int temp = catPort.get(i);
                Map<Integer,String> tempMap = new HashMap<>();
                tempMap.put(temp,"阴极保护");
                rs485List.add(tempMap);
            }
        }
        resultMap.put("rs485List",rs485List);
        return resultMap;
    }

    @RequestMapping(value = "/selectAlarmByMonth",method = RequestMethod.GET)
    public Map<String,Object> selectAlarmByMonth(HttpServletRequest req){
        Date d = new Date();
        long l = d.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Object> list = new LinkedList<>();
        for(int i=0;i<30;i++){
            String temp = sdf.format(l);
            list.add(temp);
            l -= 24*60*60*1000;
        }
        System.out.println(list);
        Map<String,Object> param = new HashMap<>();
        param.put("startTime",list.get(list.size()-1)+" 00:00:00");
        param.put("endTime",list.get(0)+" 23:59:59");
        List<Map<String,Object>> deviceOffTemp = totalService.selectDeviceOffByMonth(param);
        List<Integer> deviceOffList = new LinkedList<>();
        for(int i=0;i<list.size();i++){
            String temp = list.get(i)+"";
            //标志状态位,判断是否查询到
            int status = 0;
            for(int j=0;j<deviceOffTemp.size();j++){
                Map<String,Object> map = deviceOffTemp.get(j);
                String tempDate = map.get("tempDate")+"";
                if(temp.equals(tempDate)){
                    deviceOffList.add(Integer.parseInt(map.get("num")+""));
                    status = 1;
                    break;
                }
            }
            if(status == 0){
                //未查询到
                deviceOffList.add(0);
            }
        }

        List<Map<String,Object>> deviceWarningTemp = totalService.selectDeviceWarningByMonth(param);
        List<Integer> deviceWarningList = new LinkedList<>();
        for(int i=0;i<list.size();i++){
            String temp = list.get(i)+"";
            //标志状态位,判断是否查询到
            int status = 0;
            for(int j=0;j<deviceWarningTemp.size();j++){
                Map<String,Object> map = deviceWarningTemp.get(j);
                String tempDate = map.get("tempDate")+"";
                if(temp.equals(tempDate)){
                    deviceWarningList.add(Integer.parseInt(map.get("num")+""));
                    status = 1;
                    break;
                }
            }
            if(status == 0){
                //未查询到
                deviceWarningList.add(0);
            }
        }

        List<Map<String,Object>> rtuOffTemp = totalService.selectRTUOffByMonth(param);
        List<Integer> rtuOffList = new LinkedList<>();
        for(int i=0;i<list.size();i++){
            String temp = list.get(i)+"";
            //标志状态位,判断是否查询到
            int status = 0;
            for(int j=0;j<rtuOffTemp.size();j++){
                Map<String,Object> map = rtuOffTemp.get(j);
                String tempDate = map.get("tempDate")+"";
                if(temp.equals(tempDate)){
                    rtuOffList.add(Integer.parseInt(map.get("num")+""));
                    status = 1;
                    break;
                }
            }
            if(status == 0){
                //未查询到
                rtuOffList.add(0);
            }
        }

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("deviceOffList",deviceOffList);
        resultMap.put("deviceWarningList",deviceWarningList);
        resultMap.put("rtuOffList",rtuOffList);
        resultMap.put("list",list);
        return resultMap;
    }

    public static void main(String[] args) {

        Date d = new Date();
        long l = d.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Object> list = new LinkedList<>();
        for(int i=0;i<30;i++){
            String temp = sdf.format(l);
            list.add(temp);
            l -= 24*60*60*1000;
        }
        System.out.println(list);
        System.out.println(list.get(0)+"==="+list.get(list.size()-1));
    }

    @RequestMapping(value = "/selectForFeignMQ",method = RequestMethod.GET)
    public Map<String,Object> selectForFeignMQ(HttpServletRequest req){
        String rtuId = req.getParameter("rtu_id");
        String siteId = req.getParameter("site_id");
        Map<String,Object> param = new HashMap<>();
        if(rtuId != null && !"".equals(rtuId)){
            int rtu_id = Integer.parseInt(rtuId);
            param.put("rtu_id",rtu_id);
            param.put("site_id","");
        }else if(siteId != null && !"".equals(siteId)){
            int site_id = Integer.parseInt(siteId);
            param.put("rtu_id","");
            param.put("site_id",site_id);
        }else{
            param.put("rtu_id","");
            param.put("site_id","");
        }
        System.out.println("param : "+param);
        //List<Map<String,Object>> deviceOff = totalService.selectDeviceOff(param);
        List<Map<String,Object>> deviceWarning = totalService.selectDeviceWarning(param);
        int rtuNum = totalService.selectRTUNumBySiteId(param);
        List<Map<String,Object>> rtuOffNum = totalService.selectRTUOff(param);
        List<Map<String,Object>> rtuWarningNum = totalService.selectRTUWarning(param);
        List<Map<String,Object>> spdNum = totalService.selectSPDCount(param);
        List<Map<String,Object>> etcrNum = totalService.selectETCRCount(param);
        List<Map<String,Object>> lightningNum = totalService.selectLightningCount(param);
        List<Map<String,Object>> staticNum = totalService.selectStaticCount(param);
        List<Map<String,Object>> rswsNum = totalService.selectRswsCount(param);
        List<Map<String,Object>> svtNum = totalService.selectSvtCount(param);
        List<Map<String,Object>> hcNum = totalService.selectHcCount(param);
        List<Map<String,Object>> strayNum = totalService.selectStrayCount(param);
        List<Map<String,Object>> catNum = totalService.selectCatCount(param);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("deviceWarningCount",deviceWarning.size());
        resultMap.put("rtuNum",rtuNum);
        resultMap.put("rtuOffNum",rtuOffNum.size());
        resultMap.put("rtuWarningNum",rtuWarningNum.size());
        resultMap.put("deviceNum",spdNum.size()+etcrNum.size()+lightningNum.size()+staticNum.size()+rswsNum.size()+svtNum.size()+hcNum.size()+strayNum.size()+catNum.size());
        return resultMap;
    }

    @RequestMapping(value = "/selectForFeignRTU",method = RequestMethod.GET)
    public Map<String,Object> selectForFeignRTU(HttpServletRequest req){
        String rtuId = req.getParameter("rtu_id");
        String siteId = req.getParameter("site_id");
        Map<String,Object> param = new HashMap<>();
        if(rtuId != null && !"".equals(rtuId)){
            int rtu_id = Integer.parseInt(rtuId);
            param.put("rtu_id",rtu_id);
            param.put("site_id","");
        }else if(siteId != null && !"".equals(siteId)){
            int site_id = Integer.parseInt(siteId);
            param.put("rtu_id","");
            param.put("site_id",site_id);
        }else{
            param.put("rtu_id","");
            param.put("site_id","");
        }
        System.out.println("param : "+param);
        //List<Map<String,Object>> deviceOff = totalService.selectDeviceOff(param);
        List<Map<String,Object>> deviceWarning = totalService.selectDeviceWarning(param);
        int rtuNum = totalService.selectRTUNumBySiteId(param);
        List<Map<String,Object>> rtuOffNum = totalService.selectRTUOff(param);
        List<Map<String,Object>> rtuWarningNum = totalService.selectRTUWarning(param);
        List<Map<String,Object>> spdNum = totalService.selectSPDCount(param);
        List<Map<String,Object>> etcrNum = totalService.selectETCRCount(param);
        List<Map<String,Object>> lightningNum = totalService.selectLightningCount(param);
        List<Map<String,Object>> staticNum = totalService.selectStaticCount(param);
        List<Map<String,Object>> rswsNum = totalService.selectRswsCount(param);
        List<Map<String,Object>> svtNum = totalService.selectSvtCount(param);
        List<Map<String,Object>> hcNum = totalService.selectHcCount(param);
        List<Map<String,Object>> strayNum = totalService.selectStrayCount(param);
        List<Map<String,Object>> catNum = totalService.selectCatCount(param);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("deviceWarningCount",deviceWarning.size());
        resultMap.put("rtuNum",rtuNum);
        resultMap.put("rtuOffNum",rtuOffNum.size());
        resultMap.put("rtuWarningNum",rtuWarningNum.size());
        resultMap.put("deviceNum",spdNum.size()+etcrNum.size()+lightningNum.size()+staticNum.size()+rswsNum.size()+svtNum.size()+hcNum.size()+strayNum.size()+catNum.size());
        return resultMap;
    }


    @RequestMapping(value = "/selectHistoryValue",method = RequestMethod.GET)
    public Map<String,Object> selectHistoryValue(HttpServletRequest req){
        String rtu_id = req.getParameter("rtu_id");
        String site_id = req.getParameter("site_id");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String deviceId = req.getParameter("deviceId");
        int deviceType = Integer.parseInt(req.getParameter("deviceType"));
        Map<String,Object> param = new HashMap<>();
        param.put("rtu_id",rtu_id);
        param.put("site_id",site_id);
        param.put("startTime",startTime);
        param.put("endTime",endTime);
        Map<String,Object> resultMap = new HashMap<>();
        if(deviceType == 1){
            param.put("spd_number",deviceId);
            List<Map<String,Object>> list = totalService.selectSPDHistory(param);
            resultMap.put("items",list);
            return resultMap;
        }else if(deviceType == 2){
            param.put("rst_id",deviceId);
            List<Map<String,Object>> list = totalService.selectETCRHistory(param);
            resultMap.put("items",list);
            return resultMap;
        }else if(deviceType == 3){
            param.put("ltn_id",deviceId);
            List<Map<String,Object>> list = totalService.selectLightningHistory(param);
            resultMap.put("items",list);
            return resultMap;
        }else if(deviceType == 4){
            param.put("staet_id",deviceId);
            List<Map<String,Object>> list = totalService.selectStaticHistory(param);
            resultMap.put("items",list);
            return resultMap;
        }else if(deviceType == 5){
            param.put("hmt_id",deviceId);
            List<Map<String,Object>> list = totalService.selectRswsHistory(param);
            resultMap.put("items",list);
            return resultMap;
        }else if(deviceType == 6){
            param.put("tilt_id",deviceId);
            List<Map<String,Object>> list = totalService.selectSvtHistory(param);
            resultMap.put("items",list);
            return resultMap;
        }else if(deviceType == 7){
            param.put("es_id",deviceId);
            List<Map<String,Object>> list = totalService.selectHcHistory(param);
            resultMap.put("items",list);
            return resultMap;
        }else if(deviceType == 8){
            param.put("stret_id",deviceId);
            List<Map<String,Object>> list = totalService.selectStrayHistory(param);
            resultMap.put("items",list);
            return resultMap;
        }else if(deviceType == 9){
            param.put("cathode_id",deviceId);
            List<Map<String,Object>> list = totalService.selectCatHistory(param);
            resultMap.put("items",list);
            return resultMap;
        }
        return null;

    }

}
