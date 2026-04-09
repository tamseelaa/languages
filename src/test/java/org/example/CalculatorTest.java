package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calc;

    @BeforeEach
    void setUp() {
        calc = new Calculator();
    }

    @Test
    void testTotal() {
        assertEquals(50.0, calc.total(5, 10.0));
        assertEquals(0.0, calc.total(0, 100.0));
    }

    @Test
    void testAdd() {
        double result = calc.add(2, 10.0); // 20
        assertEquals(20.0, result);

        result = calc.add(3, 5.0); // +15 → 35
        assertEquals(35.0, result);
    }

    @Test
    void testRemove() {
        calc.add(5, 10.0); // 50

        double result = calc.remove(2, 10.0); // -20 → 30
        assertEquals(30.0, result);

        result = calc.remove(3, 10.0); // -30 → 0
        assertEquals(0.0, result);
    }

    @Test
    void testReset() {
        calc.add(5, 10.0);
        calc.reset();
        assertEquals(0.0, calc.getCurrent());
    }

    @Test
    void testGetCurrent() {
        assertEquals(0.0, calc.getCurrent());

        calc.add(1, 25.0);
        assertEquals(25.0, calc.getCurrent());
    }

    @Test
    void testNegativeValues() {
        // You didn’t validate input, so this is allowed (questionable design)
        double result = calc.add(-2, 10.0); // -20
        assertEquals(-20.0, result);

        result = calc.remove(-1, 10.0); // +10 → -10
        assertEquals(-10.0, result);
    }
}