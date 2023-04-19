public class App {
    public static void main(String[] args) {
        // Create a new instance of UserMap
        UserMap userMap = new UserMap();

        // Create a new Patient object and add it to userMap
        Patient patient1 = new Patient("username2", "passs", "John Doe", 30, "Male");
        Patient patient2 = new Patient("username2", "passs", "Jack Doe", 30, "Male");
        userMap.addUser(patient2, patient2.username);
        String[] res = userMap.addUser(patient1, patient1.username);
        System.out.println(res[1]);
    }
}
