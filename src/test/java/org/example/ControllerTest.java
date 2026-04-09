package org.example;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.*;
import org.example.db.CartService;
import org.example.db.LocalizationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerTest {

    private Controller controller;

    private CartService cartServiceMock;
    private LocalizationService localizationServiceMock;

    @BeforeAll
    static void initJavaFX() {
        new JFXPanel(); // IMPORTANT: initializes toolkit
    }

    @BeforeEach
    void setUp() {

        controller = new Controller();

        inject("languageBox", new ComboBox<>());
        inject("welcomeLabel", new Label());
        inject("instructionLabel", new Label());
        inject("labelQuantity", new Label());
        inject("labelPrice", new Label());
        inject("totalLabel", new Label());
        inject("infoLabel", new Label());

        inject("quantityField", new TextField());
        inject("priceField", new TextField());

        inject("addButton", new Button());
        inject("removeButton", new Button());
        inject("proceedButton", new Button());
        inject("cancelButton", new Button());

        cartServiceMock = mock(CartService.class);
        localizationServiceMock = mock(LocalizationService.class);

        inject("cartService", cartServiceMock);
        inject("localizationService", localizationServiceMock);

        when(localizationServiceMock.getStrings(anyString()))
                .thenReturn(Map.of(
                        "greet", "Hello",
                        "instruction", "Enter",
                        "prompt1", "Qty",
                        "prompt2", "Price",
                        "add", "Add",
                        "subs", "Remove",
                        "proceed", "Proceed",
                        "cancel", "Cancel",
                        "current", "Total",
                        "exit", "Done"
                ));

        controller.initialize();
    }

    @Test
    void testHandleAdd() {

        setText("quantityField", "2");
        setText("priceField", "10");

        controller.handleAdd();

        assertTrue(getLabel("totalLabel").contains("20.0"));
    }

    @Test
    void testHandleRemove() {

        setText("quantityField", "5");
        setText("priceField", "10");
        controller.handleAdd();

        setText("quantityField", "2");
        setText("priceField", "10");
        controller.handleRemove();

        assertTrue(getLabel("totalLabel").contains("30.0"));
    }

    @Test
    void testHandleCancel() {

        setText("quantityField", "3");
        setText("priceField", "10");
        controller.handleAdd();

        controller.handleCancel();

        assertTrue(getLabel("totalLabel").contains("0"));
    }

    @Test
    void testHandleProceed() {

        setText("quantityField", "2");
        setText("priceField", "10");

        when(cartServiceMock.saveCart(anyInt(), anyDouble(), anyString()))
                .thenReturn(100);

        controller.handleAdd();
        controller.handleProceed();

        verify(cartServiceMock).saveCart(eq(1), eq(20.0), eq("en"));
        verify(cartServiceMock).saveItem(eq(100), eq(1), eq(10.0), eq(2), eq(20.0));

        assertEquals("Done", getLabel("infoLabel"));
        assertTrue(getLabel("totalLabel").contains("0"));
    }

    // ---------------- helpers ----------------

    private void inject(String fieldName, Object value) {
        try {
            Field f = Controller.class.getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(controller, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setText(String fieldName, String value) {
        ((TextField) get(fieldName)).setText(value);
    }

    private String getLabel(String fieldName) {
        return ((Label) get(fieldName)).getText();
    }

    private Object get(String fieldName) {
        try {
            Field f = Controller.class.getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(controller);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}