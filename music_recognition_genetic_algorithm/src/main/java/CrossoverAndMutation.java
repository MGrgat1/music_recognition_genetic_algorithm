import java.util.*;

public class CrossoverAndMutation {

    private static long seed = System.currentTimeMillis();

    private List<Chromosome> nextGeneration;
    private int nextGenerationSize;
    private int numberOfMutants;
    private int numberOfMutationsOnOneMutant;


    public CrossoverAndMutation(int nextGenerationSize, int numberOfMutants, int numberOfMutationsOnOneMutant) {
        this.nextGenerationSize = nextGenerationSize;
        this.numberOfMutants = numberOfMutants;
        this.numberOfMutationsOnOneMutant = numberOfMutationsOnOneMutant;
    }

    public CrossoverAndMutation createNextGeneration(List<Chromosome> winners) {

        //pick randomly 2 contestants, and cross them over
        Random random = new Random(seed);
        seed++;
        List<Chromosome> nextGeneration = new ArrayList<>();

        for (int i = 0; i < nextGenerationSize; i++) {
            Chromosome winner1 = winners.get(Math.abs(random.nextInt() % winners.size()));
            Chromosome winner2 = winners.get(Math.abs(random.nextInt() % winners.size()));

            nextGeneration.add(createOffspring(winner1, winner2));
        }

        this.nextGeneration = nextGeneration;

        return this;

    }

    public Chromosome createOffspring(Chromosome parent1, Chromosome parent2) {

        Random random = new Random(seed);

        int crossoverMax = parent1.getLength();
        int crossoverIndex = Math.abs(random.nextInt() % crossoverMax);

        List<Integer> offspringGenes = new ArrayList<>();
        offspringGenes.addAll(parent1.getGenes().subList(0, crossoverIndex));
        offspringGenes.addAll(parent2.getGenes().subList(crossoverIndex, crossoverMax));


        return new Chromosome(offspringGenes);
    }

    public CrossoverAndMutation createMutations() {

        Random random = new Random(seed);
        seed++;
        //System.out.println("Mutations at: ");
        for (int i = 0; i < numberOfMutants; i++) {

            //pick the Chromosome
            int mutantChromosomeIndex = Math.abs(random.nextInt() % nextGeneration.size());
            Chromosome ChromosomeToBeMutated = nextGeneration.get(mutantChromosomeIndex);

            //mutate the Square
            Chromosome mutantChromosome = createMutant(ChromosomeToBeMutated, numberOfMutationsOnOneMutant);

            nextGeneration.add(mutantChromosomeIndex, mutantChromosome);

        }


        return this;
    }

    /**
     * Creates a mutated Chromosome. Mutating a gene will change the HVLR and fitness function values
     * of the whole chessbaord. This is taken care of by returning a constructor.
     * The HVLR and fitness values will be modified through the constructor.
     * @return
     */
    public Chromosome createMutant (Chromosome Chromosome, int numberOfMutationsOnOneMutant) {

        Random random = new Random(seed);
        seed++;

        List<Integer> genes = Chromosome.getGenes();

        for (int i = 0; i < numberOfMutationsOnOneMutant; i++) {
            //we swap the values of two squares
            int indexOfSquare1 = Math.abs(random.nextInt() % Chromosome.getLength());
            int indexOfSquare2 = Math.abs(random.nextInt() % Chromosome.getLength());
            int valueOfSquare1 = genes.get(indexOfSquare1);
            int valueOfSquare2 = genes.get(indexOfSquare2);
            genes.set(indexOfSquare1, valueOfSquare2);
            genes.set(indexOfSquare2, valueOfSquare1);
        }

        return new Chromosome(genes);


    }

    public List<Chromosome> getNextGeneration() {
        return nextGeneration;
    }
}
