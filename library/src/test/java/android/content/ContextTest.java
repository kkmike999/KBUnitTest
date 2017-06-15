package android.content;

import android.annotation.TargetApi;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.ShadowResources;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;

import net.kb.test.library.CGLibProxy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by kkmike999 on 2017/06/15.
 */
public class ContextTest {

    Context       context;
    ShadowContext shadowContext;

    @Before
    public void setUp() throws Exception {
        Resources resources = new CGLibProxy().proxy(Resources.class, new ShadowResources(), new Class[]{AssetManager.class, DisplayMetrics.class, Configuration.class}, new Object[]{null, null, null});
        context = new CGLibProxy().proxy(Context.class, shadowContext = new ShadowContext(resources));
    }

    @After
    public void tearDown() throws Exception {
        shadowContext.deleteAllTempDir();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Test
    public void getFileDir() throws IOException {

        displayPath(context.getDataDir());
        displayPath(context.getFilesDir());
        displayPath(context.getFileStreamPath("stream"));
        displayPath(context.getCacheDir());

        System.out.println("\n----\n");

        displayPath(context.getCodeCacheDir());
        displayPath(context.getNoBackupFilesDir());

        System.out.println(context.getPackageCodePath());
        System.out.println(context.getPackageResourcePath());
//        displayPath(context.getObbDir());
//        displayPath(context.getObbDirs());

        System.out.println("\n----\n");

        displayPath(context.getExternalCacheDir());
        displayPath(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC));
        displayPath(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES));
//        displayPath(context.getExternalMediaDirs());
//        displayPath(context.getExternalCacheDirs());

        System.out.println("\n-- fileList --\n");

        // 创建临时文件
        File tmpFile = new File(context.getFilesDir(), "tmp.txt");
        tmpFile.getParentFile().mkdirs();
        tmpFile.createNewFile();

        for (String f : context.fileList()) {
            System.out.println(f);
        }

        System.out.println("\n-- databaseList --\n");

        // 创建数据库
        context.openOrCreateDatabase("test.db", 0, null);

        for (String db : context.databaseList()) {
            System.out.println(db);
        }

        System.out.println("\n-- getDatabasePath --\n");

        System.out.println(context.getDatabasePath("mydb"));
    }

    private void displayPath(File... files) {
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
    }
}
