package com.siskema.gryffindor.ui;

import com.siskema.gryffindor.model.Activity;
import com.siskema.gryffindor.model.User;
import com.siskema.gryffindor.service.DataService;
import com.siskema.gryffindor.service.SessionManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class UKMDetailStage extends Stage {

    private User ukmUser;
    private DataService dataService;

    public UKMDetailStage(User ukmUser) {
        this.ukmUser = ukmUser;
        this.dataService = new DataService();
        setTitle("Detail UKM: " + ukmUser.getOrganizationName());

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: " + UIConstants.HEX_BACKGROUND + ";");

        Label title = new Label(ukmUser.getOrganizationName());
        title.setFont(UIConstants.FONT_TITLE);
        
        Label subtitle = new Label("Daftar Kegiatan yang Diadakan:");
        subtitle.setFont(UIConstants.FONT_SUBTITLE);

        VBox listPanel = new VBox(10);
        List<Activity> activities = dataService.getActivitiesByOrganizer(ukmUser.getOrganizationName());

        if (activities.isEmpty()) {
            listPanel.getChildren().add(new Label("Belum ada kegiatan."));
        } else {
            for (Activity act : activities) {
                HBox card = new HBox(10);
                card.setPadding(new Insets(10));
                card.setStyle("-fx-background-color: white; -fx-background-radius: 5;");
                
                VBox info = new VBox(2);
                Label actName = new Label(act.getName());
                actName.setFont(UIConstants.FONT_SUBTITLE);
                Label actDate = new Label(act.getDate());
                actDate.setTextFill(Color.web(UIConstants.HEX_TEXT_LIGHT));
                
                info.getChildren().addAll(actName, actDate);
                card.getChildren().add(info);
                listPanel.getChildren().add(card);
            }
        }

        ScrollPane scroll = new ScrollPane(listPanel);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent;");

        root.getChildren().addAll(title, subtitle, scroll);
        Scene scene = new Scene(root, 500, 600);
        setScene(scene);
    }
}