package at.csiber.activityrecorder;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

public class CheckPasswordFragment extends DialogFragment {

    public interface CheckPasswordListener {
        public void onDialogPositiveClick(DialogFragment dialog);
    }

    CheckPasswordListener mListener;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (CheckPasswordListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement CheckPasswordListener");
        }
    }

    public static String TAG = "CheckPassword";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View content = inflater.inflate(R.layout.checkpassword, null);
        builder.setView(content);
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText pwdTxt = (EditText) content.findViewById(R.id.pwd);
                //TODO make password configurable in the resource file
                if (pwdTxt.getText().toString().equals("1234"))
                    mListener.onDialogPositiveClick(CheckPasswordFragment.this);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.checkpassword, container);
        EditText editText = (EditText) view.findViewById(R.id.pwd);

        //Request focus. The keyboard should show up when the fragment starts.
        editText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return view;
    }
}
