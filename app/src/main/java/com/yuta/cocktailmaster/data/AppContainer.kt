package com.yuta.cocktailmaster.data

import android.content.Context

// いくつかの派生を作ることも考えてinterfaceにしておく
// ViewModelファクトリーで使うためのファイル
interface DefaultContainer {
    val context: Context
}

class AppContainer(context: Context) : DefaultContainer {
    override val context: Context = context
}