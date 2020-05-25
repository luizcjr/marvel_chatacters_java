package com.luiz.marvelcharacters.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.luiz.marvelcharacters.AppApplication;
import com.luiz.marvelcharacters.R;
import com.luiz.marvelcharacters.ui.adapters.NoResultAdapter;
import com.luiz.marvelcharacters.ui.custom_view.AlertDefault;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class Utils {
    private static Dialog dialog;

    public static void onStopLoading() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static void onStartLoading(Context context) {
        dialog = loadingDialog(context);
        dialog.show();
    }

    public static SharedPreferences getSessionPreferences() {
        Context ctx = AppApplication.getInstance();
        return ctx.getSharedPreferences("SESSION_PREFERENCES", ctx.MODE_PRIVATE);
    }

    @SuppressLint("ObsoleteSdkInt")
    public static Dialog loadingDialog(final Context ctx) {
        Dialog loading = new Dialog(ctx);
        loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loading.setContentView(R.layout.dialog_loading);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(loading.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        loading.setCanceledOnTouchOutside(false);
        loading.setCancelable(false);
        return loading;
    }

    public static void startActivity(Context context, Class redirect) {
        Intent intent = new Intent(context, redirect);
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(context, R.anim.activity_slide_pop_vertical_open_in, R.anim.activity_slide_pop_vertical_open_out);
        context.startActivity(intent, options.toBundle());
    }

    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void alert(Context ctx, String title, String message) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ctx);
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(Html.fromHtml(message));
        alertDialog.show();
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                AppApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void putPref(String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AppApplication.getInstance());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getPref(String key, String defValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AppApplication.getInstance());
        return preferences.getString(key, defValue);
    }

    public static void removePref(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AppApplication.getInstance());
        SharedPreferences.Editor ed = prefs.edit();
        ed.remove(key);
        ed.apply();
    }

    public static Double unformatBRL(String number) {
        String str = number.replaceAll("\\D", "");
        str = str == "" ? "0" : str;
        return Double.valueOf(str);
    }

    public static String formatBRL(Double number) {
        Locale ptBr = new Locale("pt", "BR");
        return NumberFormat.getCurrencyInstance(ptBr).format(number);
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertDateFormat(String date, String initDateFormat, String endDateFormat) {
        try {
            Date initDate = new SimpleDateFormat(initDateFormat).parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
            return formatter.format(initDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Erro ao obter data";
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertDateBrl(String date) {
        try {
            Date initDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return formatter.format(initDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Erro ao obter data";
        }
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void alertError(Context context, String message) {
        AlertDefault alertDefault = new AlertDefault(context, "Erro!", message, true);
        alertDefault.show();
    }

    public static String getMessageErrorObject(Throwable e) {
        String loadError = "";

        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            try {
                JSONObject jsonObject = new JSONObject(responseBody.string());
                loadError = jsonObject.getString("message");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else if (e instanceof SocketTimeoutException) {
            loadError = e.getMessage();
        } else if (e instanceof IOException) {
            loadError = e.getMessage();
        } else {
            loadError = e.getMessage();
        }

        return loadError;
    }

    public static NoResultAdapter noResultAdapter(Context context, String message, int image) {
        return new NoResultAdapter(context, message, R.color.colorGreyAdapter, image, 0);
    }

    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1)
                    hexString.append('0');

                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
