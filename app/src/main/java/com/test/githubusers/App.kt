package com.test.githubusers

import android.app.Application
import com.test.githubusers.di.dataModule
import com.test.githubusers.di.presentationModule
import com.test.githubusers.di.retrofitModule
import com.test.githubusers.di.roomModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class App: Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(androidXModule(this@App))
        import(retrofitModule)
        import(roomModule)
        import(dataModule)
        import(presentationModule)
    }


}

