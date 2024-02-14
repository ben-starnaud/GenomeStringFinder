import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main class for mode 2
 */
class ModeTwo {
    public static String[] fullAlphabet = new String[]{"A", "C", "G", "T"};
    public static String char1;
    public static String char2;
    public static String char3;


    public static String outputFile;
    public static char firstChar;
    public static char secondChar;
    public static int initialPosition;

    public static char[] charArray;

    public static ArrayList<String> mainList;
    public static ArrayList<String> repetitionsList;
    public static FileWriter fileWriter;

    /**
     * Constructor for mode 2
     * Contains an if statement for the specific alphabet size and
     * runs all the methods needed
     *
     * @param alphabetSize int parameter for the amount of alphabet symbols
     *                     usable
     * @param startChar String parameter for the char value for which
     *                  the strings must start
     * @param depth int parameter for the length of the string outputted
     * @throws IOException throws IOException, which is a Java exception
     *                      that occurs when an IO operation fails
     */
    ModeTwo(int alphabetSize, String startChar, int depth)
            throws IOException {

        File file = new File("../out/" + createOutputFile());
        fileWriter = new FileWriter(file);

        alphabetPopulation(startChar, alphabetSize);
        mainList = new ArrayList<>();
        repetitionsList = new ArrayList<>();

        /*
         * If alphabet size is one
         */
        if (alphabetSize == 1) {
            char[] set1 = new char[]{
                    startChar.charAt(0)
            };

            for (int k = 0; k < 2; k++) {
                stringPrinter(set1, k, startChar);
            }

            mainList.removeAll(repetitionsList);
            printOutput();


            /*
             * If alphabet size is two
             */
        } else if (alphabetSize == 2) {
            char[] set1 = new char[]{
                    startChar.charAt(0),
                    char1.charAt(0)
            };

            if (depth != 0) {
                for (int k = 0; k < depth; k++) {
                    stringPrinter(set1, k, startChar);
                }
            } else {
                int k = 0;
                while (k < 8) {
                    stringPrinter(set1, k, startChar);
                    k++;
                }

            }

            mainList.removeAll(repetitionsList); // removes repetition
            // strings from arraylist
            printOutput();


            /*
             * If alphabet size is three
             */

        } else if (alphabetSize == 3) {

            char[] set1 = new char[]{
                    startChar.charAt(0),
                    char1.charAt(0),
                    char2.charAt(0)
            };

            if (depth != 0) {
                for (int k = 0; k < depth; k++) {
                    stringPrinter(set1, k, startChar);
                }

            } else {
                for (int k = 0; k < 22; k++) {
                    stringPrinter(set1, k, startChar);
                }

            }
            mainList.removeAll(repetitionsList);
            printOutput();


        /*
          If alphabet size is four
         */
        } else if (alphabetSize == 4) {
            char[] set1 = new char[] {
                    startChar.charAt(0),
                    char1.charAt(0),
                    char2.charAt(0),
                    char3.charAt(0) };

            for (int k = 0; k < depth; k++) {
                stringPrinter(set1, k, startChar);
            }

            mainList.removeAll(repetitionsList);
            printOutput();
        }
    }

    /**
     * Void method that prints output out
     * @throws IOException throws IOException, which is a Java
     * exception that occurs when an IO operation fails
     */
    public static void printOutput() throws IOException {
        for (String s : mainList) {
            fileWriter.write(s.length() + " - " + s);
            fileWriter.write("\r\n");
        }
        fileWriter.close();
    }

    /**
     *
     * Method that check for the repetitions and adds them to a separate
     * arraylist.
     *
     * @param prefix String that has to be checked
     */
    public static void checkForRepetitions(String prefix) {
            addToArray(prefix);
            repetitionFinder(charArray, prefix);
    }

    /**
     * Recursive method to generate strings that start with the startChar
     * @param set char array that contains all the letters of the given
     *            alphabet size
     * @param k int parameter for the depth
     * @param startChar String parameter for the starting Symbol
     */
    public static void stringPrinter(char[] set, int k, String startChar) {
        int n = set.length;
        recursivePrinter(set, startChar, n, k);
    }

    /**
     * Recursive method that add characters onto string so that it can form
     * longer strings with no repetitions.
     * @param set char array that contains all the letters of the given
     *            alphabet size
     * @param prefix String letter that was previously added (Initially the
     *               startChar)
     * @param n int parameter for the length of the set
     * @param k int parameter for the depth
     */
    public static void recursivePrinter(char[] set, String prefix,
                                        int n, int k) {

        if (k == 0) {
            mainList.add(prefix);
            return;
        }

        for (int i = 0; i < n; ++i) {
            String newPrefix = prefix + set[i];
            checkForRepetitions(newPrefix);
            recursivePrinter(set, newPrefix, n, k - 1);
        }
    }

