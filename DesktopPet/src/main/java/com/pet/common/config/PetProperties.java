package com.pet.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 桌宠项目配置类
 * 自动读取 application.yml 中 pet: 开头的所有配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "pet")
public class PetProperties {

    /**
     * 主窗口配置
     */
    private MainConfig main;

    /**
     * 气泡对话框配置
     */
    private BubbleConfig bubble;

    /**
     * AI 配置
     */
    private AiConfig ai;

    /**
     * 日程提醒配置
     */
    private ScheduleConfig schedule;

    // --------------------- 内部配置类 ---------------------
    @Data
    public static class MainConfig {
        private Integer width;
        private Integer height;
        private Boolean alwaysOnTop;
        private Boolean dragEnabled;
    }

    @Data
    public static class BubbleConfig {
        private Integer showTime;
        private Double opacity;
    }

    @Data
    public static class AiConfig {
        private Integer timeout;
        private Integer maxTokens;
    }

    @Data
    public static class ScheduleConfig {
        private Integer remindBeforeMinutes;
        private Integer checkInterval;
    }
}