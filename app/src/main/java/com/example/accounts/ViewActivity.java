package com.example.accounts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private static String POPUP_CONSTANT = "mPopup";
    private static String POPUP_FORCE_SHOW_ICON = "setForceShowIcon";
    boolean doubleBackToExitPressedOnce = false;
    private TextView wellcome;
    private AppBarLayout appBar;
    private String path;
    private String owner;
    private ManageUser mngUsr;
    private ArrayList<User> listUser = new ArrayList<>();
    private ManageApp mngApp;
    private LogApp log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        path = getIntent().getExtras().getString("path");
        owner = getIntent().getExtras().getString("owner");

        mngApp = new ManageApp();
        log = mngApp.deserializationFlag(path);

        mngUsr = new ManageUser();
        listUser = mngUsr.deserializationListUser(path);

        wellcome = findViewById(R.id.wellcome);
        wellcome.setText("Benvenuto " + owner);

        appBar = (AppBarLayout) findViewById(R.id.app_bar);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                wellcome.setAlpha((1.0f - (float) Math.abs(verticalOffset) / appBarLayout.getTotalScrollRange()));
                collapsingToolbarLayout.setTitle("Lista Account di " + owner);
                collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.transperent));
                collapsingToolbarLayout.setCollapsedTitleTextColor(Color.rgb(255, 255, 255));

            }
        });


        final FloatingActionButton setting = (FloatingActionButton) findViewById(R.id.action_settings);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(ViewActivity.this, v, Gravity.END, 0, R.style.rounded_menu_style);
                popup.setOnMenuItemClickListener(ViewActivity.this);
                popup.inflate(R.menu.popup);
                popup.show();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                // do your code
                return true;
            case R.id.sort:
                // do your code
                return true;
            case R.id.setting:
                // do your code
                return true;
            case R.id.exit:
                log = new LogApp();
                mngApp.serializationFlag(log, path);
                Intent intent = new Intent(ViewActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("path", path);
                intent.putExtra("owner", "");
                startActivity(intent);
                finish();
                return true;
            default:
                return false;
        }
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            System.exit(0);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Per favore clicca di nuovo BACK per uscire", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}


