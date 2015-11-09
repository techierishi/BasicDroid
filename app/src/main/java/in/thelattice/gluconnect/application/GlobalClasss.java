package in.thelattice.gluconnect.application;

import android.app.Application;

import in.thelattice.gluconnect.libs.icenet.IceNet;
import in.thelattice.gluconnect.libs.icenet.IceNetConfig;
import in.thelattice.gluconnect.util.CONST;
import io.paperdb.Paper;

/**
 * Created by techierishi on 8/11/15.
 */
public class GlobalClasss extends Application implements CONST{

    @Override
    public void onCreate() {
        super.onCreate();
        initIceNet();
        Paper.init(getApplicationContext());

    }

    public void initIceNet() {
        IceNetConfig config = new IceNetConfig.Builder()
                .setBaseUrl(REST_API.BASE_URL)
                .setContext(getApplicationContext())
                .build();
        IceNet.init(config);
    }
}
