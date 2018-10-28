public class Main {

    public static void main(String[] args) {
        MatrixOperations matrixOperations = new MatrixOperations();

        System.out.printf("Maximum numbers of processors: %d\n", Runtime.getRuntime().availableProcessors());

        // Java - thread pool
        {
            matrixOperations.MatrixAdditionSingleThreaded(true);
            matrixOperations.MatrixAdditionMultiThreaded(true);
            matrixOperations.MatrixMultiplicationSingleThreaded(true);
            matrixOperations.MatrixMultiplicationMultiThreaded(true);
            matrixOperations.MatrixAdditionParallelFutures();
        }
    }
}
