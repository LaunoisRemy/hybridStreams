package fable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;

public class FileToGzip {
    private ArrayList<Fable> index;

    public FileToGzip() {
        this.index = new ArrayList<>();
    }

    public void fileToGzip(String sourceFileName,String targetFileName,int format){
        String tmpSource = "./src/main/resources/"+sourceFileName;
        String tmpTarget = "./src/main/resources/"+targetFileName;
        read(tmpSource,format);
        write(tmpTarget);
    }

    public void read(String sourceFileName,int format){
        //Path path = Paths.get("test.txt");
        byte[] buffer = new byte[1024];

        try {
            //BufferedWriter writer = Files.newBufferedWriter(path);

            FileReader fileInput = new FileReader(sourceFileName);
            BufferedReader bufferedReader = new BufferedReader(fileInput);
            String line;
            String breakPoint = "";


            while (!(line = bufferedReader.readLine()).equals("CONTENTS")) {
            }

            switch (format){
                case 1:
                    breakPoint="LIST OF ILLUSTRATIONS";
                    break;
                default:
                    breakPoint="AESOP'S FABLES";
                    break;
            }

            while (!(line = bufferedReader.readLine()).equals(breakPoint)) {
                if(!line.equals("")){
                    this.index.add(new Fable(line));
                }
            }

            if(format==1){
                while (!(line = bufferedReader.readLine()).equals("ÆSOP'S FABLES")) {
                }
            }


            while ((line = bufferedReader.readLine()).equals("")){

            }

            Fable fCourante=getFable(line);
            StringBuilder text = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {

                if(!line.equals("")){
                    if(contains(line)){
                        fCourante.setContent(String.valueOf(text));
                        fCourante = getFable(line);
                        text=new StringBuilder();
                    }else{
                        text.append(line).append("\n");
                    }
                }

            }
            fCourante.setContent(String.valueOf(text));

            fileInput.close();

            //writer.close();
            System.out.println("The file was read successfully!");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void write(String targetFileName){
        Path path = Paths.get(targetFileName);
        try {
            OutputStream outStream = Files.newOutputStream(path);
            OutputStreamWriter indexWriter = new OutputStreamWriter(outStream, StandardCharsets.ISO_8859_1);
            indexWriter.write("Aesop Fables\n");
            indexWriter.write(String.format("%d\n", this.index.size()));
            indexWriter.write("Index:\n");
            ByteArrayOutputStream allContent = new ByteArrayOutputStream();

            for (Fable fable: this.index
            ) {
                ByteArrayOutputStream currentContent = new ByteArrayOutputStream();
                GZIPOutputStream contentStream = new GZIPOutputStream(currentContent);

                contentStream.write(fable.getContent().getBytes(StandardCharsets.ISO_8859_1));
                contentStream.close();
                String line = String.format("%07d", allContent.size())+" " +
                        String.format("%07d",currentContent.size())+" "+
                        fable.getName()+"\n";
                indexWriter.write(line);
                currentContent.writeTo(allContent);
            }
            indexWriter.write("Fables:\n");
            indexWriter.flush();

            allContent.writeTo(outStream);

            indexWriter.close();
            System.out.println("The file was compressed successfully!");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean contains(String fable){
        for (Fable f: this.index
        ) {
            if(f.getName().equals(fable)) return true;
        }
        return false;
    }

    private Fable getFable(String fable){
        for (Fable f: this.index
        ) {
            if(f.getName().equals(fable)) return f;
        }
        return null;
    }

}