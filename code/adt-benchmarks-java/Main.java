import java.util.Random;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.ArrayList; // for storing test results temporarily, and comparison

// Used for storing the results/times of all operations on a particular dsa
class ResultTime {
    public double insert;
    public double find;
    public double remove;
}

public class Main {
    // For generating random numbers
    private static final Random rand = new Random();
    // A formatted that ensures all numbers are formatted to 2f precision
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) {
        boolean quit = false;
        int choice = -1;

        Scanner scanner = new Scanner(System.in);

        while (!quit) {
            System.out.println("Testing Menu");
            System.out.println("1 - Run Consistent Test Suite");
            System.out.println("2 - Run Random Test Suite");
            System.out.println("3 - Manually Test AVL Tree");
            System.out.println("4 - Manually Test Splay Tree");
            System.out.println("5 - Manually Test Hash Table (w/ Chaining)");
            System.out.println("6 - Manually Test Hash Table (w/ Quadratic Probing)");
            System.out.println("7 - Quit");
            System.out.print("-- Selection: ");

            choice = scanner.nextInt();

            System.out.println();
            switch (choice) {
                case 1:
                    System.out.println("Running test suite...\n");
                    runConsistentTestSuite();
                    break;
                case 2:
                    System.out.println("Running test suite...\n");
                    runRandomTestSuite();
                    break;
                case 3:
                    System.out.println("Testing AVL Tree...\n");
                    testAVLTree();
                    break;
                case 4:
                    System.out.println("Testing Splay Tree...\n");
                    testSplayTree();
                    break;
                case 5:
                    System.out.println("Testing Hash Table (w/ Chaining)...\n");
                    testHashWithChaining();
                    break;
                case 6:
                    System.out.println("Testing Hash Table (w/ Quadratic Probing)...\n");
                    testHashWithQuadProbing();
                    break;
                case 7:
                    System.out.println("Goodbye...\n");
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid Choice.");
                    System.out.println();
                    break;
            }
        }

        scanner.close();
    }

    public static void runRandomTestSuite() {
        runTestSuite(false);
    }

    public static void runConsistentTestSuite() {
        runTestSuite(true);
    }

    public static void runTestSuite(boolean useData) {
        // Initialize an array storing each Collection type (ADT) to be compared
        ArrayList<ContainerType<Integer>> collections = new ArrayList<>();
        collections.add( new AVLTree<Integer>() );
        collections.add( new SplayTree<Integer>() );
        collections.add( new ChainingHashTable<Integer>() );
        collections.add( new QuadProbingHashTable<Integer>() );

        // The different sizes of n to test against
        int[] testSizes = {
              1_000,
             10_000,
            100_000
        };

        ArrayList<Integer[]> testArrays = new ArrayList<>(3);

        if (useData) {

            testArrays.add(0, new Integer[1000]);
            testArrays.add(1, new Integer[10000]);
            testArrays.add(2, new Integer[100000]);

            for (int i = 0; i < testSizes.length; i++) {
                testArrays.set(i, getRandomValuedArray(testSizes[i]));
            }
        }

        // A 2D array to store a table of Results at each N for each collection type.
        // eg. SplayTree -> [ n10, n100, n1000 ]
        //     AVLTree   -> [ n10, n100, n1000 ]
        //     HashTable -> [ n10, n100, n1000 ]
        ArrayList<ArrayList<ResultTime>> results = new ArrayList<>(collections.size());
        results.add( new ArrayList<ResultTime>(testSizes.length) );
        results.add( new ArrayList<ResultTime>(testSizes.length) );
        results.add( new ArrayList<ResultTime>(testSizes.length) );
        results.add( new ArrayList<ResultTime>(testSizes.length) );

        for (int i = 0; i < results.size(); i++) {
            ContainerType<Integer> obj = collections.get(i);

            System.out.println("-----");
            System.out.println("Running test operations on " + obj.getClass().getSimpleName());
            System.out.println("-----");
            System.out.println();

            ArrayList<ResultTime> dsaResults = results.get(i);

            for (int n_i = 0; n_i < testSizes.length; n_i++) {
                dsaResults.add( new ResultTime() );

                ResultTime dsaResultAtN = dsaResults.get(n_i);

                if (useData) {
                    dsaResultAtN.insert = insertFromArray(testSizes[n_i], obj, testArrays.get(n_i));
                    dsaResultAtN.find = findFromArray(testSizes[n_i], obj, testArrays.get(n_i));
                    dsaResultAtN.remove = removeFromArray(testSizes[n_i], obj, testArrays.get(n_i));
                } else {
                    dsaResultAtN.insert = insertNTimes(testSizes[n_i], obj);
                    dsaResultAtN.find = findNTimes(testSizes[n_i], obj);
                    dsaResultAtN.remove = removeNTimes(testSizes[n_i], obj);
                }

                obj.clear();
            }
        }

        System.out.println("Insertion Performance Comparison (Time in milliseconds)");
        System.out.printf("%-25s%-15d%-15d%-15d\n", "ADT", testSizes[0], testSizes[1], testSizes[2]);

        for (int i = 0; i < collections.size(); i++) {
            System.out.printf("%-25s", collections.get(i).getClass().getSimpleName() + " ");

            for (ResultTime result : results.get(i)) {
                System.out.printf("%-15s", df.format(result.insert) + "ms  ");
            }
            System.out.println();
        }
        System.out.println(); // Newline for readability

        System.out.println("Search Performance Comparison (Time in milliseconds)");
        System.out.printf("%-25s%-15d%-15d%-15d\n", "ADT", testSizes[0], testSizes[1], testSizes[2]);

        for (int i = 0; i < collections.size(); i++) {
            System.out.printf("%-25s", collections.get(i).getClass().getSimpleName() + " ");

            for (ResultTime result : results.get(i)) {
                System.out.printf("%-15s", df.format(result.find) + "ms  ");
            }
            System.out.println();
        }
        System.out.println(); // Newline for readability

        System.out.println("Removal Performance Comparison (Time in milliseconds)");
        System.out.printf("%-25s%-15d%-15d%-15d\n", "ADT", testSizes[0], testSizes[1], testSizes[2]);

        for (int i = 0; i < collections.size(); i++) {
            System.out.printf("%-25s", collections.get(i).getClass().getSimpleName() + " ");

            for (ResultTime result : results.get(i)) {
                System.out.printf("%-15s", df.format(result.remove) + "ms  ");
            }
            System.out.println();
        }
        System.out.println(); // Newline for readability

        System.out.println("Test suite completed...\n");
    }

    public static void testAVLTree() {
        testContainer(new AVLTree<Integer>());
    }

    public static void testSplayTree() {
        testContainer(new SplayTree<Integer>());
    }

    public static void testHashWithChaining() {
        testContainer(new ChainingHashTable<Integer>());
    }

    public static void testHashWithQuadProbing() {
        testContainer(new QuadProbingHashTable<Integer>());
    }

    public static void testContainer(ContainerType<Integer> obj) {
        boolean back = false;
        int choice = -1;

        Scanner scanner = new Scanner(System.in);

        while (!back) {
            System.out.println("Testing Menu");
            System.out.println("1 - Insert a value");
            System.out.println("2 - Remove a value");
            System.out.println("3 - Find a value");
            System.out.println("4 - Print Collection");
            System.out.println("5 - Quit");
            System.out.print("-- Selection: ");

            choice = scanner.nextInt();

            System.out.println();
            switch (choice) {
                case 1:
                    System.out.println("Insert a value: ");
                    int valToInsert = scanner.nextInt();
                    if (obj.insert(valToInsert)) {
                        System.out.println("Inserted " + valToInsert + "\n");
                    } else {
                        System.out.println("Unable to insert duplicate " + valToInsert + ".\n");
                    }
                    break;
                case 2:
                    System.out.println("Remove a value: ");
                    int valToRemove = scanner.nextInt();
                    if (obj.remove(valToRemove)) {
                        System.out.println("Removed " + valToRemove + "\n");
                    } else {
                        System.out.println("Unable to remove " + valToRemove + ".\n");
                    }
                    break;
                case 3:
                    System.out.println("Find a value: ");
                    int valToFind = scanner.nextInt();
                    if (obj.contains(valToFind)) {
                        System.out.println("Found " + valToFind + "\n");
                    } else {
                        System.out.println("Unable to find " + valToFind + ".\n");
                    }
                    break;
                case 4:
                    System.out.println("Printing collection...");
                    obj.print();
                    System.out.println("\n");
                    break;
                case 5:
                    System.out.println("Going back to main menu...\n");
                    back = true;
                    break;
                default:
                    System.out.println("Invalid Choice.");
                    System.out.println();
                    break;
            }
        }
    }

    public static Integer[] getRandomValuedArray(int n) {
        Integer[] array = new Integer[n];

        for (int i = 0; i < n; i++) {
            array[i] = rand.nextInt(n+1);
        }

        return array;
    }

    public static double insertNTimes(int n, ContainerType<Integer> obj) {
        Integer[] array = getRandomValuedArray(n);

        return insertFromArray(n, obj, array);
    }

    public static double removeNTimes(int n, ContainerType<Integer> obj) {
        Integer[] array = getRandomValuedArray(n);

        return removeFromArray(n, obj, array);
    }

    public static double findNTimes(int n, ContainerType<Integer> obj) {
        Integer[] array = getRandomValuedArray(n);

        return findFromArray(n, obj, array);
    }

    public static double insertFromArray(int n, ContainerType<Integer> obj, Integer[] array) {
        System.out.println("Adding " + n + " elements to " + obj.getClass().getSimpleName() + ".");
        long start = System.nanoTime();

        int nAdded = 0;
        for (int i = 0; i < n; i++) {
            if (obj.insert(array[i])) {
                nAdded++;
            }
        }

        long end = System.nanoTime();
        double duration = (end - start) / Math.pow(10, 6);

        System.out.println("Added " + nAdded + " elements.");
        System.out.println("Time elapsed: " + df.format(duration) + "ms");
        System.out.println();

        return duration;
    }

    public static double removeFromArray(int n, ContainerType<Integer> obj, Integer[] array) {
        System.out.println("Attempting to remove " + n + " elements from " + obj.getClass().getSimpleName() + ".");

        long start = System.nanoTime();

        int nRemoved = 0;
        for (int i = 0; i < n; i++) {
            if(obj.remove(array[i])) {
                nRemoved++;
            }
        }

        long end = System.nanoTime();
        double duration = (end - start) / Math.pow(10, 6);

        int nNotFound = n - nRemoved;
        System.out.println("Removed " + nRemoved
                         + " elements (" + nNotFound + " not found).");
        System.out.println("Time elapsed: " + df.format(duration) + "ms");
        System.out.println();

        return duration;
    }

    public static double findFromArray(int n, ContainerType<Integer> obj, Integer[] array) {
        System.out.println("Attempting to find " + n + " elements in " + obj.getClass().getSimpleName()+ ".");

        int nFound = 0;

        long start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            if (obj.contains(array[i])) {
                nFound++;
            }
        }

        long end = System.nanoTime();
        double duration = (end - start) / Math.pow(10, 6);

        int nNotFound = n - nFound;
        System.out.println("Found " + nFound
                         + " elements (" + nNotFound + " not found).");
        System.out.println("Time elapsed: " + df.format(duration) + "ms");
        System.out.println();

        return duration;
    }
}
