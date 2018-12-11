package com.wodingo.shoppingbuddy.data;

import android.annotation.SuppressLint;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public abstract class AbstractShoppingList implements ShoppingList {

    //Creates the ArrayList of type generic Items. I didn't make it private because I wanted to make sure it was available within the class,
    //from the same package, and from any class inheriting from this class
    protected ArrayList<Item> items = new ArrayList<>();

    //adds an item to the list
    @Override
    public void addItem(Item item) {
        //Check for duplicates
        for (Item aItem : items) {
            if (aItem.getName().equals(item.getName())) {
                throw new ValidationException("That item is already on your list!");
            }
        }
        items.add(item);
    }

    //Prints the list
    @Override
    public void printList() {
        for (Item item : getSortedList()) {
            System.out.println(item);
        }

    }

    @Override
    public int getCount() {
        return items.size();
    }

    //Reads the list and places it into fileName which is used as the value of shoppingList.txt
    //Throws an IO exception if it can't do it.
    @Override
    public void readFile(File file) throws IOException {
        //Attempt to read the file and assign each field to line
        try (FileReader reader = new FileReader(file); BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitLine = line.split(",");//Delimits the fields by a comma, which is why I validate the input for commas
                Item item = new Item(splitLine[0], Double.parseDouble(splitLine[1]), Integer.parseInt(splitLine[2]),
                        Integer.parseInt(splitLine[3]));
                addItem(item);
            }

        }

    }

    //Writes the file to fileName and then clears the buffer/memory with the flush method
    @Override
    public void writeFile(File file) throws IOException {
        try (PrintStream writer = new PrintStream(file)) {
            for (Item item : items) {
                @SuppressLint("DefaultLocale") String line = String.format("%s,%f,%d,%d", item.getName(), item.getPrice(), item.getPriority(), item.getQuantity());
                writer.println(line);
            }
            writer.flush();
        }

    }

    //This is by far my most complicated method. It carries out the shopping function after the sorted list is set and then makes
    //purchases based on the priority the item was given by the user. If the priorities are the same it further prioritizes by the lower cost
    //item. A cool piece of functionality that I added was to purchase less than the quantity provide of an item if the budget didn't allow
    //purchasing them all. Whatever items were not purchased remain in the shoppingList.txt file for future purchase.
    @Override
    public ShoppingList shop(double budget) {
        double spent = 0;
        ShoppingList purchasedItems = new BubbleSortShoppingList();
        for (Item item : getSortedList()) {
            if(spent + item.getPrice() * item.getQuantity() <= budget) {
                items.remove(item);
                purchasedItems.addItem(item);
                spent += item.getPrice() * item.getQuantity();
            }else {
                budget -= spent;
                int num = (int)(budget/item.getPrice());
                item.setQuantity(item.getQuantity() - num);
                if (num > 0) {
                    //The item was purchased so we need to add something to purchased items.
                    //We have to create a new item object with the same name, price and priority as the
                    //original, and where the quantity (num) is the quantity purchased.
                    purchasedItems.addItem(new Item(item.getName(),item.getPrice(),item.getPriority(),num));
                }
                break;
            }
        }
        return purchasedItems;
    }

    //Calculates the total cost of the items
    @Override
    public double getTotalCost() {
        double total = 0;
        //For every Item in items multiply the price by the quantity and add it to total
        for(Item item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

}

