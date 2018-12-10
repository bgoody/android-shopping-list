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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wodingo.shoppingbuddy.R;
import com.wodingo.shoppingbuddy.adapters.ShoppingListAdapter;
import com.wodingo.shoppingbuddy.data.ShoppingList;

import java.util.Objects;

public class ResultsDialog extends DialogFragment {
    private ResultsListener listener;

    public interface ResultsListener {
        ShoppingList getResults();
        double getRemainingBudget();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ResultsListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        ShoppingList results = listener.getResults();
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        if (results == null){
            dismiss();
            return builder.create();
        }
        @SuppressLint("InflateParams") View view = getActivity().getLayoutInflater().inflate(R.layout.shopping_results_dialog, null);
        TextView remainingView = view.findViewById(R.id.remainingView);
        remainingView.setText(String.valueOf(listener.getRemainingBudget()));
        TextView spentView = view.findViewById(R.id.spentView);
        spentView.setText(String.valueOf(results.getTotalCost()));
        RecyclerView resultsView = view.findViewById(R.id.resultsView);
        resultsView.setLayoutManager(new LinearLayoutManager(getContext()));
        resultsView.setAdapter(new ShoppingListAdapter(results));
        builder.setView(view);
        builder.setTitle(R.string.shopping_results);
        builder.setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ResultsDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }
}
