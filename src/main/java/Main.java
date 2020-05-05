import Downloader.Downloader;
import fable.FileToGzip;
import fable.GzipExtract;

public class Main {

    public static void main(String[] args) {
        FileToGzip ftgUrl = new FileToGzip();
        FileToGzip ftg = new FileToGzip();
        Downloader d = new Downloader("https://www.gutenberg.org/files/11339/11339-8.txt");
        d.download("./src/main/resources/fablesUrl.txt");

        ftg.fileToGzip("fables.txt","fables.gz",0);
        ftgUrl.fileToGzip("fablesUrl.txt","fablesUrl.gz",1);

        GzipExtract ge = new GzipExtract();
        System.out.println("With name ===============================================================");
        ge.readWithName("fablesUrl.gz","THE GOATHERD AND THE WILD GOATS");
        //ge.readWithName("fables.gz","THE GOATHERD AND THE WILD GOATS");

        System.out.println("With id ===============================================================");
        ge.readWithId("fablesUrl.gz",1);
        ge.readWithId("fables.gz",1);


    }
}