import java.util.*;

class User {
    private String username;
    private String password;
    private List<String> messages;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.messages = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getMessages() {
        return messages;
    }
}


class ChatSystem {
    private HashMap<String, User> users;

    public ChatSystem() {
        users = new HashMap<>();
    }

    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists! Please try a different username.");
            return false;
        }
        users.put(username, new User(username, password));
        System.out.println("User registered successfully!");
        return true;
    }

    public User loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful! Welcome, " + username + "!");
            return user;
        }
        System.out.println("Invalid username or password.");
        return null;
    }

    public void sendMessage(User sender, String recipientUsername, String message) {
        User recipient = users.get(recipientUsername);
        if (recipient == null) {
            System.out.println("Recipient not found!");
        } else {
            String fullMessage = "From " + sender.getUsername() + ": " + message;
            recipient.getMessages().add(fullMessage); // Ensure this is executed only once
            System.out.println("Message sent to " + recipientUsername);
        }
    }

    public void viewMessages(User user) {
        System.out.println("\n--- Message History ---");
        for (String message : user.getMessages()) {
            System.out.println(message);
        }
        if (user.getMessages().isEmpty()) {
            System.out.println("No messages yet!");
        }
    }
}

public class ChatClient {
    public static void main(String[] args) {
        ChatSystem chatSystem = new ChatSystem();
        Scanner scanner = new Scanner(System.in);

        User currentUser = null;

        while (true) {
            System.out.println("\n--- Chat World ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Send Message");
            System.out.println("4. View Messages");
            System.out.println("5. Logout");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    chatSystem.registerUser(username, password);
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();

                    currentUser = chatSystem.loginUser(loginUsername, loginPassword);
                    break;

                case 3:
                    if (currentUser == null) {
                        System.out.println("You need to login first!");
                        break;
                    }
                    System.out.print("Enter recipient username: ");
                    String recipient = scanner.nextLine();

                    System.out.print("Enter your message: ");
                    String message = scanner.nextLine();

                    chatSystem.sendMessage(currentUser, recipient, message);
                    break;

                case 4:
                    if (currentUser == null) {
                        System.out.println("You need to login first!");
                        break;
                    }
                    System.out.println("Viewing your messages...");
                    chatSystem.viewMessages(currentUser);
                    break;

                case 5:
                    if (currentUser == null) {
                        System.out.println("You're not logged in!");
                    } else {
                        System.out.println("Logging out " + currentUser.getUsername() + "...");
                        currentUser = null; // Log out the current user
                    }
                    break;

                case 0:
                    System.out.println("Exiting Chat World. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
