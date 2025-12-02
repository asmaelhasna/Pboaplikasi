package com.siskema.gryffindor.ui;

import com.siskema.gryffindor.model.Activity;
import com.siskema.gryffindor.model.User;
import com.siskema.gryffindor.service.DataService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.stream.Collectors;

public class ActivityListStage extends VBox {

    private DataService dataService;
    private VBox listPanel;
    private User currentUser;
    private List<Activity> allActivities;

    public ActivityListStage(User user) {
        this.currentUser = user;
        this.dataService = new DataService();
        this.allActivities = dataService.getApprovedActivities();
        
        setPadding(new Insets(20));
        setSpacing(20);

        Label title = new Label("Daftar Kegiatan");
        title.setFont(UIConstants.FONT_TITLE);

        TextField searchField = new TextField();
        searchField.setPromptText("Cari Kegiatan (Nama, Lokasi, Penyelenggara)...");
        StyleUtil.styleRoundedTextField(searchField);
        searchField.setMaxWidth(400);
        
        // Search Logic
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterActivities(newVal));

        listPanel = new VBox(10);
        ScrollPane scrollPane = new ScrollPane(listPanel);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setBorder(Border.EMPTY);

        getChildren().addAll(title, searchField, scrollPane);
        renderList(allActivities);
    }

    private void filterActivities(String query) {
        if (query == null || query.isEmpty()) {
            renderList(allActivities);
            return;
        }
        
        String lowerQuery = query.toLowerCase();
        List<Activity> filtered = allActivities.stream()
            .filter(a -> a.getName().toLowerCase().contains(lowerQuery) || 
                         a.getLocation().toLowerCase().contains(lowerQuery) ||
                         a.getOrganizerName().toLowerCase().contains(lowerQuery))
            .collect(Collectors.toList());
        
        renderList(filtered);
    }

    private void renderList(List<Activity> activities) {
        listPanel.getChildren().clear();
        if (activities.isEmpty()) {
            listPanel.getChildren().add(new Label("Tidak ditemukan kegiatan."));
        } else {
            for (Activity activity : activities) {
                listPanel.getChildren().add(createActivityCard(activity));
            }
        }
    }

    private HBox createActivityCard(Activity activity) {
        HBox card = new HBox(15);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-border-color: #E6E6E6; -fx-border-radius: 8;");
        card.setAlignment(Pos.CENTER_LEFT);

        VBox info = new VBox(5);
        Label title = new Label(activity.getName());
        title.setFont(UIConstants.FONT_SUBTITLE);
        title.setTextFill(Color.web(UIConstants.HEX_TEXT_DARK)); // FIX COLOR
        
        Label details = new Label(activity.getDate() + " | " + activity.getOrganizerName());
        details.setFont(UIConstants.FONT_SMALL);
        details.setTextFill(Color.web(UIConstants.HEX_TEXT_LIGHT));
        
        info.getChildren().addAll(title, details);
        HBox.setHgrow(info, Priority.ALWAYS);

        Button detailBtn = new Button("Lihat Detail");
        StyleUtil.styleSecondaryButton(detailBtn);
        detailBtn.setOnAction(e -> {
            new ActivityDetailStage(currentUser, activity).show();
        });

        card.getChildren().addAll(info, detailBtn);
        return card;
    }
}