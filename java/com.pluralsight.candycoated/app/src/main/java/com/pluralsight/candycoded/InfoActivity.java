package com.pluralsight.candycoded;

import android.net.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import roidx.appcompat.app.*;
import com.squareup.picasso.*;

public class InfoActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, EditPreferences.class));
                ret        }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Uri uri = Uri.parse("android.resource://com.codeschool.candycoded/" + R.drawable.store_front);
        ImageView candyStoreImageView = (ImageView)findViewById(R.id.image_view_candy_store);
        Picasso.with(this).
                load(uri).
                into(candyStoreImageView);


    }

    // ***
    // TODO - Task 2 - Launch the Google Maps Activity
    // ***

    // ***
    // TODO - Task 3 - Launch the Phone Activity
    // ***

}
