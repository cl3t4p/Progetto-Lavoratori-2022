package com.cl3t4p.progetto.lavoratori2022.fx.components;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class Toast {

    public static void makeText(Node node, String msg, int fadeInDelay, int fadeOutDelay) {
        Stage main_stage = (Stage) node.getScene().getWindow();
        Point2D point = new Point2D(main_stage.getX() + main_stage.getWidth() / 2, main_stage.getY() + main_stage.getHeight() - main_stage.getHeight() / 6);


        Text text = new Text(msg);
        text.setFont(Font.font("Verdana", 10));

        StackPane root = new StackPane(text);
        root.setStyle("-fx-background-radius: 20; -fx-background-color: rgba(54,53,53,0.17); -fx-padding: 5px;");
        root.setOpacity(0);

        Stage toastStage = new Stage();
        toastStage.setX(point.getX() - text.getLayoutBounds().getWidth() / 2);
        toastStage.setY(point.getY() - text.getLayoutBounds().getHeight() / 2);
        toastStage.initOwner(main_stage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        toastStage.show();

        Timeline timelinein = new Timeline();
        KeyFrame fadein = new KeyFrame(Duration.millis(fadeInDelay), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 1));
        timelinein.getKeyFrames().add(fadein);
        timelinein.setOnFinished((ae) ->
        {
            Timeline timelineout = new Timeline();
            KeyFrame fateoutkey = new KeyFrame(Duration.millis(fadeOutDelay), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0));
            timelineout.getKeyFrames().add(fateoutkey);
            timelineout.setOnFinished((aeb) -> toastStage.close());
            timelineout.play();
        });
        timelinein.play();


    }
}
