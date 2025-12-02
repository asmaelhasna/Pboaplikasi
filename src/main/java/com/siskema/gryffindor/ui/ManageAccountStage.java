package com.siskema.gryffindor.ui;

import com.siskema.gryffindor.model.User;
import com.siskema.gryffindor.model.UserRole;
import com.siskema.gryffindor.service.DataService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ManageAccountStage extends VBox {

    private DataService dataService;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField fullNameField;
    private TextField orgNameField; // Khusus UKM

    public ManageAccountStage(User user) {
        this.dataService = new DataService();
        setPadding(new Insets(20));
        setSpacing(20);

        Label title = new Label("Kelola Akun & Tambah UKM");
        title.setFont(UIConstants.FONT_TITLE);

        // --- Form Tambah UKM Baru ---
        VBox addUkmPanel = new VBox(15);
        addUkmPanel.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        
        Label subTitle = new Label("Tambah Akun UKM Baru");
        subTitle.setFont(UIConstants.FONT_SUBTITLE);

        usernameField = new TextField(); 
        usernameField.setPromptText("Username (untuk login)");
        StyleUtil.styleRoundedTextField(usernameField);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        StyleUtil.styleRoundedPasswordField(passwordField);

        fullNameField = new TextField();
        fullNameField.setPromptText("Nama Lengkap Penanggung Jawab");
        StyleUtil.styleRoundedTextField(fullNameField);

        orgNameField = new TextField();
        orgNameField.setPromptText("Nama Organisasi UKM (misal: UKM Basket)");
        StyleUtil.styleRoundedTextField(orgNameField);

        Button addButton = new Button("Tambah UKM");
        StyleUtil.styleRoundedButton(addButton);
        addButton.setOnAction(e -> addUKM());

        addUkmPanel.getChildren().addAll(
            subTitle, 
            new Label("Username"), usernameField,
            new Label("Password"), passwordField,
            new Label("Nama Penanggung Jawab"), fullNameField,
            new Label("Nama Organisasi"), orgNameField,
            new Region() {{ setMinHeight(10); }},
            addButton
        );

        getChildren().addAll(title, addUkmPanel);
    }

    private void addUKM() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        String orgName = orgNameField.getText();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || orgName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Semua field wajib diisi.");
            alert.showAndWait();
            return;
        }

        // Cek apakah username sudah ada
        if (dataService.getUserByUsername(username) != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username sudah digunakan.");
            alert.showAndWait();
            return;
        }

        // Buat User dengan role PENYELENGGARA
        User newUkm = new User(username, password, fullName, UserRole.PENYELENGGARA, orgName);
        dataService.addUser(newUkm);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukses");
        alert.setHeaderText(null);
        alert.setContentText("Akun UKM berhasil ditambahkan!");
        alert.showAndWait();

        // Clear fields
        usernameField.clear();
        passwordField.clear();
        fullNameField.clear();
        orgNameField.clear();
    }
}