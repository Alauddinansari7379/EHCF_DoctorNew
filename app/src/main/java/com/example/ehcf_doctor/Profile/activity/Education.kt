package com.example.ehcf_doctor.Profile.activity

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import com.example.ehcf.Helper.myToast
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.ehcf_doctor.MainActivity.activity.MainActivity
import com.example.ehcf_doctor.Profile.modelResponse.*
import com.example.ehcf_doctor.Registration.activity.TermsAndConditions
import com.example.ehcf_doctor.Registration.modelResponse.ModelDegreeJava
import com.example.ehcf_doctor.Registration.modelResponse.ModelGender
import com.example.ehcf_doctor.databinding.ActivityEducationBinding
import com.example.myrecyview.apiclient.ApiClient
import com.giphy.sdk.analytics.GiphyPingbacks.context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class Education : AppCompatActivity() {
    private lateinit var binding: ActivityEducationBinding
    private val context: Context = this@Education
    private var spclistId = ""
    private var specilistId = ""
    private var progressDialog: ProgressDialog? = null
    var genderId = ""
    var langaugeId = ""
    var description = ""
    var experience = ""
    var clinicName = ""
    var clinicAddress = ""
    var clinicAddress1 = ""
    var clinicAddress2 = ""
    var address = ""
    var city = ""
    var country = ""
    var state = ""
    var pricing = ""
    private var services = ""
    private var degree = ""
    private var yearOfCom = ""
    private var collegeName = ""
    private var hospitalName = ""
    private var hospitalAddress = ""
    private var awards = ""
    private var awardsYear = ""
    private var membership = ""
    private var registration = ""
    private var registrationYear = ""
    var degreeList = ModelDegreeJava()
    var yearList =ModelYear()

    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEducationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)

        apiCallYearSpinner()
        apiCallDegreeSpinner()

//        binding.btnUpdate.setOnClickListener {
//          //  startActivity(Intent(this, TermsAndConditions::class.java))
//        }
        Log.e("sessionManager.college.toString()",sessionManager.college.toString())
        Log.e("sessionManager.college.toString()",sessionManager.hospitalName.toString())
        Log.e("sessionManager.college.toString()",sessionManager.hospitalAddress.toString())
        Log.e("sessionManager.college.toString()",sessionManager.registration.toString())
        binding.edtRegistration.setText(sessionManager.registration.toString())

        if (sessionManager.registration!!.isNotEmpty()) {
            binding.edtCollege.setText(sessionManager.college.toString())
            binding.edtHospitalName.setText(sessionManager.hospitalName.toString())
            binding.edtHospitalAddress.setText(sessionManager.hospitalAddress.toString())
            binding.edtRegistration.setText(sessionManager.registration.toString())
        }

//        awardYearList = readYearNameJSON(this)
//        registrationYearList = readYearNameJSON(this)
//
//        binding.spinnerYearOfCom.adapter =
//            ArrayAdapter<ModelYearList>(context, R.layout.simple_list_item_1, yearList)
//        binding.spinnerAwardYear.adapter =
//            ArrayAdapter<ModelYearList>(context, R.layout.simple_list_item_1, awardYearList)
//        binding.spinnerRegistrationYear.adapter =
//            ArrayAdapter<ModelYearList>(context, R.layout.simple_list_item_1, registrationYearList)

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
         spclistId = intent.getStringExtra("specilistId").toString()
        experience = intent.getStringExtra("experience").toString()
        genderId = intent.getStringExtra("genderId").toString()
        clinicName = intent.getStringExtra("clinicName").toString()
        clinicAddress = intent.getStringExtra("clinicAddress").toString()
        clinicAddress1 = intent.getStringExtra("clinicAddress1").toString()
        clinicAddress2 = intent.getStringExtra("clinicAddress2").toString()
        address = intent.getStringExtra("address").toString()
        city = intent.getStringExtra("city").toString()
        country = intent.getStringExtra("country").toString()
        state = intent.getStringExtra("state").toString()
        pricing = intent.getStringExtra("pricing").toString()
        services = intent.getStringExtra("services").toString()
        Log.e("specilistId", spclistId)
        Log.e("experience", experience)
        Log.e("genderId", genderId)
        Log.e("clinicName", clinicName)
        Log.e("clinicAddress", clinicAddress)
        Log.e("clinicAddress", clinicAddress1)
        Log.e("clinicAddress", clinicAddress2)
        Log.e("address", address)
        Log.e("city", city)
        Log.e("country", country)
        Log.e("state", state)
        Log.e("pricing", pricing)
        Log.e("services", services)




