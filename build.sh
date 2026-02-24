srcClases=" ./src/orm/builders/migration/*.java ./src/orm/builders/querys/*.java ./src/orm/connection/*.java ./src/orm/connection/dao/*.java ./src/orm/connection/execution/*.java ./src/orm/utils/formats/*.java ./src/orm/utils/model/*.java ./src/orm/utils/query/*.java ./src/samples/migration/*.java ./src/samples/models/foreign/*.java ./src/samples/models/primary/*.java ./src/samples/query/*.java "
libFiles="./lib/mysql-connector-j-9.6.0/mysql-connector-j-9.6.0.jar"
javac -Werror -Xlint:all -d ./bin/ -cp $libFiles $srcClases
jar -cfm App.jar Manifesto.txt -C ./bin/ .
