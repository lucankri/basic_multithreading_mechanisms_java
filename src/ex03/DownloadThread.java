package ex03;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DownloadThread extends Thread {
    private final List<String> fileUrls;
    private final int threadNumber;
    private static int countFileStart = 0;

    public DownloadThread(List<String> fileUrls, int threadNumber) {
        this.fileUrls = fileUrls;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        while (!fileUrls.isEmpty()) {
            String url = getNextUrlToDownload();
            if (url != null) {
                ++countFileStart;
                int countFileId = countFileStart;
                System.out.println("Thread-" + threadNumber + " start download "
                        + getFileNameFromUrl(url) + " number " + countFileStart);
                downloadFile(url);

                System.out.println("Thread-" + threadNumber + " finish download "
                        + getFileNameFromUrl(url) + " number " + countFileId);
            }
        }
    }

    private synchronized String getNextUrlToDownload() {
        if (!fileUrls.isEmpty()) {
            return fileUrls.remove(0);
        }
        return null;
    }

    private void downloadFile(String url) {
        try {
            URL fileUrl = new URL(url);
            try (InputStream in = fileUrl.openStream();
                OutputStream out = Files.newOutputStream(Paths.get(getFileNameFromUrl(url)))) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String getFileNameFromUrl(String url) {
        int lastSlashIndex = url.lastIndexOf('/');
        if (lastSlashIndex >= 0 && lastSlashIndex < url.length() - 1) {
            return url.substring(lastSlashIndex + 1);
        }
        return "downloaded_file";
    }
}
