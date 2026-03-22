package org.example;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Calculator {

    private double current = 0;

    public double total(int quantity, double price) {

        return quantity * price;
    }

    public double add(int quantity, double price) {
        double ad = total(quantity, price);
        current = current + ad;
        return current;
    }

    public double remove(int quantity, double price) {
        double rm = total(quantity, price);
        current = current - rm;
        return current;
    }

    public double getCurrent() {
        return current;
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();

        Scanner input = new Scanner(System.in);

        System.out.print("1: English\n");
        System.out.print("2: Finnish\n");
        System.out.print("3: Swedish\n");
        System.out.print("4: Japanese\n");
        System.out.print("5: Urdu\n");
        System.out.print("Enter your language number: ");

        Locale loca = null;
        int choice = input.nextInt();

        switch (choice) {
            case 1:
                loca = new Locale("en", "US");
                break;
            case 2:
                loca = new Locale("fi", "FI");
                break;
            case 3:
                loca = new Locale("sv", "SE");
                break;
            case 4:
                loca = new Locale("ja", "JP");
                break;
            case 5:
                loca = new Locale("ur", "PK");
                break;
            default:
                loca = new Locale("en", "US");
        }

        ResourceBundle rb = ResourceBundle.getBundle("MessagesBundle", loca);

        boolean leave = false;

        System.out.println(rb.getString("wish"));
        System.out.println(rb.getString("greet"));

        while (!leave) {

            System.out.println("1: " + rb.getString("add"));
            System.out.println("2: " + rb.getString("subs"));
            System.out.println("3: " + rb.getString("proceed"));
            System.out.println("4: " + rb.getString("cancel"));

            int stage = input.nextInt();

            switch (stage) {

                case 1:
                    System.out.println(rb.getString("instruction"));

                    System.out.println(rb.getString("prompt1"));
                    int quantity = input.nextInt();

                    System.out.println(rb.getString("prompt2"));
                    double price = input.nextDouble();

                    double added = calc.add(quantity, price);
                    System.out.println(rb.getString("current") + ": " + added);
                    break;

                case 2:
                    System.out.println(rb.getString("instruction"));

                    System.out.println(rb.getString("prompt1"));
                    int quantitys = input.nextInt();

                    System.out.println(rb.getString("prompt2"));
                    double prices = input.nextDouble();

                    double removed = calc.remove(quantitys, prices);
                    System.out.println(rb.getString("current") + ": " + removed);
                    break;

                case 3:
                    System.out.println(rb.getString("proceed"));
                    System.out.println(rb.getString("current") + ": " + calc.getCurrent());
                    System.out.println(rb.getString("exit"));
                    leave = true;
                    break;

                case 4:
                    leave = true;
                    System.out.println(rb.getString("cancel"));
                    System.out.println(rb.getString("exit"));
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }

        input.close();
    }
}