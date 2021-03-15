package com.mkozi;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

class Bird {
    public Bird() {

    }

    public void move() {
        System.out.println("The bird flies away");
    }
}

class Penguin extends Bird {
    public Penguin() {

    }

    @Override
    public void move() {
        System.out.println("The bird waddles away");
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        Penguin myBird = new Penguin();
        myBird.move();

        Date now = new Date();
        System.out.println(now);
        
        String message = "howdy";     
        
        int[] numbers = new int[5];
        numbers[0] = 1;
        numbers[2] = 2;
        System.out.println(Arrays.toString(numbers));

        int[] numbers2 = {5,4,3,2,1};
        System.out.println(numbers.length);
        System.out.println(Arrays.toString(numbers2));
        Arrays.sort(numbers2);
        System.out.println(Arrays.toString(numbers2));

        int[][] numbers3 = new int[2][3];
        numbers3[0][1] = 1;
        System.out.println(Arrays.deepToString(numbers3));

        int[][] numbers4 = { {1,2,3}, {4,5,6}};
        System.out.println(Arrays.deepToString(numbers4));

        // constants
        final float PI = (float) 3.14;

        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
        String result = currency.format(123345.689);
        System.out.println(result);

        NumberFormat percent = NumberFormat.getPercentInstance();
        String result2 = percent.format(0.145);
        System.out.println(result2);

        // reading data
        Scanner scanner = new Scanner(System.in);
        System.out.print("Age: ");
        byte age = scanner.nextByte();
        scanner.nextLine();
        System.out.println("You are " + age);

        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.println("You are " + name);

        scanner.close();
    }
}
