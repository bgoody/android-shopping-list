package com.wodingo.shoppingbuddy.data;

import java.util.ArrayList;
import java.util.List;

public class BubbleSortShoppingList extends AbstractShoppingList {

    @Override
    public List<Item> getSortedList() {
        //Sort ArrayList
        boolean didSwap = true;
        while (didSwap) {
            didSwap = false;
            for (int i = 0; i < items.size()-1; i++) {
                Item left = items.get(i);
                Item right = items.get(i + 1);

                //Boolean logic that supports multiple priorities. First it compares the priority set by the user and then by the
                //lowest priced item.
                if (left.getPriority() > right.getPriority() ||
                        (left.getPriority() == right.getPriority() && left.getPrice() > right.getPrice())) {
                    didSwap = true;
                    items.set(i, right);
                    items.set(i + 1, left);
                }
            }
        }
        //Return a copy of the ArrayList
        return new ArrayList<>(items);
    }

}

