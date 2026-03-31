package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller {

    @FXML private ComboBox<String> languageBox;

    @FXML private Label welcomeLabel;
    @FXML private Label instructionLabel;
    @FXML private Label labelQuantity;
    @FXML private Label labelPrice;
    @FXML private Label totalLabel;
    @FXML private Label infoLabel;

    @FXML private TextField quantityField;
    @FXML private TextField priceField;

    @FXML private Button addButton;
    @FXML private Button removeButton;
    @FXML private Button proceedButton;
    @FXML private Button cancelButton;

    private final Calculator calc = new Calculator();
    private ResourceBundle rb;

    @FXML
    public void initialize() {
        languageBox.getItems().addAll("English", "Finnish", "Swedish", "Japanese", "Urdu");
        languageBox.setValue("English");

        loadLanguage(new Locale("en", "US"));
    }

    @FXML
    public void changeLanguage() {
        switch (languageBox.getValue()) {
            case "Finnish":
                loadLanguage(new Locale("fi", "FI"));
                break;
            case "Swedish":
                loadLanguage(new Locale("sv", "SE"));
                break;
            case "Japanese":
                loadLanguage(new Locale("ja", "JP"));
                break;
            case "Urdu":
                loadLanguage(new Locale("ur", "PK"));
                break;
            default:
                loadLanguage(new Locale("en", "US"));
        }
    }

    private void loadLanguage(Locale locale) {
        rb = ResourceBundle.getBundle("MessagesBundle", locale);

        // Top labels
        welcomeLabel.setText(rb.getString("greet"));
        instructionLabel.setText(rb.getString("instruction"));

        // Input labels
        labelQuantity.setText(rb.getString("prompt1"));
        labelPrice.setText(rb.getString("prompt2"));

        // Buttons
        addButton.setText(rb.getString("add"));
        removeButton.setText(rb.getString("subs"));
        proceedButton.setText(rb.getString("proceed"));
        cancelButton.setText(rb.getString("cancel"));

        // Total
        totalLabel.setText(rb.getString("current") + ": 0");
    }

    @FXML
    public void handleAdd() {
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());

        double total = calc.add(quantity, price);
        totalLabel.setText(rb.getString("current") + ": " + total);
    }

    @FXML
    public void handleRemove() {
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());

        double total = calc.remove(quantity, price);
        totalLabel.setText(rb.getString("current") + ": " + total);
    }

    @FXML
    public void handleProceed() {

        infoLabel.setText(rb.getString("proceed") );
        infoLabel.setText(rb.getString("exit") );
        calc.reset();
        totalLabel.setText(rb.getString("current") + ": " + calc.getCurrent());
    }


    @FXML
    public void handleCancel() {
        calc.reset();
        totalLabel.setText(rb.getString("current") + ": 0");
    }
}