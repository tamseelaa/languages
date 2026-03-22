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
        assertEquals(10.0, calc.total(2, 5.0));
        assertEquals(0.0, calc.total(0, 100.0));
        assertEquals(7.5, calc.total(3, 2.5));
    }

    @Test
    void testAdd() {
        double result1 = calc.add(2, 5.0); // 10
        assertEquals(10.0, result1);

        double result2 = calc.add(1, 3.0); // 13
        assertEquals(13.0, result2);
    }

    @Test
    void testRemove() {
        calc.add(2, 5.0); // 10

        double result = calc.remove(1, 5.0); // 5
        assertEquals(5.0, result);
    }

    @Test
    void testSequenceOperations() {
        calc.add(2, 5.0);   // 10
        calc.add(1, 2.0);   // 12
        calc.remove(1, 5.0);// 7

        assertEquals(7.0, calc.getCurrent());
    }

    @Test
    void testNegativeTotalStillPossible() {
        // exposing a remaining design flaw
        double result = calc.remove(1, 10.0);
        assertEquals(-10.0, result);
    }
}