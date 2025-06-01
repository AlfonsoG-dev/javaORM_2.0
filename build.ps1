$srcClases = ".\src\ORM\Utils\Query\*.java .\src\ORM\DbConnection\Execution\*.java .\src\ORM\Utils\Formats\*.java .\src\Samples\Models\Foreign\*.java .\src\Samples\Migration\*.java .\src\ORM\DbConnection\*.java .\src\ORM\Builders\Migration\*.java .\src\ORM\Builders\Query\*.java .\src\Samples\Query\*.java .\src\ORM\Utils\Model\*.java .\src\Samples\Models\Primary\*.java .\src\ORM\DbConnection\DAO\*.java "
$libFiles = ".\lib\javaORM_2.0\javaORM_2.0.jar;"
$compile = "javac -Werror -Xlint:all -d .\bin\ -cp '$libFiles' $srcClases"
$createJar = "jar -cfm javaORM_2.0.jar Manifesto.txt -C .\bin\ ."
$runCommand = "$compile" + " && " + "$createJar" 
Invoke-Expression $runCommand 
