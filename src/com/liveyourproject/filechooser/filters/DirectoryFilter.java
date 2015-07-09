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

package com.liveyourproject.filechooser.filters;

import java.io.File;
import java.io.FileFilter;

/**
 * Filter that select only the directories and ignore the files.
 * @author Fabian Keunecke <f.keunecke@liveyourproject.com>
 *
 */
public class DirectoryFilter implements FileFilter {

	/* (non-Javadoc)
	 * @see java.io.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File pathname) {
		
		boolean isDirectory = pathname.isDirectory();
		return isDirectory;
		
	}	
	
}
