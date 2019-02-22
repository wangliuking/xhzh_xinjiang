package run.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import run.bean.RTU;
import run.service.RTUService;

import java.util.Map;

@SuppressWarnings("all")
@RestController
public class AsyncController {
    @Autowired
    FeignForMQ feignForMQ;
    @Autowired
    RTUService rtuService;

    @Async
    public void asyncDelDeviceByRTUID(int rtu_id){
        rtuService.delSpd(rtu_id);
        rtuService.delEtcr(rtu_id);
        rtuService.delLightning(rtu_id);
        rtuService.delStatic(rtu_id);
        rtuService.delRsws(rtu_id);
        rtuService.delSvt(rtu_id);
        rtuService.delHc(rtu_id);
        rtuService.delStray(rtu_id);
        rtuService.delCat(rtu_id);
    }

    @Async
    public void asyncSendRtuDelNewConf (Map<String,Object> param){
        feignForMQ.sendRtuDelNewConf(param);
    }

    @Async
    public void asyncSendRTUConf (RTU rtu){
        feignForMQ.sendRTUConf(rtu);
    }


}
