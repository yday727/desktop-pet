package com.pet.common.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleConfig {
    private String name;
    private String displayName;
    private String description;
    private Integer width;
    private Integer height;
    private List<ActionConfig> actions;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ActionConfig {
        private String name;
        private String folderName;
        private Integer frameCount;
        private Boolean loop;
        private Integer frameDurationMs;
    }
}
