package pl.cinema.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.cinema.Model.Seat;
import pl.cinema.Util.ServiceInjector;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class RoomController extends BaseController{
    @FXML
    private VBox seatsContainer;
    @FXML
    private VBox ticketContainer;
    @FXML
    private Button returnButton;

    private Long id;
    private LocalDate selectedDate;

    @FXML
    public void initialize() {
        ServiceInjector.injectAllServices(this);
    }

    public void initData(Long id, LocalDate selectedDate){
        this.id = id;
        this.selectedDate = selectedDate;
        loadSeats();
    }

    public void loadSeats(){
        if (id == null) {
            System.err.println("ERROR: Wrong room id");
            return;
        }
        seatsContainer.getChildren().clear();
        List<String> rows = roomService.getRows(id);
        List<Long> seats = roomService.getSeats(id);
        List<Boolean> isVip = seatService.isVip(id);
        int seatIndex = 0;
        for (int r = 0; r < rows.size(); r++) {
            String rowName = rows.get(r);
            HBox rowBox = new HBox();
            rowBox.setAlignment(Pos.CENTER);
            rowBox.setSpacing(5);
            for (int c = 0; c < seats.get(r); c++) {
                Button seat = new Button(rowName + (c + 1));
                Integer seatNumber = c + 1;
                Seat seatInfo = seatService.getSeatInfo(id, rowName, seatNumber);

                seat.setPrefSize(40, 40);
                boolean vipStatus = isVip.get(seatIndex);

                if (vipStatus) {
                    seat.setStyle("-fx-background-color: gold; -fx-text-fill: black;");
                    seat.setUserData("vip-unselected");
                } else {
                    seat.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                    seat.setUserData("normal-unselected");
                }

                seatIndex++;

                seat.setOnAction(e -> toggleSeat(seat, seatInfo));

                seat.setOnMouseEntered(e -> {
                    String userData = (String) seat.getUserData();
                    if ("normal-unselected".equals(userData)) {
                        seat.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white;");
                    }
                    if ("vip-unselected".equals(userData)){
                        seat.setStyle("-fx-background-color: #bda730; -fx-text-fill: black;");
                    }
                });
                seat.setOnMouseExited(e -> {
                    String userData = (String) seat.getUserData();
                    if ("normal-unselected".equals(userData)) {
                        seat.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                    }
                    if ("vip-unselected".equals(userData)) {
                        seat.setStyle("-fx-background-color: gold; -fx-text-fill: black;");
                    }
                });

                rowBox.getChildren().add(seat);
            }
            seatsContainer.getChildren().add(rowBox);
        }
    }

    private void toggleSeat(Button seat, Seat seatInfo) {
        String userData = (String) seat.getUserData();

        if ("normal-unselected".equals(userData)) {
            seat.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
            seat.setUserData("normal-selected");

            HBox ticketBox = createTicketBox(seatInfo);
            ticketContainer.getChildren().add(ticketBox);
            seat.setId("ticket-" + seatInfo.getId());

        } else if ("normal-selected".equals(userData)) {
            seat.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            seat.setUserData("normal-unselected");

            removeTicketBox(seat);

        } else if ("vip-unselected".equals(userData)) {
            seat.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
            seat.setUserData("vip-selected");

            HBox ticketBox = createTicketBox(seatInfo);
            ticketContainer.getChildren().add(ticketBox);
            seat.setId("ticket-" + seatInfo.getId());

        } else if ("vip-selected".equals(userData)) {
            seat.setStyle("-fx-background-color: gold; -fx-text-fill: black;");
            seat.setUserData("vip-unselected");

            removeTicketBox(seat);
        }
    }

    private HBox createTicketBox(Seat seatInfo) {
        HBox ticketBox = new HBox();
        ticketBox.setAlignment(Pos.CENTER);
        ticketBox.setSpacing(5);
        ticketBox.setId("ticket-box-" + seatInfo.getId());
        Label ticketLabel = new Label("Room number: "+ seatInfo.getRoom().getId()+ "\nRow: " + seatInfo.getRowChar()+ "\nSeat: "+ seatInfo.getNumber());
        ticketBox.getChildren().add(ticketLabel);
        return ticketBox;
    }

    private void removeTicketBox(Button seat) {
        String ticketBoxId = "ticket-box-" + seat.getId().replace("ticket-", "");
        for (javafx.scene.Node node : ticketContainer.getChildren()) {
            if (node.getId() != null && node.getId().equals(ticketBoxId)) {
                ticketContainer.getChildren().remove(node);
                break;
            }
        }
    }

    @FXML
    public void onReturnButtonClicked(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/cinema/fxml/main-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) returnButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}