package com.siskema.gryffindor.ui;

import com.siskema.gryffindor.model.Activity;
import com.siskema.gryffindor.model.User;
import com.siskema.gryffindor.service.DataService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CreateActivityStage extends Stage {

    private User currentUser;
    private DataService dataService;
    private Activity activityToEdit;

    private TextField nameField;
    private TextField dateField;
    private TextField locationField;
    private TextArea descriptionArea;

    public CreateActivityStage(User user) {
        this(user, null);
    }

    public CreateActivityStage(User user, Activity activityToEdit) {
        this.currentUser = user;
        this.activityToEdit = activityToEdit;
        this.dataService = new DataService();
        
        boolean isEditing = (activityToEdit != null);
        setTitle(isEditing ? "Edit Kegiatan" : "Buat Kegiatan Baru");

        VBox formPanel = new VBox(15);
        formPanel.setPadding(new Insets(20));
        formPanel.setStyle("-fx-background-color: white;");

        Label title = new Label(isEditing ? "Edit Kegiatan" : "Buat Kegiatan Baru");
        title.setFont(UIConstants.FONT_TITLE);

        nameField = new TextField(); StyleUtil.styleRoundedTextField(nameField);
        dateField = new TextField(); StyleUtil.styleRoundedTextField(dateField);
        locationField = new TextField(); StyleUtil.styleRoundedTextField(locationField);
        descriptionArea = new TextArea(); 
        descriptionArea.setWrapText(true);
        descriptionArea.setPrefHeight(150);
        // Style area manual karena StyleUtil hanya untuk TextField
        descriptionArea.setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-border-color: #D2D2D2; -fx-border-radius: 16; -fx-padding: 8;");

        if (isEditing) {
            nameField.setText(activityToEdit.getName());
            dateField.setText(activityToEdit.getDate());
            locationField.setText(activityToEdit.getLocation());
            descriptionArea.setText(activityToEdit.getDescription());
        }

        HBox btnBox = new HBox(10);
        btnBox.setAlignment(Pos.CENTER_RIGHT);
        
        Button cancelBtn = new Button("Batal");
        StyleUtil.styleSecondaryButton(cancelBtn);
        cancelBtn.setOnAction(e -> {
            // Kita tidak perlu memanggil new ManageActivityStage() karena
            // window utama (BaseStage) masih terbuka di belakang.
            // Cukup tutup window popup ini.
            // Jika ingin refresh data di window utama, kita butuh callback, 
            // tapi untuk sekarang tutup saja.
            this.close();
        });

        Button submitBtn = new Button(isEditing ? "Simpan" : "Ajukan");
        StyleUtil.styleRoundedButton(submitBtn);
        submitBtn.setOnAction(e -> submitActivity());

        btnBox.getChildren().addAll(cancelBtn, submitBtn);

        formPanel.getChildren().addAll(
            title,
            new Label("Nama Kegiatan"), nameField,
            new Label("Tanggal"), dateField,
            new Label("Lokasi"), locationField,
            new Label("Deskripsi"), descriptionArea,
            new Region() {{ setMinHeight(10); }},
            btnBox
        );

        Scene scene = new Scene(formPanel, 600, 700);
        setScene(scene);
    }

    private void submitActivity() {
        String name = nameField.getText();
        String date = dateField.getText();
        String loc = locationField.getText();
        String desc = descriptionArea.getText();

        if (name.isEmpty() || date.isEmpty() || loc.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Semua field wajib diisi.");
            a.showAndWait();
            return;
        }

        if (activityToEdit == null) {
            Activity newAct = new Activity(name, currentUser.getOrganizationName(), date, loc, desc);
            dataService.addActivity(newAct);
        } else {
            activityToEdit.setName(name);
            activityToEdit.setDate(date);
            activityToEdit.setLocation(loc);
            activityToEdit.setDescription(desc);
            dataService.updateActivity(activityToEdit);
        }

        // Kembali ke tampilan utama
        // Cara ideal: Panggil BaseStage.getInstance().setView(new ManageActivityStage(currentUser));
        // Jika BaseStage singleton.
        if (BaseStage.getInstance() != null) {
            BaseStage.getInstance().setView(new ManageActivityStage(currentUser));
        }
        
        this.close();
    }
}