package android.content;

import android.annotation.TargetApi;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.ShadowAssetManager;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.ShadowSQLiteDatabase;
import android.os.Build;
import android.shadow.Shadow;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import net.kb.test.library.CGLibProxy;
import net.kkmike.sptest.SharedPreferencesHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kkmike999 on 2017/05/26.
 */
public class ShadowContext implements Shadow {

    public static final  String DB_PATH  = "build/db/";
    private static final String DATA_DIR = "build/data/";
    private static final String EXT_DIR  = "build/ext";

    private Resources resources;
    private Context   mockContext;
    private Map<String, SQLiteDatabase> dbMap = new HashMap<>();

    public ShadowContext(Resources resources) {
        this.resources = resources;
    }

    @NonNull
    public final String getString(@StringRes int resId) {
        return getResources().getString(resId);
    }

    public Resources getResources() {
        return resources;
    }

    public SharedPreferences getSharedPreferences(String name, int mode) {
        return SharedPreferencesHelper.getInstance(name);
    }

    public Context getApplicationContext() {
        return mockContext;
    }

    public AssetManager getAssets() {
        return new CGLibProxy().proxy(AssetManager.class, new ShadowAssetManager());
    }

    // 原生方法
    public File getDatabasePath(String name) {
        return staticGetDatabasePath(name);
    }

    private static File staticGetDatabasePath(String name) {
        String path = DB_PATH + (DB_PATH.endsWith("/") ? "" : "/") + name + (name.endsWith(".db") ? "" : ".db");
        return new File(path);
    }

    public String[] databaseList() {
        File dbPath = new File(DB_PATH);
        return dbPath.list();
    }

    public static String getDbDir() {
        File file = new File(DB_PATH);
        file.mkdirs();
        return file.getPath();
    }

    public File getDataDir() {
        File file = new File(DATA_DIR);
        file.mkdirs();
        return file;
    }

    public File getFilesDir() {
        File file = new File(getDataDir(), "files");
        file.mkdirs();
        return file;
    }

    public File getFileStreamPath(String name) {
        File file = new File(getFilesDir(), name);
        file.mkdirs();
        return file;
    }

    public File getCacheDir() {
        File file = new File(getDataDir(), "cache");
        file.mkdirs();
        return file;
    }

    public File getCodeCacheDir() {
        File file = new File(getDataDir(), "code_cache");
        file.mkdirs();
        return file;
    }

    public File getNoBackupFilesDir() {
        File file = new File(getDataDir(), "no_backup");
        file.mkdirs();
        return file;
    }

    public File getExtDir() {
        File ext = new File(EXT_DIR);
        ext.mkdirs();
        return ext;
    }

    public File getExternalCacheDir() {
        File file = new File(EXT_DIR, "cache");
        file.mkdirs();
        return file;
    }

    @Nullable
    public File getExternalFilesDir(@Nullable String type) {
        File file = new File(EXT_DIR, "files/" + type);
        file.mkdirs();
        return file;
    }

    public String[] fileList() {
        File filesDir = getFilesDir();
        return filesDir.list();
    }

    public String getPackageResourcePath() {
        return new File("").getPath();
    }

    public String getPackageCodePath() {
        return new File("").getPath();
    }

    /**
     * 删除临时数据库 & 数据库目录
     */
    public static void deleteAllTempDir() {
        FileUtils.deletePath(DB_PATH);
        FileUtils.deletePath(DATA_DIR);
        FileUtils.deletePath(EXT_DIR);
    }

    /////////////////////////////   SQLiteDatabase    /////////////////////////////
    public void putSQLiteDatabase(String name, SQLiteDatabase db) {
        dbMap.put(name, db);
    }

    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return openOrCreateDatabase(name, mode, factory, null);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        if (dbMap.containsKey(name)) {
            return dbMap.get(name);
        }
        // 创建数据库
        try {
            String path = staticGetDatabasePath(name).getPath();

            ShadowSQLiteDatabase sdb = new ShadowSQLiteDatabase(path, 0, null);
            SQLiteDatabase       db  = new CGLibProxy().proxy(SQLiteDatabase.class, sdb);

            sdb.setMockDatabase(db);

            putSQLiteDatabase(name, db);

            return db;
        } catch (java.sql.SQLException e) {
            throw new android.database.SQLException("", e);
        }
    }

    public boolean deleteDatabase(String name) {
        SQLiteDatabase db = dbMap.get(name);
//        db.execSQL("DROP DATABASE " + name);
        db.close();

        String path = getDatabasePath(name).getPath();

        new File(path).delete();

        return true;
    }

    public Map<String, SQLiteDatabase> getDbMap() {
        return dbMap;
    }

    /////////////////////////////   SQLiteDatabase end  /////////////////////////////

    @Override
    public String toString() {
        return "ShadowContext@" + hashCode() + "{}";
    }

    @Override
    public void setProxyObject(Object proxyObject) {
        mockContext = (Context) proxyObject;
    }
}
