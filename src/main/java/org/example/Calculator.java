package org.example;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    public static void main(String[] args) {

        String lang="ur";
        String country="PK";
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your language number: ");
        System.out.print("1: English");
        System.out.println("2: Finnish");
        System.out.print("3: Vitnamies");
        System.out.print("4: Urdu");
        Locale loca = null;
        int choice =  input.nextInt();
        switch (choice) {
            case 1:
                loca=new Locale("en","US");
                break;
            case 2:
                loca=new Locale("fi","FI");
                break;
            case 3:
                loca=new Locale("vn","VN");
                break;
            case 4:
                loca=new Locale("ur","PK");
                break;
        }

        ResourceBundle rb=ResourceBundle.getBundle("MessagesBundle",loca);
        String message=rb.getString("wish");
        System.out.println(message);

    }
}
