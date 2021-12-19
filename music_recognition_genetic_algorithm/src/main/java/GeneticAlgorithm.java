import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneticAlgorithm {

    private List<Chromosome> generation;

    private Tournament tournament;
    private CrossoverAndMutation crossoverAndMutation;

    private int numberOfIterations = 0;
    private Chromosome solution;

    private double averageFitness;
    private double maxFitness;


    public GeneticAlgorithm startInitialPopulation(int populationSize, int chromosomeLength) {

        generation = Stream.generate(() -> Chromosome.initializeWithRandomGenes(chromosomeLength))
                .limit(populationSize)
                .collect(Collectors.toList());

        //System.out.println("First generation:");
        //System.out.println("Average and max fitness: " + FitnessCalculator.getAverageFitness(generation) + "," + FitnessCalculator.getMaxFitness(generation));

        //double averageFitness;
        //double maxFitness;

        return this;
    }

    /**
     * Select a few chessboards to attend a tournament, and sort them in it
     * @param tournamentSize Number of chessboards selected for the tournament
     * @return
     */
    public GeneticAlgorithm startTournaments(int numberOfTournaments, int tournamentSize) {

        tournament = new Tournament(numberOfTournaments, tournamentSize);

        return this;
    }

    /**
     * Sets crossovers and mutations
     */
    public GeneticAlgorithm startCrossoversAndMutations(int nextGenerationSize, int numberOfMutants, int numberOfMutationsOnOneMutant) {

        crossoverAndMutation = new CrossoverAndMutation(nextGenerationSize, numberOfMutants, numberOfMutationsOnOneMutant);

        return this;
    }

    /**
     * Runs the algorithm
     * @return
     */
    public GeneticAlgorithm run(int maxNumberOfIterations) {

        Chromosome bestChromosome;

        do {

            List<Chromosome> tournamentWinners = new ArrayList<>();

            for (int i = 0; i < tournament.numberOfTournaments; i++) {
                tournamentWinners.add(tournament.chooseContestants(generation).determineWinner());
            }


            generation = crossoverAndMutation
                    .createNextGeneration(tournamentWinners)
                    .createMutations()
                    .getNextGeneration();

            //System.out.println("Generation " + numberOfIterations + ":");
            //generation.forEach(Chromosome::print);
            averageFitness = FitnessCalculator.getAverageFitness(generation);
            maxFitness = FitnessCalculator.getMaxFitness(generation);
            //System.out.println("Average and max fitness: " + averageFitness + "," + maxFitness);

            bestChromosome = generation.stream().max(Comparator.comparing(Chromosome::getFitness))
                    .orElseGet(null);

            numberOfIterations++;

            //System.out.println("Best melody in generation " + numberOfIterations + ":");
            //Converter.convertChromosomeToMelody(bestChromosome).print();

            if(numberOfIterations >= maxNumberOfIterations)
                break;

        } while (maxFitness < 1.0);

        solution = bestChromosome;

        return this;

    }

    public Chromosome getSolution() {
        return solution;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public double getAverageFitness() {
        return averageFitness;
    }

    public double getMaxFitness() {
        return maxFitness;
    }
}
