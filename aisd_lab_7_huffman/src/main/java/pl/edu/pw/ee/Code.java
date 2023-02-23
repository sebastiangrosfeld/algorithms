
package pl.edu.pw.ee;


public class Code {
    
    private Character character;
    private String bitCode;
    
    public Code(char character, String bitCode) {
        this.character = character;
        this.bitCode = bitCode;
    }
    
    public Character returnCharacter() {
        return character;
    }
    
    public String returnBitCode() {
        return bitCode;
    }
    
    @Override
    public String toString() {
        return "" + character + " " + bitCode;
    }
}
