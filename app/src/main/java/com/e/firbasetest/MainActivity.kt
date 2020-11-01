package com.e.firbasetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    // User
    private lateinit var id: TextView
    private lateinit var name: TextView
    private lateinit var surname: TextView
    private lateinit var group: TextView

    //Personal
    private lateinit var job: TextView
    private lateinit var rang: TextView
    private lateinit var gmail: TextView
    private lateinit var address: TextView

    //SomeData
    private lateinit var editText: EditText
    private lateinit var save: Button
    private lateinit var text: TextView
    private lateinit var get: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()


        getUserData()
        getPersonalData()
        getSomeData()




    }

    fun init(){

        //User
        id = findViewById(R.id.idTV)
        name = findViewById(R.id.nameTV)
        surname = findViewById(R.id.surnameTV)
        group = findViewById(R.id.groupTV)

        //Personal
        job = findViewById(R.id.jobTV)
        rang = findViewById(R.id.rangTV)
        address = findViewById(R.id.addressTV)
        gmail = findViewById(R.id.gmailTV)

        //SomeData
        editText = findViewById(R.id.textET)
        save = findViewById(R.id.saveBTN)
        text = findViewById(R.id.textTV)
        get = findViewById(R.id.getBTN)
    }

    fun getUserData(){
        ApiService
                .getDataApi()
                .getUserData()
                .enqueue(object : Callback<User>{
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        val user: User? = response.body()

                        id.append(user?.id.toString())
                        name.append(user?.name)
                        surname.append(user?.surname)
                        group.append(user?.group)

                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(applicationContext, "Can not get user data!", Toast.LENGTH_SHORT).show()
                    }

                })
    }

    fun getPersonalData(){
        ApiService
                .getDataApi()
                .getPersonalData()
                .enqueue(object : Callback<Personal>{
                    override fun onResponse(call: Call<Personal>, response: Response<Personal>) {
                        val personal: Personal? = response.body()

                        job.append(personal?.job)
                        rang.append(personal?.rang)
                        gmail.append(personal?.gmail)
                        address.append(personal?.address)

                    }

                    override fun onFailure(call: Call<Personal>, t: Throwable) {
                        Toast.makeText(applicationContext, "Can not get personal data!", Toast.LENGTH_SHORT).show()
                    }

                })
    }

    fun getSomeData(){
        var database: DatabaseReference = FirebaseDatabase.getInstance().reference
        var ref: DatabaseReference = database.child("somedata")

        save.setOnClickListener{
            val value: String = editText.text.toString()

            ref.setValue(value)

        }

        get.setOnClickListener{
            ref.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    val value: String? = p0.getValue(String::class.java)
                    text.setText(value)
                }

                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(applicationContext,"Error fetching data!", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }


}