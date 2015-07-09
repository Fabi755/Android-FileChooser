
# How to use

## Required Manifest settings

```xml
	
	...

	<uses-permission 
		android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

	<application
		...
	>

		...

		<activity 
			android:name="com.liveyourproject.filechooser.activities.FileChooserActivity" />

	</application>

	...

```

## Type of selection - file or directory?
For selecting define the selection type use `FileChooserActivity.INTENT_SELECTION_TYPE_KEY` as intent extra key.
If you wish to select a file use `FileChooserActivity.NTENT_SELECTION_TYPE_FILE_VALUE` as extra value. 
For select a directory use `FileChooserActivity#INTENT_SELECTION_TYPE_DIRECTORY_VALUE` as extra value.<br />
By default a file selection is chosen.

For example for selecting a file:
```java
	Intent selectFileIntent = new Intent(this, FileChooserActivity.class);
	selectFileIntent.putExtra(FileChooserActivity.INTENT_SELECTION_TYPE_KEY, FileChooserActivity.INTENT_SELECTION_TYPE_FILE_VALUE);
	startActivityForResult(selectfileIntent, 1);
```

## Start location

For use a custom start location use `FileChooserActivity.INTENT_PATH_KEY` as intent extra key and put your path as value.
For example:
```java
	Intent selectFileIntent = new Intent(this, FileChooserActivity.class);
	selectFileIntent.putExtra(FileChooserActivity.INTENT_PATH_KEY, "/my/custom/path");
	startActivityForResult(selectfileIntent, 1);
```
## Result code

The result code will be `Activity.RESULT_OK` if the user has select a file.<br />
The selected file path you find in the intent by key `FileChooserActivity#INTENT_PATH_KEY`.

## Used resources

### Icons

* https://www.iconfinder.com/icons/443791/directory_document_file_folder_open_icon
* https://www.iconfinder.com/icons/443783/document_page_paper_sheet_text_writing_icon
from icon set: https://www.iconfinder.com/iconsets/flat-right-angles-free

# License

Copyright 2015 LiveYourProject

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 