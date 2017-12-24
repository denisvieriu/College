package utils;

import java.io.BufferedReader;

public class FileData {
    private String filename;
    private BufferedReader header;

    public FileData(String m, BufferedReader b){
        filename = m;
        header = b;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public BufferedReader getHeader() {
        return header;
    }

    public void setHeader(BufferedReader header) {
        this.header = header;
    }

    @Override
    public String toString(){
        return filename;
    }
}

