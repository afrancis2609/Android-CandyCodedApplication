package com.pluralsight.candycoded;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.google.gson.*;
import com.loopj.android.http.*;
import com.pluralsight.candycoded.DB.*;
import cz.msebera.android.httpclient.*;

public class MainActivity extends AppCompatActivity {
  private Candy[] candies;
  private CandyDbHelper candyDbHelper = new CandyDbHelper(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SQLiteDatabase db = candyDbHelper.getWritableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM candy", null);

    final CandyCursorAdapter adapter = new CandyCursorAdapter(this, cursor);
    ListView listView = (ListView)this.findViewById(R.id.list_view_candy);

    listView.setAdapter(adapter);

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
        detailIntent.putExtra("position", i);
        startActivity(detailIntent);
      }
    });

    AsyncHttpClient client = new AsyncHttpClient();
    client.get("https://vast-brushlands-23089.herokuapp.com/main/api",
        new TextHttpResponseHandler() {
          @Override
          public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
            Log.e("AsyncHttpClient", "response = " + response);
          }

          @Override
          public void onSuccess(int statusCode, Header[] headers, String response) {
            Log.d("AsyncHttpClient", "response = " + response);
            Gson gson = new GsonBuilder().create();;
            candies = gson.fromJson(response, Candy[].class);

            addCandiesToDatabase(candies);

            SQLiteDatabase db = candyDbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM candy", null);
            //adapter.changeCursor(cursor);
          }
        });
  }
 
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main, menu);
    return true;
  }
  // ***
  // TODO - Task 1 - Show Store Information Activity
  // ***
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId() == R.id(menu_refresh));
          menuRefreshSelectedFromAnnotatedClass =true;

    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
          case R.id.settings:
              startActivity(new Intent(this, EditPreferences.class));
              return (true);
      }
  return(super.onOptionsItemSelected(item));

  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
	  Intent infoIntent = new Intent(this, InfoActivity.class);
		startActivity(infoIntent);
  return super.onOptionsItemSelected(item);
	}
	// ***
	
  private void addCandiesToDatabase(Candy[] candies) {
     SQLiteDatabase db = candyDbHelper.getWritableDatabase();

     for (Candy candy : candies) {
       ContentValues values = new ContentValues();
       values.put(CandyEntry.COLUMN_NAME_NAME, candy.name);
	   values.put(CandyEntry.COLUMN_NAME_PRICE, candy.price);
	   values.put(CandyEntry.COLUMN_NAME_DESC, candy.description);
	   values.put(CandyEntry.COLUMN_NAME_IMAGE, candy.image);
       db.insert(CandyContract.CandyEntry.TABLE_NAME, null, values);
    }
  }
}
