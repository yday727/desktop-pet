package com.pet.common.animation;

/**
 * 所有宠物动作的通用接口
 */
public interface PetAnimationAction {
    /** 帧数 */
    int getFrameCount();

    /** 文件名 **/
    String getFolderName();

    /** 是否循环动画 */
    boolean isLoop();
}