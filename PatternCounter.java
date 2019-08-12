import java.util.*;
import java.io.*;

// Pattern counting class, Tom Tom skills test
public class PatternCounter
{
    // The following is a structure returned from the ToInteger() method
    // It stores the value, and whether or not it's valid.
    private static class IntegerTest
    {
        private int value_ = 0;
        private boolean valid_ = false;

        public IntegerTest(int value, boolean valid)
        {
            this.value_ = value;
            this.valid_ = valid;
        }

        public int GetValue()
        {
            return value_;
        }

        public boolean IsValid()
        {
            return valid_;
        }
    }

    // Tests if a String is an integer or not. If it's not, the exception
    // is ignored, as we don't care about it.
    private static IntegerTest ToInteger(String Num)
    {
        int value = 0;
        boolean valid = false;
        
        try 
        { 
            value = Integer.parseInt(Num);
            valid = true; 
        }  
        catch (NumberFormatException e)  
        {
            // We don't care about the exception, ignore it
        }

        return new IntegerTest(value, valid);
    }

    // Pattern testing code, used for both pattern 1 and 2, as they are so
    // similar. Words are pushed into a HashMap, with a running count of
    // the occurences of the word. The HashMap is then iterated over, and
    // relevant results are printed.
    private static void Pattern(ArrayList<String> data, int num)
    {
        HashMap<String, Integer> occurences = new HashMap<String, Integer>();

        for (String str : data)
        {
            String[] words = str.split(" ");

            for (String word : words)
            {
                Integer count = occurences.get(word);

                if (count == null)
                {
                    occurences.put(word, 1);
                }
                else
                {
                    occurences.put(word, count + 1);
                }
            }
        }

        Set<Map.Entry<String, Integer>> set = occurences.entrySet();

        for (Map.Entry<String, Integer> me : set)
        {
            IntegerTest test = ToInteger(me.getKey());

            if (!test.IsValid() && num == 1)
            {
                System.out.print(me.getKey() + ", "); 
                System.out.println(me.getValue());
            }
            else if (test.IsValid() && num == 2)
            {
                System.out.print(me.getKey() + ", "); 
                System.out.println(me.getValue());
            }
        }
    }

    // Pattern 1 counts occurences of each unique word in a
    // String array.
    private static void Pattern1(ArrayList<String> data)
    {
        Pattern(data, 1);
    }

    // Pattern 2 counts occurences of each unique number (integer)
    // in a String array.
    private static void Pattern2(ArrayList<String> data)
    {
        Pattern(data, 2);
    }

    // Pattern 3 counts occurences of each unique phrase of three
    // consecutive words in a String array.
    private static void Pattern3(ArrayList<String> data)
    {
        HashMap<String, Integer> occurences = new HashMap<String, Integer>();

        for (String str : data)
        {
            String[] words = str.split(" ");

            // Looking for 3 word patterns
            if (words.length < 3)
                continue;

            for (int i = 0; i <= words.length - 3; ++i)
            {
                String phrase = words[i] + " " + words[i+1] + " " + words[i+2];
                Integer count = occurences.get(phrase);

                if (count == null)
                {
                    occurences.put(phrase, 1);
                }
                else
                {
                    occurences.put(phrase, count + 1);
                }
            }
        }

        Set<Map.Entry<String, Integer>> set = occurences.entrySet();

        for (Map.Entry<String, Integer> me : set)
        {
            System.out.print(me.getKey() + ", "); 
            System.out.println(me.getValue());
        }
    }

    // Usage text, printed upon a usage error
    private static void usage()
    {
        System.err.println("Usage: PatternCounter <filename> <pattern index (1-3)>");
    }

    // Main entry point. Do some sanity checking to make sure we have a valid range
    // for the pattern number. If so, try reading the filename into an array of
    // Strings. If that works, pass the array to the various pattern matching routines.
    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            usage();
            return;
        }

        String filename = args[0];
        String pattern_string = args[1];

        IntegerTest pattern_num = ToInteger(pattern_string);

        if (!pattern_num.IsValid() || pattern_num.GetValue() < 1 || pattern_num.GetValue() > 3)
        {
            usage();
            return;
        }

        ArrayList<String> data = new ArrayList<String>(10);

        try
        {
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            
            while ((strLine = br.readLine()) != null)
            {
                data.add(strLine);
            }
            
            in.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
            return;
        }

        switch (pattern_num.GetValue())
        {
        case 1:
            Pattern1(data);
            break;
        case 2:
            Pattern2(data);
            break;
        case 3:
            Pattern3(data);
            break;
//        case 4:
//            Pattern4(data);
//            break;
//            ...
        }
    }
}