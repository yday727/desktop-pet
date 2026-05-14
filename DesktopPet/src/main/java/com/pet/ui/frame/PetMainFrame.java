package com.pet.ui.frame;

import com.pet.common.animation.PetAnimationAction;
import com.pet.common.config.PetProperties;
import com.pet.common.enums.PetRole;
import com.pet.ui.animation.PetAnimationPlayer;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 桌宠主窗口
 */
@Component
public class PetMainFrame {

    @Autowired
    private PetProperties petProperties;

    private Stage stage;
    private final ImageView imageView = new ImageView();
    private final PetAnimationPlayer animPlayer = new PetAnimationPlayer(imageView);

    public void init(Stage stage) {
        this.stage = stage;
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(petProperties.getMain().getAlwaysOnTop());

        int w = petProperties.getMain().getWidth();
        int h = petProperties.getMain().getHeight();

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color:transparent;");

        imageView.setFitWidth(w);
        imageView.setFitHeight(h);
        imageView.setPreserveRatio(true);
        root.getChildren().add(imageView);

        enableDrag(root);

        Scene scene = new Scene(root, w, h);
        scene.setFill(null);
        stage.setScene(scene);
        stage.show();
    }

    // ==================== 对外接口 ====================
    public void setRole(PetRole role) {
        animPlayer.setRole(role);
    }

    public void playStatic(PetAnimationAction action) {
        animPlayer.playStatic(action);
    }

    public void playAnimation(PetAnimationAction action) {
        animPlayer.playAnimation(action);
    }

    // ==================== 拖拽 ====================
    private double x, y;
    private void enableDrag(StackPane root) {
        root.setOnMousePressed(e -> {
            x = e.getScreenX() - stage.getX();
            y = e.getScreenY() - stage.getY();
        });
        root.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - x);
            stage.setY(e.getScreenY() - y);
        });
    }
}