import java.util.List;
import java.util.stream.Collectors;

public class Converter {


    /**
     * Converts the int values of the chromosome into a playable melody pattern.
     * For example, the chromosome [48, 10, 4, 34, 37, 5, 42, 42, 3, 41] corresponds
     * to the melody [B, F, G, B, E, A, C, C, F, B]
     * @param chromosome
     * @return
     */
    public static Melody convertChromosomeToMelody(Chromosome chromosome) {

        List<String> notes = chromosome.getGenes().stream()
                .map(Converter::convertGeneToNote)
                .collect(Collectors.toList());

        return new Melody(notes);
    }

    /**
     * Converts the melody pattern into a chromosome.
     * For example, the melody "E G E F C D D" corresponds to the chromosome [2, 4, 2, 3, 0, 1, 1]
     * @param melody
     * @return
     */
    /*
    public static Chromosome convertMelodyToChromosome(Melody melody) {

        List<Integer> genes = melody.getNotes().stream()
                .map(Converter::convertNoteToGene)
                .collect(Collectors.toList());

        return new Chromosome(genes);
    }

     */


    /**
     * Maps the int value of a gene into the cyclical pattern C D E F G A B
     * For example, a gene with the value 0 corresponds to the note C,
     * and a gene with the value 6 corresponds to the note B
     * @param gene
     * @return
     */
    public static String convertGeneToNote(int gene) {
        return switch (gene % 7) {
            case 0 -> "C";
            case 1 -> "D";
            case 2 -> "E";
            case 3 -> "F";
            case 4 -> "G";
            case 5 -> "A";
            case 6 -> "B";
            default -> "X";
        };
    }

    public static int convertNoteToGene(String note) {
        return switch (note) {
            case "C" -> 0;
            case "D" -> 1;
            case "E" -> 2;
            case "F" -> 3;
            case "G" -> 4;
            case "A" -> 5;
            case "B" -> 6;
            default -> -1;
        };
    }
}
