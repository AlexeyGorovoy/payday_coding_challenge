package com.example.paydaybanktesttask.domain.network.model

import com.example.paydaybanktesttask.domain.repository.model.Customer
import com.google.gson.annotations.SerializedName

data class CustomerDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("First Name") val firstName: String,
    @SerializedName("Last Name") val lastName: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("dob") val dob: String,
    @SerializedName("phone") val phone: String

) : Dto<Customer> {

    override fun convert(): Customer {
        return Customer(
            id = id,
            firstName = firstName,
            lastName = lastName,
            gender = gender,
            email = email,
            password = password,
            dob = dob,
            phone = phone
        )
    }
}
//@POST("/customers")
//fun bookConsultation(@Body booking: CustomersBody): Single<CustomerDTO>


//    POST: http://localhost:3000/customers
//    Body:
//    {
//        "First Name": "Tom",
//        "Last Name": "Newton",
//        "gender": "female",
//        "email": "Tom.Newton@example.com",
//        "password": "springs",
//        "dob": "1993-02-12T08:22:24.377Z",
//        "phone": "1049520521"
//    }
//
//    Response:
//    {
//        "First Name": "Nadiah",
//        "Last Name": "Spoel",
//        "gender": "female",
//        "email": "Tom.Newton@example.com",
//        "password": "springs",
//        "dob": "1993-02-12T08:22:24.377Z",
//        "phone": "1049520521",
//        "id": 5
//    }