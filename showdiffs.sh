 # must be in the udacity source directory
 find . -name "*.java" -exec diff -q  {} ~/AndroidStudioProjects/Sunshine/{} \;  |sed "s/^Files/diffuse/" |sed "s/ and / /" |sed "s/differ//" |bash
 #find . -name "*.xml" -exec diff -q  {} ~/AndroidStudioProjects/Sunshine/{} \;  |sed "s/^Files/diffuse/" |sed "s/ and / /" |sed "s/differ//" |bash
