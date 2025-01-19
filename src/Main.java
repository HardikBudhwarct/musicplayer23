import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;
public class Main {
    public static void write(){
        String csvFile = "src/sheet.csv";
        Scanner scanner = new Scanner(System.in);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile,true))) {
            writer.write("Column1,Column2");
            writer.newLine();
            for (int i = 0; i < 2; i++) {
                System.out.print("Enter value for Column1: ");
                String column1 = scanner.nextLine();
                System.out.print("Enter value for Column2: ");
                String column2 = scanner.nextLine();
                writer.write(column1 + "," + column2);
                writer.newLine();
            }
            System.out.println("Data successfully written to " + csvFile);

        } catch (Exception e) {
            System.err.println("An error occurred while writing to the file."+e);
        }
    }
    public static void read(){
        String file="src/sheet.csv";
        BufferedReader reader = null;
        String line = "";
        try{
            reader = new BufferedReader(new FileReader(file));
            while(( line= reader.readLine())!=null){
                String[] row=line.split(",");
                for(String index:row){
                    System.out.print(index+" ");
                }
                System.out.println();
            }
        }
        catch(Exception e) {
            System.out.println("error is there" + e);
        }
    }
    public static void main(String[] args) {
        write();
        read();
    }
}