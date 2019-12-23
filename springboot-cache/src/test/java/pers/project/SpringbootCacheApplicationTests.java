package pers.project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringbootCacheApplicationTests {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
        stringRedisTemplate.opsForValue().set("aaa", "bbbbb");
        stringRedisTemplate.opsForValue().set("min", "2min", 2, TimeUnit.MINUTES); // 设置key生存周期为2分
        stringRedisTemplate.delete("aaa");
    }

    /**
     * stringRedisTemplate默认不开启事务，每个redis connection都会重新获取，导致multi和exec不在一个连接上
     * 解决方案：①开启事务 ②通过 SessionCallback，保证所有的操作都在同一个 Session 中完成（推荐）
     * <p>
     * redis支持部分事务
     * MULTI：开启一个事务，MULTI 执行之后，客户端可以继续向服务器发送任意多条命令，这些命令不会立即被执行，而是被放到一个队列中。
     * EXEC：执行队列中所有的命令
     * DISCARD：清空事务队列,并放弃执行事务
     * UNWATCH：取消 WATCH 命令对所有 key 的监视
     * WATCH key1 key2 ...	：监视一个(或多个) key ，如果在事务执行之前这个(或这些) key 被其他命令所改动，那么事务将被打断。
     * <p>
     * 作者：Mrrrrr10
     * 链接：https://juejin.im/post/5cb1c9adf265da03893284e9
     * 来源：掘金
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    @Test
    void testMultiFailure() {
        stringRedisTemplate.execute(new SessionCallback<Object>() {
            /*
             * 保证所有的操作都在同一个 Session 中完成
             * 只有exec成功执行后才会提交里面3个set,否则不提交（与开启事务的方式效果相同）
             */
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.watch("age");
                operations.multi();
                operations.opsForValue().set("name", "qinyi");
                operations.opsForValue().set("gender", "male");
                operations.opsForValue().set("age", "19");
                return operations.exec();
            }
        });

        /* 若不开启事务,虽然exec抛出异常,但3个set都成功执行了,推测每个操作redis都是独立的连接,因为set name后有name，set age后就能查到age*/
        /* 若开启事务,则只有在exec成功执行后3个set的结果才会提交*/
//        stringRedisTemplate.setEnableTransactionSupport(true); // 开启事务支持，在同一个 Connection 中执行命令
//        stringRedisTemplate.multi();
//        stringRedisTemplate.opsForValue().set("name", "qinyi");
//        stringRedisTemplate.opsForValue().set("gender", "male");
//        stringRedisTemplate.opsForValue().set("age", "19");
//        System.out.println(stringRedisTemplate.exec());
    }

}
