package com.test.githubusers.presentation

import android.os.Bundle
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
        viewModel.getUserData().observe(this, Observer {
            adapter.setItems(it)
        })
        viewModel.getLoadingState().observe(this, Observer {
//            if (it) progress.show() else progress.hide()
        })
    }

    override val kodein: Kodein by kodein()
}
