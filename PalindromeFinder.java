import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A palindrome is a word, that if spelled backwards, produces the same word.
 * An example of this would be the word 'racecar'. Interestingly, sometimes a word
 * that is not a palindrome in normal English can be a palindrome when converted
 * into morse code. An example of this would be the word 'ya' -> (-.--.-).
 *
 * This is a simple program will take each word in a provided dictionary
 * and find all the morse code palindromes using characters ranging from a - z.
 *
 * @author Justin Newkirk
 * @date   June 28, 2022
 */
public class PalindromeFinder {

    // Member Variables =======================================================

    // This is the morse code alphabet from a - z.
    // The first number details how manny morse dots or dashes
    // the letter contains and the following 1's will represent a dot (.)
    // and 0's will represent a dash (-).
    private static final int[][] MORSE_ALPHABET = {
            {2, 1, 0},       // a (.-)
            {4, 0, 1, 1, 1}, // b (-...)
            {4, 0, 1, 0, 1}, // c (-.-.)
            {3, 0, 1, 1},    // d (-..)
            {1, 1},          // e (.)
            {4, 1, 1, 0, 1}, // f (..-.)
            {3, 0, 0, 1},    // g (--.)
            {4, 1, 1, 1, 1}, // h (....)
            {2, 1, 1},       // i (..)
            {4, 1, 0, 0, 0}, // j (.---)
            {3, 0, 1, 0},    // k (-.-)
            {4, 1, 0, 1, 1}, // l (.-..)
            {2, 0, 0},       // m (--)
            {2, 0, 1},       // n (-.)
            {3, 0, 0, 0},    // o (---)
            {4, 1, 0, 0, 1}, // p (.--.)
            {4, 0, 0, 1, 0}, // q (--.-)
            {3, 1, 0, 1},    // r (.-.)
            {3, 1, 1, 1},    // s (...)
            {1, 0},          // t (-)
            {3, 1, 1, 0},    // u (..-)
            {4, 1, 1, 1, 0}, // v (...-)
            {3, 1, 0, 0},    // w (.--)
            {4, 0, 1, 1, 0}, // x (-..-)
            {4, 0, 1, 0, 0}, // y (-.--)
            {4, 0, 0, 1, 1}  // z (--..)
    };


    // Member Methods =========================================================

    /**
     * Main entry point into the program.
     *
     */
    public static void main(String[] args) throws FileNotFoundException {
        findMorsePalindrome();
    }

    /**
     * This will convert each word in the provided dictionary
     * into morse code and then determine whether it is a morse
     * code palindrome. If it is a palindrome, the word will be printed
     * along with its morse code representation.
     *
     * This will only consider words that contain the characters a - z.
     *
     * @throws FileNotFoundException - If the file isn't found, this exception will be thrown.
     */
    private static void findMorsePalindrome() throws FileNotFoundException {

        Scanner wordScanner = new Scanner(new File("words.txt"));
        int totalMorsePalindromes = 0;

        // Holds the morse code representation. The largest word in the dictionary
        // is 24 characters, of which the largest character morse code representation is 4
        // dots or dashes. Therefore, the most dots and dashes we could face would be 96
        // and we simply round up to 100.
        boolean[] morseCode = new boolean[100];

        // This will iterate over each word in the dictionary.
        while(wordScanner.hasNext()){

            String word = wordScanner.next();

            boolean isValidWord = true;
            int morseLength = 0;

            // Converts each word into morse code. If a character is encountered that is
            // not a - z, the word will be skipped.
            for(int i = 0; i < word.length(); i++) {

                int letter = word.charAt(i) - 'a';

                // Detects invalid characters.
                if(letter < 0 || letter > 25){
                    isValidWord = false;
                    break;
                }

                int[] morseLetter = MORSE_ALPHABET[letter];
                int morseLetterLength = MORSE_ALPHABET[letter][0];

                for(int j = 0; j < morseLetterLength; j++)
                    morseCode[morseLength + j] = morseLetter[j + 1] == 1;

                morseLength += morseLetterLength;
            }

            // A non-valid word will be skipped here.
            if(!isValidWord)
                continue;

            boolean isPalandrome = true;
            int left  = 0;
            int right = morseLength - 1;

            // This will detect if the morse code representation is a palindrome.
            while(left < right){

                if(morseCode[left] != morseCode[right]){
                    isPalandrome = false;
                    break;
                }

                left++;
                right--;
            }

            // If the morse representation is a palindrome, it will be printed
            // to the console here.
            if(isPalandrome){

                totalMorsePalindromes++;

                System.out.print("[" + totalMorsePalindromes + "] " + word + ": ( ");

                for(int i = 0; i < morseLength; i++)
                    System.out.print(morseCode[i] ? "." : "-");

                System.out.print(" )\n");
            }
        }
    }
}
