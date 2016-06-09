# Night-Day-Classifier

This Repository contains code, that classifies Images for its environment-lightning.
The purpose is to understand the environmental background. This is important for self-driving cars to know, how they can realise barriers.

The Project uses functionality of ImageJ. It is used as an library so its jar is definitely needed.

There are 4 Classes the image can be classified with.

* Day
* Night
* Inside (Tunnels etc.)
* Not Classifiable

## Project Structure

-- src - SourceFiles
-- files - default dir of used files
... -- learn - Images for the learning-phase of the algorithm
-- lib - a library for artificial neural nets
-- result - directory where the classification-results are placed

## Usage

There are several ways to use the tool.

### Jar and Command Line
A runnable jar comes within the repo. To use it you can just run it with

	java -jar Night_Day_Classifier.jar -i=pathToInputFile

There are some more parameter to pass to the tool which are optional, as long as the projectstructure is the same as in the repo.
If you change one of the resource/outoput-directories via the parameter, make sure the directories really exists.

*Parameters:*
	- -i
		- inputfile
		- can be absolute or realtive
		- no dots are allowed, except that one before the filetype
			- my/super/relative/path/image123_239.png -- Works
			- ../siblingfolder/myFile.png -- won't work
			- my/dot.name.separator/file.png -- won't work
	- -o
		- Folder where the resultfiles are placed in
		- The Directory has to be present when the execution starts
    - -learnPath
    	- directory where the images for learning are
    	- files have to be named like that in the repo under files/learn


### Eclipse

Import the Project in your eclipse and make sure the *ij-'version'.jar* is in your classpath as well as the *snipe-library* under lib/ .