package com.pet.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RoleManager {

    private static final Logger log = LoggerFactory.getLogger(RoleManager.class);
    private static final String ROLES_PATH = "/roles/";
    private static final String CONFIG_FILE = "/config/role.json";

    private final Map<String, RoleConfig> roleConfigCache = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    public RoleManager(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public RoleConfig getRoleConfig(String roleName) {
        return roleConfigCache.computeIfAbsent(roleName, this::loadRoleConfig);
    }

    private RoleConfig loadRoleConfig(String roleName) {
        String configPath = ROLES_PATH + roleName + CONFIG_FILE;
        try {
            ClassPathResource resource = new ClassPathResource("roles" + CONFIG_FILE.replace("/config", "/" + roleName + "/config"));
            try (InputStream inputStream = resource.getInputStream()) {
                RoleConfig config = objectMapper.readValue(inputStream, RoleConfig.class);
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
        try {
            ClassPathResource resource = new ClassPathResource("roles");
            try (InputStream inputStream = resource.getInputStream()) {
                roles.add("default");
            }
        } catch (IOException e) {
            log.warn("无法扫描角色目录，使用默认角色");
            roles.add("default");
        }
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
