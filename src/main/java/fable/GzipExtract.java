package fable;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.zip.GZIPInputStream;

public class GzipExtract {
    /**
     * Give me a name i give the text
     * @param sourceFileName gzip file
     * @param idfable fable name
     */
    public  void readWithId(Path path,String sourceFileName, int idfable){
        try {

            InputStream fileInputStream = GzipExtract.class.getClassLoader().getResourceAsStream(sourceFileName);
            assert fileInputStream != null;
            BufferedInputStream bufferedfileInputStream = new BufferedInputStream(fileInputStream);

            InputStreamReader inputStreamReader = new InputStreamReader(bufferedfileInputStream, StandardCharsets.ISO_8859_1);
            LineNumberReader lineNumberReader = new LineNumberReader(inputStreamReader);

            String line = "";
            int length=0;

            int size = (int) Files.size(path);
            bufferedfileInputStream.mark(size+1);

            int offSet = 0;
            int allOffset = 0;
            int i=1;
            idfable+=3;
            while (!(line=lineNumberReader.readLine()).equals("Fables:")){
                allOffset+= line.length()+1;
                if(i==idfable){
                    String[] lineSplit = line.split(" ");
                    offSet =Integer.parseInt(lineSplit[0]);
                    length = Integer.parseInt(lineSplit[1]);
                }
                i++;
            }
            allOffset+= line.getBytes().length+1;
            if(length==0){
                throw new Exception("Problem to find fable");
            }
            bufferedfileInputStream.reset();
            searchText(allOffset,offSet,length,bufferedfileInputStream);
            //System.out.println(allOffset + "\n"+offSet);
            //Global offset



        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Give me a name i give the text
     * @param sourceFileName gzip file
     * @param fable fable name
     */
    public void readWithName(Path path,String sourceFileName, String fable){

        //URL url = this.getClass().getResource(sourceFileName);
        //Path path = Paths.get("./src/main/resources/"+sourceFileName);
        try {
            //Path path = Paths.get(url.toURI());
            //InputStream fileInputStream = Files.newInputStream(path, StandardOpenOption.READ);
            System.out.println(sourceFileName);
            InputStream fileInputStream = GzipExtract.class.getClassLoader().getResourceAsStream(sourceFileName);

            BufferedInputStream bufferedfileInputStream = new BufferedInputStream(fileInputStream);

            InputStreamReader inputStreamReader = new InputStreamReader(bufferedfileInputStream, StandardCharsets.ISO_8859_1);
            LineNumberReader lineNumberReader = new LineNumberReader(inputStreamReader);

            String line = "";
            int length=0;

            int size = (int) Files.size(path);
            bufferedfileInputStream.mark(size+1);

            int offSet = 0;
            int allOffset = 0;
            while (!(line=lineNumberReader.readLine()).equals("Fables:")){
                allOffset+= line.length()+1;
                if(line.contains(fable)){
                    String[] lineSplit = line.split(" ");
                    offSet =Integer.parseInt(lineSplit[0]);
                    length = Integer.parseInt(lineSplit[1]);
                }
            }
            allOffset+= line.getBytes().length+1;
            if(length==0){
                throw new Exception("Problem to find fable");
            }
            bufferedfileInputStream.reset();

            searchText(allOffset,offSet,length,bufferedfileInputStream);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method to read part compressed
     * @param allOffset
     * @param offSet
     * @param length
     * @param bufferedfileInputStream
     * @throws IOException
     */
    private void searchText(int allOffset, int offSet, int length, BufferedInputStream bufferedfileInputStream) throws IOException {
        long skipped = 0;
        long remaining = allOffset+offSet;

        while(skipped < allOffset+offSet) {
            skipped += bufferedfileInputStream.skip(remaining);
            remaining = (allOffset+offSet) - skipped;
        }

        byte[] compressedData = new byte[4096];
        bufferedfileInputStream.read(compressedData);
        //System.out.println(Arrays.toString(compressedData));
        ByteArrayInputStream rawStream = new ByteArrayInputStream(compressedData,0,length);
        GZIPInputStream zipStream = new GZIPInputStream(rawStream);
        StringBuilder content = new StringBuilder();

        int c;
        while ((c = zipStream.read()) != -1) {
            content.append((char) c);
        }

        System.out.println(content.toString());
    }


}
