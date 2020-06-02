package run.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import run.EncryptUtil;
import run.bean.App;
import run.bean.UDPConf;
import run.bean.User;
import run.redis.RedisTest;
import run.service.LoginService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;
    @Autowired
    private UDPConf udpConf;

    @RequestMapping(value = "/loginWeb",method = RequestMethod.POST)
    public Map<String,Object> login(@RequestBody User user, HttpServletRequest req) {
        Map<String,Object> param = new HashMap<>();
        String username = user.getUsername();
        String password = user.getPassword();
        System.out.println("username为： "+username+"====="+"password为： "+ EncryptUtil.encrypt(password));
        param.put("username",username);
        param.put("password", EncryptUtil.encrypt(password));
        List<Map<String,Object>> list = loginService.selectUser(param);
        System.out.println("返回的userlist为： "+list);
        Map<String,Object> resultMap = new HashMap<>();
        if(list.size()>0){
            RedisTest.addLoginUser(req.getSession().getId(),username);

            Map<String,Object> logParam = new HashMap<>();
            logParam.put("type","登录操作");
            logParam.put("userId",username);
            logParam.put("ip",getIpAddress(req));
            logParam.put("content","登录平台");
            loginService.insertLog(logParam);

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

    @RequestMapping("/getLoginUser")
    public Map<String,Object> getLoginUser(HttpServletRequest req) {
        String userId = RedisTest.getLoginUser(req.getSession().getId());
        System.out.println("userId : "+userId);
        Map<String,Object> param = new HashMap<>();
        param.put("userId",userId);
        return loginService.selectUserPower(param);
    }

    @RequestMapping(value = "/selectLogList",method = RequestMethod.GET)
    public Map<String,Object> selectLogList(HttpServletRequest req) {
        int start = Integer.parseInt(req.getParameter("start"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        String type = req.getParameter("type");
        String userId = req.getParameter("userId");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");

        Map<String,Object> result = new HashMap<>();
        Map<String,Object> params = new HashMap<>();
        params.put("start",start);
        params.put("limit",limit);
        params.put("type",type);
        params.put("userId",userId);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        result.put("items",loginService.selectLogList(params));
        result.put("totals",loginService.selectLogListCount(params));
        return result;
    }

    /**
     * 日志调用服务
     */
    @RequestMapping(value = "/insertLog",method = RequestMethod.GET)
    public void insertLog(HttpServletRequest req) {
        String sessionId = req.getSession().getId();
        System.out.println("sessionId : "+sessionId);
        String userId = RedisTest.getLoginUser(sessionId);
        System.out.println("userId : "+userId);
        Map<String,Object> logParam = new HashMap<>();
        logParam.put("type",req.getParameter("type"));
        logParam.put("userId",userId);
        logParam.put("ip",getIpAddress(req));
        logParam.put("content",req.getParameter("content"));
        loginService.insertLog(logParam);
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static void main(String[] args) {
        LoginController loginController = new LoginController();
        String path = loginController.getClass().getResource("/").getPath();
        System.out.println(path);

        String imgFile = path;
        InputStream in = null;
        byte[] data = null;
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Base64.Encoder encoder = Base64.getEncoder();
        System.out.println(encoder.encode(data));

        /*String filename = "static/xhzh/JSsignature_pad/" + "text.txt";
        try {
            File imageFile = new File(filename);
            imageFile.createNewFile();
            if (!imageFile.exists()) {
                imageFile.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    /**
     * 解析base64并保存
     */
    @RequestMapping(value = "/uploadImg",method = RequestMethod.POST)
    public void uploadImg(HttpServletRequest req) {
        String imageFiles = req.getParameter("imgStr");
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] imageByte = null;
        try {
            imageByte = decoder.decode(imageFiles);
            for (int i = 0; i < imageByte.length; ++i) {
                if (imageByte[i] < 0) {
                    imageByte[i] += 256;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String files = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + (new Random().nextInt(9000) % (9000 - 1000 + 1) + 1000) + ".png";
        String filename = this.getClass().getResource("/").getPath()+"static/xhzh/JSsignature_pad/" + files;
        try {
            File imageFile = new File(filename);
            imageFile.createNewFile();
            if (!imageFile.exists()) {
                imageFile.createNewFile();
            }else{
                imageFile.delete();
                imageFile.createNewFile();
            }
            OutputStream imageStream = new FileOutputStream(imageFile);
            imageStream.write(imageByte);
            imageStream.flush();
            imageStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param req
     * @return
     * @description 删除
     */
    @GetMapping("deleteApp")
    public Map<String,Object> deleteApp(HttpServletRequest req) throws Exception {
        String id = req.getParameter("id");
        String path = req.getParameter("path");
        loginService.deleteApp(Integer.parseInt(id));

        //删除数据库成功后删除对应文件
        String fileName = udpConf.getAppPath()+"/"+path;
        System.out.println(fileName);
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("文件不存在！");
        } else {
            if (file.isFile()){
                deleteFile(fileName);
            }
            else{
                System.out.println("不是文件类型");
            }
        }

        Map<String,Object> map = new HashMap<>(){{
            put("success",true);
            put("message","删除成功");
        }};
        return map;
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * ajax文件上传
     * @param file
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Map<String,Object> fileUpload(@RequestParam("file") MultipartFile file, HttpSession session) throws IOException{
        System.out.println("已进入文件上传方法！！！");
        System.out.println("file : "+file);
        System.out.println("session : "+session);
        String fileName = imgsUpload(file, session);
        Map<String,Object> map = new HashMap<String,Object>(){{
           put("fileName",fileName);
           put("fileUrl",udpConf.getAppPath()+"/"+fileName);
        }};
        return map;
    }

    /**
     * 文件上传公共方法
     * @param file
     * @param session
     * @return
     * @throws IOException
     */
    private String imgsUpload(MultipartFile file, HttpSession session)
            throws IOException {
        //获取文件在服务器的存储路径
        String path = udpConf.getAppPath();
        //获取上传文件的名称
        String fileName = file.getOriginalFilename();
        //进行文件存储
        file.transferTo(new File(path,fileName));
        return fileName;
    }
    /**
     * 文件下载方法
     */
    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response){
        try{
            String path = request.getParameter("path");
            String filePath = udpConf.getAppPath()+"/"+path; //文件在项目中的路径
            File outfile = new File(filePath);
            String filename = outfile.getName();// 获取文件名称
            InputStream fis = new BufferedInputStream(new FileInputStream(
                    filePath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer); //读取文件流
            fis.close();
            response.reset(); //重置结果集
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filename.replaceAll(" ", "").getBytes("utf-8"),
                    "iso8859-1")); //返回头 文件名
            response.addHeader("Content-Length", "" + outfile.length()); //返回头 文件大小
            response.setContentType("application/octet-stream");  //设置数据种类
            //获取返回体输出权
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            os.write(buffer); // 输出文件
            os.flush();
            os.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param app
     * @return
     * @description 添加
     */
    @RequestMapping("addApp")
    public Map<String,Object> addApp(@Valid @RequestBody App app) {
        System.out.println("***********************");
        System.out.println(app);
        loginService.addApp(app);
        Map<String,Object> map = new HashMap<String,Object>(){{
            put("success",true);
            put("message","添加成功");
        }};
        return map;
    }

    /**
     * @return
     * @description 获取列表
     */
    @GetMapping("appList")
    public Map<String, Object> findAllApp(HttpServletRequest req) {
        String start = req.getParameter("start");
        String limit = req.getParameter("limit");
        String appName = req.getParameter("appName");
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("start", "".equals(start) ? -1 : Integer.parseInt(start));
            put("limit", "".equals(limit) ? -1 : Integer.parseInt(limit));
            put("appName", appName);
        }};
        return loginService.selectAppList(map);
    }

}
