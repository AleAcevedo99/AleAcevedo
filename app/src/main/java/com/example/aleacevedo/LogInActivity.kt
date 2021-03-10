package com.example.aleacevedo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.aleacevedo.Entity.EntityUser
import com.example.aleacevedo.Entity.ListUsers
import com.example.aleacevedo.Tools.Constants
import com.example.aleacevedo.databinding.ActivityLogInBinding
import com.google.android.material.snackbar.Snackbar

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private var listUsers = ListUsers()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLogInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setTitle(R.string.txt_log_in)

        binding.btnOk.setOnClickListener{
            if(binding.edtEmail.text.trim().isNotEmpty() && binding.edtPassword.text.trim().isNotEmpty()){
                val user = EntityUser()
                user.email = binding.edtEmail.text.toString()
                user.password = binding.edtPassword.text.toString()
                val userPosition = listUsers.existsUser(user)
                if(userPosition != -1){
                    cleanForm()
                    val intent = Intent(this@LogInActivity, HomeActivity::class.java).apply{
                        putExtra(Constants.USER, userPosition)
                        finish()
                    }
                    startActivity(intent)
                }else{
                    Snackbar.make(it, "Usuario o contraseña incorrectos.",
                            Snackbar.LENGTH_SHORT).show()
                }
            }else{
                Snackbar.make(it, "Ingrese el usuario y contraseña.",
                        Snackbar.LENGTH_SHORT).show()
            }


        }
    }

    fun cleanForm(){
        binding.edtEmail.setText("")
        binding.edtPassword.setText("")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_register, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itmRegister -> {
                val intent = Intent(this@LogInActivity, RegisterActivity::class.java)
                finish()
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }
}