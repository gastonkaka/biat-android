package com.tn.biat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.tn.biat.Biat;
import com.tn.biat.R;

import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by mednaceur on 25/04/2017.
 */

public class Outils {

    private  static SweetAlertDialog alertDialog;

    public  static void  startLoading(Context ctx){
        Outils.alertDialog= new SweetAlertDialog(ctx, SweetAlertDialog.PROGRESS_TYPE);
        Outils.alertDialog.getProgressHelper().setBarColor(Color.parseColor("#f28922"));
        Outils.alertDialog.setTitleText("Chargement ...");
        Outils.alertDialog.setCancelable(false);
        Outils.alertDialog.show();
    }
    public static void stopLoading(){
        Outils.alertDialog.dismissWithAnimation();
    }
    public static void switchLoadingToError(String title,String content){
        Outils.alertDialog.setTitleText(title)
                .setContentText(content)
                .setConfirmText("OK")
                .setConfirmClickListener(null)
                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
    }
    public static void switchLoadingToSuccess(String title,String content){
        Outils.alertDialog.setTitleText(title)
                .setContentText(content)
                .setConfirmText("OK")
                .setConfirmClickListener(null)
                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
    }
    public static void alertError(String title,String content){
        Outils.alertDialog=new SweetAlertDialog(Biat.getAppContext(),SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(content);
        Outils.alertDialog.show();
    }
    public static void alertSuccess(String title,String content){
        Outils.alertDialog=new SweetAlertDialog(Biat.getAppContext(),SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(content);
        Outils.alertDialog.show();
    }
    public static void alertWarning(String title,String content){
        Outils.alertDialog=new SweetAlertDialog(Biat.getAppContext(),SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content);
        Outils.alertDialog.show();
    }
    public static void alertNormale(String title,String content){
        Outils.alertDialog=new SweetAlertDialog(Biat.getAppContext(),SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(title)
                .setContentText(content);
        Outils.alertDialog.show();
    }
    public static void alertCustum(String title,String content,int resource){
        Outils.alertDialog=new SweetAlertDialog(Biat.getAppContext(),SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setCustomImage(resource)
                .setTitleText(title)
                .setContentText(content);
        Outils.alertDialog.show();
    }



    public static void saveUser(JSONObject user){
        SharedPreferences settings=Biat.getAppContext().getSharedPreferences(DataApplication.PREFS_NAME,0);
        SharedPreferences.Editor editor=settings.edit();
        editor.putString("login",user.toString());
        editor.commit();
    }
    public static String getSavedUser(){
        SharedPreferences sttings=Biat.getAppContext().getSharedPreferences(DataApplication.PREFS_NAME,0);
        return sttings.getString("login",null);
    }
    public static void deleteSavedUser(){
        SharedPreferences settings=Biat.getAppContext().getSharedPreferences(DataApplication.PREFS_NAME,0);
        SharedPreferences.Editor editor=settings.edit();
        editor.remove("login");
        editor.commit();
    }
    public static boolean isUserExist(){
        SharedPreferences settings=Biat.getAppContext().getSharedPreferences(DataApplication.PREFS_NAME,0);
        return settings.contains("login");
    }


}
