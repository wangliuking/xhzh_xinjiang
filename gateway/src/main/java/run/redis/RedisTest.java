package run.redis;

import redis.clients.jedis.Jedis;

public class RedisTest {
    private static String ip = "39.104.240.180";

    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis(ip,6379);
        jedis.auth("XinHong12345");
        jedis.select(0);
        String res = jedis.get("8F49A7C1C75AC882E6F9DFAE93637F0C");
        System.out.println("res : "+res);
        //jedis.flushAll();
        //System.out.println("Connection to server sucessfully");
        //查看服务是否运行
        //System.out.println("Server is running: "+jedis.ping());
        jedis.close();
    }

    public static boolean searchLoginUser(String sessionId){
        Jedis jedis = new Jedis(ip,6379);
        jedis.auth("XinHong12345");
        jedis.select(0);
        String result = jedis.get(sessionId);
        System.out.println("result :"+result);
        jedis.close();
        if(result != null && !"".equals(result)){
            return true;
        }else{
            return false;
        }

    }

    public static void addLoginUser(String sessionId,String userId){
        Jedis jedis = new Jedis(ip,6379);
        jedis.auth("XinHong12345");
        jedis.select(0);
        jedis.set(sessionId,userId);
        jedis.close();
    }

    public static void removeLoginUser(String sessionId){
        Jedis jedis = new Jedis(ip,6379);
        jedis.auth("XinHong12345");
        jedis.select(0);
        jedis.del(sessionId);
        jedis.close();
    }


}
