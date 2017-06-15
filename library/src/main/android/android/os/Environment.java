package android.os;

import android.annotation.TargetApi;
import android.text.TextUtils;

import java.io.File;

@TargetApi(Build.VERSION_CODES.N)
public class Environment {
    private static final String TAG = "Environment";

    private static final String ENV_EXTERNAL_STORAGE = "EXTERNAL_STORAGE";
    private static final String ENV_ANDROID_ROOT     = "ANDROID_ROOT";
    private static final String ENV_ANDROID_DATA     = "ANDROID_DATA";
    private static final String ENV_ANDROID_EXPAND   = "ANDROID_EXPAND";
    private static final String ENV_ANDROID_STORAGE  = "ANDROID_STORAGE";
    private static final String ENV_DOWNLOAD_CACHE   = "DOWNLOAD_CACHE";
    private static final String ENV_OEM_ROOT         = "OEM_ROOT";
    private static final String ENV_ODM_ROOT         = "ODM_ROOT";
    private static final String ENV_VENDOR_ROOT      = "VENDOR_ROOT";

    /** {@hide} */
    public static final  String DIR_ANDROID = "Android";
    private static final String DIR_DATA    = "data";
    private static final String DIR_MEDIA   = "media";
    private static final String DIR_OBB     = "obb";
    private static final String DIR_FILES   = "files";
    private static final String DIR_CACHE   = "cache";

    /** {@hide} */
    @Deprecated
    public static final String DIRECTORY_ANDROID = DIR_ANDROID;

    private static final File DIR_ANDROID_ROOT    = getDirectory(ENV_ANDROID_ROOT, "/system");
    private static final File DIR_ANDROID_DATA    = getDirectory(ENV_ANDROID_DATA, "/data");
    private static final File DIR_ANDROID_EXPAND  = getDirectory(ENV_ANDROID_EXPAND, "/mnt/expand");
    private static final File DIR_ANDROID_STORAGE = getDirectory(ENV_ANDROID_STORAGE, "/storage");
    private static final File DIR_DOWNLOAD_CACHE  = getDirectory(ENV_DOWNLOAD_CACHE, "/cache");
    private static final File DIR_OEM_ROOT        = getDirectory(ENV_OEM_ROOT, "/oem");
    private static final File DIR_ODM_ROOT        = getDirectory(ENV_ODM_ROOT, "/odm");
    private static final File DIR_VENDOR_ROOT     = getDirectory(ENV_VENDOR_ROOT, "/vendor");

    /**
     * Return root of the "system" partition holding the core Android OS.
     * Always present and mounted read-only.
     */
    public static File getRootDirectory() {
        return DIR_ANDROID_ROOT;
    }

    /** {@hide} */
    public static File getStorageDirectory() {
        return DIR_ANDROID_STORAGE;
    }

    /**
     * Return root directory of the "oem" partition holding OEM customizations,
     * if any. If present, the partition is mounted read-only.
     *
     * @hide
     */
    public static File getOemDirectory() {
        return DIR_OEM_ROOT;
    }

    /**
     * Return root directory of the "odm" partition holding ODM customizations,
     * if any. If present, the partition is mounted read-only.
     *
     * @hide
     */
    public static File getOdmDirectory() {
        return DIR_ODM_ROOT;
    }

    /**
     * Return root directory of the "vendor" partition that holds vendor-provided
     * software that should persist across simple reflashing of the "system" partition.
     *
     * @hide
     */
    public static File getVendorDirectory() {
        return DIR_VENDOR_ROOT;
    }

    /**
     * Return the system directory for a user. This is for use by system
     * services to store files relating to the user. This directory will be
     * automatically deleted when the user is removed.
     *
     * @hide
     * @deprecated This directory is valid and still exists, but callers should
     * <em>strongly</em> consider switching to
     * {@link #getDataSystemCeDirectory(int)} which is protected
     * with user credentials or
     * {@link #getDataSystemDeDirectory(int)} which supports fast
     * user wipe.
     */
    @Deprecated
    public static File getUserSystemDirectory(int userId) {
        return new File(new File(getDataSystemDirectory(), "users"), Integer.toString(userId));
    }

