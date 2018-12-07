package com.wodingo.shoppingbuddy.data;

import java.io.IOException;
import java.util.List;

//This is my interface that is implemented by AbstractShopping list with method signatures to carry out all of the
//functionality associated with the program such as reading and writing the list to a file and printing the list to the user.
public interface ShoppingList {

    void addItem(Item item);
    List<Item> getSortedList();
    void readFile(String fileName) throws IOException;
    void writeFile(String fileName) throws IOException;
    void printList();
    ShoppingList shop(double budget);
    double getTotalCost();

}
