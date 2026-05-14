package com.pet.common.enums;

/**
 * 桌宠枚举类
 * 添加的自定义桌宠应加入此枚举类
 */
public enum PetRole {
    Default("default");

    private final String folder;

    PetRole(String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }
}