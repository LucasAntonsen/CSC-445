import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class read {

        // Read a string from the standard input using `BufferedReader`
        public static void main(String[] args)
        {
            BufferedReader br;
            File input = new File("input.txt");

            int flag = 1;

            try {
                FileWriter writer = new FileWriter(input);

                br = new BufferedReader(new InputStreamReader(System.in));
                String str = br.readLine();

                while (str != null) {
                    if(flag == 1){
                        writer.write(str);
                        flag = 0;
                    }else{
                        writer.write("\n" + str);
                    }
                    str = br.readLine();
                }
                writer.close();
            }
            catch (IOException e) {
                System.out.println(e);
            }
        }

}
