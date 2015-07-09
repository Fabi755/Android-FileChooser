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

package com.liveyourproject.filechooser.listeners;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.liveyourproject.filechooser.activities.FileChooserActivity;
import com.liveyourproject.filechooser.adapters.FileAdapter;

/**
 * Click listener for file list.<br />
 * If a file was clicked we select this one and finish the current activity with a success result.<br />
 * If the user clicked a directory we start the next activity with the new path.
 * @author Fabian Keunecke <f.keunecke@liveyourproject.com>
 *
 */
public class FileListClickListener implements OnItemClickListener {

	/**
	 * Current activity that hold the file list
	 */
	private Activity mActivity;
	
	/**
	 * Type of selection (file or directory)
	 */
	private boolean mSelectFile = true;
	
	/**
	 * Create new click listener for file list.<br />
	 * This will open the next path if a folder was clicked
	 * @param activity Current activity that hold the file list
	 * @param selectFile Set to {@code true} if we need a file or {@code false} if we need a directory
	 */
	public FileListClickListener(Activity activity, boolean selectFile) {
		
		mActivity = activity;
		mSelectFile = selectFile;
		
	}
	
	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		if(parent.getAdapter() instanceof FileAdapter) {
		
			FileAdapter fileAdapter = (FileAdapter) parent.getAdapter();
			File clickedFile = fileAdapter.getFile(position);
			
			if(clickedFile.isDirectory()) {
			
				Intent intent = new Intent(mActivity, FileChooserActivity.class);
				intent.putExtra(FileChooserActivity.INTENT_PATH_KEY, clickedFile.getAbsolutePath());
				
				int selectionType;
				if(mSelectFile) {
					selectionType = FileChooserActivity.INTENT_SELECTION_TYPE_FILE_VALUE;
				} else {
					selectionType = FileChooserActivity.INTENT_SELECTION_TYPE_DIRECTORY_VALUE;
				}
				
				intent.putExtra(FileChooserActivity.INTENT_SELECTION_TYPE_KEY, selectionType);
				
				mActivity.startActivityForResult(intent, FileChooserActivity.INTENT_SELECT_FILE_REQUEST_CODE);
				
			} else {
				
				Intent resultIntent = new Intent();
				resultIntent.putExtra(FileChooserActivity.INTENT_SELECTED_FILE_KEY, clickedFile.getAbsolutePath());
				
				mActivity.setResult(Activity.RESULT_OK, resultIntent);
				mActivity.finish();
			
			}
			
		} else {
			throw new IllegalArgumentException("No! List requires an adapter from type FileAdapter."); 
		}
		
	}

}
