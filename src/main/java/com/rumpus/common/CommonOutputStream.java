package com.rumpus.common;

import java.io.IOException;
import java.io.OutputStream;

public class CommonOutputStream extends OutputStream {

    private StringBuilder string = new StringBuilder();
    
    @Override
    public void write(int b) throws IOException {
        this.string.append((char) b );
    }
    
    public String toString(){
        return this.string.toString();
    }
}
