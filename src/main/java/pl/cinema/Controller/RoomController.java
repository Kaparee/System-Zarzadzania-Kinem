package pl.cinema.Controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.cinema.Model.Seat;
import pl.cinema.Util.ServiceInjector;

import java.util.List;

public class RoomController extends BaseController{
    @FXML
    private VBox seatsContainer;
    @FXML
    private VBox ticketContainer;

    @FXML
    public void initialize() {
        ServiceInjector.injectAllServices(this);

        List<String> rows = roomService.getRows(1L);
        List<Long> seats = roomService.getSeats(1L);
        List<Boolean> isVip = seatService.isVip(1L);
        int seatIndex = 0;
        for (int r = 0; r < rows.size(); r++) {
            String rowName = rows.get(r);
            HBox rowBox = new HBox();
            rowBox.setAlignment(Pos.CENTER);
            rowBox.setSpacing(5);
            for (int c = 0; c < seats.get(r); c++) {
                Button seat = new Button(rowName + (c + 1));
                Integer seatNumber = c + 1;
                Seat seatInfo = seatService.getSeatInfo(1L, rowName, seatNumber);

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
}