    /**
     * Returns the config directory for a user. This is for use by system
     * services to store files relating to the user which should be readable by
     * any app running as that user.
     *
     * @hide
     * @deprecated This directory is valid and still exists, but callers should
     * <em>strongly</em> consider switching to
     * {@link #getDataMiscCeDirectory(int)} which is protected with
     * user credentials or {@link #getDataMiscDeDirectory(int)}
     * which supports fast user wipe.
     */
    @Deprecated
    public static File getUserConfigDirectory(int userId) {
        return new File(new File(new File(
                getDataDirectory(), "misc"), "user"), Integer.toString(userId));
    }

    /**
     * Return the user data directory.
     */
    public static File getDataDirectory() {
        return DIR_ANDROID_DATA;
    }

    /** {@hide} */
    public static File getDataDirectory(String volumeUuid) {
        if (TextUtils.isEmpty(volumeUuid)) {
            return DIR_ANDROID_DATA;
        } else {
            return new File("/mnt/expand/" + volumeUuid);
        }
    }

    /** {@hide} */
    public static File getExpandDirectory() {
        return DIR_ANDROID_EXPAND;
    }

    /** {@hide} */
    public static File getDataSystemDirectory() {
        return new File(getDataDirectory(), "system");
    }

    /**
     * Returns the base directory for per-user system directory, device encrypted.
     * {@hide}
     */
    public static File getDataSystemDeDirectory() {
        return buildPath(getDataDirectory(), "system_de");
    }

    /**
     * Returns the base directory for per-user system directory, credential encrypted.
     * {@hide}
     */
    public static File getDataSystemCeDirectory() {
        return buildPath(getDataDirectory(), "system_ce");
    }

    /** {@hide} */
    public static File getDataSystemCeDirectory(int userId) {
        return buildPath(getDataDirectory(), "system_ce", String.valueOf(userId));
    }

    /** {@hide} */
    public static File getDataSystemDeDirectory(int userId) {
        return buildPath(getDataDirectory(), "system_de", String.valueOf(userId));
    }

    /** {@hide} */
    public static File getDataMiscDirectory() {
        return new File(getDataDirectory(), "misc");
    }

    /** {@hide} */
    public static File getDataMiscCeDirectory(int userId) {
        return buildPath(getDataDirectory(), "misc_ce", String.valueOf(userId));
    }

    /** {@hide} */
    public static File getDataMiscDeDirectory(int userId) {
        return buildPath(getDataDirectory(), "misc_de", String.valueOf(userId));
    }

    private static File getDataProfilesDeDirectory(int userId) {
        return buildPath(getDataDirectory(), "misc", "profiles", "cur", String.valueOf(userId));
    }

    /** {@hide} */
    public static File getReferenceProfile(String packageName) {
        return buildPath(getDataDirectory(), "misc", "profiles", "ref", packageName);
    }

    /** {@hide} */
    public static File getDataProfilesDePackageDirectory(int userId, String packageName) {
        return buildPath(getDataProfilesDeDirectory(userId), packageName);
    }

    /** {@hide} */
    public static File getDataProfilesDeForeignDexDirectory(int userId) {
        return buildPath(getDataProfilesDeDirectory(userId), "foreign-dex");
    }

    /** {@hide} */
    public static File getDataAppDirectory(String volumeUuid) {
        return new File(getDataDirectory(volumeUuid), "app");
    }

    /** {@hide} */
    public static File getDataAppEphemeralDirectory(String volumeUuid) {
        return new File(getDataDirectory(volumeUuid), "app-ephemeral");
    }

    /** {@hide} */
    public static File getDataUserCeDirectory(String volumeUuid) {
        return new File(getDataDirectory(volumeUuid), "user");
    }

    /** {@hide} */
    public static File getDataUserCeDirectory(String volumeUuid, int userId) {
        return new File(getDataUserCeDirectory(volumeUuid), String.valueOf(userId));
    }

    /** {@hide} */
    public static File getDataUserCePackageDirectory(String volumeUuid, int userId,
                                                     String packageName) {
        // TODO: keep consistent with installd
        return new File(getDataUserCeDirectory(volumeUuid, userId), packageName);
    }

    /** {@hide} */
    public static File getDataUserDeDirectory(String volumeUuid) {
        return new File(getDataDirectory(volumeUuid), "user_de");
    }

    /** {@hide} */
    public static File getDataUserDeDirectory(String volumeUuid, int userId) {
        return new File(getDataUserDeDirectory(volumeUuid), String.valueOf(userId));
    }

