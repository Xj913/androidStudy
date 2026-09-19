package example.web_service;


import android.annotation.SuppressLint;
import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope

import com.style.base.BaseCompoModel;
import com.style.base.BaseViewModel;
import com.style.data.http.exception.HttpExceptionConsumer;
import com.style.data.http.function.impl.UserNetSourceImpl;
import com.style.data.http.function.impl.WebNetSourceImpl;
import com.style.data.prefs.AppPrefsManager;
import com.style.http.exception.HttpResultException;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody;


class WebServiceViewModel : BaseCompoModel() {

    val content = mutableStateOf("")

    fun getPhoneInfo(phone: String) {
        viewModelScope.launch {
            val s = withContext(Dispatchers.IO) {
                val r: ResponseBody = UserNetSourceImpl.test()
                val code = r.contentLength()
            }
        }
    }

    fun getWeather(code: String) {
        viewModelScope.launch {
            runCatching {
                val s = async {
                    WebNetSourceImpl.getWeather(code)
                }
            }.onSuccess { it ->
                withContext(Dispatchers.Main) {

                }
            }

            }.onFailure {

            }

            content.value = s.await()
            showToast("查询天气成功")
        }
    }

    public void getKuaiDi(String s, String s1) {
        Disposable d = WebNetSourceImpl.getKuaiDi("", "")
                .flatMap(kuaiDis -> WebNetSourceImpl.getKuaiDi("", ""))
                .subscribe(s2 ->
                                content.postValue(s2)
                        , new HttpExceptionConsumer() {
                            @Override
                            public void accept(@NotNull Throwable e) {
                                super.accept(e);
                            }

                            @Override
                            public void onOtherError(@NotNull HttpResultException t) {
                                super.onOtherError(t);
                            }
                        });
        addTask(d);
    }

    public void login(final String userName, final String pass) {
        Disposable d = UserNetSourceImpl.getToken().flatMap(r -> {
            if (TextUtils.isEmpty(r.data.access_token))
                throw HttpResultException.serverError();
            Log.e(getTAG(), r.data.access_token);
            AppPrefsManager.getInstance().setSignKey(r.data.access_token);
            return UserNetSourceImpl.login2(userName, pass);
        }).subscribe(userInfo ->
                        Log.e(getTAG(), userInfo.toString())
                , new HttpExceptionConsumer()
                , ()  -> Log.e(getTAG(), "sfdfsd"));
        addTask(d);
    }
}
