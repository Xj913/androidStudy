package example.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.xiajun.base.BaseCompoModel
import com.xiajun.data.http.function.impl.UserNetSourceImpl
import com.xiajun.data.prefs.AppPrefsManager
import com.xiajun.entity.UserInfo
import com.xiajun.http.core.safeApiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.security.MessageDigest
import java.security.cert.X509Certificate
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection
import kotlin.experimental.and

@HiltViewModel
class LoginModel @Inject constructor() : BaseCompoModel() {

    val phone = MutableStateFlow("")
    val password = mutableStateOf("")
    var user = MutableLiveData<UserInfo>()
    val loginState = MutableLiveData<Boolean>()

    init {

    }
    fun login() {
        /*   new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        getPubkey();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();*/

        val u = UserInfo("sfsf", "fasfgasfg")
        user.value = u
        u.password = "123456"
        //loginSucceed.set(true);
        loginState.value = true

    }

    fun getLoginUser() {
        //val a = getPreferences().currentAccount
    }

    fun login2(userName: String, pass: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            val s = async { safeApiCall { UserNetSourceImpl.getToken() } }.await()
            if (s.isSucceed()) {
                AppPrefsManager.getInstance().setSignKey(s.data?.access_token)
                val s1 = async {
                    safeApiCall { UserNetSourceImpl.login2(userName, pass) }
                }.await()
                if (s1.isSucceed()) {
                    logI("login", s1.data.toString())
                }
            }
        }
    }

    fun login(userName: String, password: String) {
        val user = UserInfo(userName, password)
        AppPrefsManager.getInstance().currentUser = user
        viewModelScope.launch(Dispatchers.IO) {
            val s = async {
                synData()
                UserNetSourceImpl.login(userName, password)
            }.await()
            if (s.isSucceed()) {
                withContext(Dispatchers.Main) {
                    loginState.value = true
                }
            }
        }
    }

    suspend fun synData() {
        /*curUser = AccountManager.getInstance().getCurrentUser();
        List<User> friends = UserDBManager.getInstance().getAllMyFriend(curUser.getUserId());
        if (friends != null && friends.size() > 0) {
        } else {
            for (int i = 2; i < 10; i++) {
                User user = new User(i + "", "123456");
                UserDBManager.getInstance().insertUser(user);
            }

            for (int i = 2; i < 5; i++) {
                Friend bean = new Friend();
                bean.setFriendId(i + "");
                bean.setOwnerId(curUser.getUserId());
                bean.setMark("朋友" + i);
                UserDBManager.getInstance().insertFriend(bean);
            }

        }*/
    }

    @Throws(Exception::class)
    fun getPubkey() {
        val url = URL("https://watch.lemonnc.com")
        val conn = url.openConnection() as HttpsURLConnection
        //conn.setInstanceFollowRedirects(false);
        conn.connect()
        //initData(conn.getInputStream());
        //Certificate[] certs0 = conn.getLocalCertificates();    //会拿到完整的证书链
        val certificates = conn.serverCertificates    //会拿到完整的证书链
        val cert = certificates[0] as X509Certificate    //cert[0]是证书链的最下层
        println("序号：" + cert.serialNumber)
        println("颁发给：" + cert.subjectDN.name)
        println("颁发者：" + cert.issuerDN.name)
        println("起始：" + cert.notBefore)
        println("过期：" + cert.notAfter)
        println("算法：" + cert.sigAlgName)
        println("公钥：" + cert.publicKey.encoded)
        println("指纹：" + getThumbPrint(cert)!!)
        conn.disconnect()
    }

    @Throws(Exception::class)
    private fun getThumbPrint(cert: X509Certificate): String? {
        val md = MessageDigest.getInstance("SHA-1")
        val der = cert.encoded
        md.update(der)
        val digest = md.digest()
        return bytesToHexString(digest)
    }

    private fun bytesToHexString(src: ByteArray?): String? {
        val stringBuilder = StringBuilder("")
        if (src == null || src.isEmpty()) {
            return null
        }
        for (i in src.indices) {
            val v = src[i].and(0xFF.toByte())
            val hv = Integer.toHexString(v.toInt())
            if (hv.length < 2) {
                stringBuilder.append(0)
            }
            stringBuilder.append(hv)
        }
        return stringBuilder.toString()
    }
}
