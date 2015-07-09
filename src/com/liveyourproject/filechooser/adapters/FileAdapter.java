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

package com.liveyourproject.filechooser.adapters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liveyourproject.filechooser.R;

/**
 * Adapter for file list. Here we control the list items and create the views.
 * @author Fabian Keunecke <f.keunecke@liveyourproject.com>
 *
 */
public class FileAdapter extends BaseAdapter {

	/**
	 * Context of activity
	 */
	private Context mContext;
	
	/**
	 * Files that showed in list
	 */
	private ArrayList<File> mFiles = new ArrayList<File>();
	
	/**
	 * Create new adapter that manage the file items on list view.
	 * @param context Context of activity
	 */
	public FileAdapter(Context context) {
		
		mContext = context;
		
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		
		int count = 0;
		if(mFiles != null) {
			count = mFiles.size(); 
		}
		
		return count;
		
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		
		return mFiles.get(position);
		
	}
	
	/**
	 * Get file item from given position
	 * @param position Position of file
	 * @return Return file item from list
	 */
	public File getFile(int position) {
		
		return mFiles.get(position);
		
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		
		return position;
		
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null) {
			
			LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.list_item_file, null, false);
			
		}
		
		File file = mFiles.get(position);
		
		ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
		TextView name = (TextView) convertView.findViewById(R.id.name);
		
		if(file.isDirectory()) {
			icon.setImageResource(R.drawable.folder);
		} else {
			icon.setImageResource(R.drawable.file);
		}
		
		name.setText(file.getName());
		
		return convertView;
		
	}

	/**
	 * Set files
	 * @param files New files for adapter
	 */
	public void setFiles(List<File> files) {
		
		mFiles = new ArrayList<File>(files);
		
	}
	
}
