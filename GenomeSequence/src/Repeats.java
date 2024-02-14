import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Main class that contains main method and gets all the inputs from args
 */
public class Repeats {

    static String outputFile;
    static String filename;

    /**
     * Main method
     * @param args args is used to input all the arguments
     * @throws IOException throws IOException, which is a Java
     * exception that occurs when an IO operation fails
     */

    public static void main(String[] args) throws IOException {

        int mode = Integer.parseInt(args[0]);

        if (mode == 1) {
            try {
                filename = args[1] + "";
                File file = new File("../out/"
                        + createOutputFileM1(createInputFile(filename)));
                FileWriter fileWriter = new FileWriter(file);
         if (args.length > 2) {
             System.err.println("ERROR: invalid number of arguments");
             fileWriter.write("ERROR: invalid number of arguments");
             fileWriter.close();
         }
         if (args.length == 2) {
             try {
                ModeOne m1 = new ModeOne(filename);
             } catch (NullPointerException nullPointerException) {
                 System.err.println("ERROR: invalid or missing file");
                 fileWriter.write("ERROR: invalid or missing file");
                 fileWriter.close();
             }
         }
         } catch (ArrayIndexOutOfBoundsException | FileNotFoundException
                    | StringIndexOutOfBoundsException | NullPointerException
                    indexOutOfBoundsException) {
           System.err.println("ERROR: invalid number of arguments");
           try {
               File file = new File("../out/"
                       + createOutputFileM1(createInputFile(filename)));
               FileWriter fileWriter = new FileWriter(file);
               fileWriter.write("ERROR: invalid number of arguments");
               fileWriter.close();
           } catch (IOException ignored) {

           }

            } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


        } else if (mode == 2) {
            try {
                int alphabetSize = Integer.parseInt(args[1]);
                String startChar = args[2] + "";
                int depth = Integer.parseInt(args[3]);
                ModeTwo m2 = new ModeTwo(alphabetSize, startChar, depth);
            } catch (IOException ignored) {

            }
        } else if (mode == 3) {

            int alphabetSize = Integer.parseInt(args[1]);
            String startChar = args[2] + "";
            double maxTime = Double.parseDouble(args[3]);

             ModeThree modeThree = new ModeThree(alphabetSize, startChar, maxTime);


        } else {
            try {

                if (args.length == 2 || args.length == 4) {
                System.err.println("ERROR: invalid mode");

                if (args.length == 2) {
                    System.err.println("ERROR: invalid number of arguments");
                } else {
                    FileWriter fileWriter = new FileWriter("../out/"
                            + createOutputFileM2());
                    fileWriter.write("ERROR: invalid mode");
                    fileWriter.close();
                }
              } else {
                  System.err.println("ERROR: invalid number of arguments");
              }
                } catch (IOException ignored) {

                }

            }
    }
    /**
     * Creates new file for output to be sent to
     *
     * @param filename Original filename
     * @return new filename that will be situated in the ../out folder
     */
    public static String createOutputFileM1(String filename) {
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
     * Creates new file for output to be sent to for mode 2
     *
     * @return new filename that will be situated in the ../out folder
     *
     */
    public static String createOutputFileM2() {

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


    /**
     * Creates and returns file extension for each test case
     *
     * @param filename Original filename
     * @return Input file
     *
     */
    public static String createInputFile(String filename) {
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

