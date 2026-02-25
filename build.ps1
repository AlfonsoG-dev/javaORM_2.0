$srcClasses = "src\orm\builders\migration\*.java src\orm\builders\querys\*.java src\orm\connection\*.java src\orm\connection\dao\*.java src\orm\connection\execution\*.java src\orm\utils\formats\*.java src\orm\utils\model\*.java src\orm\utils\query\*.java "
$libFiles = ".\lib\mysql-connector-j-9.3.0\mysql-connector-j-9.3.0.jar;"
$compile = "javac --release 23 -Xlint:all -Xdiags:verbose -d .\bin\ -cp '$libFiles' $srcClasses"
$createJar = "jar -cfm javaORM_2.0.jar Manifesto.txt -C .\bin\ . -C .\extractionFiles\mysql-connector-j-9.3.0\ ."
$runCommand = "$compile" + " && " + "$createJar"
Invoke-Expression $runCommand 
