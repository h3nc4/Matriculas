# Gerar a documentação do projeto
javadoc -private -d javadoc -sourcepath src/ src/*/*.java

# Mudar branch para web
git checkout web
