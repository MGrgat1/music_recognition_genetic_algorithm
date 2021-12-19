public class Stopwatch {


    private long startTime;
    private long runningTime;

    public double totalTime = 0.0;
    public int totalIterations = 0;

    int numberOfRuns;

    public Stopwatch(int numberOfRuns) {
        this.numberOfRuns = numberOfRuns;
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        long stopTime = System.currentTimeMillis();
        runningTime = stopTime - startTime;
        addToTotalTime(runningTime);
    }

    public long getRunningTime() {
        return runningTime;
    }

    public int getNumberOfRuns() {
        return numberOfRuns;
    }

    public void addToTotalTime(double time) {
        totalTime += time;
    }

    public void printAverageTime() {
        System.out.println("Average time: " + totalTime/numberOfRuns + " ms");
    }

    public void addToTotalIterations(int iterations) {
        totalIterations += iterations;
    }

    public void printAverageIterations() {
        System.out.println("Average iterations: " + totalIterations/numberOfRuns);
    }

}