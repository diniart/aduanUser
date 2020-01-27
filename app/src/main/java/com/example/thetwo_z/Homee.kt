package com.example.thetwo_z


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import com.example.thetwo_z.Pengaduan.*
import com.example.thetwo_z.chat.latestMessage
import com.example.thetwo_z.model.homeee
import kotlinx.android.synthetic.main.activity_homeee.*
import kotlinx.android.synthetic.main.item_home.view.*

//import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.item_binatang.view.*

class Homee : AppCompatActivity() {
    var listKategori = ArrayList<homeee>()
    var adapter: AdapterKategori? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homeee)

        supportActionBar?.title = "Category"


        listKategori.add(homeee("Dosen", "", R.drawable.dosen))
        listKategori.add(homeee("Karyawan", "", R.drawable.karyawan))
        listKategori.add(homeee("Akademik", "",R.drawable.akademik))
        listKategori.add(homeee("Keuangan", "", R.drawable.uang1))
        listKategori.add(homeee("Inventaris", "", R.drawable.pc))
        listKategori.add(homeee("About", "", R.drawable.attention))




        //panggil adapter
        adapter = AdapterKategori(this, listKategori)
        gvListKategori.adapter = adapter

    }


    inner class AdapterKategori:BaseAdapter{


        var listKategori = ArrayList<homeee>()
        var context: Context?=null
        constructor(context:Context,listOfKategori: ArrayList<homeee>):super(){
            this.context=context
            this.listKategori=listOfKategori
        }
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val kategori= this.listKategori[p0]



            var inflator =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE)as LayoutInflater
            var kategoriView = inflator.inflate(R.layout.item_home,null)
            kategoriView.ivGambarKategori.setImageResource(kategori.gambar!!)
            kategoriView.ivGambarKategori.setOnClickListener{
                //  if(kategori.gambar!=R.drawable.attention){

                //val intent = Intent(context,
                //  latestMessage::class.java)
                /* intent.putExtra("nama",kategori.nama!!)
                 intent.putExtra("deskripsi",kategori.deskripsi!!)
                 intent.putExtra("gambar", kategori.gambar!!)
                 context!!.startActivity((intent))

                 */
                //  startActivity(intent)}


                if(kategori.gambar==R.drawable.dosen){
                    val intent = Intent(context, aduanDosen::class.java)

                    context!!.startActivity(intent)

                }
                else if(kategori.gambar==R.drawable.karyawan){
                    val intent = Intent(context, aduanKaryawan::class.java)

                    context!!.startActivity(intent)
                }
                else if(kategori.gambar==R.drawable.akademik){
                    val intent = Intent(context, aduanAkademik::class.java)

                    context!!. startActivity(intent)
                }
                else if(kategori.gambar==R.drawable.pc){
                    val intent = Intent(context, aduanInventaris::class.java)

                    context!!.startActivity(intent)
                }
                else if(kategori.gambar==R.drawable.uang1){
                    val intent = Intent(context, Aduan_Keuangan::class.java)

                    context!!.startActivity(intent)
                }
                else if(kategori.gambar==R.drawable.attention) {

                    val intent = Intent(context, about::class.java)

                    context!!. startActivity(intent)
                }

            }
            kategoriView.tvNamaKategori.text =kategori.nama!!
            return  kategoriView

        }

        override fun getItem(p0: Int): Any {
            return listKategori[p0]
        }

        override fun getItemId(p0: Int): Long {
            return  p0.toLong()
        }

        override fun getCount(): Int {
            return listKategori.size
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.profil ->{

                val intent =Intent(this,
                    profil::class.java)
                startActivity(intent)

            }
            R.id.riwayat ->{

                val intent = Intent(this, Riwayat::class.java)

                startActivity(intent)
            }
            R.id.newMessagemenu ->{

                val intent = Intent(this, latestMessage::class.java)

                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return super.onCreateOptionsMenu(menu)

    }

}











//coba2

