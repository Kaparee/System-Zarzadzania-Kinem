package pl.cinema.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.cinema.Model.Show;
import pl.cinema.Util.ServiceInjector;
import pl.cinema.Util.SessionManager;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainController extends BaseController{
    @FXML
    private VBox showsContainer;

     LocalDate now = LocalDate.now();

    @FXML
    public void initialize(){
        ServiceInjector.injectAllServices(this);
        showsContainer.getChildren().add(menuBar());
        loadShowsForDate(LocalDate.now());
    }

    public void loadShowsForDate(LocalDate localDate){
        if(showsContainer.getChildren().size() > 1) {
            showsContainer.getChildren().remove(1, showsContainer.getChildren().size());
        }
        Map<String, List<Show>> allShows = showService.getAllShowsGroupedByFilm(localDate);

        if (allShows != null && !allShows.isEmpty()) {
            for (Map.Entry<String, List<Show>> entry : allShows.entrySet()) {
                String filmTitle = entry.getKey();
                List<Show> filmShows = entry.getValue();

                HBox filmBox = new HBox(10);
                filmBox.setAlignment(Pos.TOP_LEFT);
                filmBox.getStyleClass().add("film-box");

                VBox posterBox = new VBox(5);
                posterBox.setAlignment(Pos.CENTER);
                posterBox.maxWidth(150);

                String posterPath = null;
                if (!filmShows.isEmpty() && filmShows.getFirst().getFilm().getPosterURL() != null) {
                    posterPath = "/pl/cinema/images/" + filmShows.getFirst().getFilm().getPosterURL();
                } else {
                    posterPath = "/pl/cinema/images/kamera.jpg";
                }
                ImageView imageView = new ImageView();
                try {
                    Image image = new Image(Objects.requireNonNull(getClass().getResource(posterPath)).toExternalForm());
                    imageView.setImage(image);
                } catch (Exception e) {
                    System.err.println("Błąd ładowania obrazu z zasobów: " + posterPath + ". Używam domyślnego.");
                    imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/pl/cinema/images/kamera.jpg")).toExternalForm()));
                }

                imageView.setFitHeight(200);
                imageView.setFitWidth(150);

                Label titleLabel = new Label(filmTitle);
                titleLabel.setWrapText(true);
                titleLabel.getStyleClass().add("title-label");

                posterBox.getChildren().addAll(imageView, titleLabel);

                VBox showTimesBox = new VBox(5);
                showTimesBox.setAlignment(Pos.CENTER_LEFT);

                for (Show show : filmShows) {
                    HBox showTimeRow = new HBox();
                    showTimeRow.getStyleClass().add("show-times-box");
                    showTimeRow.setAlignment(Pos.CENTER_LEFT);
                    Label roomLabel = new Label(show.getRoom().getName() + ":");
                    roomLabel.getStyleClass().add("room-label");
                    Button timeButton = new Button(String.format("%02d:%02d", show.getStartTime().getHour(), show.getStartTime().getMinute()));
                    timeButton.setId(String.valueOf(show.getRoom().getId()));
                    timeButton.getStyleClass().add("time-button");
                    timeButton.setOnAction(event -> {
                        Long roomId = Long.parseLong(((Button) event.getSource()).getId());
                        changeToRoomScene(event, roomId, localDate);
                    });
                    if (show.getFilm().isIs3D()){
                        Label is3D = new Label();
                        is3D.setText("3D");
                        is3D.getStyleClass().add("is3D-label");
                        showTimeRow.getChildren().addAll(roomLabel, timeButton, is3D);
                    } else {
                        showTimeRow.getChildren().addAll(roomLabel, timeButton);
                    }

                    showTimesBox.getChildren().add(showTimeRow);
                }

                filmBox.getChildren().addAll(posterBox, new Separator(Orientation.VERTICAL), showTimesBox);
                showsContainer.getChildren().add(filmBox);
            }
        } else {
            System.out.println("Brak dostępnych seansów.");
        }
    }

    public HBox menuBar(){
        HBox menuBar = new HBox();
        menuBar.getStyleClass().add("menu-bar");
        HBox datePicker = new HBox();
        datePicker.getStyleClass().add("menu-bar");
        for (int i = 0; i < 7; i++) {
            Button dateButton = new Button();
            dateButton.setText(now.getDayOfMonth() +"\n" + now.getDayOfWeek());
            dateButton.setId(String.valueOf(now));
            dateButton.getStyleClass().add("date-button");
            dateButton.setOnAction(event -> {
                String buttonId = ((Button) event.getSource()).getId();
                LocalDate selectedDate = LocalDate.parse(buttonId);
                changeDate(selectedDate);
            });
            now = now.plusDays(1);
            datePicker.getChildren().add(dateButton);
        }
        if (!SessionManager.getInstance().isLoggedIn()) {
            HBox menuPicker = new HBox();
            menuPicker.getStyleClass().add("menu-bar");
            Button menuButton = new Button();
            menuButton.setText("Login or Register");
            menuButton.getStyleClass().add("menu-button");
            menuButton.setOnAction(event -> changeToLoginScene(menuButton));
            menuPicker.getChildren().add(menuButton);
            menuBar.getChildren().addAll(datePicker, menuPicker);
        } else {
            menuBar.getChildren().addAll(datePicker);
        }

        return menuBar;
    }

    public void changeDate(LocalDate localDate){
        System.out.println("Selected date: " + localDate);
        loadShowsForDate(localDate);
    }

    public void changeToRoomScene(javafx.event.ActionEvent event, Long id, LocalDate selectedDate){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/cinema/fxml/room-view.fxml"));
        try {
            Parent root = fxmlLoader.load();
            RoomController roomController = fxmlLoader.getController();
            ServiceInjector.injectAllServices(roomController);
            roomController.initData(id, selectedDate);
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeToLoginScene(Button menuButton){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/cinema/fxml/login-view.fxml"));
        try {
            Parent root = fxmlLoader.load();
            LoginController loginController = fxmlLoader.getController();
            ServiceInjector.injectAllServices(loginController);
            Stage stage = (Stage) menuButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
