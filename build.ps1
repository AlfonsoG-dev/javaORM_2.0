$srcClases = ".\src\ORM\Builders\Migration\*.java .\src\ORM\Builders\Query\*.java .\src\ORM\DbConnection\*.java .\src\ORM\DbConnection\DAO\*.java .\src\ORM\DbConnection\Execution\*.java .\src\ORM\Utils\Formats\*.java .\src\ORM\Utils\Model\*.java .\src\ORM\Utils\Query\*.java .\src\Samples\Migration\*.java .\src\Samples\Models\Foreign\*.java .\src\Samples\Models\Primary\*.java .\src\Samples\Query\*.java "
$libFiles = ".\lib\mysql-connector-j-9.3.0\mysql-connector-j-9.3.0.jar;"
$compile = "javac -Werror -Xlint:all -d .\bin\ -cp '$libFiles' $srcClases"
$createJar = "jar -cfm javaORM_2.0.jar Manifesto.txt -C .\bin\ . -C .\extractionFiles\mysql-connector-j-9.3.0\ ."
$runCommand = "$compile" + " && " + "$createJar" 
Invoke-Expression $runCommand 
