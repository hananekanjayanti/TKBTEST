<?php
require("koneksi.php");

$response = array();

if($_SERVER[ 'REQUEST_METHOD'] === 'POST'){
    $nama = $_POST["nama"];
    $nomor = $_POST["nomor"];

    $perintah = "INSERT INTO tbl_kontak (nama, nomor) VALUES ('$nama', '$nomor')";
    $eksekusi = mysqli_query($konek, $perintah);
    $cek = mysqli_affected_rows($konek);

    if($cek > 0){
        $response["kode"] = 1;
        $response["pesan"] = "Simpan data berhasil";


    }else{
        $response["kode"] = 0;
        $response["pesan"] = "Gagal menyimpan data";
    }
}
else{
    $response["kode"] = 0;
    $response["pesan"] = "Tidak ada post data";

}
echo  json_encode($response);
mysqli_close($konek);

