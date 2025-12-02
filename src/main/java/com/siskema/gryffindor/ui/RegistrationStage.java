package com.siskema.gryffindor.ui;

import com.siskema.gryffindor.model.User;
import com.siskema.gryffindor.model.UserRole;
import com.siskema.gryffindor.service.DataService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RegistrationStage extends Stage {

    private DataService dataService;
    private TextField nimField;
    private TextField nameField;
    private TextField emailField;
    private PasswordField passwordField;

    public RegistrationStage() {
        this.dataService = new DataService();
        setTitle("Siskema Gryffindor - Daftar Akun Baru");

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: " + UIConstants.HEX_BACKGROUND + ";");

        HBox cardPanel = new HBox();
        cardPanel.setMaxSize(900, 600);
        cardPanel.setStyle("-fx-background-color: white; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);");

        // Left Panel
        VBox leftPanel = new VBox(10);
        leftPanel.setPrefWidth(300);
        leftPanel.setAlignment(Pos.CENTER);
        leftPanel.setStyle("-fx-border-color: #E0E0E0; -fx-border-width: 0 1 0 0;");
        
        Label shield = new Label("🛡️");
        shield.setFont(Font.font(64));
        Label title = new Label("SISKEMA");
        title.setFont(UIConstants.FONT_TITLE);
        title.setTextFill(Color.web(UIConstants.HEX_PRIMARY));
        leftPanel.getChildren().addAll(shield, title, new Label("GRYFFINDOR"));

        // Right Panel (Form)
        VBox rightPanel = new VBox(15);
        rightPanel.setPadding(new Insets(40));
        rightPanel.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(rightPanel, Priority.ALWAYS);

        Label lblTitle = new Label("Daftar Akun Baru");
        lblTitle.setFont(UIConstants.FONT_TITLE);

        Hyperlink loginLink = new Hyperlink("Sudah punya akun? Silahkan login");
        loginLink.setOnAction(e -> {
            new LoginStage().show();
            this.close();
        });

        nimField = new TextField(); StyleUtil.styleRoundedTextField(nimField);
        nameField = new TextField(); StyleUtil.styleRoundedTextField(nameField);
        emailField = new TextField(); StyleUtil.styleRoundedTextField(emailField);
        passwordField = new PasswordField(); StyleUtil.styleRoundedPasswordField(passwordField);

        Button registerButton = new Button("Daftar");
        StyleUtil.styleRoundedButton(registerButton);
        registerButton.setOnAction(e -> registerUser());

        rightPanel.getChildren().addAll(
            lblTitle, loginLink,
            new Label("NIM (sebagai username)"), nimField,
            new Label("Nama Lengkap"), nameField,
            new Label("Email"), emailField,
            new Label("Password"), passwordField,
            new Region() {{ setMinHeight(20); }},
            registerButton
        );

        cardPanel.getChildren().addAll(leftPanel, rightPanel);
        root.getChildren().add(cardPanel);

        Scene scene = new Scene(root, 1000, 700);
        setScene(scene);
    }

    private void registerUser() {
        String nim = nimField.getText();
        String name = nameField.getText();
        String pass = passwordField.getText();

        if (nim.isEmpty() || name.isEmpty() || pass.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "NIM, Nama, dan Password wajib diisi.");
            return;
        }

        User newUser = new User(nim, pass, name, UserRole.MAHASISWA, null);
        try {
            dataService.addUser(newUser);
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Pendaftaran Berhasil! Silahkan Login.");
            new LoginStage().show();
            this.close();
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal mendaftar: " + ex.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}