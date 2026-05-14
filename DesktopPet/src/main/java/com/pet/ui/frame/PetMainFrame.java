package com.pet.ui.frame;

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

    public void setRole(PetRole role) {
        animPlayer.setRole(role);
        animPlayer.loadRole(role);
    }

    public void playStatic(String actionName) {
        animPlayer.playStatic(actionName);
    }

    public void playAnimation(String actionName) {
        animPlayer.playAnimation(actionName);
    }

    public void playAnimation(String actionName, int frameMs) {
        animPlayer.playAnimation(actionName, frameMs);
    }

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
