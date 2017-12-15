package com.karbowy.samples.overflowmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.karbowy.overflowmenu.OverflowMenu;
import com.karbowy.samples.R;

public class OverflowMenuActivity extends AppCompatActivity implements OverflowMenu.OnOverflowMenuItemSelectedListener {
    private OverflowMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overflow_menu);

        initOverflowMenu();

    }

    private void initOverflowMenu() {
        menu = new OverflowMenu(this, (ViewGroup) getWindow().getDecorView());
        menu.setMenuView(R.layout.overflow_menu_main, 150);
        menu.setAnimationDuration(200);
        menu.setMenuItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.overflow_menu:
                menu.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onOverflowMenuItemSelected(int itemId) {
        Toast.makeText(this, "clicked " + itemId, Toast.LENGTH_SHORT).show();

        switch (itemId) {
            case R.id.menu_item1:
                break;
           //etc
        }

        return true;
    }
}