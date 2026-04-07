package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.db.CartService;
import org.example.db.LocalizationService;

import java.util.*;

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

    private Map<String, String> texts;
    private final LocalizationService localizationService = new LocalizationService();
    private final CartService cartService = new CartService();

    private String currentLanguage = "en";
    private int itemCounter = 0;

    @FXML
    public void initialize() {
        languageBox.getItems().addAll("English", "Finnish", "Swedish", "Japanese", "Urdu");
        languageBox.setValue("English");

        loadLanguage("en");
    }

    @FXML
    public void changeLanguage() {
        switch (languageBox.getValue()) {
            case "Finnish": loadLanguage("fi"); break;
            case "Swedish": loadLanguage("sv"); break;
            case "Japanese": loadLanguage("ja"); break;
            case "Urdu": loadLanguage("ur"); break;
            default: loadLanguage("en");
        }
    }

    private void loadLanguage(String lang) {
        currentLanguage = lang;
        texts = localizationService.getStrings(lang);

        welcomeLabel.setText(texts.get("greet"));
        instructionLabel.setText(texts.get("instruction"));
        labelQuantity.setText(texts.get("prompt1"));
        labelPrice.setText(texts.get("prompt2"));

        addButton.setText(texts.get("add"));
        removeButton.setText(texts.get("subs"));
        proceedButton.setText(texts.get("proceed"));
        cancelButton.setText(texts.get("cancel"));

        totalLabel.setText(texts.get("current") + ": 0");
    }

    @FXML
    public void handleAdd() {
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());

        double total = calc.add(quantity, price);
        totalLabel.setText(texts.get("current") + ": " + total);

        itemCounter++;
    }

    @FXML
    public void handleRemove() {
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());

        double total = calc.remove(quantity, price);
        totalLabel.setText(texts.get("current") + ": " + total);
    }

    @FXML
    public void handleProceed() {

        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());
        double subtotal = calc.total(quantity, price);

        double totalCost = calc.getCurrent();

        int cartId = cartService.saveCart(itemCounter, totalCost, currentLanguage);

        cartService.saveItem(cartId, 1, price, quantity, subtotal);

        infoLabel.setText(texts.get("exit"));

        calc.reset();
        itemCounter = 0;

        totalLabel.setText(texts.get("current") + ": 0");
    }

    @FXML
    public void handleCancel() {
        calc.reset();
        itemCounter = 0;
        totalLabel.setText(texts.get("current") + ": 0");
    }
}