package com.pet.ui.animation;

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

public class PetAnimationPlayer {

    private static final Logger log = LoggerFactory.getLogger(PetAnimationPlayer.class);

    private final ImageView imageView;
    private final Map<String, Map<String, List<Image>>> roleCache = new HashMap<>();
    private Timeline currentAnim;

    private PetRole currentRole;

    public PetAnimationPlayer(ImageView imageView) {
        this.imageView = imageView;
    }

    public void loadRole(PetRole role) {
        if (roleCache.containsKey(role.getFolder())) return;

        Map<String, List<Image>> actionMap = new HashMap<>();
        roleCache.put(role.getFolder(), actionMap);
        log.info("宠物角色已加载：{}", role.name());
    }

    public void setRole(PetRole role) {
        this.currentRole = role;
        loadRole(role);
    }

    public void playStatic(String actionName) {
        stop();
        List<Image> frames = loadFrames(actionName, 1);
        if (!frames.isEmpty()) {
            imageView.setImage(frames.get(0));
        }
    }

    public void playAnimation(String actionName) {
        playAnimation(actionName, 160);
    }

    public void playAnimation(String actionName, int frameMs) {
        stop();
        List<Image> frames = loadFrames(actionName, -1);
        if (frames.isEmpty()) return;

        Timeline timeline = new Timeline();
        boolean isLoop = shouldLoop(actionName);
        timeline.setCycleCount(isLoop ? Animation.INDEFINITE : 1);

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

    private List<Image> loadFrames(String actionName, int maxFrames) {
        if (currentRole == null) return Collections.emptyList();

        Map<String, List<Image>> actionMap = roleCache.get(currentRole.getFolder());
        if (actionMap == null) return Collections.emptyList();

        return actionMap.computeIfAbsent(actionName, k -> {
            List<Image> list = new ArrayList<>();
            int frameCount = getFrameCount(actionName);
            if (maxFrames > 0) {
                frameCount = Math.min(frameCount, maxFrames);
            }

            try {
                for (int i = 0; i < frameCount; i++) {
                    String path = String.format("/roles/%s/state/%s/%d.png",
                            currentRole.getFolder(),
                            actionName,
                            i);
                    list.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
                }
            } catch (Exception e) {
                log.error("加载动作失败: {}/{}", currentRole.getFolder(), actionName);
            }
            return list;
        });
    }

    private int getFrameCount(String actionName) {
        var config = currentRole.getConfig();
        if (config != null && config.getActions() != null) {
            return config.getActions().stream()
                    .filter(a -> a.getName().equalsIgnoreCase(actionName))
                    .findFirst()
                    .map(a -> a.getFrameCount() != null ? a.getFrameCount() : 1)
                    .orElse(1);
        }
        return 1;
    }

    private boolean shouldLoop(String actionName) {
        var config = currentRole.getConfig();
        if (config != null && config.getActions() != null) {
            return config.getActions().stream()
                    .filter(a -> a.getName().equalsIgnoreCase(actionName))
                    .findFirst()
                    .map(a -> a.getLoop() != null ? a.getLoop() : false)
                    .orElse(false);
        }
        return false;
    }

    public void stop() {
        if (currentAnim != null) currentAnim.stop();
    }
}
