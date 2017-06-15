package android.content.res;

import android.shadow.Shadow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kkmike999 on 2017/06/12.
 */
public class ShadowAssetManager implements Shadow {

    /**
     * Mode for {@link #open(String, int)}: no specific information about how
     * data will be accessed.
     */
    public static final int ACCESS_UNKNOWN   = 0;
    /**
     * Mode for {@link #open(String, int)}: Read chunks, and seek forward and
     * backward.
     */
    public static final int ACCESS_RANDOM    = 1;
    /**
     * Mode for {@link #open(String, int)}: Read sequentially, with an
     * occasional forward seek.
     */
    public static final int ACCESS_STREAMING = 2;
    /**
     * Mode for {@link #open(String, int)}: Attempt to load contents into
     * memory, for fast small reads.
     */
    public static final int ACCESS_BUFFER    = 3;

    private static final String  TAG       = "AssetManager";
    private static final boolean localLOGV = false || false;

    private static final boolean DEBUG_REFS = false;

    AssetManager proxy;

    private boolean mOpen = true;

    @Override
    public void setProxyObject(Object proxyObject) {
        proxy = (AssetManager) proxyObject;
    }

    public String[] list(String path) throws IOException {
        File     assetsPath = getAssertFile(path);
        String   abPath     = assetsPath.getAbsolutePath();
        String[] list       = assetsPath.list();
        return list == null ? new String[0] : list;
    }

    private File getAssertFile(String name) {
        return new File("build/intermediates/bundles/debug/assets/" + name);
    }

    public final InputStream open(String fileName) throws IOException {
        return open(fileName, ACCESS_STREAMING);
    }

    /**
     * Open an asset using an explicit access mode, returning an InputStream to
     * read its contents.  This provides access to files that have been bundled
     * with an application as assets -- that is, files placed in to the
     * "assets" directory.
     *
     * @param fileName   The name of the asset to open.  This name can be
     *                   hierarchical.
     * @param accessMode Desired access mode for retrieving the data.
     * @see #ACCESS_UNKNOWN
     * @see #ACCESS_STREAMING
     * @see #ACCESS_RANDOM
     * @see #ACCESS_BUFFER
     * @see #open(String)
     * @see #list
     */
    public final InputStream open(String fileName, int accessMode) throws IOException {
        synchronized (this) {
            if (!mOpen) {
                throw new RuntimeException("Assetmanager has been closed");
            }
            File file = getAssertFile(fileName);

            return new FileInputStream(file);

//            long asset = openAsset(fileName, accessMode);
//            if (asset != 0) {
//                AssetInputStream res = new AssetInputStream(asset);
//                incRefsLocked(res.hashCode());
//                return res;
//            }
        }
//        throw new FileNotFoundException("Asset file: " + fileName);
    }
}