    /** {@hide} */
    public static File getDataUserDePackageDirectory(String volumeUuid, int userId,
                                                     String packageName) {
        // TODO: keep consistent with installd
        return new File(getDataUserDeDirectory(volumeUuid, userId), packageName);
    }

    /**
     * Return preloads directory.
     * <p>This directory may contain pre-loaded content such as
     * {@link #getDataPreloadsDemoDirectory() demo videos} and
     * {@link #getDataPreloadsAppsDirectory() APK files} .
     * {@hide}
     */
    public static File getDataPreloadsDirectory() {
        return new File(getDataDirectory(), "preloads");
    }

    /**
     * @see #getDataPreloadsDirectory()
     * {@hide}
     */
    public static File getDataPreloadsDemoDirectory() {
        return new File(getDataPreloadsDirectory(), "demo");
    }

    /**
     * @see #getDataPreloadsDirectory()
     * {@hide}
     */
    public static File getDataPreloadsAppsDirectory() {
        return new File(getDataPreloadsDirectory(), "apps");
    }

    /**
     * @see #getDataPreloadsDirectory()
     * {@hide}
     */
    public static File getDataPreloadsMediaDirectory() {
        return new File(getDataPreloadsDirectory(), "media");
    }

    /** {@hide} */
    public static File getLegacyExternalStorageDirectory() {
        return new File(System.getenv(ENV_EXTERNAL_STORAGE));
    }

    /** {@hide} */
    public static File getLegacyExternalStorageObbDirectory() {
        return buildPath(getLegacyExternalStorageDirectory(), DIR_ANDROID, DIR_OBB);
    }

    /**
     * Standard directory in which to place any audio files that should be
     * in the regular list of music for the user.
     * This may be combined with
     * {@link #DIRECTORY_PODCASTS}, {@link #DIRECTORY_NOTIFICATIONS},
     * {@link #DIRECTORY_ALARMS}, and {@link #DIRECTORY_RINGTONES} as a series
     * of directories to categories a particular audio file as more than one
     * type.
     */
    public static String DIRECTORY_MUSIC = "Music";

    /**
     * Standard directory in which to place any audio files that should be
     * in the list of podcasts that the user can select (not as regular
     * music).
     * This may be combined with {@link #DIRECTORY_MUSIC},
     * {@link #DIRECTORY_NOTIFICATIONS},
     * {@link #DIRECTORY_ALARMS}, and {@link #DIRECTORY_RINGTONES} as a series
     * of directories to categories a particular audio file as more than one
     * type.
     */
    public static String DIRECTORY_PODCASTS = "Podcasts";

    /**
     * Standard directory in which to place any audio files that should be
     * in the list of ringtones that the user can select (not as regular
     * music).
     * This may be combined with {@link #DIRECTORY_MUSIC},
     * {@link #DIRECTORY_PODCASTS}, {@link #DIRECTORY_NOTIFICATIONS}, and
     * {@link #DIRECTORY_ALARMS} as a series
     * of directories to categories a particular audio file as more than one
     * type.
     */
    public static String DIRECTORY_RINGTONES = "Ringtones";

    /**
     * Standard directory in which to place any audio files that should be
     * in the list of alarms that the user can select (not as regular
     * music).
     * This may be combined with {@link #DIRECTORY_MUSIC},
     * {@link #DIRECTORY_PODCASTS}, {@link #DIRECTORY_NOTIFICATIONS},
     * and {@link #DIRECTORY_RINGTONES} as a series
     * of directories to categories a particular audio file as more than one
     * type.
     */
    public static String DIRECTORY_ALARMS = "Alarms";

    /**
     * Standard directory in which to place any audio files that should be
     * in the list of notifications that the user can select (not as regular
     * music).
     * This may be combined with {@link #DIRECTORY_MUSIC},
     * {@link #DIRECTORY_PODCASTS},
     * {@link #DIRECTORY_ALARMS}, and {@link #DIRECTORY_RINGTONES} as a series
     * of directories to categories a particular audio file as more than one
     * type.
     */
    public static String DIRECTORY_NOTIFICATIONS = "Notifications";

    /**
     * Standard directory in which to place pictures that are available to
     * the user.  Note that this is primarily a convention for the top-level
     * public directory, as the media scanner will find and collect pictures
     * in any directory.
     */
    public static String DIRECTORY_PICTURES = "Pictures";

