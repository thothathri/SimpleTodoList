package com.example.simpletodolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {

	EditText etExistingItem;
	int itemIndex;
	String itemName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		etExistingItem = (EditText) findViewById(R.id.etExistingItem);		
		itemName = getIntent().getStringExtra("itemName");
		itemIndex = getIntent().getIntExtra("itemIndex", 0);
		etExistingItem.setText(itemName);
		etExistingItem.requestFocus();
		etExistingItem.setSelection(itemName.length());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onEditSubmit(View v) {
		String itemText = etExistingItem.getText().toString();
		Intent data = new Intent();
		data.putExtra("editedItem", itemText);
		data.putExtra("editedItemPosition", itemIndex);
		setResult(RESULT_OK, data);
		finish();
	}
}
