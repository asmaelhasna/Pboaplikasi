package com.siskema.gryffindor.ui;

import com.siskema.gryffindor.model.Activity;
import com.siskema.gryffindor.model.ActivityStatus;
import com.siskema.gryffindor.model.User;
import com.siskema.gryffindor.service.DataService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Optional;

public class ManageActivityStage extends VBox {

    private DataService dataService;
    private VBox listPanel;
    private User currentUser;

    public ManageActivityStage(User user) {
        this.currentUser = user;
        this.dataService = new DataService();
        
        setPadding(new Insets(20));
        setSpacing(20);

        BorderPane topBox = new BorderPane();
        Label title = new Label("Kelola Kegiatan Saya");
        title.setFont(UIConstants.FONT_TITLE);
        
        Button createBtn = new Button("Buat Kegiatan Baru");
        StyleUtil.styleRoundedButton(createBtn);
        createBtn.setOnAction(e -> {
            new CreateActivityStage(currentUser).show();
            // Note: Refresh list manually requires reload, simpliest is to close/reopen or use callback
        });

        topBox.setLeft(title);
        topBox.setRight(createBtn);

        listPanel = new VBox(10);
        ScrollPane scrollPane = new ScrollPane(listPanel);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setBorder(Border.EMPTY);

        getChildren().addAll(topBox, scrollPane);
        loadActivities();
    }

    private void loadActivities() {
        listPanel.getChildren().clear();
        List<Activity> myActivities = dataService.getActivitiesByOrganizer(currentUser.getOrganizationName());

        if (myActivities.isEmpty()) {
            listPanel.getChildren().add(new Label("Anda belum membuat kegiatan apapun."));
        } else {
            for (Activity activity : myActivities) {
                listPanel.getChildren().add(createManageCard(activity));
            }
        }
    }

    private HBox createManageCard(Activity activity) {
        HBox card = new HBox(15);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-border-color: #E6E6E6; -fx-border-radius: 8;");
        card.setAlignment(Pos.CENTER_LEFT);

        VBox info = new VBox(5);
        Label title = new Label(activity.getName());
        title.setFont(UIConstants.FONT_SUBTITLE);
        title.setTextFill(Color.web(UIConstants.HEX_TEXT_DARK)); // FIX: Color
        
        Label details = new Label(activity.getDate() + " | Pendaftar: " + activity.getRegisteredParticipants().size() + " orang");
        details.setFont(UIConstants.FONT_SMALL);
        
        Label status = new Label("Status: " + activity.getStatus().toString());
        status.setFont(UIConstants.FONT_SMALL);
        if (activity.getStatus() == ActivityStatus.APPROVED) status.setTextFill(Color.GREEN);
        else if (activity.getStatus() == ActivityStatus.REJECTED) status.setTextFill(Color.RED);
        else status.setTextFill(Color.ORANGE);

        info.getChildren().addAll(title, details, status);
        HBox.setHgrow(info, Priority.ALWAYS);

        HBox btnBox = new HBox(10);
        
        Button editBtn = new Button("Edit");
        StyleUtil.styleSecondaryButton(editBtn);
        editBtn.setOnAction(e -> new CreateActivityStage(currentUser, activity).show());
        
        Button participantsBtn = new Button("Pendaftar");
        StyleUtil.styleSecondaryButton(participantsBtn);
        participantsBtn.setOnAction(e -> showParticipants(activity));

        Button deleteBtn = new Button("Hapus");
        deleteBtn.setStyle("-fx-background-color: #D32F2F; -fx-text-fill: white; -fx-cursor: hand; -fx-background-radius: 5;");
        deleteBtn.setOnAction(e -> deleteActivity(activity));

        btnBox.getChildren().addAll(editBtn, participantsBtn, deleteBtn);
        card.getChildren().addAll(info, btnBox);
        return card;
    }

    private void deleteActivity(Activity activity) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Hapus Kegiatan");
        alert.setHeaderText("Apakah Anda yakin ingin menghapus kegiatan ini?");
        alert.setContentText("Tindakan ini tidak dapat dibatalkan.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            dataService.deleteActivity(activity);
            loadActivities(); // Refresh list
        }
    }

    private void showParticipants(Activity activity) {
        new ParticipantListDialog(activity, dataService).showAndWait();
    }
}