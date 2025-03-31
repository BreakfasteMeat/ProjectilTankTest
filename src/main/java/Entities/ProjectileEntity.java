package Entities;

import com.example.projectiltanktest.HelloApplication;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

public class ProjectileEntity extends Entity {
    Circle circle;
    double angle;
    double speed;
    Point2D original_position;
    public ProjectileEntity(double angle, double speed, double x, double y) {
        circle = new Circle();
        circle.setRadius(5);
        this.angle = angle;
        this.speed = speed;
        entityGroup.getChildren().add(circle);
        original_position = new Point2D(x, y);
    }

    public double distanceFromOriginalPosition(){
        Point2D entityGroup_position = new Point2D(entityGroup.getLayoutX(), entityGroup.getLayoutY());
        return entityGroup_position.distance(original_position);
    }


    public double getAngle() {
        return angle;
    }

    public double getSpeed() {
        return speed;
    }
}
