import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class FinalProjectTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testExit();
        testAddStudent();
        testAddCourseAndRegister();
        testRegisterNonexistentStudent();
        testDropStudent();
        testCourseRejected();

        System.out.println();
        System.out.println();
        System.out.println("Results: " + passed + " passed, " + failed + " failed");
        clearData();
    }

    private static String runSession(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        RegistrationSystem rs = new RegistrationSystem(in, out);
        rs.run();
        return out.toString();
    }

    private static RegistrationSystem runSessionAndGet(String input, ByteArrayOutputStream out) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        RegistrationSystem rs = new RegistrationSystem(in, out);
        rs.run();
        return rs;
    }

    private static void check(String testName, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("  PASS: " + testName);
        } else {
            failed++;
            System.out.println("  FAIL: " + testName);
        }
    }

    private static void check(String testName, boolean condition, String details) {
        if (condition) {
            passed++;
            System.out.println("  PASS: " + testName);
        } else {
            failed++;
            System.out.println("  FAIL: " + testName);
            System.out.println("    " + details);
        }
    }


    private static void testExit() {
        clearData();
        System.out.println("\n[testExitImmediately]");
        String output = runSession("7\n");
        check("menu was printed", output.contains("Add Student"));
        check("save and exit option shown", output.contains("Save and Exit"));
    }


    private static void testAddStudent() {
        clearData();
        System.out.println("\n[testAddStudent]");
        String input =
                "1\n"
                        + "Brendan\n"
                        + "S001\n"
                        + "7\n";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        RegistrationSystem rs = runSessionAndGet(input, out);

        try {
            StudentIFace student = rs.findStudent("S001");
            check("student found by id", student != null);
            check("student name is correct", student != null && student.getName().equals("Brendan"),
                    "got: " + (student == null ? "null" : student.getName()));
        } catch (Exception e) {
            check("student found by id", false, "exception: " + e.getMessage());
        }
    }

    private static void testAddCourseAndRegister() {
        clearData();
        System.out.println("\n[testAddCourseAndRegister]");
        String input =
                "1\nBrendan\nS002\n"
                        + "2\nIntro CS\nCSC110\n30\n"
                        + "3\nS002\nCSC110\n"
                        + "7\n";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        RegistrationSystem rs = runSessionAndGet(input, out);

        try {
            StudentIFace teststudent = rs.findStudent("S002");
            Course testcourse = rs.findCourse("CSC110");

            check("student exists", teststudent != null);
            check("course exists", testcourse != null);
            check("course is in student's enrolled list",
                    teststudent.getEnrolledCourses().contains("CSC110"));
            check("student is in course's roster",
                    testcourse.getEnrolledStudents().contains(teststudent));
            check("success message printed",
                    out.toString().contains("registered successfully"));
        } catch (Exception e) {
            check("registration completed", false, "exception: " + e.getMessage());
        }
    }

    private static void testRegisterNonexistentStudent() {
        clearData();
        System.out.println("\n[testRegisterNonexistentStudent]");
        String input =
                "2\nIntro CS\nCSC110\n30\n"   // add course but no student
                        + "3\nS999\nCSC110\n"           // try to register nonexistent student
                        + "7\n";

        String output = runSession(input);
        check("error message shown for missing student",
                output.contains("Could not find student"));
        check("did not print success message",
                !output.contains("registered successfully"));
    }


    private static void testDropStudent() {
        clearData();
        System.out.println("\n[testDropStudent]");
        String input =
                "1\nBrendan\nS003\n"
                        + "2\nCOMP\nCOMP132\n30\n"
                        + "3\nS003\nCOMP132\n"
                        + "4\nS003\nCOMP132\n"
                        + "7\n";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        RegistrationSystem rs = runSessionAndGet(input, out);

        try {
            StudentIFace testStudent = rs.findStudent("S003");
            Course testCourse = rs.findCourse("COMP132");
            check("course removed from student's list",
                    !testStudent.getEnrolledCourses().contains("COMP132"));
            check("student removed from course roster",
                    !testCourse.getEnrolledStudents().contains(testStudent));
        } catch (Exception e) {
            check("drop completed", false, "exception: " + e.getMessage());
        }
    }


    private static void testCourseRejected() {
        clearData();
        System.out.println("\n[testFullCourseRejected]");
        String input =
                "1\nA\nS01\n"
                        + "1\nB\nS02\n"
                        + "1\nC\nS03\n"
                        + "2\nTiny\nTINY100\n2\n"
                        + "3\nS01\nTINY100\n"
                        + "3\nS02\nTINY100\n"
                        + "3\nS03\nTINY100\n"
                        + "7\n";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        RegistrationSystem rs = runSessionAndGet(input, out);

        try {
            Course testCourse = rs.findCourse("TINY100");
            check("course at capacity", testCourse.getEnrolledStudents().size() == 2,
                    "size = " + testCourse.getEnrolledStudents().size());
        } catch (Exception e) {
            check("capacity test completed", false, "exception: " + e.getMessage());
        }
    }

    private static void clearData() {
        try {
            new java.io.PrintWriter("data/students.csv").close();
            new java.io.PrintWriter("data/courses.csv").close();
        } catch (Exception e) { }
    }
}
