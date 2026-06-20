package projectjava;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GUIHotel extends Application {
    // Core data entities for managing the lifecycle of a hotel reservation
    private Guest guest;
    private Accommodation accommodation;
    private RestaurantBooking restaurant;
    private Gym gym;

    // Cache the initial registration view to enable historical back-navigation
    private Scene guestScene;
    private TextField txtName = new TextField();
    private TextField txtId = new TextField();
    private TextField txtEmail = new TextField();
    private TextField txtPhone = new TextField();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hotel Booking System");

        // Layout setup using VBox with responsive padding and spacing
        VBox guestPane = new VBox(10);
        guestPane.setPadding(new Insets(20));

        Label lblGuest = new Label("Guest Information");

        // UI alignment: Packaging form labels and inputs horizontally via HBox
        HBox nameBox = new HBox(10, new Label("Name:"), txtName);
        HBox idBox = new HBox(10, new Label("ID (10 digits):"), txtId);
        HBox emailBox = new HBox(10, new Label("Email:"), txtEmail);
        HBox phoneBox = new HBox(10, new Label("Phone (10 digits):"), txtPhone);

        Button btnNext = new Button("Next: Choose Room");
        guestPane.getChildren().addAll(lblGuest, nameBox, idBox, emailBox, phoneBox, btnNext);

        guestScene = new Scene(guestPane, 400, 300);
        primaryStage.setScene(guestScene);
        primaryStage.show();

        // Event Handler: Processes entry data and executes validation before layout progression
        btnNext.setOnAction(e -> {
            try {
                // Presence Check: Ensure no inputs are missing or composed solely of whitespace
                if (txtName.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty() || 
                    txtPhone.getText().trim().isEmpty() || txtId.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("All fields must be filled out.");
                }

                // Regex Validation: Enforce structural rules requiring exactly 10 digits for IDs
                if (!txtId.getText().trim().matches("\\d{10}")) {
                    throw new IllegalArgumentException("ID must be exactly 10 digits.");
                }

                // Regex Validation: Validates structural integrity of phone numbers (accepts any 10 digits)
                if (!txtPhone.getText().trim().matches("\\d{10}")) {
                    throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
                }

                int id = Integer.parseInt(txtId.getText().trim());

                // Instantiate validated model and switch application context to the Room Selection window
                guest = new Guest(txtName.getText(), id, txtPhone.getText(), txtEmail.getText());
                primaryStage.setScene(accommodationScene(primaryStage));

            } catch (NumberFormatException ex) {
                // Error Dialog: Handles numeric type mismatches defensively
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("ID and Phone must contain valid numbers only.");
                alert.showAndWait();
            } catch (IllegalArgumentException ex) {
                // Warning Dialog: Signals failure of structural or logical requirements
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validation Error");
                alert.setHeaderText(null);
                alert.setContentText("Error " + ex.getMessage());
                alert.showAndWait();
            }
        });
    }

    /**
     * Constructs and returns the Accommodation Selection screen interface.
     */
    private Scene accommodationScene(Stage stage) {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(20));

        Label lblAcc = new Label("Accommodation Selection");
        HBox typeBox = new HBox(10, new Label("Type:"), new ComboBox<String>());
        ComboBox<String> cbType = (ComboBox<String>) typeBox.getChildren().get(1);
        cbType.getItems().addAll("SingleRoom", "MultipleRoom", "Suite");

        HBox descBox = new HBox(10, new Label("Description:"), new TextField());
        
        // Radio Button UI Assembly managed inside a unified ToggleGroup
        HBox sizeBox = new HBox(10, new Label("Size:"));
        RadioButton rbBig = new RadioButton("Big");
        RadioButton rbSmall = new RadioButton("Small");
        ToggleGroup sizeGroup = new ToggleGroup();
        rbBig.setToggleGroup(sizeGroup);
        rbSmall.setToggleGroup(sizeGroup);
        sizeBox.getChildren().addAll(rbBig, rbSmall);

        HBox bedsBox = new HBox(10, new Label("Beds:"), new TextField());
        HBox livingBox = new HBox(10, new Label("Living room:"), new CheckBox());
        HBox bedTypeBox = new HBox(10, new Label("Bed type:"), new TextField());
        HBox roomsBox = new HBox(10, new Label("Suite rooms:"), new TextField());
        HBox guestsBox = new HBox(10, new Label("Max guests (max 6):"), new TextField());

        TextField txtBeds = (TextField) bedsBox.getChildren().get(1);
        CheckBox cbLiving = (CheckBox) livingBox.getChildren().get(1);
        TextField txtBedType = (TextField) bedTypeBox.getChildren().get(1);
        TextField txtRooms = (TextField) roomsBox.getChildren().get(1);
        TextField txtMaxGuests = (TextField) guestsBox.getChildren().get(1);

        // Hide specific features initially; fields will be loaded contextually based on types selected
        bedsBox.setVisible(false);
        livingBox.setVisible(false);
        bedTypeBox.setVisible(false);
        roomsBox.setVisible(false);
        guestsBox.setVisible(false);

        // Conditional UI Event: Controls component visibility to match subclass attributes dynamically
        cbType.setOnAction(e -> {
            bedsBox.setVisible(false);
            livingBox.setVisible(false);
            bedTypeBox.setVisible(false);
            roomsBox.setVisible(false);
            guestsBox.setVisible(false);

            if (cbType.getValue() != null) {
                switch (cbType.getValue()) {
                    case "SingleRoom" -> {
                        bedsBox.setVisible(true);
                        livingBox.setVisible(true);
                    }
                    case "MultipleRoom" -> {
                        bedsBox.setVisible(true);
                        bedTypeBox.setVisible(true);
                    }
                    case "Suite" -> {
                        roomsBox.setVisible(true);
                        guestsBox.setVisible(true);
                    }
                }
            }
        });

        Button btnBack = new Button("Back");
        Button btnNext = new Button("Next: Extras");
        HBox actionBox = new HBox(20, btnBack, btnNext);

        // Back-navigation: Restores application layout to initial registration window
        btnBack.setOnAction(e -> stage.setScene(guestScene));

        // Form processing logic for abstract and concrete Room specifications
        btnNext.setOnAction(e -> {
            try {
                if (cbType.getValue() == null) {
                    throw new IllegalArgumentException("Please select an accommodation type.");
                }

                if (!rbBig.isSelected() && !rbSmall.isSelected()) {
                    throw new IllegalArgumentException("Please select an accommodation size (Big or Small).");
                }

                String desc = ((TextField) descBox.getChildren().get(1)).getText();
                String size = rbBig.isSelected() ? "Big" : "Small";

                if (desc.trim().isEmpty()) {
                    throw new IllegalArgumentException("Description cannot be empty.");
                }

                // Factory-style layout compilation matching choices to core implementations
                switch (cbType.getValue()) {
                    case "SingleRoom" -> {
                        if (!txtBeds.getText().trim().matches("\\d+")) throw new IllegalArgumentException("Beds count must be numbers only.");
                        int beds = Integer.parseInt(txtBeds.getText().trim());
                        if (beds <= 0) throw new IllegalArgumentException("Beds count must be greater than 0.");
                        accommodation = new SingleRoom(desc, size, 400.0, beds, cbLiving.isSelected());
                    }
                    case "MultipleRoom" -> {
                        if (!txtBeds.getText().trim().matches("\\d+")) throw new IllegalArgumentException("Beds count must be numbers only.");
                        int beds = Integer.parseInt(txtBeds.getText().trim());
                        if (beds <= 0) throw new IllegalArgumentException("Beds count must be greater than 0.");
                        if (txtBedType.getText().trim().isEmpty()) throw new IllegalArgumentException("Please specify bed type.");
                        accommodation = new MultipleRoom(desc, size, 700.0, beds, txtBedType.getText());
                    }
                    case "Suite" -> {
                        if (!txtRooms.getText().trim().matches("\\d+")) throw new IllegalArgumentException("Suite rooms must be numbers only.");
                        if (!txtMaxGuests.getText().trim().matches("\\d+")) throw new IllegalArgumentException("Max guests must be numbers only.");
                        int rooms = Integer.parseInt(txtRooms.getText().trim());
                        int maxGuests = Integer.parseInt(txtMaxGuests.getText().trim());
                        if (rooms <= 0) throw new IllegalArgumentException("Rooms count must be greater than 0.");
                        if (maxGuests <= 0 || maxGuests > 6) throw new IllegalArgumentException("Max guests must be between 1 and 6.");
                        accommodation = new Suite(desc, size, 900, rooms, maxGuests);
                    }
                }
                // Safely advance views while preserving previous structure reference maps
                stage.setScene(extrasScene(stage, pane.getScene()));

            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validation Error");
                alert.setHeaderText(null);
                alert.setContentText("Error " + ex.getMessage());
                alert.showAndWait();
            }
        });

        pane.getChildren().addAll(lblAcc, typeBox, descBox, sizeBox, bedsBox, livingBox,
                bedTypeBox, roomsBox, guestsBox, actionBox);
        return new Scene(pane, 450, 520);
    }

    /**
     * Constructs and returns the optional Add-on services selection screen.
     */
    private Scene extrasScene(Stage stage, Scene previousScene) {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(20));

        // Restaurant sub-form assembly
        CheckBox cbRestaurant = new CheckBox("Book Restaurant");
        HBox peopleBox = new HBox(10, new Label("People:"), new TextField());
        HBox cuisineBox = new HBox(10, new Label("Cuisine:"), new TextField());
        TextField txtPeople = (TextField) peopleBox.getChildren().get(1);
        TextField txtCuisine = (TextField) cuisineBox.getChildren().get(1);

        peopleBox.setDisable(true);
        cuisineBox.setDisable(true);

        // Gym sub-form assembly
        CheckBox cbGym = new CheckBox("Book Gym");
        HBox hoursBox = new HBox(10, new Label("Hours:"), new TextField());
        HBox trainerBox = new HBox(10, new Label("With Trainer:"), new CheckBox());
        TextField txtHours = (TextField) hoursBox.getChildren().get(1);
        CheckBox cbTrainer = (CheckBox) trainerBox.getChildren().get(1);

        hoursBox.setDisable(true);
        trainerBox.setDisable(true);

        // Interactivity bindings: toggle form usability state contextually via check selections
        cbRestaurant.setOnAction(e -> {
            peopleBox.setDisable(!cbRestaurant.isSelected());
            cuisineBox.setDisable(!cbRestaurant.isSelected());
        });

        cbGym.setOnAction(e -> {
            hoursBox.setDisable(!cbGym.isSelected());
            trainerBox.setDisable(!cbGym.isSelected());
        });

        Button btnBack = new Button("Back");
        Button btnFinish = new Button("Confirm Booking");
        HBox actionBox = new HBox(20, btnBack, btnFinish);

        btnBack.setOnAction(e -> stage.setScene(previousScene));

        // Finalize transaction processing logic and instantiate sub-modules
        btnFinish.setOnAction(e -> {
            try {
                // Defensive Reference Check: Ensure core prerequisite object dependency exists
                if (guest == null) {
                    throw new NullPointerException("Guest object is null. Cannot complete booking.");
                }

                // Process conditional restaurant add-on values
                if (cbRestaurant.isSelected()) {
                    if (!txtPeople.getText().trim().matches("\\d+")) {
                        throw new IllegalArgumentException("People count must contain numbers only.");
                    }
                    int people = Integer.parseInt(txtPeople.getText().trim());
                    if (people <= 0 || people > 40) {
                        throw new IllegalArgumentException("Restaurant booking must be for 1 to 40 people.");
                    }
                    if (txtCuisine.getText().trim().isEmpty()) {
                        throw new IllegalArgumentException("Cuisine type cannot be empty.");
                    }
                    restaurant = new RestaurantBooking(guest, people, txtCuisine.getText());
                } else {
                    restaurant = null;
                }

                // Process conditional gym add-on values
                if (cbGym.isSelected()) {
                    if (!txtHours.getText().trim().matches("\\d+")) {
                        throw new IllegalArgumentException("Gym hours must contain numbers only.");
                    }
                    int hours = Integer.parseInt(txtHours.getText().trim());
                    if (hours <= 0) {
                        throw new IllegalArgumentException("Gym hours must be a positive number greater than 0.");
                    }
                    gym = new Gym(guest, hours, cbTrainer.isSelected());
                } else {
                    gym = null;
                }

                stage.setScene(summaryScene());

            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Booking Restriction");
                alert.setHeaderText(null);
                alert.setContentText("Error " + ex.getMessage());
                alert.showAndWait();
            }
        });

        pane.getChildren().addAll(cbRestaurant, peopleBox, cuisineBox,
                cbGym, hoursBox, trainerBox, actionBox);
        return new Scene(pane, 450, 450);
    }

    /**
     * Displays a clean read-only graphical statement invoice with all aggregated items and balances.
     */
    private Scene summaryScene() {
        VBox pane = new VBox(15);
        pane.setPadding(new Insets(20));

        // Bind guest data and dynamic abstract structures to execute central system confirmation routines
        Booking booking = new Booking(guest, new Accommodation[]{accommodation});
        booking.book();
        double total = booking.calcTotal();

        Label lblGuest = new Label("Guest Info: " + guest);
        Label lblRoom = new Label("Room Info: " + accommodation);

        // Compute conditional financial metrics and adjust summary display details
        Label lblRestaurant = new Label();
        if (restaurant != null) {
            restaurant.book();
            lblRestaurant.setText("Restaurant Booking: " + restaurant);
            total += restaurant.calcTotal();
        }

        Label lblGym = new Label();
        if (gym != null) {
            gym.book();
            lblGym.setText("Gym Booking: " + gym);
            total += gym.calcTotal();
        }

        // Apply string formatting utilities for consistent financial decimal representation
        Label lblTotal = new Label("TOTAL: " + String.format("%.2f", total) + " SAR");
        Label lblNote = new Label("Note: Bookings saved successfully.");

        pane.getChildren().addAll(
                new Label("Booking Summary"),
                lblGuest,
                lblRoom,
                lblRestaurant,
                lblGym,
                lblTotal,
                lblNote
        );

        return new Scene(pane, 500, 400);
    }

    public static void main(String[] args) {
        // Launches the JavaFX application lifecycle via native entry thread execution
        launch(args);
    }
}