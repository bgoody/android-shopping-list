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

public class BudgetDialog extends DialogFragment {
    private EditText budgetInput;
    private BudgetListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        @SuppressLint("InflateParams") View view = getActivity().getLayoutInflater().inflate(R.layout.shopping_dialog, null);
        budgetInput = view.findViewById(R.id.budgetInput);
        builder.setTitle(R.string.enter_budget);
        builder.setView(view);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BudgetDialog.this.getDialog().cancel();
            }
        });
        builder.setPositiveButton(R.string.buy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //check for empty fields
                try {
                    listener.setBudget(Double.parseDouble(budgetInput.getText().toString()));
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), getString(R.string.fields_required), Toast.LENGTH_LONG).show();
                }
            }
        });
        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Whenever an activity shows this poarticular fragment it needs to implement AddItemListener because
        //otherwise the cast will fail
        listener = (BudgetListener) context;
    }

    public interface BudgetListener {
        void setBudget(double budget);
    }

}
