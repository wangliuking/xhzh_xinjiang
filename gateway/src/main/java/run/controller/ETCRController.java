package run.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import run.ExcelUtil;
import run.service.ETCRService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class ETCRController {
    @Autowired
    private ETCRService ETCRService;

    @RequestMapping(value = "/exportAllETCRHistoryExcel",method = RequestMethod.GET)
    public void exportAllETCRHistoryExcel (HttpServletRequest req, HttpServletResponse response){
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
        int rst_id;
        if(req.getParameter("rst_id") != null && req.getParameter("rst_id") != ""){
            rst_id = Integer.parseInt(req.getParameter("rst_id"));
        }else {
            rst_id = -1;
        }
        String rst_location = req.getParameter("location");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        List<Map<String,Object>> etcrList = ETCRService.exportAllETCRHistoryExcel(site_id,rtu_id,rst_id,rst_location,startTime,endTime);

        String sheetName = "测试";
        String fileName = "ExcelTest.xls";
        System.out.println("准备进行导出！！！");
        try {
            ExcelUtil.exportExcel(response, etcrList, sheetName, fileName, 15) ;
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
