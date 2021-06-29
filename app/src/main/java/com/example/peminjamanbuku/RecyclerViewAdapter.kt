package com.example.peminjamanbuku

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class RecyclerViewAdapter(private val listdata_pinjam: ArrayList<data_pinjam>, context: Context) :
RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val JudulBuku: TextView
        val Nama: TextView
        val Alamat: TextView
        val NoHP: TextView
        val TglPinjam: TextView
        val TglKembali: TextView
        val Status: TextView
        val ListItem: LinearLayout

        init {
            JudulBuku = itemView.findViewById(R.id.judul)
            Nama = itemView.findViewById(R.id.nama)
            Alamat = itemView.findViewById(R.id.alamat)
            NoHP = itemView.findViewById(R.id.no_hp)
            TglPinjam = itemView.findViewById(R.id.tgl_pjm)
            TglKembali = itemView.findViewById(R.id.tgl_kmb)
            Status = itemView.findViewById(R.id.sts)
            ListItem = itemView.findViewById(R.id.data_list)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val V:View = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_data,
                parent, false)
        return ViewHolder(V)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val JudulBuku: String? = listdata_pinjam.get(position).judul
        val Nama: String? = listdata_pinjam.get(position).nama
        val Alamat: String? = listdata_pinjam.get(position).alamat
        val NoHP: String? = listdata_pinjam.get(position).noHp
        val TglPinjam: String? = listdata_pinjam.get(position).pinjam
        val TglKembali: String? = listdata_pinjam.get(position).kembali
        val Status: String? = listdata_pinjam.get(position).status

        holder.JudulBuku.text = "JudulBuku: $JudulBuku"
        holder.Nama.text = "Nama: $Nama"
        holder.Alamat.text = "Alamat: $Alamat"
        holder.NoHP.text = "NoHP: $NoHP"
        holder.TglPinjam.text = "TglPinjam: $TglPinjam"
        holder.TglKembali.text = "TglKembali : $TglKembali"
        holder.Status.text = "Status : $Status"
        holder.ListItem.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                holder.ListItem.setOnLongClickListener { view ->
                    val action = arrayOf("Update", "Delete")
                    val alert: AlertDialog.Builder = AlertDialog.Builder(view.context)
                    alert.setItems(action, DialogInterface.OnClickListener {dialog, i ->
                        when (i) {
                            0 -> {
                                val bundle = Bundle()
                                bundle.putString("dataJudulBuku", listdata_pinjam[position].judul)
                                bundle.putString("dataNama", listdata_pinjam[position].nama)
                                bundle.putString("dataAlamat", listdata_pinjam[position].alamat)
                                bundle.putString("dataNoHP", listdata_pinjam[position].noHp)
                                bundle.putString("dataTglPinjam", listdata_pinjam[position].pinjam)
                                bundle.putString("dataTglKembali", listdata_pinjam[position].kembali)
                                bundle.putString("dataStatus", listdata_pinjam[position].status)
                                val intent = Intent(view.context, UpdateData::class.java)
                                intent.putExtras(bundle)
                                context.startActivity(intent)
                            }
                            1 -> {
                                listener?.onDeleteData(listdata_pinjam.get(position), position)
                            }
                        }
                    })
                    alert.create()
                    alert.show()
                    true
                }
                return true
            }
        })
    }

    override fun getItemCount(): Int {
        return listdata_pinjam.size
    }

    var listener: dataListener? = null

    init {
        this.context = context
        this.listener = context as ListData
    }

    interface dataListener {
        fun onDeleteData(data: data_pinjam?, position: Int)
    }

}