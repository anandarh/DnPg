package com.anandarherdianto.dinaspangan.utilitas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.anandarherdianto.dinaspangan.R;

/**
 * Created by Ananda R. Herdianto on 13/04/2017.
 * Done
 */

public class NitrogenImageDialogBox extends DialogFragment {

    private LayoutInflater inflater;
    private View v;

    private RelativeLayout camera, gallery;

    private MyInterface mListener;

    private String code;

    public void setCode(String code) {
        this.code = code;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.nitrogen_dialogbox, null);

        camera = (RelativeLayout)v.findViewById(R.id.dialogBtnCamera);
        gallery = (RelativeLayout)v.findViewById(R.id.dialogBtnGallery);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v).setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        final Dialog dialog = builder.create();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(code == "get1") {
                    mListener.camera1();
                    dialog.dismiss();
                } else if(code == "get2") {
                    mListener.camera2();
                    dialog.dismiss();
                } else if(code == "get3") {
                    mListener.camera3();
                    dialog.dismiss();
                } else if(code == "get4") {
                    mListener.camera4();
                    dialog.dismiss();
                } else if(code == "get5") {
                    mListener.camera5();
                    dialog.dismiss();
                } else if(code == "get6") {
                    mListener.camera6();
                    dialog.dismiss();
                }
            }
        });


        return dialog;
    }

    public interface MyInterface{
        void camera1();
        void camera2();
        void camera3();
        void camera4();
        void camera5();
        void camera6();
    }

    @Override
    public void onAttach(Activity activity) {
        mListener = (MyInterface) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }
}
