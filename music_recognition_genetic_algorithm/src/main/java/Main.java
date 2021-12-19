import java.util.Arrays;
import java.util.Scanner;

public class Main {


    private static void runGeneticAlgorithm(int populationSize, int maxNumberOfIterations, Melody inputMelody, Stopwatch stopwatch) {

        int numberOfTournaments = 150;
        int tournamentSize = 50;
        int numberOfMutants = 50;
        int numberOfMutationsOnOneMutant = 5;


        System.out.println("[INFO] Initial population of melodies: "  + populationSize);
        System.out.println("[INFO] Number of tournaments, tournament size: " + numberOfTournaments + "," + tournamentSize);
        System.out.println("[INFO] Number of mutants in one generation: " + numberOfMutants);
        System.out.println("[INFO] Number of mutations on one mutant: " + numberOfMutationsOnOneMutant);
        System.out.println("[INFO] Max number of iterations: " + maxNumberOfIterations);

        System.out.println("[INFO] Starting the algorithm!");

        stopwatch.start();

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm()
                .startInitialPopulation(populationSize, inputMelody.getLength())
                .startTournaments(numberOfTournaments, tournamentSize)
                .startCrossoversAndMutations(populationSize, numberOfMutants, numberOfMutationsOnOneMutant)
                .run(maxNumberOfIterations);

        stopwatch.stop();

        int numberOfIterations = geneticAlgorithm.getNumberOfIterations();
        stopwatch.addToTotalIterations(numberOfIterations);

        double averageFitness = geneticAlgorithm.getAverageFitness();
        double maxFitness = geneticAlgorithm.getMaxFitness();

        System.out.println("");
        System.out.println("[INFO] Algorithm finished!");
        System.out.println("[INFO] Calculation time: " + stopwatch.getRunningTime() + "ms");
        System.out.println("[INFO] Number of iterations until solution found: " + numberOfIterations);
        System.out.println("[INFO] Average fitness and max fitness: " + averageFitness + "," + maxFitness);
        System.out.println("");

        Chromosome solution = geneticAlgorithm.getSolution();
        System.out.println("[INFO] Solution:");
        Converter.convertChromosomeToMelody(solution).print();
        System.out.println("[INFO] Playing the melody");
        Converter.convertChromosomeToMelody(solution).play();



        boolean solutionFound = false;
        if (solution.getFitness() == 1) {
            solutionFound = true;
        }
        System.out.println(solution.getFitness() + ", " + stopwatch.getRunningTime() + "ms, " + numberOfIterations);
        System.out.println();
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Input a melody you want the algorithm to recognize.");
        System.out.println("The melody needs to be a series of letters representing musical notes, separated by spaces");
        System.out.println("(Example melody: C D E D F F F G G G D D D");
        System.out.print("Your melody: ");
        String inputMelodyString = scanner.nextLine();

        Melody inputMelody = new Melody(Arrays.stream(inputMelodyString.split(" ")).toList());
        FitnessCalculator.setInputMelody(inputMelody);

        int populationSize = 1000;
        int maxNumberOfIterations = 100000;
        int numberOfRuns = 10;

        Stopwatch stopwatch = new Stopwatch(numberOfRuns);

        System.out.println("[INFO] Entering genetic algorithm runs:");
        for (int i = 0; i < stopwatch.getNumberOfRuns(); i++) {
            System.out.println("[INFO] Starting run number: " + (i+1));
            runGeneticAlgorithm(populationSize, maxNumberOfIterations, inputMelody, stopwatch);
        }

        System.out.println("Finished " + stopwatch.getNumberOfRuns() + "runs");
        stopwatch.printAverageTime();
        stopwatch.printAverageIterations();


    }
}
