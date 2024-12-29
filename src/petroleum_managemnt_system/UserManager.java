package petroleum_managemnt_system;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static UserManager instance;
    private List<User> users;
    private User currentUser;
    private static final int MAX_MANAGERS = 5;
    private MenuManager menuManager;
    private InputValidator inputValidator;
    
    private UserManager() {
        users = new ArrayList<>();
        menuManager = MenuManager.getInstance();
        inputValidator = InputValidator.getInstance();
    }
    
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void createFirstAdmin() {
        menuManager.printHeader("Create Administrator Account");
        System.out.println("This is the first time setup. Create admin account.");
        
        while (true) {
            try {
                String username = inputValidator.getValidString("Enter Admin Username: ");
                if (username.isEmpty()) {
                    throw new PetroleumSystemException("Username cannot be empty!");
                }
                
                String password = inputValidator.getValidString("Enter Admin Password: ");
                if (password.isEmpty()) {
                    throw new PetroleumSystemException("Password cannot be empty!");
                }
                
                users.add(new User(username, password, "Administrator"));
                System.out.println("Administrator account created successfully!");
                break;
            } catch (PetroleumSystemException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void createManagerAccount() {
        try {
            int managerCount = (int) users.stream()
                .filter(u -> u.getRole().equals("Manager"))
                .count();

            if (managerCount >= MAX_MANAGERS) {
                throw new PetroleumSystemException("Maximum number of managers (" + MAX_MANAGERS + ") reached!");
            }

            menuManager.printHeader("Create Manager Account");
            
            String username = inputValidator.getValidString("Enter Manager Username: ");
            if (username.isEmpty()) {
                throw new PetroleumSystemException("Username cannot be empty!");
            }
            
            if (users.stream().anyMatch(u -> u.getUsername().equals(username))) {
                throw new PetroleumSystemException("Username already exists!");
            }

            String password = inputValidator.getValidString("Enter Manager Password: ");
            if (password.isEmpty()) {
                throw new PetroleumSystemException("Password cannot be empty!");
            }
            
            users.add(new User(username, password, "Manager"));
            System.out.println("Manager account created successfully!");
            
        } catch (PetroleumSystemException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void login() {
        try {
            String username = inputValidator.getValidString("Username: ");
            if (username.isEmpty()) {
                throw new PetroleumSystemException("Username cannot be empty!");
            }

            String password = inputValidator.getValidString("Password: ");
            if (password.isEmpty()) {
                throw new PetroleumSystemException("Password cannot be empty!");
            }

            User user = users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new PetroleumSystemException("Invalid credentials!"));

            currentUser = user;
            System.out.println("Login successful!");
            
        } catch (PetroleumSystemException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewAllUsers() {
        menuManager.printHeader("USER LIST");
        User currentUser = getCurrentUser();
        
        System.out.println("\n┌──────────────────────────────────────────────────┐");
        if (currentUser != null && currentUser.getRole().equals("Administrator")) {
            // Show detailed view for admin including passwords
            System.out.println("│ Username          Role             Password    │");
            System.out.println("├──────────────────────────────────────────────────┤");
            for (User user : users) {
                if (user.getRole().equals("Manager")) {
                    // Only show manager credentials
                    System.out.printf("│ %-16s %-16s %-11s │\n", 
                        user.getUsername(), 
                        user.getRole(), 
                        user.getPassword());
                }
            }
        } else {
            // Show limited view for non-admin users
            System.out.println("│ Username          Role                          │");
            System.out.println("├──────────────────────────────────────────────────┤");
            for (User user : users) {
                System.out.printf("│ %-16s %-16s                │\n", 
                    user.getUsername(), 
                    user.getRole());
            }
        }
        System.out.println("└──────────────────────────────────────────────────┘");
    }

    // Getters and setters
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public List<User> getUsers() {
        return users;
    }
} 