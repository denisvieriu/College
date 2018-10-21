import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatrixOperations {

    private long gStartTime;
    private long gEndTime;

    public enum TIME_ELAPSED_STATE {
        START_MEASURE,
        END_MEASURE
    }

    private static final int MAX_LINES = 200;
    private static final Integer MAX_COLUMNS = 200;

    private int[][] matrixA;
    private int[][] matrixB;
    private int[][] matrixResult;

    MatrixOperations() {
        this.matrixA = new int[MAX_LINES][MAX_COLUMNS];
        this.matrixB = new int[MAX_LINES][MAX_COLUMNS];
        this.matrixResult = new int[MAX_LINES][MAX_COLUMNS];

        initializeMatrix(matrixA, true);
        initializeMatrix(matrixB, true);
    }

    private void initializeMatrix(int matrix[][], boolean randVal) {
        Random rand = new Random();

        for (int i = 0; i < MAX_LINES; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                matrix[i][j] = (randVal) ? (rand.nextInt(10) + 1) : (0);
            }
        }
    }
    public void printMatrix(int matrix[][]) {
        for (int i = 0; i < MAX_LINES; i++, System.out.print("\n")) {
            for (int j = 0; j < MAX_COLUMNS; j++, System.out.print(" ")) {
                System.out.printf("%d", matrix[i][j]);
            }
        }
        System.out.println();
    }
    private void PrintElapsedTime() {
        long gTimeElapsed = gEndTime - gStartTime;
        System.out.println("----> Execution time in microseconds : " +
                gTimeElapsed / 1000);
    }
    private void measureElapsedTime(@NotNull TIME_ELAPSED_STATE timeState) {
        switch (timeState) {
            case START_MEASURE:
                this.gStartTime = System.nanoTime();
                break;
            case END_MEASURE:
                this.gEndTime = System.nanoTime();
                this.PrintElapsedTime();
        }
    }

    void MatrixAdditionSingleThreaded(boolean memzeroMatrix) {
        System.out.println("Running matrix addition single threaded");

        if (memzeroMatrix) {
            initializeMatrix(matrixResult, false);
        }

        measureElapsedTime(TIME_ELAPSED_STATE.START_MEASURE);
        for (int i = 0; i < MAX_LINES; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                matrixResult[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }
        measureElapsedTime(TIME_ELAPSED_STATE.END_MEASURE);
    }

    class MatrixAdditionMultithreaded implements Runnable {
        int line;

        MatrixAdditionMultithreaded(int _line) {
            this.line = _line;
        }

        public void run() {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                matrixResult[this.line][j] = matrixA[this.line][j] + matrixB[this.line][j];
            }
        }
    }
    class MatrixMultiplicationMultithreaded implements Runnable {
        int line;

        MatrixMultiplicationMultithreaded(int _line) {
            this.line = _line;
        }

        public void run() {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                for (int k = 0; k < MAX_COLUMNS; k++) {
                    matrixResult[this.line][j] += (matrixResult[this.line][k] * matrixResult[k][j]);
                }
            }
        }
    }

    void MatrixAdditionMultiThreaded(boolean memzeroMatrix) {
        System.out.println("Running matrix addition multi threaded");

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        measureElapsedTime(TIME_ELAPSED_STATE.START_MEASURE);
        for (int i = 0; i < MAX_LINES; i++) {
            executorService.execute(new MatrixAdditionMultithreaded(i));
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MICROSECONDS);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        measureElapsedTime(TIME_ELAPSED_STATE.END_MEASURE);
    }
    void MatrixMultiplicationSingleThreaded(boolean memzeroMatrix) {
        System.out.println("Running matrix multiplication single threaded");

        if (memzeroMatrix) {
            initializeMatrix(matrixResult, false);
        }

        measureElapsedTime(TIME_ELAPSED_STATE.START_MEASURE);
        for (int i = 0; i < MAX_LINES; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                for (int k = 0; k < MAX_COLUMNS; k++) {
                    matrixResult[i][j] += (matrixA[i][k] * matrixB[k][j]);
                }
            }
        }
        measureElapsedTime(TIME_ELAPSED_STATE.END_MEASURE);
    }
    void MatrixMultiplicationMultiThreaded(boolean memzeroMatrix) {
        System.out.println("Running matrix multiplication multi threaded");

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        measureElapsedTime(TIME_ELAPSED_STATE.START_MEASURE);
        for (int i = 0; i < MAX_LINES; i++) {
            executorService.execute(new MatrixMultiplicationMultithreaded(i));
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MICROSECONDS);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        measureElapsedTime(TIME_ELAPSED_STATE.END_MEASURE);
    }
}
