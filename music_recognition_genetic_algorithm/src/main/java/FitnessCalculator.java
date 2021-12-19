import java.util.List;

public class FitnessCalculator {

    private static Melody inputMelody;

    public static void setInputMelody(Melody inputMelody) {
        FitnessCalculator.inputMelody = inputMelody;
    }

    /**
     * The total fitness is inversely proportional to the total distance of all the notes on the same position in the melody.
     * The best solution is one where the total distance is 0. This is when the total fitness will be 1.
     * As the distance increases, the fitness will fall based on the equation 1 - distanceOfMelody/maxDistanceOfMelody
     * Examples:
     * distance(C-D-E-E, C-D-E-D) = 1
     * distance(C-D-E-E, E-D-E-D) = 3
     */
    public static double calculateFitness(Chromosome chromosome) {

        //a note belonging to the sequence C-D-E-F-G-A-B can be at most 3 notes distant from any other note
        int maxDistanceOfNote = 3;

        //the max distance of all notes in the melody
        int maxDistanceOfMelody = maxDistanceOfNote * chromosome.getLength();

        //summing all the individual distances to get the distance of the entire melody from the chosen melody
        int distanceOfMelody = 0;
        for (int i = 0; i < chromosome.getLength(); i++) {
            int geneValue1 = Converter.convertNoteToGene(inputMelody.getNoteAt(i));
            int geneValue2 = chromosome.getGeneAt(i);
            distanceOfMelody += getGeneDistance(geneValue1, geneValue2);
        }

        return 1 - distanceOfMelody/(double) maxDistanceOfMelody;

    }

    /**
     * In the sequence C-D-E-F-G-A-B, the distance is cyclical and measured in both directions.
     * A note can get closer to another note either by being raised or by being lowered.
     * Examples:
     * distance(C,E) = 2 (measured clockwise)
     * or, distance(C,E) = 5 (measured counter-clockwise)
     * We'll always evaluate the fitness based on the shorter distance.
     */
    public static double getGeneDistance(int gene1, int gene2) {

        int LENGTH_OF_NOTE_SCALE = 7;

        int distance1 = Math.abs(gene2 - gene1);
        int distance2 = LENGTH_OF_NOTE_SCALE - distance1;

        return Math.min(distance2, distance1);
    }

    public static double getAverageFitness(List<Chromosome> generation) {
        return generation.stream()
                .mapToDouble(Chromosome::getFitness)
                .average()
                .orElse(Double.NaN);
    }

    public static double getMaxFitness(List<Chromosome> generation) {

        return generation.stream()
                .mapToDouble(Chromosome::getFitness)
                .max()
                .orElse(Double.NaN);
    }


}
