package com.example.ehcf_doctor.Login.modelResponse

data class Result(
    val additional_qualification: String,
    val booking_commission: String,
    val c_id: String,
    val c_stat: String,
    val created_at: String,
    val description: Any,
    val doctor_name: String,
    val document_approved_status: String,
    val document_update_status: String,
    val earnings: String,
    val email: String,
    val experience: String,
    val clinic_name: String,
    val address: String,
    val services: String,
    val college: String,
    val hos_name: String,
    val hos_address: String,
    val registration: String,
    val fcm_token: String,
    val gender: String?=null,
    val hospital_id: String,
    val pricing: String,
    val reg_no: String,
    val clinic_address: String,
    val clinic_address_one: String,
    val clinic_address_two: String,
    val id: Int,
    val is_recommended: String,
    val no_of_ratings: String,
    val online_status: String,
    val overall_ratings: String,
    val password: String,
    val phone_number: String,
    val phone_with_code: String,
    val profile_image: String,
    val profile_status: String,
    val qualification: String,
    val specialist: String,
    val status: String,
    val sub_specialist: Any,
    val unique_code: String,
    val updated_at: String,
    val wallet: String
)