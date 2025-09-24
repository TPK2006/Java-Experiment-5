import java.io.*;
import java.util.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    int id;
    String name;
    double marks;
    Student(int id, String name, double marks) {
        this.id = id; this.name = name; this.marks = marks;
    }
    public void display() {
        System.out.println("ID: " + id + " | Name: " + name + " | Marks: " + marks);
    }
}

public class Main {
    static Scanner sc = new Scanner(System.in);

    // Part 1: Autoboxing & Unboxing Sum
    static void autoboxingSum() {
        System.out.print("Enter number of integers: ");
        int n = sc.nextInt();
        Integer[] nums = new Integer[n];
        int sum = 0;
        System.out.println("Enter " + n + " integers:");
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
            sum += nums[i];
        }
        System.out.println("Sum = " + sum);
    }

    // Part 2: Serialization & Deserialization
    static void serializationDemo() {
        try {
            System.out.print("Enter Student ID: ");
            int id = sc.nextInt(); sc.nextLine();
            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Marks: ");
            double marks = sc.nextDouble();

            Student s = new Student(id, name, marks);
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("student.dat"));
            out.writeObject(s);
            out.close();
            System.out.println("Data Serialized Successfully!");

            ObjectInputStream in = new ObjectInputStream(new FileInputStream("student.dat"));
            Student read = (Student) in.readObject();
            in.close();
            System.out.println("Deserialized Data:");
            read.display();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Part 3: File Handling & Efficient Processing
    static void fileHandlingDemo() {
        try {
            sc.nextLine();
            System.out.print("Enter text to save in file: ");
            String data = sc.nextLine();
            FileWriter fw = new FileWriter("data.txt");
            fw.write(data);
            fw.close();
            System.out.println("Data Written to data.txt");

            BufferedReader br = new BufferedReader(new FileReader("data.txt"));
            String line;
            System.out.println("Reading File Content:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Sum using Autoboxing & Unboxing");
            System.out.println("2. Serialization & Deserialization");
            System.out.println("3. File Handling & Data Processing");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1: autoboxingSum(); break;
                case 2: serializationDemo(); break;
                case 3: fileHandlingDemo(); break;
                case 4: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }
}
