package com.example.peminjamanbuku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update_data.*

class UpdateData : AppCompatActivity() {
    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var cekJudulBuku: String? = null
    private var cekNama: String? = null
    private var cekAlamat: String? = null
    private var cekNoHP: String? = null
    private var cekPinjam: String? = null
    private var cekKembali: String? = null
    private var cekStatus: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        supportActionBar!!.title = "Update Data"

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        data
        update.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                cekJudulBuku = new_judul.getText().toString()
                cekNama = new_alamat.getText().toString()
                cekAlamat = new_alamat.getText().toString()
                cekNoHP = new_no_hp.getText().toString()
                cekPinjam = new_tgl_pjm.getText().toString()
                cekKembali = new_tgl_kmb.getText().toString()
                cekStatus = new_sts.getText().toString()

                if (isEmpty(cekJudulBuku!!) || isEmpty(cekNama!!) || isEmpty(cekAlamat!!)
                        || isEmpty(cekNoHP!!) || isEmpty(cekPinjam!!) || isEmpty(cekKembali!!)
                        || isEmpty(cekStatus!!)) {
                    Toast.makeText(this@UpdateData,
                            "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
                } else {
                    val setdata_pinjam = data_pinjam()
                    setdata_pinjam.judul = new_judul.getText().toString()
                    setdata_pinjam.nama = new_nama.getText().toString()
                    setdata_pinjam.alamat = new_alamat.getText().toString()
                    setdata_pinjam.noHp = new_no_hp.getText().toString()
                    setdata_pinjam.pinjam = new_tgl_kmb.getText().toString()
                    setdata_pinjam.kembali = new_tgl_kmb.getText().toString()
                    setdata_pinjam.status = new_sts.getText().toString()
                    updatePinjam(setdata_pinjam)
                }
            }
        })
    }

    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }

    private val data: Unit
    private get() {
        val getJudulBuku = intent.extras!!.getString("dataJudulBuku")
        val getNama = intent.extras!!.getString("dataNama")
        val getAlamat = intent.extras!!.getString("dataAlamat")
        val getNoHP = intent.extras!!.getString("dataNoHP")
        val getPinjam = intent.extras!!.getString("dataPinjam")
        val getKembali = intent.extras!!.getString("dataKembali")
        val getStatus = intent.extras!!.getString("dataStatus")

        new_judul!!.setText(getJudulBuku)
        new_nama!!.setText(getNama)
        new_alamat!!.setText(getAlamat)
        new_no_hp!!.setText(getNoHP)
        new_tgl_pjm!!.setText(getPinjam)
        new_tgl_kmb!!.setText(getKembali)
        new_sts!!.setText(getStatus)
    }

    private fun updatePinjam(pinjam: data_pinjam) {
        val userID = auth!!.uid
        val getKey = intent.extras!!.getString("getPrimaryKey")
        database!!.child("Admin")
                .child(userID!!)
                .child("DataPinjam")
                .child(getKey!!)
                .setValue(pinjam)
                .addOnSuccessListener {
                    new_judul!!.setText("")
                    new_nama!!.setText("")
                    new_alamat!!.setText("")
                    new_no_hp!!.setText("")
                    new_tgl_pjm!!.setText("")
                    new_tgl_kmb!!.setText("")
                    new_sts!!.setText("")
                    Toast.makeText(this@UpdateData, "Data Berhasil diubah", Toast.LENGTH_SHORT).show()
                    finish()
                }
    }
    }



