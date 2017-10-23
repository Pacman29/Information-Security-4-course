package Enigma;

import java.util.ArrayList;
import java.util.List;

public class Enigma {
    List<Rotor> rotors = new ArrayList();
    Reflector reflector = null;

    public void setRotor(ArrayList<Byte> face, ArrayList<Byte> back){
        rotors.add(new Rotor(face,back));
    }

    public void setRotor(Rotor r){
        rotors.add(r);
    }

    public void setReflector(ArrayList<Byte> face, ArrayList<Byte> back) throws Exception {
        if(this.reflector != null)
            throw new Exception("Reflector is set");
        this.reflector = new Reflector(face,back);
    }

    public void setReflector(Reflector r) throws Exception {
        if(this.reflector != null)
            throw new Exception("Reflector is set");
        this.reflector = r;
    }

    public void setCode(String code){
        char keys[] = code.toCharArray();
        for (int i = 0; i< rotors.size(); ++i){
            rotors.get(i).setCodeKey(keys[i]);
        }
    }

    public String getCode(){
        StringBuilder stringBuilder = new StringBuilder();

        for(Rotor r: rotors)
            stringBuilder.append(r.getCodeKey());

        return stringBuilder.toString();
    }

    public Byte encrypt(Byte letter){
        for(Rotor r: rotors)
            letter = r.getKeyStraight(letter);

        letter = reflector.getKey(letter);

        for(int i = rotors.size()-1; i >= 0; --i){
            letter = rotors.get(i).getKeyReturn(letter);
        }

        for(Rotor r: rotors){
            r.turn();
            if(!r.isRotorTurned())
                break;
        }

        return letter;
    }
}
