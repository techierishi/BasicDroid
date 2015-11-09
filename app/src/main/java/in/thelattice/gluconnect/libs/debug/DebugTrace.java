package in.thelattice.gluconnect.libs.debug;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import in.thelattice.gluconnect.R;

/**
 * Created by techierishi on 8/11/15.
 */
public class DebugTrace  extends Activity {
    WebView webComp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_trace);
        webComp = (WebView) findViewById(R.id.webC);

        WebSettings webSettings = webComp.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);

        webComp.setWebViewClient(new MyBrowser());

        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            DBG.d("DebugTrace", "No SDCARD");
        } else {
            webComp.loadUrl("file://"
                    + Environment.getExternalStorageDirectory()
                    + "/vpdbg.html");
        }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}