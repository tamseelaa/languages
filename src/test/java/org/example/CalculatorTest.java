package org.example;

import org.example.Calculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void testAdd_positiveNumbers() {
        Calculator calc = new Calculator();
        assertEquals(5, calc.add(2, 3));
    }

    @Test
    void testAdd_negativeNumbers() {
        Calculator calc = new Calculator();
        assertEquals(-5, calc.add(-2, -3));
    }

    @Test
    void testAdd_mixedNumbers() {
        Calculator calc = new Calculator();
        assertEquals(1, calc.add(-2, 3));
    }

    @Test
    void testAdd_withZero() {
        Calculator calc = new Calculator();
        assertEquals(3, calc.add(3, 0));
    }
}