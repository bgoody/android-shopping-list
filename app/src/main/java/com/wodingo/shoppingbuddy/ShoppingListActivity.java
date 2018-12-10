package com.wodingo.shoppingbuddy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wodingo.shoppingbuddy.adapters.ShoppingListAdapter;
import com.wodingo.shoppingbuddy.data.BubbleSortShoppingList;
import com.wodingo.shoppingbuddy.data.Item;
import com.wodingo.shoppingbuddy.data.ShoppingList;
import com.wodingo.shoppingbuddy.data.ValidationException;
import com.wodingo.shoppingbuddy.dialogs.AddItemDialog;

import java.io.File;
import java.io.IOException;

public class ShoppingListActivity extends AppCompatActivity implements AddItemDialog.AddItemListener {

    private ShoppingList shoppingList;
    private ShoppingListAdapter shoppingListadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shoppingList = new BubbleSortShoppingList();

        openList();

        shoppingListadapter = new ShoppingListAdapter(shoppingList);

        RecyclerView shoppingListView = findViewById(R.id.shoppingList);

        //The Layout Manager is responsible for the actual layout of the children within the
        //RecyclerView. RecyclerView is responsible for presenting a list of items. You can use
        //a GridManager if you wanted a grid.
        shoppingListView.setLayoutManager(new LinearLayoutManager(this));

        shoppingListView.setAdapter(shoppingListadapter);

        FloatingActionButton addButton = findViewById(R.id.fab);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddItemDialog().show(getSupportFragmentManager(), "AddItem");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_shop) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addItem(String name, double price, int quantity, int priority) {
        try {
            shoppingList.addItem(new Item(name, price, priority, quantity));
            saveList();
            shoppingListadapter.notifyDataSetChanged();
        }catch (ValidationException e){
            Toast.makeText(this, "Invalid Data!", Toast.LENGTH_SHORT).show();
        }

    }

    private void saveList(){
        try {
            shoppingList.writeFile(getSavedFile());
        } catch (IOException e) {
            Toast.makeText(this, "Error saving file!", Toast.LENGTH_SHORT).show();
        }
    }
    private File getSavedFile (){
        return new File(getFilesDir(), "shoppingList.txt");
    }
    private void openList(){
        try {
            shoppingList.readFile(getSavedFile());
            Toast.makeText(this, "Loaded previous list!", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Toast.makeText(this, "Error reading file!", Toast.LENGTH_SHORT).show();
        }

    }
}