//        degreeList.add(ModelDegree("Select Your Degree"))
//        degreeList.add(ModelDegree("MBBS"))
//        degreeList.add(ModelDegree("MS"))
//        degreeList.add(ModelDegree("MD"))
//        degreeList.add(ModelDegree("BAMS"))
//        degreeList.add(ModelDegree("BHMS"))
//        degreeList.add(ModelDegree("BPT"))
//        degreeList.add(ModelDegree("B.VSc"))
//        degreeList.add(ModelDegree("BUMS"))
//        degreeList.add(ModelDegree("BSMS"))
//        degreeList.add(ModelDegree("BNYS"))




        binding.btnUpdate.setOnClickListener {
            if (binding.spinnerDegree.selectedItem.toString() == "Select Your Degree") {
                myToast(this@Education, "Please Select Your Degree!")
                binding.spinnerDegree.requestFocus()
                return@setOnClickListener
            }
            if (binding.spinnerYearOfCom.selectedItem.toString() == "Select Your Year") {
                myToast(this@Education, "Please Select Year Of Completion!")
                binding.spinnerYearOfCom.requestFocus()
                return@setOnClickListener
            }
            if (binding.edtCollege.text.isEmpty()) {
                binding.edtCollege.error = "Enter Collage Name"
                binding.edtCollege.requestFocus()
                return@setOnClickListener
            }
            if (binding.edtHospitalName.text.isEmpty()) {
                binding.edtHospitalName.error = "Enter Hospital Name"
                binding.edtHospitalName.requestFocus()
                return@setOnClickListener
            }
            if (binding.edtHospitalAddress.text.isEmpty()) {
                binding.edtHospitalAddress.error = "Enter Hospital Address"
                binding.edtHospitalAddress.requestFocus()
                return@setOnClickListener
            }

            if (binding.edtRegistration.text.isEmpty()) {
                binding.edtRegistration.error = "Enter Registration"
                binding.edtRegistration.requestFocus()
                return@setOnClickListener
            }
            if (binding.spinnerRegistrationYear.selectedItem.toString() == "Select Your Year") {
                myToast(this@Education, "Please Select Registration Year!")
                binding.spinnerRegistrationYear.requestFocus()
                return@setOnClickListener
            } else {

                collegeName = binding.edtCollege.text.toString().trim()
                hospitalName = binding.edtHospitalName.text.toString().trim()
                hospitalAddress = binding.edtHospitalAddress.text.toString().trim()
                registration = binding.edtRegistration.text.toString().trim()


                Log.e("specilistId", spclistId)
                Log.e("experience", experience)
                Log.e("genderId", genderId)
                Log.e("clinicName", clinicName)
                Log.e("clinicAddress", clinicAddress)
                Log.e("address", address)
                Log.e("city", city)
                Log.e("country", country)
                Log.e("state", state)
                Log.e("pricing", pricing)
                Log.e("services", services)
                Log.e("degree", degree)
                Log.e("yearOfCom", yearOfCom)
                Log.e("collegeName", collegeName)
                Log.e("hospitalName", hospitalName)
                Log.e("awards", awards)
                Log.e("awardsYear", awardsYear)
                Log.e("membership", membership)
                Log.e("registration", registration)
                Log.e("registrationYear", registrationYear)


                apiCallProFileUpdate()
            }
        }
    }
    private fun apiCallYearSpinner() {
        progressDialog = ProgressDialog(this@Education)
        progressDialog!!.setMessage("Loading..")
        progressDialog!!.setTitle("Please Wait")
        progressDialog!!.isIndeterminate = false
        progressDialog!!.setCancelable(true)

        //  progressDialog!!.show()

        ApiClient.apiService.getYear()
            .enqueue(object : Callback<ModelYear> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelYear>, response: Response<ModelYear>
                ) {


                    yearList = response.body()!!;
                    if (degreeList != null) {

                        //spinner code start
                        val items = arrayOfNulls<String>(yearList.result!!.size)

                        for (i in yearList.result!!.indices) {

                            items[i] = yearList.result!![i].year
                        }
                        val adapter: ArrayAdapter<String?> =
                            ArrayAdapter(this@Education, com.example.ehcf_doctor.R.layout.simple_list_item_1, items)
                        var spProvince: SmartMaterialSpinner<String>? = null
                        var spEmptyItem: SmartMaterialSpinner<String>? = null
                        binding.spinnerYearOfCom.adapter = adapter
                        binding.spinnerRegistrationYear.adapter = adapter

                        progressDialog!!.dismiss()

                        binding.spinnerYearOfCom.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    adapterView: AdapterView<*>?,
                                    view: View,
                                    i: Int,
                                    l: Long
                                ) {
                                    yearOfCom = yearList.result!![i].year
                                    yearOfCom = yearOfCom
                                    // Toast.makeText(this@RegirstrationTest, "" + id, Toast.LENGTH_SHORT).show()
                                }

                                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                            }
                        binding.spinnerRegistrationYear.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    adapterView: AdapterView<*>?,
                                    view: View,
                                    i: Int,
                                    l: Long
                                ) {
                                    registrationYear = yearList.result!![i].year
                                    registrationYear = registrationYear
                                    // Toast.makeText(this@RegirstrationTest, "" + id, Toast.LENGTH_SHORT).show()
                                }

                                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                            }

                    }
                }

                override fun onFailure(call: Call<ModelYear>, t: Throwable) {
                    myToast(this@Education, "Something went wrong")
                    progressDialog!!.dismiss()

                }

            })
    }
    private fun apiCallDegreeSpinner() {
        progressDialog = ProgressDialog(this@Education)
        progressDialog!!.setMessage("Loading..")
        progressDialog!!.setTitle("Please Wait")
        progressDialog!!.isIndeterminate = false
        progressDialog!!.setCancelable(true)

        //  progressDialog!!.show()

        ApiClient.apiService.getDegree()
            .enqueue(object : Callback<ModelDegreeJava> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelDegreeJava>, response: Response<ModelDegreeJava>
                ) {


                    degreeList = response.body()!!;
                    if (degreeList != null) {

                        //spinner code start
                        val items = arrayOfNulls<String>(degreeList.result!!.size)

                        for (i in degreeList.result!!.indices) {

                            items[i] = degreeList.result!![i].degree
                        }
                        val adapter: ArrayAdapter<String?> =
                            ArrayAdapter(this@Education, com.example.ehcf_doctor.R.layout.simple_list_item_1, items)
                        var spProvince: SmartMaterialSpinner<String>? = null
                        var spEmptyItem: SmartMaterialSpinner<String>? = null
                        binding.spinnerDegree.adapter = adapter
                        binding.spinnerDegree.setSelection(items.indexOf(sessionManager.qualification.toString()));
                     //   binding.spinnerDegree.setSelection(sessionManager.qualification.toString().toInt())

                        progressDialog!!.dismiss()

                        binding.spinnerDegree.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    adapterView: AdapterView<*>?,
                                    view: View,
                                    i: Int,
                                    l: Long
                                ) {
                                    degree = degreeList.result!![i].degree
                                    degree = degree
                                    // Toast.makeText(this@RegirstrationTest, "" + id, Toast.LENGTH_SHORT).show()
                                }

                                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                            }

                    }
                }

                override fun onFailure(call: Call<ModelDegreeJava>, t: Throwable) {
                    myToast(this@Education, "Something went wrong")
                    progressDialog!!.dismiss()

                }

            })
    }

    private fun apiCallProFileUpdate() {
        progressDialog = ProgressDialog(this@Education)
        progressDialog!!.setMessage("Loading..")
        progressDialog!!.setTitle("Please Wait")
        progressDialog!!.isIndeterminate = false
        progressDialog!!.setCancelable(true)

        progressDialog!!.show()

        ApiClient.apiService.profileUpdate(
            spclistId,
            "0",
            experience,
            genderId,
            sessionManager.id.toString(),
            clinicName,
            address,
            city,
            country,
            state,
            pricing,
            services,
            degree,
            yearOfCom,
            collegeName,
            hospitalAddress,
            hospitalName,
            registration,
            registrationYear,
            clinicAddress,
            clinicAddress1,
            clinicAddress2
        )
            .enqueue(object : Callback<ModelProfileUpdate> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelProfileUpdate>, response: Response<ModelProfileUpdate>
                ) {
                    if (response.code() == 500) {
                        myToast(this@Education, "Server Error")
                    }
                   else if (response.body()!!.status == 1) {
                        myToast(this@Education, response.body()!!.message)
                        sessionManager.clinicAddress = response.body()!!.result.clinic_address
                        sessionManager.clinicAddressOne = response.body()!!.result.clinic_address_one
                        sessionManager.clinicAddressTwo = response.body()!!.result.clinic_address_two
                        sessionManager.clinicAddress = response.body()!!.result.clinic_address
                        sessionManager.pricing = response.body()!!.result.pricing
                        sessionManager.experience =response.body()!!.result.experience
                        sessionManager.clinicName =response.body()!!.result.clinic_name
                        sessionManager.address =response.body()!!.result.address
                        sessionManager.services =response.body()!!.result.services
                        sessionManager.college =response.body()!!.result.college
                        sessionManager.hospitalName =response.body()!!.result.hos_name
                        sessionManager.city =response.body()!!.result.city
                        sessionManager.state =response.body()!!.result.state
                        sessionManager.hospitalAddress =response.body()!!.result.hos_address
                        sessionManager.registration =response.body()!!.result.registration
                        sessionManager.specialist =response.body()!!.result.specialist
                        progressDialog!!.dismiss()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        finish()
                        startActivity(intent)

                    } else {
                        myToast(this@Education, response.body()!!.message)
                    }

                }
                override fun onFailure(call: Call<ModelProfileUpdate>, t: Throwable) {
                    myToast(this@Education, "Something went wrong")
                    progressDialog!!.dismiss()

                }

            })
    }

}