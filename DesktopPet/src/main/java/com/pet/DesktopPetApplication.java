package com.pet;

import com.pet.common.enums.PetRole;
import com.pet.common.enums.state.DefaultState;
import com.pet.ui.frame.PetMainFrame;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * 桌宠启动类
 */
@SpringBootApplication
public class DesktopPetApplication {

    public static void main(String[] args) {
        // 先启动 SpringBoot
        ApplicationContext context = SpringApplication.run(DesktopPetApplication.class, args);

        // 再启动 JavaFX
        Platform.startup(() -> {
            try {
                PetMainFrame mainFrame = context.getBean(PetMainFrame.class);
                Stage stage = new Stage();
                mainFrame.init(stage);

                mainFrame.setRole(PetRole.Default);
                mainFrame.playAnimation(DefaultState.RUN);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}