package com.wodingo.shoppingbuddy.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wodingo.shoppingbuddy.R;

import java.util.Objects;

public class AddItemDialog extends DialogFragment {

    private EditText itemNameInput;
    private EditText itemPriceInput;
    private EditText itemQuantityInput;
    private EditText itemPriorityInput;

    private AddItemListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Whenever an activity shows this poarticular fragment it needs to implement AddItemListener because
        //otherwise the cast will fail
        listener = (AddItemListener) context;
    }

    public interface AddItemListener {
        void addItem(String name, double price, int quantity, int priority);
    }

    @NonNull
    @Override
    //Begins when the dialog is shown
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        @SuppressLint("InflateParams") View view = getActivity().getLayoutInflater().inflate(R.layout.add_item_dialog, null);
        itemNameInput = view.findViewById(R.id.itemName);
        itemPriceInput = view.findViewById(R.id.itemPrice);
        itemQuantityInput = view.findViewById(R.id.itemQuantity);
        itemPriorityInput = view.findViewById(R.id.itemPriority);
        builder.setView(view);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddItemDialog.this.getDialog().cancel();
            }
        });
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Check for empty fields
                try {
                    listener.addItem(
                            itemNameInput.getText().toString(),
                            Double.parseDouble(itemPriceInput.getText().toString()),
                            Integer.parseInt(itemQuantityInput.getText().toString()),
                            Integer.parseInt(itemPriorityInput.getText().toString())
                    );
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), getString(R.string.fields_required), Toast.LENGTH_LONG).show();
                }
            }
        });
        return builder.create();
    }
}
