package com.example.simpletodolist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class TodoActivity extends Activity {
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	EditText etNewItem;
	public final int REQUEST_CODE = 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		lvItems = (ListView) findViewById(R.id.lvItems);
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		readItems();
		itemsAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
		setUpListViewListener();
	}

	private void setUpListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				writeItems();
				return true;
			}			
		});
		
		lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				launchEditItemView(position);
			}
		});
	}
	
	private void launchEditItemView(int index) {
		Intent editActivityIntent = new Intent(TodoActivity.this, EditItemActivity.class);
		editActivityIntent.putExtra("itemName", items.get(index));
		editActivityIntent.putExtra("itemIndex", index);
		startActivityForResult(editActivityIntent, REQUEST_CODE);
	}

	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch(IOException e) {
			items = new ArrayList<String>();
		}
	}
	
	private void writeItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, items);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void onAddedItem(View v) {
		String itemText = etNewItem.getText().toString();
		itemsAdapter.add(itemText);
		etNewItem.setText("");
		writeItems();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			String editedItem = data.getExtras().getString("editedItem");
			int editedItemPosition = data.getExtras().getInt("editedItemPosition");
			items.set(editedItemPosition, editedItem);
			itemsAdapter.notifyDataSetChanged();
			writeItems();
		}
	}
}
