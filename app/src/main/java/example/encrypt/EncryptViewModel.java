package example.encrypt;

import com.xiajun.base.BaseViewModel;
import com.xiajun.data.prefs.AppPrefsManager;
import com.xiajun.entity.UserInfo;

/**
 * Created by xiajun on 2018/6/21.
 */

public class EncryptViewModel extends BaseViewModel {


    public EncryptViewModel() {
        super();
    }

    public void saveUser() {
        UserInfo user = new UserInfo("123456789", "zxcvbnm");
        user.setSex("男");
        user.setTelPhone("17364814713");
        user.setUserName("夏军");
        user.setSignKey("osfsnffnuj ekrfasfhaweoirwefnejfwefaslfheoifhefhnewfwfwfpenpnmsnmfnejfic");
        AppPrefsManager.getInstance().saveUserEncrypt(user);
    }

    public void getUser() {
        AppPrefsManager.getInstance().getUserDecrypt();
    }
}
