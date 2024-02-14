import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Main class for mode 3
 *
 */
class ModeThree {

    public String[] fullAlphabet = new String[]{"A", "C", "G", "T"};
    public boolean repetitions;
    public char firstChar;
    Stopwatch stopwatch;
    public char secondChar;
    public int initialPosition;
    public char[] charArray;
    public static String outputFile;
    public static String longestString;
    public static int maxDepth;
    public static double maxTimer;

    public static final ArrayList<String> ALL_ALPHABET = new ArrayList<>();

    /**
     * Constructor for mode 3 that runs all the code part by part
     *
     * @param alphabetSize size of the given alphabet
     * @param startChar starting character
     * @param maxTime maximum time possible to achieve a result
     * @throws IOException throwable exception
     */

     ModeThree(int alphabetSize, String startChar, double maxTime)
            throws IOException {

        stopwatch = new Stopwatch();
        maxTimer = maxTime;
        File file = new File("../out/" + createOutputFile());
        alphabetPopulation(startChar, alphabetSize);
        String output = recursiveString(startChar, alphabetSize);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(output);
        fileWriter.close();
    }
    /**
     * Creates new file for output to be sent to
     *
     * @return new filename that will be situated in the ../out folder
     */
    public static String createOutputFile() {

        File file = new File("../out");

        String[] contents = file.list();
        int max = 1;

        for (int i = 0; i < contents.length; i++) {
            if (contents[i].endsWith("_opt.txt")) {
                String string = contents[i].substring(3, contents[i].indexOf("_"));
                int number = Integer.parseInt(string);
                if (number + 1 > max) {
                    max = number + 1;
                }
            }
        }
        outputFile = "out" + (max) + "_opt.txt";
        return outputFile;

    }

    /**
     * recursive String method recursively adds characters form the usable
     * alphabet to the string and
     * looks for repetitions, if there is it - it uses another one. The method
     * ends when the max time has been reached.
     *
     * @return returns the longest string and length
     * @param startSymbol recursive sequence
     * @param depth length of string
     */
    public String recursiveString(String startSymbol, int depth) {
        if (stopwatch.elapsedTime() > (maxTimer - (maxTimer * (0.20)))) {
            repetitions = false;
            return maxDepth + " - " + longestString;
        } else {
            for (String s : ALL_ALPHABET) {
                String sequence = startSymbol + s;
                repetitions = false;
                checkForRepetitions(sequence);
                if (!repetitions) {
                    int newDepth = depth + 1;
                    if (newDepth >= maxDepth) {
                        longestString = sequence;
                        maxDepth = longestString.length();
                    }
                    recursiveString(sequence, newDepth);
                }
            }
        }
        return maxDepth + " - " + longestString;
    }
    /**
     * Finds and populates the char value of the alphabet for the given
     * alphabet size
     *
     * @param startChar String parameter for the starting Symbol
     * @param alphabetSize int parameter for the amount of alphabet symbols
     *                     usable
     */

    public void alphabetPopulation(String startChar, int alphabetSize) {
        int index = findIndex(startChar);
        if (alphabetSize == 1) {
            ALL_ALPHABET.add(startChar);
        } else if (alphabetSize == 2) {
            ALL_ALPHABET.add(startChar);
            if (index == 0) {
                ALL_ALPHABET.add(fullAlphabet[1]);
            } else {
                ALL_ALPHABET.add(fullAlphabet[0]);
            }
        } else if (alphabetSize == 3) {
            ALL_ALPHABET.add(startChar);
            if (index == 0) {
                ALL_ALPHABET.add(fullAlphabet[1]);
                ALL_ALPHABET.add(fullAlphabet[2]);
            } else {
                ALL_ALPHABET.add(fullAlphabet[0]);
                if (index == 2 || index == 3) {
                    ALL_ALPHABET.add(fullAlphabet[1]);
                } else {
                    ALL_ALPHABET.add(fullAlphabet[3]);
                }
            }

        } else if (alphabetSize == 4) {
            ALL_ALPHABET.add(startChar);
            if (index == 0) {
                ALL_ALPHABET.add(fullAlphabet[1]);
                ALL_ALPHABET.add(fullAlphabet[2]);
                ALL_ALPHABET.add(fullAlphabet[3]);
            } else if (index == 1) {
                ALL_ALPHABET.add(fullAlphabet[0]);
                ALL_ALPHABET.add(fullAlphabet[2]);
                ALL_ALPHABET.add(fullAlphabet[3]);
            } else if (index == 2) {
                ALL_ALPHABET.add(fullAlphabet[0]);
                ALL_ALPHABET.add(fullAlphabet[1]);
                ALL_ALPHABET.add(fullAlphabet[3]);
            } else if (index == 3) {
                ALL_ALPHABET.add(fullAlphabet[0]);
                ALL_ALPHABET.add(fullAlphabet[1]);
                ALL_ALPHABET.add(fullAlphabet[2]);
            }
        }
    }
    /**
     * Method that check for the repetitions and adds them to a
     * separate arraylist.
     *
     * @param prefix String that has to be checked
     */
    public void checkForRepetitions(String prefix) {
        addToArray(prefix);
        repetitionFinder(charArray);
    }


    /**
     * Finds an xyx and sends it to repetitions() to finds another
     *
     * @param charArray char array build up of word that must tested
     *                  for repetitions.
     */
    public void repetitionFinder(char[] charArray) {
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
                    repetitions(xyx, length, i);
                }
            }
        }
    }

    /**
     * Sub-Method that finds repetitions for specific sequence and
     * then adds all the sequences with repetitions to an
     * arraylist for deletion.
     *
     * @param xyx           String parameter for the xyx repetition
     *                      in sequence.
     * @param length        int parameter for length of xyx repetition
     * @param startPosition int value for the position where the
     *                      repetition starts.
     */
    public void repetitions(String xyx, int length, int startPosition) {

        char[] arr = new char[length];
        for (int i = startPosition + 1;
             i < charArray.length - xyx.length() + 1; i++) {
            if (length >= 0) {
                System.arraycopy(charArray, i, arr, 0, length);
            }
                String str = new String(arr);
                if (xyx.equals(str)) {
                    repetitions = true;
                }

        }
    }
    /**
     * Finds index of the startChar in the alphabet
     *
     * @param startChar String parameter for the starting Symbol
     * @return returns the int index
     */
    public int findIndex(String startChar) {
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
     *
     * @param string String sequence that needs to be put into an array
     */
    public void addToArray(String string) {
        charArray = new char[string.length()];
        for (int i = 0; i < string.length(); i++) {
            charArray[i] = string.charAt(i);
        }
    }
}
