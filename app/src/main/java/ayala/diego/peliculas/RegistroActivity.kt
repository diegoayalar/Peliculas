package ayala.diego.peliculas

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegistroActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var etCorreo: EditText
    private lateinit var etContra1: EditText
    private lateinit var etContra2: EditText
    private lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        etCorreo = findViewById(R.id.et_correo_registro)
        etContra1 = findViewById(R.id.et_contra1)
        etContra2 = findViewById(R.id.et_contra2)
        btnRegistrar = findViewById(R.id.btn_registrar)

        // Initialize Firebase Auth
        auth = Firebase.auth

        btnRegistrar.setOnClickListener {
            var correo: String = etCorreo.text.toString()
            var contra1: String = etContra1.text.toString()
            var contra2: String = etContra2.text.toString()

            if(correo.isNotBlank() && contra1.isNotBlank() && contra2.isNotBlank()) {
                if(contra1 != contra2) {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                } else if(contra1.length < 8) {
                    Toast.makeText(this, "Las contraseña debe contener al menos 8 caracteres", Toast.LENGTH_SHORT).show()
                } else {
                    auth.createUserWithEmailAndPassword(correo, contra1)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser

                                // Sign in success, update UI with the signed-in user's information
                                Log.d("exito", "createUserWithEmail:success")
                                Toast.makeText(
                                    baseContext,
                                    "Se ha registrado correctamente ${user?.email}",
                                    Toast.LENGTH_SHORT,
                                ).show()
                                finish()
                                //updateUI(user)
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("error", "createUserWithEmail:failure", task.exception)
                                Toast.makeText(
                                    baseContext,
                                    "No se pudo registrar al usuario",
                                    Toast.LENGTH_SHORT,
                                ).show()
                                //updateUI(null)
                            }
                        }
                }
            } else {
                Toast.makeText(this, "Ingresar datos de correo y contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }
}