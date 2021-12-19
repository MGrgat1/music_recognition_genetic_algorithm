import org.jfugue.player.Player;

import java.util.List;

public class Melody {

    int length;
    List<String> notes;
    String playablePattern = "";

    public Melody(List<String> notes) {
        this.notes = notes;
        this.length = notes.size();
        notes.forEach(note -> playablePattern = playablePattern.concat(note + " "));
    }

    public String getNoteAt(int x) {
        return notes.get(x);
    }

    public List<String> getNotes() {
        return notes;
    }

    public int getLength() {
        return length;
    }


    public void print() {
        System.out.println("Melody: " + notes);
    }

    public void play() {

        System.out.println("Playing the melody: " + notes);
        Player player = new Player();
        player.play(playablePattern);
    }
}
