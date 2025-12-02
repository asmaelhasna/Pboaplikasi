package com.siskema.gryffindor.ui;

import com.siskema.gryffindor.model.Activity;
import com.siskema.gryffindor.model.User;
import com.siskema.gryffindor.service.DataService;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class ReportStage extends VBox {

    public ReportStage(User user) {
        setPadding(new Insets(20));
        setSpacing(20);
        
        Label title = new Label("Laporan & Statistik");
        title.setFont(UIConstants.FONT_TITLE);
        
        DataService ds = new DataService();
        List<Activity> all = ds.getAllActivities();
        
        // --- Tab Pane untuk Memisahkan Laporan ---
        TabPane tabPane = new TabPane();
        
        // Tab 1: Statistik Bulanan
        Tab tabStatistik = new Tab("Statistik Pendaftar Bulanan", createMonthlyStatistics(all));
        tabStatistik.setClosable(false);
        
        // Tab 2: Laporan Partisipasi
        Tab tabPartisipasi = new Tab("Laporan Partisipasi", createParticipationReport(all));
        tabPartisipasi.setClosable(false);
        
        tabPane.getTabs().addAll(tabStatistik, tabPartisipasi);
        
        getChildren().addAll(title, tabPane);
    }

    private VBox createMonthlyStatistics(List<Activity> activities) {
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Bulan");
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Jumlah Pendaftar");
        
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Statistik Pendaftar Tahun " + LocalDate.now().getYear());
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Pendaftar");
        
        // Inisialisasi map bulan
        Map<Month, Integer> monthlyCounts = new EnumMap<>(Month.class);
        for (Month m : Month.values()) {
            monthlyCounts.put(m, 0);
        }

        // Hitung pendaftar per bulan berdasarkan tanggal kegiatan
        // Asumsi format tanggal di activity adalah "dd MMM yyyy" (misal: "20 Nov 2025")
        // Jika parsing gagal, data diabaikan (untuk simplifikasi dummy data)
        for (Activity a : activities) {
            try {
                // Parsing sederhana untuk nama bulan dalam Bahasa Indonesia/Inggris
                String dateStr = a.getDate(); 
                // Coba ambil 3 huruf bulan (e.g. "Nov")
                String[] parts = dateStr.split(" ");
                if (parts.length >= 2) {
                    String monthStr = parts[1];
                    Month m = parseMonth(monthStr);
                    if (m != null) {
                        monthlyCounts.put(m, monthlyCounts.get(m) + a.getRegisteredParticipants().size());
                    }
                }
            } catch (Exception e) {
                // Ignore parsing errors for dummy data
            }
        }

        // Masukkan ke chart
        for (Month m : Month.values()) {
            series.getData().add(new XYChart.Data<>(m.getDisplayName(TextStyle.SHORT, Locale.ENGLISH), monthlyCounts.get(m)));
        }
        
        barChart.getData().add(series);
        container.getChildren().add(barChart);
        return container;
    }

    private Month parseMonth(String monthStr) {
        String lower = monthStr.toLowerCase();
        if (lower.startsWith("jan")) return Month.JANUARY;
        if (lower.startsWith("feb")) return Month.FEBRUARY;
        if (lower.startsWith("mar")) return Month.MARCH;
        if (lower.startsWith("apr")) return Month.APRIL;
        if (lower.startsWith("mei") || lower.startsWith("may")) return Month.MAY;
        if (lower.startsWith("jun")) return Month.JUNE;
        if (lower.startsWith("jul")) return Month.JULY;
        if (lower.startsWith("agu") || lower.startsWith("aug")) return Month.AUGUST;
        if (lower.startsWith("sep")) return Month.SEPTEMBER;
        if (lower.startsWith("okt") || lower.startsWith("oct")) return Month.OCTOBER;
        if (lower.startsWith("nov")) return Month.NOVEMBER;
        if (lower.startsWith("des") || lower.startsWith("dec")) return Month.DECEMBER;
        return null;
    }

    private VBox createParticipationReport(List<Activity> activities) {
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));

        TableView<ActivityReportRow> table = new TableView<>();
        
        TableColumn<ActivityReportRow, String> colName = new TableColumn<>("Nama Kegiatan");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<ActivityReportRow, String> colOrg = new TableColumn<>("Penyelenggara");
        colOrg.setCellValueFactory(new PropertyValueFactory<>("organizer"));
        
        TableColumn<ActivityReportRow, String> colDate = new TableColumn<>("Tanggal");
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        TableColumn<ActivityReportRow, Integer> colCount = new TableColumn<>("Jumlah Peserta");
        colCount.setCellValueFactory(new PropertyValueFactory<>("participantCount"));

        table.getColumns().addAll(colName, colOrg, colDate, colCount);
        
        List<ActivityReportRow> rows = activities.stream()
                .map(a -> new ActivityReportRow(a.getName(), a.getOrganizerName(), a.getDate(), a.getRegisteredParticipants().size()))
                .collect(Collectors.toList());
        
        table.setItems(FXCollections.observableArrayList(rows));
        
        container.getChildren().add(table);
        return container;
    }

    // Helper class untuk tabel
    public static class ActivityReportRow {
        private String name;
        private String organizer;
        private String date;
        private int participantCount;

        public ActivityReportRow(String name, String organizer, String date, int participantCount) {
            this.name = name;
            this.organizer = organizer;
            this.date = date;
            this.participantCount = participantCount;
        }

        public String getName() { return name; }
        public String getOrganizer() { return organizer; }
        public String getDate() { return date; }
        public int getParticipantCount() { return participantCount; }
    }
}