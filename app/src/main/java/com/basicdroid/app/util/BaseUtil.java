package com.basicdroid.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.basicdroid.app.libs.debug.DBG;
import com.basicdroid.app.models.User;


public class BaseUtil implements CONST{
    Context ctx;



    public BaseUtil(Context _ctx) {
        ctx = _ctx;

    }


    public boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public void backupDatabase() {
        try {
            String inFileName = "/data/data/" + ctx.getPackageName()
                    + "/databases/velopaldb.sqlite";
            String outFileName = Environment.getExternalStorageDirectory()
                    + "/gluconnect.sqlite";
            brDatabase(inFileName, outFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restoreDatabase(String inFileName) {
        try {
            String outFileName = "/data/data/" + ctx.getPackageName()
                    + "/databases/gluconnect.sqlite";
            brDatabase(inFileName, outFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void brDatabase(String inFileName, String outFileName)
            throws IOException {
        // Open your local db as the input stream

        File dbFile = new File(inFileName);
        FileInputStream fis = new FileInputStream(dbFile);

        // Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outFileName);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        // Close the streams
        output.flush();
        output.close();
        fis.close();
    }

    public void setLoggedInUser(User usr, boolean is_loggedin) {
        SharedPreferences sharedPref = ctx.getSharedPreferences("logged_user",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (usr != null && is_loggedin) {
            editor.putString("user_id", "" + usr.getUser_id());
            editor.putBoolean("logged_in", true);

        } else {
            editor.putString("user_id", "");
            editor.putBoolean("logged_in", false);
        }

        editor.commit();
    }

    public User getLoggedInUser() {
        User usr = new User();
        SharedPreferences sharedPref = ctx.getSharedPreferences("logged_user",
                Context.MODE_PRIVATE);
        String user_id = sharedPref.getString("user_id", "");
        boolean logged_in = sharedPref.getBoolean("logged_in", false);
        boolean is_admin = sharedPref.getBoolean("is_admin", false);

        usr.setUser_id(user_id);


        return usr;

    }


    public long getUTS(String str_date) {

        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date;
            date = (Date) formatter.parse(str_date);

            return (date.getTime() / 1000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long getUTS(String str_date, String format) {

        try {
            DateFormat formatter = new SimpleDateFormat(format);
            Date date;
            date = (Date) formatter.parse(str_date);

            return (date.getTime() / 1000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long getUTSWithHHmmSS(String str_date) {

        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date;
            date = (Date) formatter.parse(str_date);

            return (date.getTime() / 1000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long getUTS_from_YYYYMMddHHmmSS(String str_date) {

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date;
            date = formatter.parse(str_date);

            return (date.getTime() / 1000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isDateOk(String dstr) {
        return dstr.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})");
    }

    public String dateFromUTS(long timeStamp) {
        String UNIX_DATE_FORMAT = "dd-MM-yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(UNIX_DATE_FORMAT);
        java.util.Date time = new java.util.Date((long) timeStamp * 1000L);

        return formatter.format(time);
    }

    public String month_dd_yyyyFromUTS(long timeStamp) {
        String UNIX_DATE_FORMAT = "MMMM dd, yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(UNIX_DATE_FORMAT);
        java.util.Date time = new java.util.Date((long) timeStamp * 1000L);

        return formatter.format(time);
    }

    public String YYYY_mm_dd(long timeStamp) {
        String SERVER_DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(SERVER_DATE_FORMAT);
        java.util.Date time = new java.util.Date((long) timeStamp * 1000L);

        return formatter.format(time);
    }

    public static Double parseDouble(String str) {
        try {
            if (str.isEmpty()) {
                return 0.0;
            } else {
                return Double.parseDouble(str);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0D;
    }

    public static Integer parseInt(String str) {
        try {
            if (str.isEmpty()) {
                return 0;
            } else {
                return Integer.parseInt(str);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Long parseLong(String str) {
        try {
            if (str.isEmpty()) {
                return (long) 0;
            } else {
                return Long.parseLong(str);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public void setPref(String key, String val) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(CONST.PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public String getPref(String key) {
        User usr = new User();
        SharedPreferences sharedPref = ctx.getSharedPreferences(CONST.PREF_NAME,
                Context.MODE_PRIVATE);
        String value = sharedPref.getString(key, "");
        return value;
    }

    public String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }



    public File getAppDir() {
        PackageManager m = ctx.getPackageManager();
        String s = ctx.getPackageName();
        try {
            PackageInfo p = m.getPackageInfo(s, 0);
            s = p.applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
            DBG.w("yourtag", "Error Package name not found " + e);
        }
        return new File(s);
    }

    public File getAppExtDir() {
        String packageName = ctx.getPackageName();
        File myFilesDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Android" + File.separator + "data" + File.separator + packageName + File.separator + "cache");
        if (!myFilesDir.exists())
            myFilesDir.mkdirs();

        return myFilesDir;
    }

    public File getAppFilesDir() {
        String packageName = ctx.getPackageName();
        File myFilesDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Android" + File.separator + "data" + File.separator + packageName + File.separator + "files");
        if (!myFilesDir.exists())
            myFilesDir.mkdirs();

        return myFilesDir;
    }

    public InputStream toInputSteam(Object obj) {
        InputStream is = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);


            oos.writeObject(obj);

            oos.flush();
            oos.close();

            is = new ByteArrayInputStream(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return is;
    }


    public Object fromInputStream(InputStream is) {
        Object object = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(is);
            object = ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void unzip(File zipFile, File targetDirectory) throws IOException {
        ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(new FileInputStream(zipFile)));
        try {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " +
                            dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                }
            /* if time should be restored as well
            long time = ze.getTime();
            if (time > 0)
                file.setLastModified(time);
            */
            }
        } finally {
            zis.close();
        }
    }

    public void downloadFile(String url, String directory, File outputFile, OnExtractedListener callback) {
        try {
            java.net.URL u = new java.net.URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
            fos.write(buffer);
            fos.flush();
            fos.close();

            unzip(outputFile, new File(directory));

            callback.onExtracted();


        } catch (FileNotFoundException e) {
            return; // swallow a 404
        } catch (IOException e) {
            return; // swallow a 404
        }
    }

    public void deleteAFile(File outputFile) throws IOException {
        outputFile.delete();
        if (outputFile.exists()) {
            outputFile.getCanonicalFile().delete();
            if (outputFile.exists()) {
                ctx.deleteFile(outputFile.getName());
            }
        }
    }

    public String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public String getStringFromFile(String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    public interface OnExtractedListener {
        void onExtracted();
    }

}