    /**
     * Finds an xyx and sends it to repetitions() to finds another
     *
     * @param charArray char array build up of word that must tested
     *                  for repetitions
     * @param string full word that is being tested
     */
    public static void repetitionFinder(char[] charArray, String string) {
        for (int i = 0; i < charArray.length; i++) {
            firstChar = charArray[i];
            initialPosition = i;
            for (int j = i + 1; j < charArray.length; j++) {
                if (charArray[j] == firstChar) {
                    int length = 0;
                    secondChar = charArray[j];

                    char[] array = new char[j - i + 1];
                    for (int k = 0; k < j - i + 1; k++) {
                        array[k] = charArray[k + i];
                        length++;
                    }
                    String xyx = new String(array);
                    repetitions(xyx, length, i, string);
                }
            }

        }

    }

    /**
     * Sub-Method that finds repetitions for specific sequence and then
     * adds all the sequences with repetitions to an arraylist for deletion
     * @param xyx String parameter for the xyx repetition in sequence
     * @param length int parameter for length of xyx repetition
     * @param startPosition int value for the position where the repetition
     *                     starts.
     * @param string full word that is being tested
     */
    public static void repetitions(String xyx, int length,
                                   int startPosition, String string) {

        char[] arr = new char[length];

        for (int i = startPosition + 1;
             i < charArray.length - xyx.length() + 1; i++) {

             if (length >= 0) { //keep eye
                 System.arraycopy(charArray, i, arr, 0, length);
                 String str = new String(arr);
                 if (xyx.equals(str)) {
                     repetitionsList.add(string);
                 }
             }
        }

    }

    /**
     * Finds and populates the char value of the alphabet for the given
     * alphabet size
     * @param startChar String parameter for the starting Symbol
     * @param alphabetSize int parameter for the amount of alphabet symbols
     *                     usable.
     */
    public static void alphabetPopulation(String startChar, int alphabetSize) {
        int index = findIndex(startChar);
        if (alphabetSize == 2) {
            if (index == 0) {
                char1 = fullAlphabet[1];
            } else {
                char1 = fullAlphabet[0];
            }
        } else if (alphabetSize == 3) {
            if (index == 0) {
                char1 = fullAlphabet[1];
                char2 = fullAlphabet[2];
            } else {
                char1 = fullAlphabet[0];
                if (index == 2 || index == 3) {
                    char2 = fullAlphabet[1];
                } else {
                    char2 = fullAlphabet[3];
                }
            }

        } else if (alphabetSize == 4) {
            if (index == 0) {
                char1 = fullAlphabet[1];
                char2 = fullAlphabet[2];
                char3 = fullAlphabet[3];
            } else if (index == 1) {
                char1 = fullAlphabet[0];
                char2 = fullAlphabet[2];
                char3 = fullAlphabet[3];
            } else if (index == 2) {
                char1 = fullAlphabet[0];
                char2 = fullAlphabet[1];
                char3 = fullAlphabet[3];
            } else if (index == 3) {
                char1 = fullAlphabet[0];
                char2 = fullAlphabet[1];
                char3 = fullAlphabet[2];
            }
        }
    }
    /**
     * Finds index of the startChar in the alphabet
     * @param startChar String parameter for the starting Symbol
     * @return returns the int index
     */
    public static int findIndex(String startChar) {
        int index = 0;
        for (int i = 0; i < 4; i++) {
            if (startChar.equals(fullAlphabet[i])) {
                index = i;
            }
        }
        return index;
    }
    /**
     * Populates a char array from the given sequence
     * @param string String sequence that needs to be put into an array
     */
    public static void addToArray(String string) {
        charArray = new char[string.length()];
        for (int i = 0; i < string.length(); i++) {
            charArray[i] = string.charAt(i);
        }
    }

    /**
     * Creates new file for output to be sent to
     *
     * @return new filename that will be situated in the ../out folder
     */
    public static String createOutputFile() {

        File file = new File("../out");

        String[] contents = file.list();
        int max = 0;

        if (!(contents == null)) {
            for (String content : contents) {
                int value = Character.getNumericValue(content.charAt(3));
                if (value > max) {
                    max = value;
                }
            }
        }
        outputFile = "gen" + (max) + "_bf.txt";
        return outputFile;

    }
}





