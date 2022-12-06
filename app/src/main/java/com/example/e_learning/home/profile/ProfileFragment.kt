package com.example.e_learning.home.profile

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.e_learning.auth.LoginActivity
import com.example.e_learning.R
import com.example.e_learning.zroomdatabase.ELEARNINGDB
import com.example.e_learning.home.profile.dataprofile.ResponseProfile
import com.example.e_learning.databinding.FragmentProfileBinding
import com.example.e_learning.home.profile.camera.CameraActivity
import com.example.e_learning.home.profile.dataprofile.Profile
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.itextpdf.barcodes.BarcodeQRCode
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.TextAlignment
import com.shashank.sony.fancytoastlib.FancyToast
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
//import com.itextpdf.barcodes.BarcodeQRCode
//import com.itextpdf.io.image.ImageDataFactory
//import com.itextpdf.io.source.ByteArrayOutputStream
//import com.itextpdf.kernel.colors.ColorConstants
//import com.itextpdf.kernel.geom.PageSize
//import com.itextpdf.kernel.pdf.PdfDocument
//import com.itextpdf.kernel.pdf.PdfName.Table
//import com.itextpdf.kernel.pdf.PdfWriter
//import com.itextpdf.layout.Document
//import com.itextpdf.layout.element.Cell
//import com.itextpdf.layout.element.Image
//import com.itextpdf.layout.element.Paragraph
//import com.itextpdf.layout.element.Table
//import com.itextpdf.layout.property.HorizontalAlignment
//import com.itextpdf.layout.property.TextAlignment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONObject
//import java.io.File
//import java.io.FileOutputStream
import java.nio.charset.StandardCharsets


class ProfileFragment : Fragment() {
    val db by lazy { activity?.let { ELEARNINGDB(it) } }
//    private val id = "idKey"
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences? = null
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val channelLogout = "channelLogoutNotification"
    private val notificationId = 10
    private var queue: RequestQueue? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        createNotificationChannel()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val LogoutButton: FloatingActionButton = view.findViewById(R.id.floatingActionLogout)
        val logout = Intent(this.getActivity(), LoginActivity::class.java)
        val camera = button
        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        queue = Volley.newRequestQueue(requireContext())

        camera.setOnClickListener{
            val moveCamera = Intent(activity, CameraActivity::class.java)
            startActivity(moveCamera)
        }

        allProfile()

//        CoroutineScope(Dispatchers.IO).launch {
//            val profile = db?.profileDAO()?.getProfile(sharedPreferences!!.getString(id,"")!!.toInt())?.get(0)
//            binding.username.setText(profile?.username)
//            binding.password.setText(profile?.password)
//            binding.email.setText(profile?.email)
//            binding.noTelp.setText(profile?.noTelp)
//            binding.tglLahir.setText(profile?.tglLahir)
//
            binding.floatingActionUpdate.setOnClickListener {
                val moveEdit = Intent(activity, UpdateProfile::class.java)
                startActivity(moveEdit)
                activity?.finish()
            }
//        }

        LogoutButton.setOnClickListener(){
            val builder1 = AlertDialog.Builder(
                this.requireContext()
            )
            builder1.setMessage("Anda Yakin Ingin Meninggal Aplikasi ini ? ")
            builder1.setCancelable(true)

            builder1.setPositiveButton(
                "Yes"
            ) { dialog, id -> startActivity(logout)
            sendNotification()}

            builder1.setNegativeButton(
                "No"
            ) { dialog, id -> dialog.cancel() }

            val alert11 = builder1.create()
            alert11.show()


        }

        binding.buttonPdf.setOnClickListener{
            val username = binding.username.getText().toString()
            val email = binding.email.getText().toString()
            val tanggalLahir = binding.tglLahir.getText().toString()
            val notelp = binding.noTelp.getText().toString()
            createPdf(username, email,tanggalLahir,notelp)
        }
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val logoutNotif = NotificationChannel(channelLogout, name,  NotificationManager.IMPORTANCE_DEFAULT).apply{
                description = descriptionText
            }

