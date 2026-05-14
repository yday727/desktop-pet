package com.pet;

import com.pet.common.enums.PetRole;
import com.pet.ui.frame.PetMainFrame;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DesktopPetApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DesktopPetApplication.class, args);

        Platform.startup(() -> {
            try {
                PetMainFrame mainFrame = context.getBean(PetMainFrame.class);
                Stage stage = new Stage();
                mainFrame.init(stage);

                mainFrame.setRole(PetRole.getDefault());
                mainFrame.playAnimation("run");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
