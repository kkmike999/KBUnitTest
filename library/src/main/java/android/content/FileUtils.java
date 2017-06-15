package android.content;

import java.io.File;

/**
 * Created by kkmike999 on 2017/06/15.
 */
class FileUtils {

    protected static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {

                final File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
                file.renameTo(to);
                to.delete();
            } else if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    deleteFile(f);
                }

                file.delete();
            }
        }
    }

    protected static void deletePath(String path){
        deleteFile(new File(path));
    }
}
