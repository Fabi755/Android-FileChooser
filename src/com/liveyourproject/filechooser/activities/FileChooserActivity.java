/**
 * Copyright 2015 LiveYourProject
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package com.liveyourproject.filechooser.activities;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.liveyourproject.filechooser.R;
import com.liveyourproject.filechooser.adapters.FileAdapter;
import com.liveyourproject.filechooser.filters.DataFileFilter;
import com.liveyourproject.filechooser.filters.DirectoryFilter;
import com.liveyourproject.filechooser.listeners.FileListClickListener;

/**
 * Activity for select a file or a directory.
 * <p>
 * 		For selecting define the selection type use {@link FileChooserActivity#INTENT_SELECTION_TYPE_KEY} as intent extra key.<br />
 * 		If you wish to select a file use {@link FileChooserActivity#INTENT_SELECTION_TYPE_FILE_VALUE} as extra value. 
 * 		For select a directory use {@link FileChooserActivity#INTENT_SELECTION_TYPE_DIRECTORY_VALUE} as extra value.<br />
 * 		By default a file selection is chosen.<br /> 
 * 		For example for selecting a file:<br />
 * 		<code>
 * 			Intent selectFileIntent = new Intent(this, FileChooserActivity.class);
 *			selectFileIntent.putExtra(FileChooserActivity.INTENT_SELECTION_TYPE_KEY, FileChooserActivity.INTENT_SELECTION_TYPE_FILE_VALUE);
 *			startActivityForResult(selectfileIntent, 1);
 * 		</code>
 * </p>
 * <p>
 * 		For use a custom start location use {@link FileChooserActivity#INTENT_PATH_KEY} as intent extra key and put your path as value.<br />
 * 		For example:<br />
 * 		<code>
 * 			Intent selectFileIntent = new Intent(this, FileChooserActivity.class);
 *			selectFileIntent.putExtra(FileChooserActivity.INTENT_PATH_KEY, "/my/custom/path");
 *			startActivityForResult(selectfileIntent, 1);
 * 		</code>
 * </p>
 * <p>
 * 		The result code will be {@link Activity#RESULT_OK} if the user has select a file.<br />
 * 		The selected file path you find in the intent by key {@link FileChooserActivity#INTENT_PATH_KEY}.
 * </p>
 * @author Fabian Keunecke <f.keunecke@liveyourproject.com>
 *
 */
public class FileChooserActivity extends ListActivity {

	/**
	 * Request code for select file 
	 */
	public static final int INTENT_SELECT_FILE_REQUEST_CODE = 400;
	
	/**
	 * Result code for go one step back
	 */
	private static final int INTENT_GO_BACK_RESULT_CODE = RESULT_FIRST_USER + 1; 
	
	/**
	 * Intent key for set path for show on start.<br />
	 * If not set we use the external storage directory from Android.
	 */
	public static final String INTENT_PATH_KEY = "com.liveyourproject.filechooser.activities.FileChooserActivity#INTENT_PATH_KEY";

	/**
	 * Intent key for set type of selection.<br />
	 * If not set we use the file selection by default.
	 */
	public static final String INTENT_SELECTION_TYPE_KEY = "com.liveyourproject.filechooser.activities.FileChooserActivity#INTENT_SELECTION_TYPE_KEY";

	/**
	 * Value for intent extras for define selection of file.
	 */
	public static final int INTENT_SELECTION_TYPE_FILE_VALUE = 1;
	
	/**
	 * Value for intent extras for define selection of directory.
	 */
	public static final int INTENT_SELECTION_TYPE_DIRECTORY_VALUE = 2;
	
	/**
	 * Intent key for set selected file.
	 */
	public static final String INTENT_SELECTED_FILE_KEY = "com.liveyourproject.filechooser.activities.FileChooserActivity#INTENT_SELECTED_FILE_KEY";
	
	/**
	 * Current file location
	 */
	private File mCurrentFile = null;
	