/*inner class AdapterKategori : BaseAdapter {


    /**  override fun getItem(p0: Int): Any {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemId(p0: Int): Long {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }**/

    var listKategori = ArrayList<homeee>()
    var context: Context? = null

    constructor(context: Context, listOfKategori: ArrayList<homeee>) : super() {
        this.listKategori = listOfKategori
        this.context = context

    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val kategori = this.listKategori[p0]

        if (kategori.Dosen == true) {
            var inflator =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var kategoriView = inflator.inflate(R.layout.item_home, null)
            kategoriView.ivGambarKategori.setImageResource(kategori.gambar!!)
            kategoriView.ivGambarKategori.setOnClickListener {


                val intent = Intent(
                    context,
                    aduanDosen::class.java
                )
                /* intent.putExtra("nama",kategori.nama!!)
                    intent.putExtra("deskripsi",kategori.deskripsi!!)
                    intent.putExtra("gambar", kategori.gambar!!)
                    context!!.startActivity((intent))

                    */
                startActivity(intent)

            }
            return kategoriView
        } else if (kategori.Karyawan == true) {
            val kategori = this.listKategori[p0]


            var inflator =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var kategoriView = inflator.inflate(R.layout.item_home, null)
            kategoriView.ivGambarKategori.setImageResource(kategori.gambar!!)
            kategoriView.ivGambarKategori.setOnClickListener {


                val intent = Intent(
                    context,
                    aduanKaryawan::class.java
                )
                /* intent.putExtra("nama",kategori.nama!!)
                    intent.putExtra("deskripsi",kategori.deskripsi!!)
                    intent.putExtra("gambar", kategori.gambar!!)
                    context!!.startActivity((intent))

                    */
                startActivity(intent)

            }

        } else if (kategori.Akademik == true) {
            val kategori = this.listKategori[p0]


            var inflator =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var kategoriView = inflator.inflate(R.layout.item_home, null)
            kategoriView.ivGambarKategori.setImageResource(kategori.gambar!!)
            kategoriView.ivGambarKategori.setOnClickListener {


                val intent = Intent(
                    context,
                    aduanAkademik::class.java
                )
                /* intent.putExtra("nama",kategori.nama!!)
                            intent.putExtra("deskripsi",kategori.deskripsi!!)
                            intent.putExtra("gambar", kategori.gambar!!)
                            context!!.startActivity((intent))

                            */
                startActivity(intent)

            }
            return kategoriView
        } else if (kategori.Inventaris == true) {
            val kategori = this.listKategori[p0]


            var inflator =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var kategoriView = inflator.inflate(R.layout.item_home, null)
            kategoriView.ivGambarKategori.setImageResource(kategori.gambar!!)
            kategoriView.ivGambarKategori.setOnClickListener {


                val intent = Intent(
                    context,
                    aduanInventaris::class.java
                )
                /* intent.putExtra("nama",kategori.nama!!)
                                intent.putExtra("deskripsi",kategori.deskripsi!!)
                                intent.putExtra("gambar", kategori.gambar!!)
                                context!!.startActivity((intent))

                                */
                startActivity(intent)

            }
            return kategoriView
        } else if (kategori.Keuangan == true) {
            val kategori = this.listKategori[p0]


            var inflator =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var kategoriView = inflator.inflate(R.layout.item_home, null)
            kategoriView.ivGambarKategori.setImageResource(kategori.gambar!!)
            kategoriView.ivGambarKategori.setOnClickListener {


                val intent = Intent(
                    context,
                    aduanKeuangan::class.java
                )
                /* intent.putExtra("nama",kategori.nama!!)
                                    intent.putExtra("deskripsi",kategori.deskripsi!!)
                                    intent.putExtra("gambar", kategori.gambar!!)
                                    context!!.startActivity((intent))

                                    */
                startActivity(intent)

            }
            return kategoriView
        } else if (kategori.gambar != R.drawable.attention) {
            val kategori = this.listKategori[p0]
            var inflator =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var kategoriView = inflator.inflate(R.layout.item_home, null)
            kategoriView.ivGambarKategori.setImageResource(kategori.gambar!!)
            kategoriView.ivGambarKategori.setOnClickListener {


                val intent = Intent(
                    context,
                    about::class.java
                )
                /* intent.putExtra("nama",kategori.nama!!)
                    intent.putExtra("deskripsi",kategori.deskripsi!!)
                    intent.putExtra("gambar", kategori.gambar!!)
                    context!!.startActivity((intent))

                    */
                startActivity(intent)

            }

            }



return null!!


    }



        override fun getItem(p0: Int): Any {
            return listKategori[p0]

        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getCount(): Int {
            return listKategori.size
        }

    }
}



 */


