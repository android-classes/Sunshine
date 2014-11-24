 # must be in the udacity source directory

 # sometimes you have to create directory and file first when it does not exist
 # mkdir -p /home/ralph/AndroidStudioProjects/Sunshine/./app/src/main/res/layout-sw600dp
 # touch /home/ralph/AndroidStudioProjects/Sunshine/./app/src/main/res/layout-sw600dp/activity_main.xml

 #
 find . -name "*.java" -exec diff -q  {} ~/AndroidStudioProjects/Sunshine/{} \;  |sed "s/^Files/cp/" |sed "s/ and / /" |sed "s/differ//" |bash
 find . -name "*.xml" -exec diff -q  {} ~/AndroidStudioProjects/Sunshine/{} \;  |sed "s/^Files/cp/" |sed "s/ and / /" |sed "s/differ//" |bash
 find . -name "*.png" -exec diff -q  {} ~/AndroidStudioProjects/Sunshine/{} \;  |sed "s/^Files/cp/" |sed "s/ and / /" |sed "s/differ//" |bash
