cd ..
javac -g -d bin/ src/*.java
jar cfm ./Matriculas.jar docs/MANIFEST.MF -C bin/ . README.md LICENSE
rm -rf bin/
java -jar ./Matriculas.jar
