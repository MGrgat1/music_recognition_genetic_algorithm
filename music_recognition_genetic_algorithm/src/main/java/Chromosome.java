import java.util.*;

public final class Chromosome {

    private final List<Integer> genes;

    private final double fitness;


    public Chromosome(List<Integer> genes) {
        this.genes = genes;
        this.fitness = FitnessCalculator.calculateFitness(this);
    }


    public int getLength() {
        return genes.size();
    }

    public int getGeneAt(int x) {
        return genes.get(x);
    }


    /**
     * The seed value for the initialization of random boards.
     */
    private static long seed = System.currentTimeMillis();

    /**
     * Creates a board randomly filled with the right amount of queens.
     */
    public static Chromosome initializeWithRandomGenes(int chromosomeLength) {

        //the values of the gene will correspond to the musical scale C-D-E-F-G-A-B (0-1-2-3-4-5-6)
        final int SCALE_LENGTH = 7;

        Random random = new Random(seed);

        ArrayList<Integer> genes = new ArrayList<>();

        int randomPick = -1;
        for (int i = 0; i < chromosomeLength; i++) {
            randomPick = Math.abs(random.nextInt() % SCALE_LENGTH);
            genes.add(randomPick);
        }

        seed++;
        return new Chromosome(genes);

    }

    public double getFitness() {

        return fitness;

    }

    public List<Integer> getGenes(){
        return genes;
    }


    public void print(){

        System.out.println("Chromosome:");
        System.out.println("Calculated fitness: " + fitness);
        System.out.println(genes);


    }


}