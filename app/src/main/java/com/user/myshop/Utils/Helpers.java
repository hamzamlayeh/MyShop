package com.user.myshop.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;
import com.user.myshop.BoutiqueActivity;
import com.user.myshop.FavoriteActivity;
import com.user.myshop.LoginActivity;
import com.user.myshop.ProduitsActivity;
import com.user.myshop.ProfileActivity;
import com.user.myshop.R;

public class Helpers {

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void ShowMessageConnection(Context context) {
        Toast.makeText(context, R.string.chek_internet, Toast.LENGTH_SHORT).show();
    }

    public static void AddMenu(final Context context, BottomNavigationView bottomNavigationView) {
        BottomNavigationItem bottomNavigationItem = new BottomNavigationItem
                (context.getString(R.string.profil), ContextCompat.getColor(context, R.color.colorPrimary), R.drawable.ic_profile);
        BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem
                (context.getString(R.string.produits), ContextCompat.getColor(context, R.color.colorAccent), R.drawable.ic_allproduit);
        BottomNavigationItem bottomNavigationItem2 = new BottomNavigationItem
                (context.getString(R.string.favori), ContextCompat.getColor(context, R.color.colorAccent), R.drawable.ic_favorite);
        BottomNavigationItem bottomNavigationItem3 = new BottomNavigationItem
                (context.getString(R.string.ajouter_botique), ContextCompat.getColor(context, R.color.colorAccent), R.drawable.ic_addproduit);
        bottomNavigationView.addTab(bottomNavigationItem);
        bottomNavigationView.addTab(bottomNavigationItem1);
        bottomNavigationView.addTab(bottomNavigationItem2);
        bottomNavigationView.addTab(bottomNavigationItem3);
        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                switch (index) {
                    case 0:
                        context.startActivity(new Intent(context, ProfileActivity.class));
                        break;
                    case 1:
                        context.startActivity(new Intent(context, ProduitsActivity.class));
                        break;
                    case 2:
                        context.startActivity(new Intent(context, FavoriteActivity.class));
                        break;
                    case 3:
                        context.startActivity(new Intent(context, BoutiqueActivity.class));
                        break;
                }
            }
        });
    }
}
