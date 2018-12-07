package com.wodingo.shoppingbuddy.data;

public class Item {

    private String name;
    private double price;
    private int priority;
    private int quantity;

    public Item (String name, double price, int priority, int quantity) {

        setName(name);
        setPrice(price);
        setPriority(priority);
        setQuantity(quantity);
    }

    public String getName() {
        return name;
    }

    //Sets name and throws an exception from the ValidationException class if it is empty or contains a comma.
    //I check for a comma because I use it as a delimiter in other methods.
    public void setName(String name) {
        if (name == null || name.isEmpty() || name.contains(",")) {
            throw new ValidationException();
        }
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    //Sets the price and throws an exception from my ValidationException class in the event that the price is less than 0
    public void setPrice(double price) {
        if (price < 0) {
            throw new ValidationException();
        }
        this.price = price;
    }

    public int getPriority() {
        return priority;
    }

    //Sets the priority and makes sure it is an int 1 through 5, else it throws an object from my ValidationException class.
    public void setPriority(int priority) {
        if (priority < 1 || priority > 5) {
            throw new ValidationException();
        }
        this.priority = priority;
    }
    public int getQuantity() {
        return quantity;
    }

    //Sets the quantity and makes sure it is not less than one, else it throws an object from my ValidationException class.
    public void setQuantity(int quantity) {
        if (quantity < 1) {
            throw new ValidationException();
        }
        this.quantity = quantity;
    }

}

