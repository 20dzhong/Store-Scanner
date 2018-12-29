package Redis_Test;

import redis.clients.jedis.Jedis;

public class Test {
    public static void main(String[] args) {
        Jedis ree = new Jedis("StudentStore");

        // :100:111:110:111:118:97:110:95:122:104:111:110:103:
        // 20dzhong
        String cachedResponse = ree.get("donovan_zhong");
        System.out.println(cachedResponse);
    }
}
