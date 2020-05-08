package com.test.githubusers.presentation

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.githubusers.R
import com.test.githubusers.di.activityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class MainActivity : AppCompatActivity(), KodeinAware {

    private val viewModel: GithubUsersViewModel by activityViewModel()
    private val adapter: UsersAdapter = UsersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userRecyclerView.adapter = adapter
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.getFilteredData().observe(this, Observer {
            adapter.setItems(it)
        })
        viewModel.getLoadingState().observe(this, Observer {
            if (it) progress.show() else progress.hide()
        })
        viewModel.getError().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()gi
        })
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
    }

    private fun handleIntent(intent: Intent){
        if (Intent.ACTION_SEARCH.equals(intent.action)){
            intent.getStringExtra(SearchManager.QUERY)?.let {
                viewModel.filterData(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as? SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as? SearchView
        val componentName = ComponentName(applicationContext, MainActivity::class.java)
        val info = searchManager?.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(info)
        searchView?.setOnSearchClickListener {
                if(searchView.query.isEmpty()){
                    searchView.setQuery("",true)
                }
        }
        return true
    }

    override val kodein: Kodein by kodein()

}
