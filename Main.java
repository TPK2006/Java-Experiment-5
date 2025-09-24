import java.io.*;
import java.util.*;

class Student implements Serializable {
    int id; String name;
    Student(int id,String name){this.id=id;this.name=name;}
    public String toString(){return "ID:"+id+" Name:"+name;}
}

public class Main {
    static String empFile="employees.txt";

    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int choice;
        do{
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Autoboxing Sum");
            System.out.println("2. Student Serialization");
            System.out.println("3. Employee Management");
            System.out.println("4. Exit");
            choice=sc.nextInt();

            switch(choice){
                case 1:
                    Integer a=10,b=20; System.out.println("Sum = "+(a+b));
                    break;

                case 2:
                    try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("student.ser"))){
                        oos.writeObject(new Student(101,"Rahul"));
                        System.out.println("Student Serialized!");
                    }catch(Exception e){e.printStackTrace();}
                    try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream("student.ser"))){
                        System.out.println("Deserialized: "+(Student)ois.readObject());
                    }catch(Exception e){e.printStackTrace();}
                    break;

                case 3:
                    int ch;
                    do{
                        System.out.println("\n1.Add  2.View  3.Search  4.Delete  5.Back");
                        ch=sc.nextInt();
                        if(ch==1){
                            System.out.print("ID Name Salary: ");
                            try(PrintWriter pw=new PrintWriter(new FileWriter(empFile,true))){
                                pw.println(sc.nextInt()+","+sc.next()+","+sc.nextDouble());
                            }catch(Exception e){}
                        }
                        else if(ch==2){
                            try(BufferedReader br=new BufferedReader(new FileReader(empFile))){
                                br.lines().forEach(System.out::println);
                            }catch(Exception e){System.out.println("No Records!");}
                        }
                        else if(ch==3){
                            System.out.print("Enter ID: "); int id=sc.nextInt(); boolean f=false;
                            try(BufferedReader br=new BufferedReader(new FileReader(empFile))){
                                String l; while((l=br.readLine())!=null){if(l.startsWith(id+",")){System.out.println("Found: "+l);f=true;break;}}
                            }catch(Exception e){}
                            if(!f)System.out.println("Not Found!");
                        }
                        else if(ch==4){
                            System.out.print("Enter ID: "); int id=sc.nextInt();
                            try(BufferedReader br=new BufferedReader(new FileReader(empFile));
                                PrintWriter pw=new PrintWriter(new FileWriter("temp.txt"))){
                                String l; boolean d=false;
                                while((l=br.readLine())!=null){
                                    if(l.startsWith(id+",")) d=true; else pw.println(l);
                                }
                                if(d)System.out.println("Deleted!"); else System.out.println("Not Found!");
                            }catch(Exception e){}
                            new File(empFile).delete(); new File("temp.txt").renameTo(new File(empFile));
                        }
                    }while(ch!=5);
                    break;

                case 4: System.out.println("Exit..."); break;
            }
        }while(choice!=4);
        sc.close();
    }
}





