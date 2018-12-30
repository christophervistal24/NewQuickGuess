package com.example.forest.quickguess;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class SwitchDialog extends AppCompatDialogFragment {
    private EditText userName;
    private EditText userPin;
    private SwitchDialogListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater  = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        userName = view.findViewById(R.id.userName);
        userPin = view.findViewById(R.id.userPin);
        builder.setView(view)
                .setPositiveButton("Switch", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = userName.getText().toString();
                        String user_pin = userPin.getText().toString();
                        listener.applyText(username,user_pin);
                    }
                });
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (SwitchDialogListener) context;
        } catch(Exception e) {
           throw new ClassCastException(context.toString() + "Must implement SwitchDialogListener");
        }
    }

    public interface SwitchDialogListener
    {
        void applyText(String username, String password);
    }
}
