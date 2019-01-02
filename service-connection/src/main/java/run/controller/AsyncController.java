package run.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import run.bean.RTU;

import java.util.Map;

@SuppressWarnings("all")
@RestController
public class AsyncController {
    @Autowired
    FeignForMQ feignForMQ;

    @Async
    public void asyncSendRtuDelNewConf (Map<String,Object> param){
        feignForMQ.sendRtuDelNewConf(param);
    }

    @Async
    public void asyncSendRTUConf (RTU rtu){
        feignForMQ.sendRTUConf(rtu);
    }


}
