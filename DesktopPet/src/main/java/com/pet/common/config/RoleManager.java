package com.pet.common.config;

import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RoleManager {

    private static final Logger log = LoggerFactory.getLogger(RoleManager.class);
    private static final String CONFIG_FILE = "config/role.json";

    private final Map<String, RoleConfig> roleConfigCache = new ConcurrentHashMap<>();

    public RoleConfig getRoleConfig(String roleName) {
        return roleConfigCache.computeIfAbsent(roleName, this::loadRoleConfig);
    }

    private RoleConfig loadRoleConfig(String roleName) {
        String configPath = "roles/" + roleName + "/" + CONFIG_FILE;
        try {
            ClassPathResource resource = new ClassPathResource(configPath);
            try (InputStream inputStream = resource.getInputStream()) {
                String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                RoleConfig config = JSON.parseObject(json, RoleConfig.class);
                log.info("角色配置加载成功: {}", roleName);
                return config;
            }
        } catch (IOException e) {
            log.error("加载角色配置失败: {}", configPath, e);
            return null;
        }
    }

    public Set<String> getAvailableRoles() {
        Set<String> roles = new HashSet<>();
        roles.add("default");
        return roles;
    }

    public Optional<RoleConfig.ActionConfig> getActionConfig(String roleName, String actionName) {
        RoleConfig config = getRoleConfig(roleName);
        if (config == null || config.getActions() == null) {
            return Optional.empty();
        }
        return config.getActions().stream()
                .filter(a -> a.getName().equals(actionName))
                .findFirst();
    }
}
