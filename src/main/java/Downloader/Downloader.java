package Downloader;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Downloader {
    private URL url;

    public Downloader(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.printf("Erreur url");
        }
    }

    public void download(String targetFileName){
        Path path = Paths.get(targetFileName);
        try (   BufferedWriter writer = Files.newBufferedWriter(path);
                BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
        ){
            int p;
            while ((p = inputStream.read()) != -1){
                writer.write(p);
            }
            writer.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
