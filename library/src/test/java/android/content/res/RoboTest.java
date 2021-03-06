package android.content.res;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import net.kb.test.library.BuildConfig;
import net.kb.test.library.R;
import net.kb.test.library.utils.RoboRunner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.internal.DoNotInstrument;

import java.io.File;
import java.io.IOException;

/**
 * Created by kkmike999 on 2017/05/26.
 * <p>
 * manifest = "build/intermediates/manifests/aapt/debug/AndroidManifest.xml"
 */
@RunWith(RoboRunner.class)
@Config(constants = BuildConfig.class)
@DoNotInstrument
public class RoboTest {

    Resources resources;
    Context   context;

    @Before
    public void setUp() throws Exception {
        context = RuntimeEnvironment.application;
        resources = context.getResources();
    }

    @Test
    public void getStringArray() throws Exception {
        String[] array = resources.getStringArray(R.array.arrayName);

        Assert.assertEquals("item0", array[0]);
        Assert.assertEquals("item1", array[1]);
    }

    @Test
    public void getIntArray() {
        int[] intArray = resources.getIntArray(R.array.intArray);

        Assert.assertEquals(0, intArray[0]);
        Assert.assertEquals(1, intArray[1]);

        int[] intArrayNoItem = resources.getIntArray(R.array.intArrayNoItem);

        Assert.assertEquals(0, intArrayNoItem.length);
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
        File fileDir = new File(context.getFilesDir(), "tmp.txt");
        fileDir.getParentFile().mkdirs();
        fileDir.createNewFile();

        for (String f : context.fileList()) {
            System.out.println(f);
        }

        System.out.println("\n-- databaseList --\n");

        // 创建数据库
        context.openOrCreateDatabase("test.db", 0, null);

        for (String db : context.databaseList()) {
            System.out.println(db);
        }

        System.out.println(context.getDatabasePath("mydb"));
    }

    private void displayPath(File... files) {
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
    }
}
