package com.pet.common.enums.state;

import com.pet.common.animation.PetAnimationAction;

/**
 * 默认桌宠的动作
 * 自定义桌宠也应实现PetAnimationState接口
 */
public enum DefaultState implements PetAnimationAction {
    IDLE("idle", 1, false),
    STAND("stand", 3, true),
    RUN("run", 11, true);

    private final String folderName;
    private final int frameCount;
    private final boolean Loop;

    DefaultState(String folderName, int frameCount, boolean Loop) {
        this.folderName = folderName;
        this.frameCount = frameCount;
        this.Loop = Loop;
    }

    @Override
    public String getFolderName() {
        return folderName;
    }

    @Override
    public int getFrameCount() {
        return frameCount;
    }

    @Override
    public boolean isLoop() {
        return Loop;
    }
}