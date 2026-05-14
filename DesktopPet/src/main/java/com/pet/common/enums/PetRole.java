package com.pet.common.enums;

import com.pet.common.config.RoleConfig;
import com.pet.common.config.RoleManager;
import lombok.Getter;

import java.util.List;

@Getter
public class PetRole {

    private static RoleManager roleManager;

    private final String roleName;
    private final String folder;
    private RoleConfig config;

    private PetRole(String roleName) {
        this.roleName = roleName;
        this.folder = roleName;
    }

    public static void setRoleManager(RoleManager manager) {
        roleManager = manager;
        if (Default != null) {
            Default.loadConfig();
        }
    }

    public static PetRole getDefault() {
        return Default;
    }

    public static List<PetRole> getAllRoles() {
        return List.of(Default);
    }

    private void loadConfig() {
        if (roleManager != null) {
            this.config = roleManager.getRoleConfig(this.roleName);
        }
    }

    public static final PetRole Default = new PetRole("default");
}
