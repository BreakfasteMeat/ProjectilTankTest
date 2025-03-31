package com.example.projectiltanktest;

import Entities.Entity;
import Entities.ProjectileEntity;
import Entities.TankEntity;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.*;

public class HelloApplication extends Application {
    static Group root;
    private Entity mainPlayer;
    private final Set<KeyCode> activeKeys = new HashSet<>();
    private double mouse_x, mouse_y;
    public static List<Entity> entityList;

    @Override
    public void start(Stage stage) {
        root = new Group();
        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Tank Game");
        stage.setScene(scene);
        stage.show();
        mainPlayer = new TankEntity();
        entityList = new ArrayList<>();

        double center_x = scene.getWidth() / 2;
        double center_y = scene.getHeight() / 2;

        spawnEntity(mainPlayer);

        scene.setOnMouseMoved(event -> {
            mouse_x = event.getSceneX();
            mouse_y = event.getSceneY();
            mainPlayer.lookAt(mouse_x,mouse_y);
        });
        scene.setOnMouseClicked(event -> {
            double x = mainPlayer.getX();
            double y = mainPlayer.getY();
            double angle = Math.toDegrees(Math.atan2(mouse_y - y, mouse_x - x));
            Entity entity = new ProjectileEntity(angle,5, mainPlayer.getX(), mainPlayer.getY());
            entity.setPosition(x + 45 * (Math.cos(Math.toRadians(angle))), y + 45 * (Math.sin(Math.toRadians(angle))));
            spawnEntity(entity);

        });

        gameLoop.start();
    }

    public void spawnEntity(Entity entity) {
        entityList.add(entity);
        entity.render(root);
        entity.getEntityGroup().toBack();
    }

    public void despawnEntity(Entity entity) {
        root.getChildren().remove(entity.getEntityGroup());
    }

    private final AnimationTimer gameLoop = new AnimationTimer() {
        private long lastUpdate = 0;

        @Override
        public void handle(long now) {
            if (lastUpdate == 0) {
                lastUpdate = now;
                return;
            }

            mainPlayer.getEntityGroup().setLayoutX(root.getScene().getWidth() / 2);
            mainPlayer.getEntityGroup().setLayoutY(root.getScene().getHeight() / 2);

            Iterator it = entityList.iterator();
            while (it.hasNext()) {
                Entity e = (Entity) it.next();
                if(e instanceof ProjectileEntity) {
                    ProjectileEntity projectile = (ProjectileEntity) e;
                    if(projectile.distanceFromOriginalPosition() >= 200){
                        despawnEntity(projectile);
                        it.remove();
                        continue;
                    }
                    double angleRadians = Math.toRadians(projectile.getAngle());
                    double deltaX = projectile.getSpeed() * Math.cos(angleRadians);
                    double deltaY = projectile.getSpeed() * Math.sin(angleRadians);
                    projectile.setPosition(projectile.getX() + deltaX, projectile.getY() + deltaY);
                    System.out.println("Updating projectile");
                }
            }



            double deltaTime = (now - lastUpdate) / 1_000_000_000.0; // Convert nanoseconds to seconds
            lastUpdate = now;
        }
    };

    public static void main(String[] args) {
        launch();
    }
}