    /**
     * Standard directory in which to place movies that are available to
     * the user.  Note that this is primarily a convention for the top-level
     * public directory, as the media scanner will find and collect movies
     * in any directory.
     */
    public static String DIRECTORY_MOVIES = "Movies";

    /**
     * Standard directory in which to place files that have been downloaded by
     * the user.  Note that this is primarily a convention for the top-level
     * public directory, you are free to download files anywhere in your own
     * private directories.  Also note that though the constant here is
     * named DIRECTORY_DOWNLOADS (plural), the actual file name is non-plural for
     * backwards compatibility reasons.
     */
    public static String DIRECTORY_DOWNLOADS = "Download";

    /**
     * The traditional location for pictures and videos when mounting the
     * device as a camera.  Note that this is primarily a convention for the
     * top-level public directory, as this convention makes no sense elsewhere.
     */
    public static String DIRECTORY_DCIM = "DCIM";

    /**
     * Standard directory in which to place documents that have been created by
     * the user.
     */
    public static String DIRECTORY_DOCUMENTS = "Documents";

    /**
     * List of standard storage directories.
     * <p>
     * Each of its values have its own constant:
     * <ul>
     * <li>{@link #DIRECTORY_MUSIC}
     * <li>{@link #DIRECTORY_PODCASTS}
     * <li>{@link #DIRECTORY_ALARMS}
     * <li>{@link #DIRECTORY_RINGTONES}
     * <li>{@link #DIRECTORY_NOTIFICATIONS}
     * <li>{@link #DIRECTORY_PICTURES}
     * <li>{@link #DIRECTORY_MOVIES}
     * <li>{@link #DIRECTORY_DOWNLOADS}
     * <li>{@link #DIRECTORY_DCIM}
     * <li>{@link #DIRECTORY_DOCUMENTS}
     * </ul>
     *
     * @hide
     */
    public static final String[] STANDARD_DIRECTORIES = {
            DIRECTORY_MUSIC,
            DIRECTORY_PODCASTS,
            DIRECTORY_RINGTONES,
            DIRECTORY_ALARMS,
            DIRECTORY_NOTIFICATIONS,
            DIRECTORY_PICTURES,
            DIRECTORY_MOVIES,
            DIRECTORY_DOWNLOADS,
            DIRECTORY_DCIM,
            DIRECTORY_DOCUMENTS
    };

    /**
     * @hide
     */
    public static boolean isStandardDirectory(String dir) {
        for (String valid : STANDARD_DIRECTORIES) {
            if (valid.equals(dir)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return the download/cache content directory.
     */
    public static File getDownloadCacheDirectory() {
        return DIR_DOWNLOAD_CACHE;
    }

    public static final String MEDIA_UNKNOWN = "unknown";

    public static final String MEDIA_REMOVED = "removed";

    public static final String MEDIA_UNMOUNTED = "unmounted";

    public static final String MEDIA_CHECKING = "checking";

    public static final String MEDIA_NOFS = "nofs";

    public static final String MEDIA_MOUNTED           = "mounted";
    public static final String MEDIA_MOUNTED_READ_ONLY = "mounted_ro";

    public static final String MEDIA_SHARED = "shared";

    public static final String MEDIA_BAD_REMOVAL = "bad_removal";

    public static final String MEDIA_UNMOUNTABLE = "unmountable";

    public static final String MEDIA_EJECTING = "ejecting";


    /**
     * Append path segments to each given base path, returning result.
     *
     * @hide
     */
    public static File[] buildPaths(File[] base, String... segments) {
        File[] result = new File[base.length];
        for (int i = 0; i < base.length; i++) {
            result[i] = buildPath(base[i], segments);
        }
        return result;
    }

    /**
     * Append path segments to given base path, returning result.
     *
     * @hide
     */
    public static File buildPath(File base, String... segments) {
        File cur = base;
        for (String segment : segments) {
            if (cur == null) {
                cur = new File(segment);
            } else {
                cur = new File(cur, segment);
            }
        }
        return cur;
    }

    static File getDirectory(String variableName, String defaultPath) {
        String path = System.getenv(variableName);
        return path == null ? new File(defaultPath) : new File(path);
    }
}