package com.clevercards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class SignOutManager {
    public static final int SIGNED_OUT = -1;

    public static void showSignOutDialog(Activity activity, String extra) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder.setMessage("Are you sure you want to sign out?")
                .setTitle(R.string.confirm_sign_out)
                .setIcon(R.drawable.sign_out_icon)
                .setPositiveButton("Sign Out", (dialog, which) -> {
                    signOut(activity, extra);
                    Toast.makeText(activity, "Signed out!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private static void signOut(Activity activity, String extra) {
        // Update shared preferences
        SharedPreferences sharedPreferences = activity
                .getApplicationContext()
                .getSharedPreferences(activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        sharedPreferences.edit()
                .putInt(activity.getString(R.string.preference_userId_key), SIGNED_OUT)
                .apply();

        Intent intent = SignInActivity.signInIntentFactory(activity.getApplicationContext());
        intent.putExtra(extra, SIGNED_OUT);

        activity.startActivity(intent);
        activity.finish();
    }
}

