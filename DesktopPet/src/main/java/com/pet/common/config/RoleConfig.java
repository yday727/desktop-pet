package com.pet.common.config;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class RoleConfig {
    private String name;
    private String displayName;
    private String description;
    private Integer width;
    private Integer height;
    private List<ActionConfig> actions;

    @Data
    public static class ActionConfig {
        private String name;
        private String folderName;
        private Integer frameCount;
        private Boolean loop;
        private Integer frameDurationMs;
    }
}
