package com.xiaozu.core.utils.id;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import jodd.util.StringPool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author:80906
 * @Des:
 * @Date:2019/3/7
 */
public class SystemID {
    /**
     * 主机和进程的机器码
     */
    private static Sequence WORKER = new Sequence();

    /**
     * 毫秒格式化时间
     */
    public static final DateTimeFormatter MILLISECOND = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public static long getId() {
        return WORKER.nextId();
    }

    public static String getIdStr() {
        return String.valueOf(WORKER.nextId());
    }

    /**
     * <p>
     * 格式化的毫秒时间
     * </p>
     */
    public static String getMillisecond() {
        return LocalDateTime.now().format(MILLISECOND);
    }

    /**
     * <p>
     * 时间 ID = Time + ID
     * </p>
     * <p>
     * 例如：可用于商品订单 ID
     * </p>
     */
    public static String getTimeId() {
        return getMillisecond() + getId();
    }

    /**
     * <p>
     * 有参构造器
     * </p>
     *
     * @param workerId     工作机器 ID
     * @param datacenterId 序列号
     */
    public static void initSequence(long workerId, long datacenterId) {
        WORKER = new Sequence(workerId, datacenterId);
    }

    /**
     * <p>
     * 使用ThreadLocalRandom获取UUID获取更优的效果 去掉"-"
     * </p>
     */
    public static String get32UUID() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong()).toString().replace(StringPool.DASH, StringPool.EMPTY);
    }
    /**
     * <p>
     * 生成28位流水号
     * </p>
     */
    public static String getSerialNumber() {
        return IdWorker.getTimeId().substring(0,20)+IdWorker.getTimeId().substring(28);
    }
}
