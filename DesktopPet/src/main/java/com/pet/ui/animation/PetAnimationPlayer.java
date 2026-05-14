package com.pet.ui.animation;

import com.pet.common.animation.PetAnimationAction;
import com.pet.common.enums.PetRole;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 桌宠动画播放器
 */
public class PetAnimationPlayer {

    private static final Logger log = LoggerFactory.getLogger(PetAnimationPlayer.class);

    private final ImageView imageView;
    private final Map<String, Map<String, List<Image>>> roleCache = new HashMap<>();
    private Timeline currentAnim;

    private PetRole currentRole;

    public PetAnimationPlayer(ImageView imageView) {
        this.imageView = imageView;
    }

    // ==================== 加载角色全部动作 ====================
    public void loadRole(PetRole role) {
        if (roleCache.containsKey(role.getFolder())) return;

        Map<String, List<Image>> actionMap = new HashMap<>();
        roleCache.put(role.getFolder(), actionMap);
        log.info("宠物角色已加载：{}", role.name());
    }

    // ==================== 切换当前宠物 ====================
    public void setRole(PetRole role) {
        this.currentRole = role;
        loadRole(role);
    }

    // ==================== 播放静态 ====================
    public void playStatic(PetAnimationAction action) {
        stop();
        List<Image> frames = loadFramesIfNeed(action);
        if (!frames.isEmpty()) {
            imageView.setImage(frames.get(0));
        }
    }

    // ==================== 播放动画 ====================
    public void playAnimation(PetAnimationAction action) {
        playAnimation(action, 160);
    }

    public void playAnimation(PetAnimationAction action, int frameMs) {
        stop();
        List<Image> frames = loadFramesIfNeed(action);
        if (frames.isEmpty()) return;

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        for (int i = 0; i < frames.size(); i++) {
            int idx = i;
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(frameMs * (i + 1)),
                    e -> imageView.setImage(frames.get(idx))
            ));
        }
        timeline.play();
        currentAnim = timeline;
    }

    // ==================== 懒加载帧（用到再加载） ====================
    private List<Image> loadFramesIfNeed(PetAnimationAction action) {
        Map<String, List<Image>> actionMap = roleCache.get(currentRole.getFolder());
        if (actionMap == null) return Collections.emptyList();

        return actionMap.computeIfAbsent(action.getFolderName(), k -> {
            List<Image> list = new ArrayList<>();
            try {
                for (int i = 0; i < action.getFrameCount(); i++) {
                    String path = String.format("/images/pet/%s/%s/%d.png",
                            currentRole.getFolder(),
                            action.getFolderName(),
                            i);
                    list.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
                }
            } catch (Exception e) {
                log.error("加载动作失败: {}/{}", currentRole.getFolder(), action.getFolderName());
            }
            return list;
        });
    }

    public void stop() {
        if (currentAnim != null) currentAnim.stop();
    }
}