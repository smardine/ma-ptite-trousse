<?php
if ( $_POST['marque'] == "" ) {
        die("ce script sert a recuperer une nouvelle marque saisie par l'utilisateur");
}
$random = rand(1000,9999);

$marque = $_POST['marque'];

$marque = clean($marque) ;

$handle = fopen("-Nouvelle_marque-".time()."-".$random, "w+");
fwrite($handle, $marque);
fclose($handle);
die("post termine");
// Uncomment and change the following line to have exceptions mailed to you
//mail("s.mardine@gmail.com","IMPORTANT: Exception received (".$version.")",$_POST['stacktrace'],"from:s.mardine@gmail.com");

function clean( $input, $max_length=255 ) {
        $input = trim($input) ;
        $input = substr($input, 0, $max_length) ;
        $input = preg_replace("/[^a-zA-Z0-9_-]/", "_", $input) ;
        return $input ;
}

?>
