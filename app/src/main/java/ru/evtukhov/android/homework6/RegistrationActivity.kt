package ru.evtukhov.android.homework6

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.coroutines.launch
import ru.evtukhov.android.homework6.api.Token
import java.io.IOException

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        btn_register.setOnClickListener {
            val login = edt_registration_login.text.toString()
            val password1 = edt_registration_password.text.toString()
            val password2 = edt_registration_repeat_password.text.toString()
            if (login == "" || password1 == "") {
                Toast.makeText(this, R.string.empty, Toast.LENGTH_SHORT).show()
            } else if (password1 != password2) {
                Toast.makeText(this, R.string.repeat_password, Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    switchProgressRegBar(true)
                    try {
                        val response = Repository.register(login, password1)
                        if (response.isSuccessful) {
                            val token: Token? = response.body()
                            saveToken(token, this@RegistrationActivity)
                            Toast.makeText(this@RegistrationActivity, R.string.success, Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this@RegistrationActivity, R.string.registration_failed, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: IOException) {
                        Toast.makeText(this@RegistrationActivity, R.string.registration_failed, Toast.LENGTH_SHORT).show()
                    } finally {
                        switchProgressRegBar(false)
                    }
                }
            }
        }
    }
    private fun switchProgressRegBar(isLaunch: Boolean) {
        if (isLaunch) {
            progress_circular_reg.isVisible = true
            btn_register.isEnabled = false
        } else {
            progress_circular_reg.isVisible = false
            btn_register.isEnabled = true
        }
    }
}
