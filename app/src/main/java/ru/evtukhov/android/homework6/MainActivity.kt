package ru.evtukhov.android.homework6

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.evtukhov.android.homework6.api.Token
import java.io.IOException

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        btn_login.setOnClickListener {
            val login = edt_login.text.toString()
            val password = edt_password.text.toString()
            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, R.string.empty, Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    switchProgressBar(true)
                    try {
                        val response = Repository.authenticate(login, password)
                        if (response.isSuccessful) {
                            val token: Token? = response.body()
                            saveToken(token, this@MainActivity)
                            Toast.makeText(this@MainActivity, R.string.success, Toast.LENGTH_SHORT).show()
                            startActivityIfAuthorized()
                        } else {
                            Toast.makeText(this@MainActivity, R.string.authentication_failed, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: IOException) {
                        Toast.makeText(this@MainActivity, R.string.authentication_failed, Toast.LENGTH_SHORT).show()
                    } finally {
                        switchProgressBar(false)
                    }
                }
            }
        }

        btn_registration.setOnClickListener {
            val registrationIntent = Intent(this@MainActivity, RegistrationActivity::class.java)
            startActivity(registrationIntent)
        }
    }

    override fun onStart() {
        super.onStart()
        startActivityIfAuthorized()
    }

    private fun startActivityIfAuthorized() {
        val token = getToken(this)
        if (!token.isNullOrEmpty()) {
            val intent = Intent(this, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        cancel()
    }

    private fun switchProgressBar(isLaunch: Boolean) {
        if (isLaunch) {
            progress_circular_auth.visibility = View.VISIBLE
            btn_login.isEnabled = false
            btn_registration.isEnabled = false
        }
        else {
            progress_circular_auth.visibility = View.INVISIBLE
            btn_login.isEnabled = true
            btn_registration.isEnabled = true
        }
    }
}