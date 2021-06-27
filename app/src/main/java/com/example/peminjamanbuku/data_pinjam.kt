package com.example.peminjamanbuku

class data_pinjam {
    var judul: String? = null
    var nama: String? = null
    var alamat: String? = null
    var noHp: String? = null
    var pinjam: String? = null
    var kembali: String? = null
    var status: String? = null
    var key: String? = null

    constructor()

    constructor( judul:String?, nama:String?, alamat:String?, noHp:String?,
    pinjam:String?, kembali:String?, status:String?){
        this.judul = judul
        this.nama = nama
        this.alamat = alamat
        this.noHp = noHp
        this.pinjam = pinjam
        this.kembali = kembali
        this.status = status

    }
}