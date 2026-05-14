package com.pet.common.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 全局共享的随机数工具类
 * 单例模式，避免多次创建 Random 实例导致随机数重复
 * 全项目共用一个实例，安全、高效、不重复
 */
public final class RandomUtil {

    // 全局唯一的 Random 实例（类加载时创建，全局共享）
    private static final Random RANDOM = new Random();

    // 私有构造，禁止 new 对象
    private RandomUtil() {
    }

    // ====================== 基础随机数 ======================

    /**
     * 获取随机 int 值
     */
    public static int nextInt() {
        return RANDOM.nextInt();
    }

    /**
     * 获取 [0, max) 之间的随机 int
     */
    public static int nextInt(int max) {
        if (max <= 0) {
            throw new IllegalArgumentException("max 必须大于 0");
        }
        return RANDOM.nextInt(max);
    }

    /**
     * 获取 [min, max] 之间的随机 int（包含两端）
     */
    public static int nextInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("min 必须小于 max");
        }
        return min + RANDOM.nextInt(max - min + 1);
    }

    // ====================== 随机布尔值 ======================

    public static boolean nextBoolean() {
        return RANDOM.nextBoolean();
    }

    // ====================== 随机浮点数 ======================

    public static double nextDouble() {
        return RANDOM.nextDouble();
    }

    // ====================== 安全高并发版本 ======================

    /**
     * 高并发场景使用 ThreadLocalRandom（比 Random 更快）
     */
    public static int fastInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}