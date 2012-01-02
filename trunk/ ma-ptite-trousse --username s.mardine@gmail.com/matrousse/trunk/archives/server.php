<?php
if ( $_POST['stacktrace'] == "" || $_POST['package_version'] == "" || $_POST['package_name'] == "" ) {
        die("This script is used to collect field test crash stacktraces. No personal information is transmitted, collected or stored.<br/>For more information, please contact <a href='mailto:info@wftllc.com'>info@wftllc.com</a>");
}
$random = rand(1000,9999);
$version = $_POST['package_version'];
$package = $_POST['package_name'];
$version = clean($version) ;
$package = clean($package) ;

$handle = fopen($package."-trace-".$version."-".time()."-".$random, "w+");
fwrite($handle, $_POST['stacktrace']);
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
