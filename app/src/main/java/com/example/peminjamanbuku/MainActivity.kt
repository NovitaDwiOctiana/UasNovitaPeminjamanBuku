package com.example.peminjamanbuku

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logout.setOnClickListener(this)
        save.setOnClickListener(this)
        show_data.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()
        setDataSpinnerSt()
    }

    private fun setDataSpinnerSt(){
        val adapter = ArrayAdapter.createFromResource(this,
            R.array.Status, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSt.adapter = adapter
    }
    private fun isEmpty(s : String): Boolean {
        return TextUtils.isEmpty(s)
    }

    override fun onClick(v: View) {
        when (v.getId()) {
            R.id.save -> {
                val getUserID = auth!!.currentUser!!.uid
                val database = FirebaseDatabase.getInstance()

                val getNama:String = nama.getText().toString()
                val getJudul:String = judul.getText().toString()
                val getAlamat:String = alamat.getText().toString()
                val getnoHp:String = no_hp.getText().toString()
                val getPinjam:String = tgl_pjm.getText().toString()
                val getKembali:String = tgl_kmb.getText().toString()
                val getStatus:String = spinnerSt.selectedItem.toString()



                val getReference: DatabaseReference
                getReference = database.reference

                if (isEmpty(getJudul) || isEmpty(getNama) || isEmpty(getAlamat) ||
                        isEmpty(getnoHp) || isEmpty(getPinjam) || isEmpty(getKembali) || isEmpty(getStatus)) {
                    Toast.makeText(this@MainActivity, "Data tidak boleh ada yang kosong",
                        Toast.LENGTH_SHORT).show()
                } else {
                    getReference.child("Admin").child(getUserID).child("DataPinjam").push()
                        .setValue(data_pinjam(getNama, getJudul, getAlamat, getnoHp, getPinjam, getKembali, getStatus))
                        .addOnCompleteListener(this){
                            judul.setText("")
                            nama.setText("")
                            alamat.setText("")
                            no_hp.setText("")
                            tgl_pjm.setText("")
                            tgl_kmb.setText("")
                            Toast.makeText(this@MainActivity, "Data Tersimpan", Toast.LENGTH_SHORT).show()
                        }
                }
            }

            R.id.logout -> {
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(object : OnCompleteListener<Void>{
                            override fun onComplete(p0: Task<Void>) {
                                Toast.makeText(this@MainActivity, "Logout Berhasil",
                                    Toast.LENGTH_SHORT).show()
                                intent = Intent(applicationContext, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        })
            }
            R.id.show_data -> {
                startActivity(Intent(this@MainActivity, ListData::class.java))

            }
            }
        }
    }





