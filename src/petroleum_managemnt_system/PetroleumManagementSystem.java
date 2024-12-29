package petroleum_managemnt_system;

public class PetroleumManagementSystem {
    private MenuManager menuManager;
    private InputValidator inputValidator;
    private UserManager userManager;
    private RecordManager recordManager;
    private Inventory inventory;
    private boolean isAdminCreated;

    public PetroleumManagementSystem() {
        menuManager = MenuManager.getInstance();
        inputValidator = InputValidator.getInstance();
        userManager = UserManager.getInstance();
        recordManager = RecordManager.getInstance();
        inventory = Inventory.getInstance();
        isAdminCreated = false;
    }

    public void start() {
        while (true) {
            if (!isAdminCreated) {
                userManager.createFirstAdmin();
                setupInitialInventory();
                menuManager.clearScreen();
                isAdminCreated = true;
            } else if (userManager.getCurrentUser() == null) {
                handleLoginMenu();
            } else if (userManager.getCurrentUser().getRole().equals("Administrator")) {
                handleAdminMenu();
            } else {
                handleManagerMenu();
            }
        }
    }

    private void handleLoginMenu() {
        menuManager.showLoginMenu();
        int choice = inputValidator.getValidIntInput("Choose option: ", 1, 2);
        
        switch (choice) {
            case 1:
                userManager.login();
                menuManager.clearScreen();
                break;
            case 2:
                System.exit(0);
                break;
        }
    }

    private void handleAdminMenu() {
        menuManager.showAdminMenu();
        int choice = inputValidator.getValidIntInput("Choose option: ", 1, 7);
        
        switch (choice) {
            case 1:
                userManager.createManagerAccount();
                break;
            case 2:
                userManager.viewAllUsers();
                break;
            case 3:
                recordManager.viewTodayRecord();
                break;
            case 4:
                inventory.checkInventory();
                break;
            case 5:
                inventory.addStock();
                break;
            case 6:
                recordManager.viewTotalSales();
                break;
            case 7:
                userManager.setCurrentUser(null);
                menuManager.clearScreen();
                break;
        }
    }

    private void handleManagerMenu() {
        menuManager.showManagerMenu();
        int choice = inputValidator.getValidIntInput("Choose option: ", 1, 4);
        
        switch (choice) {
            case 1:
                recordManager.addNewRecord();
                break;
            case 2:
                recordManager.viewTodayRecord();
                break;
            case 3:
                inventory.checkInventory();
                break;
            case 4:
                userManager.setCurrentUser(null);
                menuManager.clearScreen();
                break;
        }
    }

    private void setupInitialInventory() {
        menuManager.printHeader("Initial Inventory Setup");
        try {
            double petrolStock = inputValidator.getValidDoubleInput("Enter initial Petrol stock (liters): ");
            double dieselStock = inputValidator.getValidDoubleInput("Enter initial Diesel stock (liters): ");
            
            inventory.setInitialStock(petrolStock, dieselStock);
            System.out.println("Initial inventory set successfully!");
        } catch (PetroleumSystemException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Please try again.");
            setupInitialInventory(); // Retry setup
        }
    }
} 