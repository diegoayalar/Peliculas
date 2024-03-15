package ayala.diego.peliculas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var btnIngresar: Button
    private lateinit var btnRegistro: Button
    private lateinit var tvRecuperacion: TextView
    private lateinit var etCorreo: EditText
    private lateinit var etContra: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnIngresar = findViewById(R.id.btn_ingresar)
        btnRegistro = findViewById(R.id.btn_registro)
        tvRecuperacion = findViewById(R.id.tv_recuperacion)
        etCorreo = findViewById(R.id.et_correo_login)
        etContra = findViewById(R.id.et_contra_login)

        // Initialize Firebase Auth
        auth = Firebase.auth

        btnRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        btnIngresar.setOnClickListener {
            var correo: String = etCorreo.text.toString()
            var contra: String = etContra.text.toString()

            if(correo.isNotBlank() && contra.isNotBlank()) {
                auth.signInWithEmailAndPassword(correo, contra)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Datos incorrectos.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            //updateUI(null)
                        }
                    }
            } else {
                Toast.makeText(this, "Ingresar datos", Toast.LENGTH_SHORT).show()
            }
        }

        tvRecuperacion.setOnClickListener {
            val intent = Intent(this, RecuperacionActivity::class.java)
            startActivity(intent)
        }
    }
}