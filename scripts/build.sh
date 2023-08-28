# Compilar todo o projeto e escrever em bin/
javac -g -d bin/ src/*/*.java

# Criar o arquivo .jar a partir de bin/, MANIFEST.MF, README.md e LICENSE
jar cfm ./Matriculas.jar docs/MANIFEST.MF -C bin/ . README.md LICENSE

# Remover bin/ e as classes compiladas
rm -rf bin/
