package com.jimbonlemu.fundamental_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jimbonlemu.fundamental_android.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { textView, actionId, keyEvent ->
                searchBar.setText(searchView.text)
                searchView.hide()
                Toast.makeText(this@MenuActivity, searchView.text, Toast.LENGTH_SHORT).show()
                false
            }
        }
    }
}