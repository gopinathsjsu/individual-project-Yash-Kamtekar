package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Hello world!
 *
 */

public class RunClient {
    public static void main(String[] args) {
        Map<String, Flight> fligths = new HashMap<>();

        fligths.put("SJ456", new Flight("Seattle", "San Jose", "Economy", 5, 250.00));
        fligths.put("BY110", new Flight("San Francisco", "New York", "Premium Economy", 5, 500));
        fligths.put("CA453", new Flight("Seattle", "San Jose", "Business", 5, 1500));

        Flight obj = fligths.get("BY110");
        obj.updateSeatCount("Business", 5);
        obj.setPrice("Business", 2000);
        fligths.put("BY110", obj);

        obj = fligths.get("CA453");
        obj.updateSeatCount("Economy", 5);
        obj.setPrice("Economy", 300);
        fligths.put("CA453", obj);

        String path = "/Users/shkamtek/Desktop/Yash/";
        String line = "";

        // READ FLIGHT FILE

        // try {
        // BufferedReader br = new BufferedReader(new FileReader(path + "flights.csv"));

        // while((line = br.readLine()) != null) {
        // String[] values = line.split(",");
        // String category = values[0], flightNumber = values[1], arrivalCity =
        // values[4], departureCity = values[5];
        // int seats = Integer.parseInt(values[2]);
        // double price = Double.parseDouble(values[3]);

        // if (!fligths.containsKey(flightNumber)) {
        // fligths.put(flightNumber, new Flight(arrivalCity, departureCity, category,
        // seats, price));
        // }
        // else {
        // Flight obj = fligths.get(flightNumber);
        // obj.updateSeatCount(category, seats);
        // fligths.put(flightNumber, obj);
        // }
        // }

        // } catch (FileNotFoundException e) {
        // e.printStackTrace();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        // for(String key : fligths.keySet()) {
        // System.out.println(fligths.get(key).arrivalCity);
        // }

        // 2. VALIDATE IF THE FLIGHT EXISTS
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path + "Sample.csv"));
            // Sam,SJ456,Economy,2,5410000000000000

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String name = values[0], flightNumber = values[1], category = values[3], creditCard = values[5];
                int noOfSeats = Integer.parseInt(values[4]);
                // long creditCard = Long.parseLong(values[5]);

                if(!fligths.containsKey(flightNumber)) {
                    System.out.println(name + ": Flight does not exists");
                }
                else {
                    Flight f = fligths.get(flightNumber);

                    if(f.availableSeats.get(category) < noOfSeats) {
                        System.out.println(name + ": Not enough seats for this category");
                    }
                    else {
                        if(isCreditCardValid(creditCard) == false) {
                            System.out.println(name + ": Invalid Card");
                        }
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Boolean isCreditCardValid(String creditCard) {
        int n = creditCard.length();
        char firstDigit = creditCard.charAt(0), secondDigit = creditCard.charAt(1);

        // AMERICAN EXPRESS
        if(n == 15 && firstDigit == '3')
            return secondDigit == '4' || secondDigit == '7';

        else if(n == 16 && (firstDigit == '5' || firstDigit == '6')) {
            // DISCOVER
            if(creditCard.substring(0, 4).equals("6011"))
                return true;

            // MASTERCARD
            else if(secondDigit - '0' >= 1 && secondDigit - '0' <= 5)
                return true;

            return false;
        }
        // VISA
        else if(n >= 13 && n <= 16 && firstDigit == '4') {
            return true;
        }
        return false;
    }
}