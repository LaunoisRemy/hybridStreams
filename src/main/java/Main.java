import Downloader.Downloader;
import fable.FileToGzip;
import fable.GzipExtract;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        //FileToGzip ftgUrl = new FileToGzip();
        FileToGzip ftg = new FileToGzip();
        //Downloader d = new Downloader("https://www.gutenberg.org/files/11339/11339-8.txt");
        //d.download("./src/main/resources/fablesUrl.txt");
        URL url = Main.class.getClassLoader().getResource("fables.txt");
        Path path = Paths.get(url.toURI());
        ftg.fileToGzip("fables.txt","fables.gz",0);
        //ftgUrl.fileToGzip("fablesUrl.txt","fablesUrl.gz",1);

        GzipExtract ge = new GzipExtract();
        System.out.println("With name ===============================================================");
        //ge.readWithName("fablesUrl.gz","THE GOATHERD AND THE WILD GOATS");
        ge.readWithName(path,"fables.gz","THE GOATHERD AND THE WILD GOATS");

        System.out.println("With id ===============================================================");
        //ge.readWithId("fablesUrl.gz",1);
        ge.readWithId(path,"fables.gz",1);


    }
}