	/**
	 * Type of selection (file or directory)
	 */
	private boolean mSelectFile = true;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		// Setup action bar
		ActionBar actionBar = getActionBar();
		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			actionBar.setHomeButtonEnabled(true);
		}

		actionBar.setDisplayHomeAsUpEnabled(true);
		
		// Setup layout
		setContentView(R.layout.activity_file_chooser);
		
		TextView pathView = (TextView) findViewById(R.id.path);
		
		// Set it to default, so we go back if we click the back button
		setResult(INTENT_GO_BACK_RESULT_CODE);
		
		mCurrentFile = Environment.getExternalStorageDirectory();
		mSelectFile = true;
		
		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			
			String filePath = extras.getString(INTENT_PATH_KEY);
			if(filePath != null) {
				mCurrentFile = new File(filePath);
			}
			
			int selectionType = extras.getInt(INTENT_SELECTION_TYPE_KEY, -1);
			switch(selectionType) {
			
				case INTENT_SELECTION_TYPE_FILE_VALUE:
					mSelectFile = true;
				break;
					
				case INTENT_SELECTION_TYPE_DIRECTORY_VALUE:
					mSelectFile = false;
				break;
			
			}
			
		}
		
		pathView.setText(mCurrentFile.getAbsolutePath());
		
		FileAdapter fileAdapter = new FileAdapter(this);
		fileAdapter.setFiles(getFiles(mCurrentFile, !mSelectFile));
		
		FileListClickListener listClickListener = new FileListClickListener(this, mSelectFile);
		
		getListView().setAdapter(fileAdapter);
		getListView().setOnItemClickListener(listClickListener);
		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	   
	    MenuInflater inflater = getMenuInflater();
	    
	    int menuId;
	    if(mSelectFile) {
	    	menuId = R.menu.menu_file_chooser_file_selection;
	    } else {
	    	menuId = R.menu.menu_file_chooser_directory_selection;
	    }
	    
	    inflater.inflate(menuId, menu);
	    
	    return super.onCreateOptionsMenu(menu);
	    
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int itemId = item.getItemId();
		
		if(itemId == android.R.id.home) {
		    
			setResult(INTENT_GO_BACK_RESULT_CODE);
			finish();
			
		} else if(itemId == R.id.action_cancel) {
				
			setResult(RESULT_CANCELED);
			finish();
				
		} else if(itemId == R.id.action_select) {
			
			Intent resultIntent = new Intent();
			resultIntent.putExtra(FileChooserActivity.INTENT_SELECTED_FILE_KEY, mCurrentFile.getAbsolutePath());
			
			setResult(RESULT_OK, resultIntent);
			finish();
			
		}
		
		return super.onOptionsItemSelected(item);
		
	}
	
	/**
	 * Get all directories and files from given location.
	 * @param location Location from that we search the files
	 * @return Return a list with given files
	 */
	private List<File> getFiles(File location, boolean ignoreDataFiles) {
		
		DirectoryFilter dirFilter = new DirectoryFilter();
		DataFileFilter fileFilter = new DataFileFilter();
		
		ArrayList<File> allFiles = new ArrayList<File>();
		
		// Checkout directories
		File[] directories = location.listFiles(dirFilter);
		if(directories != null) {
		
			List<File> dirList = Arrays.asList(directories);
			Collections.sort(dirList);
			
			allFiles.addAll(dirList);
			
		}
		
		// Checkout files
		if(!ignoreDataFiles) {
		
			File[] files = location.listFiles(fileFilter);
			if(files != null) {
			
				List<File> fileList = Arrays.asList(files);
				Collections.sort(fileList);
				
				allFiles.addAll(fileList);
			
			}
			
		}
		
		return allFiles;
		
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == INTENT_SELECT_FILE_REQUEST_CODE) {
			
			if(resultCode == RESULT_OK) {
				
				String selectedFilePath = data.getStringExtra(INTENT_SELECTED_FILE_KEY);
				if(selectedFilePath != null) {
					
					Intent resultIntent = new Intent();
					resultIntent.putExtra(FileChooserActivity.INTENT_SELECTED_FILE_KEY, selectedFilePath);
					
					setResult(RESULT_OK, resultIntent);
					finish();
					
				}
				
			} else if(resultCode == RESULT_CANCELED) {
				
				setResult(RESULT_CANCELED);
				finish();
				
			}
			
		}
		
	}
	
}