package com.siskema.gryffindor.ui;

import com.siskema.gryffindor.model.Activity;
import com.siskema.gryffindor.model.ActivityStatus;
import com.siskema.gryffindor.model.User;
import com.siskema.gryffindor.service.DataService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class DashboardStage extends BorderPane {

    private User currentUser;
    private DataService dataService;

    public DashboardStage(User user) {
        this.currentUser = user;
        this.dataService = new DataService();
        setPadding(new Insets(20));
        
        // Kiri: Profil Singkat
        VBox leftColumn = createProfileCard();
        leftColumn.setPrefWidth(300);
        leftColumn.setMinWidth(280);
        setLeft(leftColumn);
        BorderPane.setMargin(leftColumn, new Insets(0, 20, 0, 0));

        // Tengah: Kegiatan Terbaru
        VBox centerColumn = createDashboardContent();
        setCenter(centerColumn);
    }

    private VBox createProfileCard() {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.TOP_CENTER);

        Label avatar = new Label("👤");
        avatar.setFont(Font.font(64));
        Label name = new Label(currentUser.getFullName());
        name.setFont(UIConstants.FONT_SUBTITLE);
        Label nim = new Label(currentUser.getUsername());
        nim.setFont(UIConstants.FONT_NORMAL);
        nim.setTextFill(Color.web(UIConstants.HEX_TEXT_LIGHT));
        Label role = new Label(currentUser.getRole().toString());
        role.setFont(UIConstants.FONT_NORMAL);
        role.setTextFill(Color.web(UIConstants.HEX_PRIMARY));

        card.getChildren().addAll(avatar, name, nim, role);

        VBox stats = new VBox(5);
        stats.setPadding(new Insets(20, 0, 0, 0));
        stats.getChildren().add(createStatItem("Kegiatan Diikuti", "12", UIConstants.HEX_PRIMARY));
        stats.getChildren().add(createStatItem("Total Poin", "450", "#009600"));

        card.getChildren().add(stats);
        return card;
    }

    private BorderPane createStatItem(String label, String value, String colorHex) {
        BorderPane item = new BorderPane();
        item.setPadding(new Insets(10, 0, 10, 0));
        item.setStyle("-fx-border-color: #F0F0F0; -fx-border-width: 0 0 1 0;");
        
        Label lKey = new Label(label);
        lKey.setFont(UIConstants.FONT_NORMAL);
        Label lVal = new Label(value);
        lVal.setFont(UIConstants.FONT_SUBTITLE);
        lVal.setTextFill(Color.web(colorHex));
        
        item.setLeft(lKey);
        item.setRight(lVal);
        return item;
    }

    private VBox createDashboardContent() {
        VBox content = new VBox(15);
        Label title = new Label("Kegiatan Terbaru");
        title.setFont(UIConstants.FONT_TITLE);
        
        VBox list = new VBox(10);
        
        // Load real data instead of dummy if possible, or mix
        List<Activity> approved = dataService.getApprovedActivities();
        if (approved.isEmpty()) {
            list.getChildren().add(new Label("Belum ada kegiatan terbaru."));
        } else {
            // Show max 3 items
            for (int i = 0; i < Math.min(approved.size(), 3); i++) {
                list.getChildren().add(createSimpleActivityCard(approved.get(i)));
            }
        }

        ScrollPane scroll = new ScrollPane(list);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scroll.setBorder(Border.EMPTY);

        content.getChildren().addAll(title, scroll);
        return content;
    }

    private HBox createSimpleActivityCard(Activity activity) {
        HBox card = new HBox(15);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-border-color: #E6E6E6; -fx-border-radius: 8;");
        card.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("📅");
        icon.setFont(Font.font(32));

        VBox info = new VBox(5);
        Label title = new Label(activity.getName());
        title.setFont(UIConstants.FONT_SUBTITLE);
        title.setTextFill(Color.web(UIConstants.HEX_TEXT_DARK)); // FIX: Set color explicitly
        
        Label subtitle = new Label(activity.getDate() + " | Oleh: " + activity.getOrganizerName());
        subtitle.setFont(UIConstants.FONT_NORMAL);
        subtitle.setTextFill(Color.web(UIConstants.HEX_TEXT_LIGHT));
        info.getChildren().addAll(title, subtitle);
        HBox.setHgrow(info, Priority.ALWAYS);

        Button detailBtn = new Button("Detail");
        StyleUtil.styleSecondaryButton(detailBtn);
        
        detailBtn.setOnAction(e -> {
            new ActivityDetailStage(currentUser, activity).show();
        });

        card.getChildren().addAll(icon, info, detailBtn);
        return card;
    }
}