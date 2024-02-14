import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main class for mode 2
 */
class ModeOne {

    public char[] genomeArray;

    public char firstChar;
    public char secondChar;
    public int initialPosition;

    public String outputFile;
    public FileWriter fileWriter;

    public ArrayList<String> stringList;
    public ArrayList<Integer> list;
    public ArrayList<Integer> startPositionList;


    /**
     * Constructor for mode 1 where a file is created for the output
     * and the string is read from the input file, put into an array and sent
     * to a repetition checker that finds and prints out the repetitions
     *
     * @param filename input file that gets read from
     * @throws IOException throws IOException, which is a Java
     * exception that occurs when an IO operation fails
     */

    ModeOne(String filename) throws IOException {
    File file = new File("../out/"
            + createOutputFile(createInputFile(filename)));
    fileWriter = new FileWriter(file);
    In in = new In(filename);
    String genome = in.readString();
    addToArray(genome);
    stringList = new ArrayList<>();
    list = new ArrayList<>();
    startPositionList = new ArrayList<>();
    xyx(genomeArray);
    }

    /**
     * Populates a char array from the given sequence
     *
     * @param genome genome string that has to be put into array
     */
    public void addToArray(String genome) {
        genomeArray = new char[genome.length()];
        for (int i = 0; i < genome.length(); i++) {
            genomeArray[i] = genome.charAt(i);
        }
    }


    /**
     * Finds an xyx and sends it to repetitions() to finds another
     *
     * @param genomeArr char array consisting of chars in the genome
     * @throws IOException throws IOException, which is a Java exception
     *  that occurs when an IO operation fails
     */
    public void xyx(char[] genomeArr) throws IOException {
        for (int i = 0; i < genomeArr.length; i++) {
            firstChar = genomeArr[i];
            initialPosition = i;
            for (int j = i + 1; j < genomeArr.length; j++) {

                if (genomeArr[j] == firstChar) {
                    int length = 0;
                    secondChar = genomeArr[j];

                    char[] array = new char[j - i + 1];

                    for (int k = 0; k < j - i + 1; k++) {
                        array[k] = genomeArr[k + i];
                        length++;
                    }
                    String xyx = new String(array);
                    repetitions(xyx, length, i);
                }
            }
        }
        if (!stringList.isEmpty()) {
            for (int i = 0; i < stringList.size(); i++) {
                fileWriter.write(stringList.get(i)
                        + " " + startPositionList.get(i)
                        + " " + list.get(i));
                fileWriter.write("\r\n");
            }
        } else {
            fileWriter.write("None");
        }
        fileWriter.close();

    }


    /**
     * finds repetitions and adds the starting index and current
     * index to arraylists
     *
     * @param xyx           String parameter for the xyx repetition in
     *                      sequence
     * @param length        int parameter for length of xyx repetition
     * @param startPosition int value for the position where the
     *                      repetition starts
     */
    public void repetitions(String xyx, int length, int startPosition) {
        char[] array = new char[length];
        for (int i = startPosition + 1; i < genomeArray.length
                - xyx.length() + 1; i++) {
            if (length >= 0) {
                System.arraycopy(genomeArray, i, array, 0, length);
            }

            String str = new String(array);

            if (xyx.equals(str)) {
                stringList.add(str);
                startPositionList.add(startPosition);
                list.add(i);
            }

        }
    }


    /**
     * Creates new file for output to be sent to
     *
     * @param filename Original filename
     * @return new filename that will be situated in the ../out folder
     */
    public String createOutputFile(String filename) {
            int i = 0;
            while (filename.charAt(i) != '.'
                    || filename.charAt(i) == filename.length()) {
                i++;
            }
            char[] array = new char[i];
            for (int j = 0; j < i; j++) {
                array[j] = filename.charAt(j);
            }
            StringBuilder stringBuffer = new StringBuilder();

        for (char c : array) {
            stringBuffer.append(c);
        }

            String str = stringBuffer.toString();
            outputFile = str + "_chk.txt";

        return outputFile;
    }

    /**
     * Creates and returns file extension for each test case
     *
     * @param filename Original filename
     * @return Input file
     *
     * */

    public String createInputFile(String filename) {
        if ((filename.contains("/"))) {
            int position;
            int maxP = 0;
            for (int i = filename.length() - 1; i >= 0; i--) {
                if ((filename.charAt(i) + "").equals("/")) {
                    position = i;
                    if (position > maxP) {
                        maxP = i;
                    }
                }
            }

            String[] str = new String[(filename.length()) - (maxP + 1)];
            int j = 0;

            for (int i = maxP + 1; i < filename.length(); i++) {
                str[j] = filename.charAt(i) + "";
                j++;
            }

            StringBuilder stringBuffer = new StringBuilder();

            for (String s : str) {
                stringBuffer.append(s);
            }
            filename = stringBuffer.toString();
        }

        return filename;
    }

}
