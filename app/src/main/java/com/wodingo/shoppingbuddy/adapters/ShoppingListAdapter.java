package com.wodingo.shoppingbuddy.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wodingo.shoppingbuddy.R;
import com.wodingo.shoppingbuddy.data.Item;
import com.wodingo.shoppingbuddy.data.ShoppingList;

import java.text.NumberFormat;
//The adapter is what is used to display the items within the RecyclerView.
//The RecyclerView is a view that is a class in a library written by Google.
//It is intended to display a list of some sort in an efficient manner.
public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ItemViewHolder> {

    private ShoppingList shoppingList;

    public ShoppingListAdapter(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //LayoutInflater takes the view xml file in layout and creates the view described in the file
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shopping_list_item,viewGroup,false);
        return new ItemViewHolder(v);
    }

    @Override
    //This is the meat of the adapter where we take the view and update the text views that are in it
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        //Get the ith element from the shopping list
        Item item = shoppingList.getSortedList().get(i);

        itemViewHolder.itemName.setText(item.getName());
        itemViewHolder.itemPrice.setText(NumberFormat.getCurrencyInstance().format(item.getPrice()));
        itemViewHolder.itemQuantity.setText(String.valueOf(item.getQuantity()));
    }

    @Override
    //Gets number of items in the list
    public int getItemCount() {
        return shoppingList.getCount();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView itemPrice;
        public TextView itemQuantity;

        //Provides easy access to the bits that we are going to change
        //within the particular views (name, price, quantity)
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
        }
    }
}
