<?php
require("koneksi.php");

$response = array();

if($_SERVER[ 'REQUEST_METHOD'] == 'POST'){
    $id = $_POST["id"];
    $nama = $_POST["nama"];
    $nomor = $_POST["nomor"];


    $perintah = "UPDATE tbl_kontak SET nama ='$nama', nomor = '$nomor' WHERE id = '$id'";
    $eksekusi = mysqli_query($konek, $perintah);
    $cek = mysqli_affected_rows($konek);

    if($cek > 0){
        $response["kode"] = 1;
        $response["pesan"] = "data berhasil diubah";
        $response["data"] = array();

    }else{
        $response["kode"] = 0;
        $response["pesan"] = "data gagal diubah";
        
    }
}
else{
    $response["kode"] = 0;
    $response["pesan"] = "Tidak ada post data";

}
echo  json_encode($response);
mysqli_close($konek);