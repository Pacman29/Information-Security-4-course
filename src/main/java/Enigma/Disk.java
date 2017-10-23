package Enigma;

import java.util.ArrayList;

public class Disk {
    private ArrayList<Byte> letters;
    private int seek;

    public Disk(ArrayList<Byte> letters) {
        this.letters = letters;
        seek = 0;
    }

    public Disk(ArrayList<Byte> letters, Byte key) {
        this.letters = letters;
        this.seek = letters.indexOf(key);
    }

    public Byte getLetter(int pos){
        pos = (pos+seek) % letters.size();
        return letters.get(pos);
    }

    public int getPosition(Byte letter){
        int pos = letters.indexOf(letter)-seek;
        return (pos >= 0) ? pos : letters.size() + pos;
    }

    public void turn(){
        seek = (seek+1) % letters.size();
    }

    public boolean isDiskTurned(){
        return seek == 0;
    }

    public Byte getKey(){
        return this.getLetter(0);
    }

    public void setKey(char key){
        this.seek = this.letters.indexOf(key);
    }
}
