package com.siskema.gryffindor.ui;

import com.siskema.gryffindor.model.Activity;
import com.siskema.gryffindor.model.User;
import com.siskema.gryffindor.model.UserRole;
import com.siskema.gryffindor.service.DataService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ActivityDetailStage extends Stage {

    private User currentUser;
    private Activity currentActivity;
    private DataService dataService;
    private Button daftarButton;

    public ActivityDetailStage(User user, Activity activity) {
        this.currentUser = user;
        this.currentActivity = activity;
        this.dataService = new DataService();

        setTitle("Siskema Gryffindor - Detail Kegiatan");
        
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + UIConstants.HEX_BACKGROUND + ";");

        root.setTop(createTopBar());
        root.setCenter(createMainContent());

        Scene scene = new Scene(root, 1200, 750);
        setScene(scene);
    }

    private HBox createTopBar() {
        HBox top = new HBox();
        top.setPadding(new Insets(10, 20, 10, 20));
        top.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);");
        top.setAlignment(Pos.CENTER_LEFT);
        top.setSpacing(10);

        Label logo = new Label("🛡️");
        logo.setFont(Font.font(24));
        
        Label appTitle = new Label("Siskema Gryffindor");
        appTitle.setFont(Font.font("SansSerif", FontWeight.BOLD, 20));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label profileName = new Label(currentUser.getFullName() + " 👤");
        profileName.setFont(Font.font("SansSerif", 16));

        top.getChildren().addAll(logo, appTitle, spacer, profileName);
        return top;
    }

    private Node createMainContent() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));

        Label title = new Label(currentActivity.getName());
        title.setFont(UIConstants.FONT_TITLE);
        title.setTextFill(Color.web(UIConstants.HEX_TEXT_DARK));

        SplitPane splitPane = new SplitPane();
        splitPane.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        
        ScrollPane detailScroll = new ScrollPane(createDetailPanel());
        detailScroll.setFitToWidth(true);
        detailScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        detailScroll.setBorder(Border.EMPTY);
        
        VBox actionPanel = createActionPanel();
        
        splitPane.getItems().addAll(detailScroll, actionPanel);
        splitPane.setDividerPositions(0.7); 

        VBox.setVgrow(splitPane, Priority.ALWAYS);
        container.getChildren().addAll(title, splitPane);
        
        return container;
    }

    private VBox createDetailPanel() {
        VBox card = new VBox(15);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        card.setPadding(new Insets(20));

        card.getChildren().add(createInfoItem("Penyelenggara", currentActivity.getOrganizerName(), "👤"));
        card.getChildren().add(createInfoItem("Waktu Pelaksanaan", currentActivity.getDate(), "⏱️"));
        card.getChildren().add(createInfoItem("Lokasi", currentActivity.getLocation(), "📍"));
        
        Label descTitle = new Label("Deskripsi");
        descTitle.setFont(UIConstants.FONT_SUBTITLE);
        
        Label descText = new Label(currentActivity.getDescription());
        descText.setFont(UIConstants.FONT_NORMAL);
        descText.setWrapText(true);
        
        card.getChildren().addAll(new Separator(), descTitle, descText);
        return card;
    }

    private HBox createInfoItem(String title, String value, String icon) {
        HBox item = new HBox(15);
        item.setAlignment(Pos.CENTER_LEFT);
        
        Label iconLbl = new Label(icon);
        iconLbl.setFont(Font.font(24));
        iconLbl.setTextFill(Color.web(UIConstants.HEX_PRIMARY));
        
        VBox text = new VBox(2);
        Label tLbl = new Label(title);
        tLbl.setFont(UIConstants.FONT_NORMAL);
        tLbl.setTextFill(Color.web(UIConstants.HEX_TEXT_LIGHT));
        
        Label vLbl = new Label(value);
        vLbl.setFont(UIConstants.FONT_SUBTITLE);
        vLbl.setTextFill(Color.web(UIConstants.HEX_TEXT_DARK));
        
        text.getChildren().addAll(tLbl, vLbl);
        item.getChildren().addAll(iconLbl, text);
        return item;
    }

    private VBox createActionPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(0, 0, 0, 20));
        
        VBox card = new VBox(15);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        card.setPadding(new Insets(20));
        
        if (currentUser.getRole() == UserRole.MAHASISWA) {
            daftarButton = new Button("Daftar Sekarang");
            StyleUtil.styleRoundedButton(daftarButton);
            daftarButton.setMaxWidth(Double.MAX_VALUE);
            
            if (currentActivity.getRegisteredParticipants().contains(currentUser.getUsername())) {
                daftarButton.setText("Anda Sudah Terdaftar");
                daftarButton.setDisable(true);
                daftarButton.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-background-radius: 20;");
            } else {
                daftarButton.setOnAction(e -> registerToActivity());
            }
            card.getChildren().add(daftarButton);
        }

        Button backButton = new Button("Kembali");
        StyleUtil.styleSecondaryButton(backButton);
        backButton.setMaxWidth(Double.MAX_VALUE);
        backButton.setOnAction(e -> {
            this.close(); // Tutup window detail, user kembali melihat dashboard/list di window utama
        });
        
        card.getChildren().add(backButton);
        panel.getChildren().add(card);
        return panel;
    }

    private void registerToActivity() {
        try {
            currentActivity.addParticipant(currentUser.getUsername());
            dataService.updateActivity(currentActivity);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sukses");
            alert.setHeaderText(null);
            alert.setContentText("Anda berhasil terdaftar!");
            alert.showAndWait();
            
            daftarButton.setText("Anda Sudah Terdaftar");
            daftarButton.setDisable(true);
            daftarButton.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-background-radius: 20;");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Gagal mendaftar: " + e.getMessage());
            alert.showAndWait();
        }
    }
}