            val notificationManager: NotificationManager =  requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(logoutNotif)
        }
    }

    private fun  sendNotification(){
        val builder = NotificationCompat.Builder(requireContext(), channelLogout)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle("Berhasil Logout!!")
            .setContentText("Sampai Jumpa Lagi")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Thor: Love and Thunder menceritakan Thor dalam sebuah perjalanan yang belum pernah ia jalani – pencariankedamaian batin. Namun, masa pensiunnya terganggu oleh seorang pembunuh galaksi yang dikenal sebagai Gorr sang Dewa Jagal , yang ingin memusnahkan para dewa.")
                    .setSummaryText("Pembelajaran Hari Ini Sudah Selesai")

            )

        with(NotificationManagerCompat.from(requireContext())){
            notify(notificationId, builder.build())
        }
    }

    private fun allProfile(){
        val sp = requireActivity().getSharedPreferences("user", 0)
        val idLoginProfile : Int = sp.getInt("id",0)
        Log.d("idyanglogin", idLoginProfile.toString())
//        binding.linearLayout3.showLoading()
        val StringRequest: StringRequest = object : StringRequest(Method.GET, ProfileApi.GET_BY_ID_URL + idLoginProfile,
            Response.Listener { response->
                Log.d("Responss", response)
                val gson = Gson()
//              val profile = gson.fromJson(response, ResponseProfile::class.java)
                val profile = gson.fromJson(response, Array<Profile>::class.java)
                Log.d("Profile", profile.toString())


                binding!!.username.setText(profile[0].username)
                binding!!.email.setText(profile[0].email)
                binding!!.tglLahir.setText(profile[0].tglLahir)
                binding!!.noTelp.setText(profile[0].tglLahir)

//                binding.linearLayout3.hideLoading()
            }, Response.ErrorListener { error->
//                binding.linearLayout3.hideLoading()
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(requireActivity(),errors.getString("message"),FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                }catch (e: Exception){
                    FancyToast.makeText(requireActivity(),e.message,FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                }
            }
        ){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String,String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(StringRequest)
    }


    private fun createPdf(username: String, email: String, tlp: String, tglLahir: String) {
        //ini berguna untuk akses Writing ke Storage HP kalian dalam mode Download.
        //harus diketik jangan COPAS!!!!
        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        val file = File(pdfPath, "PDFNameTag.pdf")
        FileOutputStream(file)
//        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
//        val file = File(pdfPath, "pdf_10541.pdf")
//        FileOutputStream(file)

        //inisaliasi pembuatan PDF
        val writer = PdfWriter(file)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)
        pdfDocument.defaultPageSize = PageSize.A6
        document.setMargins(2f, 1f, 2f, 1f)
        @SuppressLint("UseCompatLoadingForDrawables") val d = getDrawable(requireContext(),R.drawable.welcome_notif2)

        //penambahan gambar pada Gambar atas
//        val bitmap = (d as BitmapDrawable?)!!.bitmap
//        val stream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//        val bitmapData = stream.toByteArray()
//        val imageData = ImageDataFactory.create(bitmapData)
//        val image = Image(imageData)
        val namapengguna = Paragraph("Identitas Pengguna").setBold().setFontSize(24f)
            .setTextAlignment(TextAlignment.CENTER)
        val group = Paragraph(
            """
                        Berikut adalah
                        Name Tag Aplikasi E-Learning A3
                        """.trimIndent()).setTextAlignment(TextAlignment.CENTER).setFontSize(12f)

        //proses pembuatan table
        val width = floatArrayOf(100f, 100f)
        val table = Table(width)
        //pengisian table dengan data-data
        table.setHorizontalAlignment(HorizontalAlignment.CENTER)
        table.addCell(Cell().add(Paragraph("Nama Pengguna")))
        table.addCell(Cell().add(Paragraph(username)))
        table.addCell(Cell().add(Paragraph("Email")))
        table.addCell(Cell().add(Paragraph(email)))
        table.addCell(Cell().add(Paragraph("No Telepon")))
        table.addCell(Cell().add(Paragraph(tlp)))
        table.addCell(Cell().add(Paragraph("Tanggal Lahir")))
        table.addCell(Cell().add(Paragraph(tglLahir)))


        //pembuatan QR CODE secara generate dengan bantuan IText7
        val barcodeQRCode = BarcodeQRCode(
            """
                                        $username
                                        $email
                                        $tlp
                                        $tglLahir
                                        """.trimIndent())
        val qrCodeObject = barcodeQRCode.createFormXObject(ColorConstants.BLACK, pdfDocument)
        val qrCodeImage = Image(qrCodeObject).setWidth(80f).setHorizontalAlignment(HorizontalAlignment.CENTER)

//        document.add(image)
        document.add(namapengguna)
        document.add(group)
        document.add(table)
        document.add(qrCodeImage)


        document.close()
        FancyToast.makeText(requireContext(),"Name Tag Created",FancyToast.LENGTH_LONG, FancyToast.INFO,false).show();
    }
}

