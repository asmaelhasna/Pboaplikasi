package com.siskema.gryffindor.ui;

import com.siskema.gryffindor.model.User;
import com.siskema.gryffindor.service.DataService;
import com.siskema.gryffindor.service.SessionManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginStage extends Stage {

    private TextField usernameField;
    private PasswordField passwordField;
    private DataService dataService;

    public LoginStage() {
        this.dataService = new DataService();

        setTitle("Siskema Gryffindor - Login");
        
        StackPane root = new StackPane();
        
        // Background Image Handling
        try {
            // Pastikan path resource benar. Jika di src/main/resources/images/siskema_bg.png
            // maka path harus "/images/siskema_bg.png"
            Image bgImage = new Image(getClass().getResourceAsStream("/images/siskema_bg.png"));
            if (bgImage.isError()) throw new Exception("Image not found");
            
            ImageView bgView = new ImageView(bgImage);
            bgView.setPreserveRatio(false);
            bgView.fitWidthProperty().bind(root.widthProperty());
            bgView.fitHeightProperty().bind(root.heightProperty());
            root.getChildren().add(bgView);
        } catch (Exception e) {
            System.err.println("Gagal memuat gambar background: " + e.getMessage());
            root.setStyle("-fx-background-color: " + UIConstants.HEX_BACKGROUND + ";");
        }

        HBox cardPanel = new HBox();
        cardPanel.setMaxSize(850, 450);
        cardPanel.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 5;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);"
        );

        // Left Panel (Logo)
        VBox leftPanel = new VBox(10);
        leftPanel.setPrefWidth(340);
        leftPanel.setAlignment(Pos.CENTER);
        leftPanel.setPadding(new Insets(20));
        leftPanel.setStyle("-fx-border-color: #E0E0E0; -fx-border-width: 0 1 0 0;");

        ImageView logoView = new ImageView();
        try {
            Image logoImg = new Image(getClass().getResourceAsStream("/images/siskema_logo.png"));
            if (!logoImg.isError()) {
                logoView.setImage(logoImg);
                logoView.setFitWidth(100);
                logoView.setPreserveRatio(true);
            }
        } catch (Exception e) {
             System.err.println("Gagal memuat logo: " + e.getMessage());
        }
        
        Label titleLabel = new Label("SISKEMA");
        titleLabel.setFont(UIConstants.FONT_TITLE);
        titleLabel.setTextFill(Color.web(UIConstants.HEX_PRIMARY));
        Label subtitleLabel = new Label("GRYFFINDOR");
        subtitleLabel.setFont(UIConstants.FONT_SUBTITLE);
        subtitleLabel.setTextFill(Color.web(UIConstants.HEX_TEXT_DARK));

        leftPanel.getChildren().addAll(logoView, titleLabel, subtitleLabel);

        // Right Panel (Form)
        VBox rightPanel = new VBox(15);
        rightPanel.setPrefWidth(510);
        rightPanel.setAlignment(Pos.CENTER_LEFT);
        rightPanel.setPadding(new Insets(40));
        HBox.setHgrow(rightPanel, Priority.ALWAYS);

        Label formTitle = new Label("Login");
        formTitle.setFont(UIConstants.FONT_TITLE);
        
        Label userLabel = new Label("Username (NIM/ID)");
        userLabel.setFont(UIConstants.FONT_NORMAL);
        usernameField = new TextField();
        StyleUtil.styleRoundedTextField(usernameField);

        Label passLabel = new Label("Password");
        passLabel.setFont(UIConstants.FONT_NORMAL);
        passwordField = new PasswordField();
        StyleUtil.styleRoundedPasswordField(passwordField);

        HBox helperBox = new HBox(5);
        Label belumPunya = new Label("Belum punya akun?");
        belumPunya.setFont(UIConstants.FONT_SMALL);
        
        Hyperlink daftarLink = new Hyperlink("Silahkan daftar");
        daftarLink.setFont(UIConstants.FONT_SMALL);
        daftarLink.setTextFill(Color.web(UIConstants.HEX_PRIMARY));
        daftarLink.setBorder(Border.EMPTY);
        daftarLink.setPadding(new Insets(0));
        
        daftarLink.setOnAction(e -> {
            new RegistrationStage().show();
            this.close();
        });
        
        helperBox.getChildren().addAll(belumPunya, daftarLink);

        Hyperlink lupaLink = new Hyperlink("Lupa kata sandi?");
        lupaLink.setFont(UIConstants.FONT_SMALL);
        lupaLink.setTextFill(Color.web(UIConstants.HEX_PRIMARY));
        lupaLink.setBorder(Border.EMPTY);
        lupaLink.setPadding(new Insets(0));
        // Note: ResetPasswordDialog belum ada versi JavaFX-nya di instruksi sebelumnya.
        // Anda bisa membuat Alert sederhana dulu atau implementasi Stage baru.
        lupaLink.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lupa Password");
            alert.setHeaderText(null);
            alert.setContentText("Silahkan hubungi admin untuk reset password.");
            alert.showAndWait();
        });

        HBox btnBox = new HBox();
        btnBox.setAlignment(Pos.CENTER_RIGHT);
        Button loginButton = new Button("Masuk");
        StyleUtil.styleRoundedButton(loginButton);
        loginButton.setPrefWidth(120);
        loginButton.setOnAction(e -> attemptLogin());
        btnBox.getChildren().add(loginButton);

        rightPanel.getChildren().addAll(
            formTitle, userLabel, usernameField, passLabel, passwordField, 
            helperBox, lupaLink, new Region() {{ setMinHeight(10); }}, btnBox
        );

        cardPanel.getChildren().addAll(leftPanel, rightPanel);
        root.getChildren().add(cardPanel);

        Scene scene = new Scene(root, 1000, 600);
        setScene(scene);
        centerOnScreen();
    }

    private void attemptLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = dataService.authenticateUser(username, password);

        if (user != null) {
            SessionManager.getInstance().setCurrentUser(user);
            
            // --- FIX: Membuka BaseStage (Main Window) ---
            BaseStage mainWindow = new BaseStage(user);
            
            // Set konten awal ke Dashboard
            mainWindow.setView(new DashboardStage(user)); 
            
            mainWindow.show();
            this.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Gagal");
            alert.setHeaderText(null);
            alert.setContentText("Username atau password salah.");
            alert.showAndWait();
        }
    }
}