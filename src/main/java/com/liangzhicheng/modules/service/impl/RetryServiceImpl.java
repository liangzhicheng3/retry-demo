package com.liangzhicheng.modules.service.impl;

import com.liangzhicheng.modules.service.IRetryService;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class RetryServiceImpl implements IRetryService {

    /**
     * 发送消息失败
     * 调用远程服务失败
     * 争抢锁失败
     * 可能是因为网络波动造成
     *
     * 使用重试
     *
     * value：抛出指定异常才会重试
     * include：和value一样，默认为空，当exclude也为空时，默认所有异常
     * exclude：指定不处理的异常
     * maxAttempts：最大重试次数，默认3次
     * backoff：重试等待策略，默认使用@Backoff
     *          value默认为1000L，设置为2000L
     *          multiplier（指定延迟倍数）默认为0，表示固定暂停1秒后进行重试，如果把multiplier设置为1.5，则第一次重试为2秒，第二次为3秒，第三次为4.5秒
     */
    @Retryable(value = Exception.class, maxAttempts = 4, backoff = @Backoff(delay = 2000, multiplier = 1.5))
    @Override
    public int test(int code) {
        System.out.println("test被调用，时间："+ LocalTime.now());
        if(code == 0){
            throw new RuntimeException("发生异常");
        }
        System.out.println("test被调用，未发生异常");
        return 200;
    }

    /**
     * 用于@Retryable重试失败后回调处理方法
     *
     * 注意事项
     *         方法返回值必须与@Retryable方法一致
     *         方法第一个参数必须是Throwable类型，建议与@Retryable配置的异常一致，其他参数，需要哪个参数，写进去即可（@Recover方法中有的）
     *         该回调方法与重试方法写在同一个实现类里面
     *
     *         如果重试失败需要给@Recover注解的方法做后续处理，那这个重试的方法不能有返回值，只能是void
     */
    @Recover
    public int recover(Exception e, int code){
        System.out.println("回调方法执行");
        //记录日志到数据库或者调用其余方法
        return 400;
    }

}