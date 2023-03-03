package com.dicoding.mysubmissionapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mysubmissionapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var list = ArrayList<Crypto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Crypto App Information"

        binding.rvCrypto.setHasFixedSize(true)
        list.addAll(getListCrypto())
        showRecylerList()
    }

    private fun getListCrypto(): ArrayList<Crypto> {
        val dataNameCrypto = resources.getStringArray(R.array.data_name_crypto)
        val dataTotalSupply = resources.getStringArray(R.array.data_total_supply_crypto)
        val dataDescriptionCrypto = resources.getStringArray(R.array.data_description_crypto)
        val dataPhotoCrypto = resources.getStringArray(R.array.data_photo_crypto)
        val listCrypto = ArrayList<Crypto>()
        for (i in dataNameCrypto.indices) {
            val crypto = Crypto(dataNameCrypto[i], dataTotalSupply[i], dataDescriptionCrypto[i], dataPhotoCrypto[i])
            listCrypto.add(crypto)
        }
        return listCrypto
    }

    private fun showRecylerList() {
        binding.rvCrypto.layoutManager = LinearLayoutManager(this)
        val listCryptoAdapter = ListCryptoAdapter(list) {
            val moveToDetailActivity = Intent(this@MainActivity, DetailActivity::class.java)
            moveToDetailActivity.putExtra(DetailActivity.EXTRA_CRYPTO, it)
            startActivity(moveToDetailActivity)
        }
        binding.rvCrypto.adapter = listCryptoAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about -> {
                val moveToAboutActivity = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(moveToAboutActivity)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}