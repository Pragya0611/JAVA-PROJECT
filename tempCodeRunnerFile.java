import java.util.*;

enum StudentStatus {
    Active("Currently Enrolled"),
    Graduated("Completed"),
    Suspended("Temporarily Suspended");

    private final String description;

    StudentStatus(String description) {
        this.description = description;
    }

    String getDescription() {
        return description;
    }
}

enum GradeLevel {
    Freshman(1), Sophomore(2), Pre_Final(3), Final(4);

    private final int year;

    GradeLevel(int year) {
        this.year = year;
    }

    int getYear() {
        return year;
    }
}

class Student {
    private String name;
    private int id;
    private StudentStatus status;
    private GradeLevel level;
    private double gpa;

    public Student(String name, int id, GradeLevel level) {
        this.name = name;
        this.id = id;
        this.status = StudentStatus.Active;
        this.level = level;
        this.gpa = 0.0;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public GradeLevel getLevel() {
        return level;
    }

    public double getGpa() {
        return gpa;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public void setLevel(GradeLevel level) {
        this.level = level;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public void displayInfo() {
        System.out.println("Student Information");
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Level: " + level + "->" + "Year: " + level.getYear());
        System.out.println("Status: " + status + "->" + status.getDescription());
        System.out.println("GPA: " + gpa);
    }
}

public class StudentManagementSystem {

    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int nextId = 1001;

    public static void main(String[] args) {
        System.out.println("Welcome To Student Management System!!");

        addSampleData();

        // Main Menu
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudent();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateGpa();
                    break;
                case 5:
                    promoteStudent();
                    break;
                case 6:
                    deleteStudent();
                    break;
                case 7:
                    System.out.println("Thanks for using Student Management System!");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("MENU");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Student");
        System.out.println("3. Search Student");
        System.out.println("4. Update GPA");
        System.out.println("5. Promote Student Level");
        System.out.println("6. Delete Student");
        System.out.println("7. Exit");
        System.out.print("Enter choice: ");
    }

    private static void addStudent() {
        System.out.println("Enter Student Name: ");
        String name = scanner.nextLine();

        System.out.println("Select Grade Level:");
        System.out.println("1. Freshman  2. Sophomore  3. Pre-Final  4. Final");
        int levelChoice = scanner.nextInt();

        if (levelChoice < 1 || levelChoice > 4) {
            System.out.println("Invalid level choice!");
            return;
        }
        GradeLevel level = GradeLevel.values()[levelChoice - 1];

        // Check for duplicate entries
        if (isStudentExists(name, level)) {
            System.out.println("Warning: " + name + " already exists in " + level + "level!");
            System.out.println("1. Add Anyway(different person)");
            System.out.println("2. Cancel");
            System.out.println("Enter Choice: ");
            int choice = scanner.nextInt();

            if (choice != 1) {
                System.out.println("Student addition cancelled");
                return;
            }
        }

        Student student = new Student(name, nextId++, level);
        students.add(student);

        System.out.println("Student added successfully! ID: " + student.getId());
    }

    // Check if student exists by name And level
    private static boolean isStudentExists(String name, GradeLevel level) {
        System.out.println("DEBUG: Checking for duplicate - Name: '" + name + "', Level: " + level);

        for (Student student : students) {
            System.out
                    .println("DEBUG: Comparing with - Name: '" + student.getName() + "', Level: " + student.getLevel());

            if (student.getName().equalsIgnoreCase(name) && student.getLevel() == level) {
                System.out.println("DEBUG: DUPLICATE FOUND!");
                return true;
            }
        }
        System.out.println("DEBUG: No duplicate found");
        return false;
    }

    private static void viewAllStudent() {
        if (students.isEmpty()) {
            System.out.println("No Student found!");
            return;
        }
        System.out.println("ALL STUDENTS");
        for (Student student : students) {
            System.out.println(student.getId() + ". " + student.getName() + " ( " + student.getLevel() + " ) - GPA"
                    + student.getGpa());
        }
    }

    private static void searchStudent() {
        System.out.println("Enter student ID: ");
        int id = scanner.nextInt();

        Student student = findStudentById(id);
        if (student != null) {
            student.displayInfo();
        } else {
            System.out.println("Student not found");
        }
    }

    private static void updateGpa() {
        System.out.println("Enter Student ID: ");
        int id = scanner.nextInt();

        Student student = findStudentById(id);
        if (student != null) {
            System.out.println("Enter new GPA (0.0 - 10.0): ");
            double gpa = scanner.nextDouble();
            student.setGpa(gpa);
            System.out.println("GPA updated successfully!!");
        } else {
            System.out.println("Student not found!");
        }
    }

    private static void promoteStudent() {
        System.out.println("Enter student ID: ");
        int id = scanner.nextInt();

        Student student = findStudentById(id);
        if (student != null) {
            GradeLevel currentLevel = student.getLevel();

            switch (currentLevel) {
                case Freshman:
                    student.setLevel(GradeLevel.Sophomore);
                    System.out.println("Student promoted to  Sophomore!");
                    break;
                case Sophomore:
                    student.setLevel(GradeLevel.Pre_Final);
                    System.out.println("Student promoted to  Pre-Final year!");
                    break;
                case Pre_Final:
                    student.setLevel(GradeLevel.Final);
                    System.out.println("Student promoted to  Final Year!");
                    break;
                case Final:
                    student.setStatus(StudentStatus.Graduated);
                    System.out.println("Student has graduated! Status changed to Graduated");
                    break;
                default:
                    System.out.println("Error: Unknown grade level: " + currentLevel);
                    System.out.println("Cannot promote student. Please contact administrator.");
                    break;
            }
        } else {
            System.out.println("Student not found!");
        }
    }

    private static Student deleteStudent() {
        System.out.println("Enter the Student ID to delete: ");
        int id = scanner.nextInt();

        Student student = findStudentById(id);
        if (student != null) {
            students.remove(student);
            System.out.println("Student " + student.getName() + " (ID: " + id + ") deleted successfully!");
        } else {
            System.out.println("Student not found!!");
        }
        return student;
    }

    private static Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    private static void addSampleData() {
        students.add(new Student("Aman Gupta", nextId++, GradeLevel.Freshman));
        students.add(new Student("Pragya Lachhwani", nextId++, GradeLevel.Sophomore));
        students.add(new Student("Ayushi Saxena", nextId++, GradeLevel.Pre_Final));

        students.get(0).setGpa(8.5);
        students.get(1).setGpa(7.9);
        students.get(2).setGpa(6.0);

        System.out.println("Current students in the system: " + students.size());
    }
}