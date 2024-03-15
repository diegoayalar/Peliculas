package ayala.diego.peliculas

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RecuperacionActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var etCorreo: EditText
    private lateinit var btnRecuperar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperacion)

        etCorreo = findViewById(R.id.et_correo_recuperacion)
        btnRecuperar = findViewById(R.id.btn_recuperar)

        // Initialize Firebase Auth
        auth = Firebase.auth

        btnRecuperar.isEnabled = false

        etCorreo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val isValidEmail = isValidEmail(s.toString())
                btnRecuperar.isEnabled = isValidEmail
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        btnRecuperar.setOnClickListener {
            var correo: String = etCorreo.text.toString()

            if(correo.isNotBlank()) {
                Firebase.auth.sendPasswordResetEmail(correo)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            //Log.d(TAG, "Email sent.")
                            Toast.makeText(this, "Se ha enviado un correo electrónico", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "No se pudo enviar el correo", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}