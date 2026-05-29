import java.util.ArrayList;
import java.util.Scanner;
public class StudentGrades {
    static class Student {
        String name;
        int grade;
        Student(String name, int grade) {
            this.name = name;
            this.grade = grade;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            System.out.print("Enter grade: ");
            int grade = sc.nextInt();
            sc.nextLine();
            students.add(new Student(name, grade));
        }
        int total = 0;
        int high = students.get(0).grade;
        int low = students.get(0).grade;
        System.out.println("\n--- Student Report ---");
        for (Student s : students) {
            System.out.println(s.name + " : " + s.grade);
            total += s.grade;
            if (s.grade > high)
                high = s.grade;
            if (s.grade < low)
                low = s.grade;
        }
        double average = (double) total / n;
        System.out.println("\nAverage = " + average);
        System.out.println("Highest = " + high);
        System.out.println("Lowest = " + low);
        sc.close();
    }
}