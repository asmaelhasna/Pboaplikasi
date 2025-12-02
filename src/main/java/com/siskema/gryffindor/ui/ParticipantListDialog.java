package com.siskema.gryffindor.ui;

import com.siskema.gryffindor.model.Activity;
import com.siskema.gryffindor.model.User;
import com.siskema.gryffindor.service.DataService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class ParticipantListDialog extends Stage {

    private Activity activity;
    private DataService dataService;
    private VBox listContainer;

    public ParticipantListDialog(Activity activity, DataService dataService) {
        this.activity = activity;
        this.dataService = dataService;
        
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Pendaftar: " + activity.getName());

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setPrefSize(500, 600);

        Label header = new Label("Daftar Peserta");
        header.setFont(UIConstants.FONT_SUBTITLE);
        
        listContainer = new VBox(10);
        ScrollPane scroll = new ScrollPane(listContainer);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent;");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        loadParticipants();

        Button closeBtn = new Button("Tutup");
        closeBtn.setOnAction(e -> this.close());
        closeBtn.setMaxWidth(Double.MAX_VALUE);
        StyleUtil.styleSecondaryButton(closeBtn);
        
        root.getChildren().addAll(header, scroll, closeBtn);

        Scene scene = new Scene(root);
        setScene(scene);
    }

    private void loadParticipants() {
        listContainer.getChildren().clear();
        if (activity.getRegisteredParticipants().isEmpty()) {
            listContainer.getChildren().add(new Label("Belum ada pendaftar."));
            return;
        }

        for (String nim : activity.getRegisteredParticipants()) {
            User u = dataService.getUserByUsername(nim);
            String display = u != null ? (u.getUsername() + " - " + u.getFullName()) : (nim + " - (Info Tidak Ditemukan)");
            
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setStyle("-fx-border-color: #EEE; -fx-border-width: 0 0 1 0; -fx-padding: 10;");
            
            Label nameLbl = new Label(display);
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            
            Button rejectBtn = new Button("Tolak/Hapus");
            rejectBtn.setStyle("-fx-font-size: 10px; -fx-text-fill: red;");
            rejectBtn.setOnAction(e -> removeParticipant(nim));

            row.getChildren().addAll(nameLbl, spacer, rejectBtn);
            listContainer.getChildren().add(row);
        }
    }

    private void removeParticipant(String nim) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Hapus Peserta");
        alert.setContentText("Hapus " + nim + " dari daftar?");
        
        Optional<ButtonType> res = alert.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
            dataService.removeParticipant(activity, nim);
            loadParticipants(); // Reload list
        }
    }
}