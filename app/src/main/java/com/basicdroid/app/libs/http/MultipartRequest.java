package com.basicdroid.app.libs.http;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 8/29/2015.
 */
public class MultipartRequest extends Request<String> {


    private static final String FILE_PART_NAME = "file_name";
    private static final String STRING_PART_NAME = "text";

    private final Response.Listener<String> mListener;
    private File mFilePart;
    private String mFilePath;

    private HashMap<String, String> postData;


    public MultipartRequest(String url, Response.ErrorListener errorListener, Response.Listener<String> listener, File file, HashMap<String, String> _postData) {
        super(Method.POST, url, errorListener);

        mListener = listener;
        mFilePart = file;
        postData = _postData;

    }

    public MultipartRequest(String url, Response.ErrorListener errorListener, Response.Listener<String> listener, String filePath, String stringPart) {
        super(Method.POST, url, errorListener);

        mListener = listener;
        mFilePath = filePath;
        mFilePart = new File(mFilePath);
    }


    @Override
    public String getBodyContentType() {
        //String boundary = "*****";
        String boundary = "===" + System.currentTimeMillis() + "===";
        return "multipart/form-data;boundary=" + boundary;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {

        //return getEntityByteArray(mFilePart);
        return getEntityByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        return Response.success("Uploaded", getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    public byte[] getEntityByteArray() {
        byte[] barr = null;
        String charset = "UTF-8";

        try {
            MultipartByteArray multipart = new MultipartByteArray(charset);

            multipart.addHeaderField("User-Agent", "CodeJava");
            multipart.addHeaderField("Test-Header", "Header-Value");

            Iterator it = postData.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
                multipart.addFormField(pair.getKey()+"", "" + pair.getValue());
                it.remove(); // avoids a ConcurrentModificationException
            }


            //multipart.addFormField("keywords", "Java,upload,Spring");

            multipart.addFilePart("transporter_signature", mFilePart);

            barr = multipart.finish();


        } catch (IOException ex) {
            System.err.println(ex);
        }

        return barr;
    }

    public byte[] getEntityByteArray(File mFilePart) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Log.e("Image filename", mFilePart.getAbsolutePath());
        DataOutputStream outputStream = null;

        String pathToOurFile = mFilePart.getAbsolutePath();
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024;
        try {
            FileInputStream fileInputStream = new FileInputStream(mFilePart);

            outputStream = new DataOutputStream(baos);

            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            String connstr = null;
            connstr = "Content-Disposition: form-data; name=\"" + FILE_PART_NAME + "\";filename=\""
                    + pathToOurFile + "\"" + lineEnd;
            connstr += "Content-Type: application/octet-stream" + lineEnd;
            Log.i("Connstr", connstr);

            outputStream.writeBytes(connstr);
            outputStream.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // Read file
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            Log.e("Image length", bytesAvailable + "");
            try {
                while (bytesRead > 0) {
                    try {
                        outputStream.write(buffer, 0, bufferSize);
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();

                        return null;
                    }
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
            } catch (Exception e) {
                e.printStackTrace();

                return null;
            }
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens
                    + lineEnd);

            String post_datas = null;
            if (postData != null) {
                for (String key : postData.keySet()) {

                    post_datas = "Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd + lineEnd;
                    outputStream.writeBytes(post_datas);
                    outputStream.writeBytes(postData.get(key));
                    outputStream.writeBytes(lineEnd);
                    outputStream.writeBytes(twoHyphens + boundary + twoHyphens
                            + lineEnd);
                }
            }

            Log.i("outputStream", outputStream.toString());

            fileInputStream.close();
            outputStream.flush();
            outputStream.close();
            outputStream = null;
        } catch (Exception ex) {

            Log.e("Send file Exception", ex.getMessage() + "");
            ex.printStackTrace();
        }
        String s = new String(baos.toByteArray());
        Log.e("Byte Array : ", s + "");

        return baos.toByteArray();
    }
}
