package com.example.musicplayerapp.ui.splash

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.musicplayerapp.databinding.ActivitySplashBinding
import com.example.musicplayerapp.ui.main.MainActivity
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Splash : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.start.setOnClickListener {

            PermissionX.init(this)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .request { allGranted, _, deniedList ->
                    if (allGranted) {
                        startActivity(Intent(this, MainActivity::class.java))

                    } else {
                        Toast.makeText(
                            this,
                            "These permissions are denied: $deniedList",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }


}