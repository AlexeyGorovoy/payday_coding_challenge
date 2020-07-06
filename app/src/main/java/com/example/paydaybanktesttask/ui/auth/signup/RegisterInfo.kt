package com.example.paydaybanktesttask.ui.auth.signup

import androidx.annotation.StringRes
import com.example.paydaybanktesttask.R
import com.example.paydaybanktesttask.util.isEmailValid
import com.example.paydaybanktesttask.util.isPasswordValid
import java.util.*

data class RegisterInfo(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val male: Boolean,
    val dateOfBirth: Date? = null
) {
    @StringRes
    fun getValidationStringResMessageIfNeeded(): Int? {
        return when {
            firstName.isBlank() -> R.string.not_valid_first_name
            lastName.isBlank() -> R.string.not_valid_last_name
            phone.isBlank() -> R.string.not_valid_phone
            !isEmailValid(email) -> R.string.not_valid_email
            !isPasswordValid(password) -> R.string.not_valid_password
            !isPasswordValid(confirmPassword) -> R.string.not_valid_password
            password != confirmPassword -> R.string.not_confirmed
            dateOfBirth == null -> R.string.not_valid_date_of_birth
            else -> null
        }
    }
}