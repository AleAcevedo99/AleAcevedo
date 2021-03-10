package com.example.aleacevedo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aleacevedo.Entity.EntityUser
import com.example.aleacevedo.Entity.ListUsers
import com.example.aleacevedo.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var listUsers = ListUsers()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setTitle(R.string.txt_register)

        binding.btnRegister.setOnClickListener {
            val user = EntityUser()

            if (binding.edtEmail.text.trim().isNotEmpty() && binding.edtPhone.text.trim().isNotEmpty()
                && binding.spnGender.selectedItemPosition != 0 && binding.edtPassword.text.trim().isNotEmpty()) {

                user.email = binding.edtEmail.text.toString()
                user.phoneNumber = binding.edtPhone.text.toString()
                user.password = binding.edtPassword.text.toString()
                user.gender = binding.spnGender.selectedItemPosition
                if(listUsers.add(user) != -1){
                    cleanForm()
                    Toast.makeText(this@RegisterActivity, "Usuario registrado", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, LogInActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Snackbar.make(it, "El email ya se encuentra registrado",
                        Snackbar.LENGTH_SHORT).show()
                }
            }else{
                Snackbar.make(it, "El email, teléfono, constaseña y género son obligatorios",
                    Snackbar.LENGTH_SHORT).show()
            }

        }
    }

    fun cleanForm(){
        binding.edtEmail.setText("")
        binding.edtPhone.setText("")
        binding.edtPassword.setText("")
        binding.spnGender.setSelection(0)
    }
}