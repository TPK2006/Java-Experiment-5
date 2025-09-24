import java.io.*;
import java.util.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    int id;
    String name;
    double marks;

    Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public void display() {
        System.out.println("ID: " + id + ", Name: " + name + ", Marks: " + marks);
    }
}

class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    int id;
    String name;
    double salary;

    Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public void display() {
        System.out.println("ID: " + id + ", Name: " + name + ", Salary: " + salary);
    }
}

public class Main {
    static final String EMP_FILE = "employees.dat";

    public static void partA(Scanner sc) {
        List<Integer> numbers = new ArrayList<>();
        System.out.print("Enter number of integers: ");
        int n = sc.nextInt();
        System.out.println("Enter " + n + " integers:");
        for (int i = 0; i < n; i++) {
            numbers.add(sc.nextInt());
        }
        int sum = 0;
        for (Integer val : numbers) {
            sum += val;
        }
        System.out.println("Sum of Integers: " + sum);
    }

    public static void partB() {
        String filename = "student.ser";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            Student s1 = new Student(102, "Prasanth Kumar", 90.2);
            out.writeObject(s1);
            System.out.println("Student object serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            Student s2 = (Student) in.readObject();
            System.out.println("Student object deserialized successfully.");
            s2.display();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static class AppendableObjectOutputStream extends ObjectOutputStream {
        AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            reset();
        }
    }

    public static void addEmployee(Employee emp) {
        boolean append = new File(EMP_FILE).exists();
        try (ObjectOutputStream out = append
                ? new AppendableObjectOutputStream(new FileOutputStream(EMP_FILE, true))
                : new ObjectOutputStream(new FileOutputStream(EMP_FILE))) {
            out.writeObject(emp);
            System.out.println("Employee added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void viewEmployees() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(EMP_FILE))) {
            while (true) {
                Employee emp = (Employee) in.readObject();
                emp.display();
            }
        } catch (EOFException e) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void partC(Scanner sc) {
        int choice;
        do {
            System.out.println("\n--- Employee Management Menu ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter Employee ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Employee Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Employee Salary: ");
                    double salary = sc.nextDouble();
                    addEmployee(new Employee(id, name, salary));
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 3);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Part A - Sum of Integers");
            System.out.println("2. Part B - Serialization/Deserialization");
            System.out.println("3. Part C - Employee Management System");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    partA(sc);
                    break;
                case 2:
                    partB();
                    break;
                case 3:
                    partC(sc);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 4);
        sc.close();
    }
}