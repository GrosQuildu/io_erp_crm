<?php
header('Content-Type: text/json; charset=utf-8');
$username="u1102723_erp";
$password="W1f2S2sM";
$database="db1102723_erp";
$user = $_POST["user"];
$pass = $_POST["pass"];
$query = $_POST["query"];
$query = substr($query,1,-1);
$port = 3306;

$correct = FALSE;
$conn = new mysqli('mysql611int.cp.az.pl', $username, $password, $database, $port);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$result = mysqli_query($conn, "SELECT haslo FROM pracownicy WHERE mail='".$user."';") or die("Error in Selecting " . mysqli_error($conn));
while($row = mysqli_fetch_assoc($result)) {
        if($row["haslo"]==$pass) $correct=TRUE;
    }


if($correct){
    $result = mysqli_query($conn, $query) or die("Error in Selecting " . mysqli_error($conn));
    $emparray = array();
    while($row =mysqli_fetch_assoc($result))
    {
        $emparray[] = $row;
    }
    echo json_encode($emparray);
} else echo "";
?>
