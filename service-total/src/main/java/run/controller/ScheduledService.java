package run.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import run.service.TotalService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Component
public class ScheduledService {
    @Autowired
    private TotalService totalService;

    private static LinkedList<Map<String,Object>> nowDataList = new LinkedList<>();

    public static LinkedList<Map<String, Object>> getNowDataList() {
        return nowDataList;
    }

    public static void setNowDataList(LinkedList<Map<String, Object>> nowDataList) {
        ScheduledService.nowDataList = nowDataList;
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void scheduled() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH");
        String nowTime = sdf.format(d);
        String hour = sdf1.format(d);
        String str;
        if(Integer.parseInt(hour)<10 && Integer.parseInt(hour)>0){
            str = "0"+(Integer.parseInt(hour)-1);
        }else if(Integer.parseInt(hour) == 0){
            str = "23";
        }else{
            str = hour;
        }
        System.out.println("当前时间：" + nowTime);
        System.out.println("hour：" + hour);
        System.out.println("startTime：" + nowTime+" "+str+":00:00");
        System.out.println("endTime：" + nowTime+" "+str+":59:59");
        Map<String,Object> param = new HashMap<>();
        param.put("startTime",nowTime+":00:00");
        param.put("endTime",nowTime+":59:59");
        int spdNum = totalService.selectSPDCountByTime(param);
        int etcrNum = totalService.selectETCRCountByTime(param);
        int lightningNum = totalService.selectLightningCountByTime(param);
        int staticNum = totalService.selectStaticCountByTime(param);
        int rswsNum = totalService.selectRswsCountByTime(param);
        int svtNum = totalService.selectSvtCountByTime(param);
        int hcNum = totalService.selectHcCountByTime(param);
        int strayNum = totalService.selectStrayCountByTime(param);
        int catNum = totalService.selectCatCountByTime(param);
        int total = spdNum+etcrNum+lightningNum+staticNum+rswsNum+svtNum+hcNum+strayNum+catNum;
        for(int i=0;i<nowDataList.size();i++){
            Map<String,Object> temp = nowDataList.get(i);
            String label = temp.get("label")+"";
            if(hour.equals(label)){
                Map<String,Object> params = new HashMap<>();
                params.put("label",label);
                params.put("num",total);
                nowDataList.set(i,params);
            }
        }
    }

    /**
     * 0点执行清空任务
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @Async
    public void scheduledDel(){
        if(nowDataList != null){
            nowDataList.clear();
        }
        for(int i=0;i<25;i++){
            String label = "";
            if(i<10){
                label = "0" + i;
            }else{
                label = i + "";
            }
            Map<String,Object> params = new HashMap<>();
            params.put("label",label);
            params.put("num","");
            nowDataList.add(params);
        }
    }
}
