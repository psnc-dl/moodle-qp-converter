## Question Page Converter 

## General information

In pre 2.x versions of Moodle in native course format it was possible to add question pages without answers. 
They were used to introduce necessary theory before asking actual question. Moodle 2.x broke backward 
compatibility to clean this up, they have introduced Content Pages (previously named Branching Table), 
which can be used to introduce all the necessary information.  

Unfortunately when you restore backup from Moodle 1.9x in Moodle 2.4+ import mechanism does not perform 
conversion of answers-less Question Pages into Content Pages. Question Page Converter does exactly that.

The exact user scenario which is supported looks like that:
* Import your 1.9x course into 2.4x. 
* Backup migrated course and download .mbz file
* Download and unpack Question Page Converter
* Adjust configuration.xml of Question Page Converter (see Running the tool chapter below)
* Run ./run.sh to perform conversion
* Upload results of conversion and import them into Moodle

## Building from source

Download the source code (clone or used zipped version of repo). 
Tool was written in Java 1.6 and it requires Apache Maven to run a build.

Run: mvn assembly:assembly (in the folder where pom.xml is)

This build will result with zip archive: /target/question-page-converter-jar.zip

## Running the tool

Distribution contains the following files:
```
.
..
lib 
configuration.xml  //configuration of conversion
questionPage-converter.jar 
run.sh //run this one to perform conversion
```
In order to run the tool you will need Java installed on your machine. 
Before you will launch the main script make sure that you have changed configuration.xml 
with path to appropriate folders.

Configuration consists of the following elements:
* `input.file` - file with Moodle backup 
* `output.file` - file to store results of conversion
* `temp.dir` - folder which should be used as temporary space
* `continue.label` - word "Continue" in your language
* `initial.seq` - set it to some high number i.e. 2000 (this would be removed in the future)

## TODO

* improve assertions in AppTest
* prepareWorkingCopy in App throw checked IOException wrap this somehow
* scanning backup to get the highest used value of answers id seq (bypassed by giving it very high number by default)

Recently done
* change implementation of moodleBackupExporter to exclude top folder from mbz (include this in tests)
* unpacking zip
* packing results to zip
* add test-configuration.xml and change configuration init.seq to some high number
* preparation of executable jar
* log information about parameters of conversion

## Acknowledgements
The widget is developed by [Digital Libraries Team](http://dl.psnc.pl/) of 
[Poznań Supercomputing and Networking Center](http://www.man.poznan.pl/).

This project was initiated and partially funded as a part of the 
[Access IT plus](http://accessitplus.eu) project, funded from the Culture Programme 
(2007-2013) of the European Union

## Licence
Copyright (c) 2013 Poznań Supercomputing and Networking Center  
Licensed under the [EUPL, Version 1.1](https://joinup.ec.europa.eu/software/page/eupl/licence-eupl). 

"Compatible Licences" according to article 5 EUPL are: GNU General Public License (GNU GPL) v. 2, Open Software License (OSL) v. 2.1, v. 3.0, Common Public License v. 1.0, Eclipse Public License v. 1.0, Cecill v. 2.0.
