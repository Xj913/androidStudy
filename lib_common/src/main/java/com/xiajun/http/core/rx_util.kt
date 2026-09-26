package com.xiajun.http.core

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers


  fun <T : Any> io2uiOtf(): ObservableTransformer<T, T> {
      return ObservableTransformer { s: Observable<T> ->
          s.subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
      }
  }

