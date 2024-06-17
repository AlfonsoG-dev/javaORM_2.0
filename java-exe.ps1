$srcClases = ".\src\ORM\Utils\Query\*.java .\src\ORM\Builders\Migration\*.java .\src\ORM\Utils\Model\*.java .\src\ORM\DbConnection\DAO\*.java .\src\ORM\Utils\Formats\*.java .\src\ORM\DbConnection\Execution\*.java .\src\ORM\Builders\Query\*.java .\src\ORM\DbConnection\*.java .\src\Samples\Migration\*.java .\src\Samples\Query\*.java .\src\Samples\Models\Foreign\*.java .\src\Samples\Models\Primary\*.java "
$libFiles = ".\lib\mysql-connector-j-8.3.0\mysql-connector-j-8.3.0.jar;"
$compile = "javac -Werror -Xlint:all -d .\bin\ -cp '$libFiles' $srcClases"
$createJar = "jar -cfm javaORM.jar Manifesto.txt -C .\bin\ ."
$runCommand = "$compile" + " && " + "$createJar" 
Invoke-Expression $runCommand 
