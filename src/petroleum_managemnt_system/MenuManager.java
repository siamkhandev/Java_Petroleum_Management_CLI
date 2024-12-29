package petroleum_managemnt_system;

public class MenuManager {
    private static MenuManager instance;
    
    private MenuManager() {}
    
    public static MenuManager getInstance() {
        if (instance == null) {
            instance = new MenuManager();
        }
        return instance;
    }

    public void printHeader(String title) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.printf("║ %-54s ║\n", title);
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

    public void showLoginMenu() {
        printHeader("PETROLEUM MANAGEMENT SYSTEM");
        System.out.println("\n┌──────────────────────┐");
        System.out.println("│      Login Menu      │");
        System.out.println("├──────────────────────┤");
        System.out.println("│  1. Login            │");
        System.out.println("│  2. Exit             │");
        System.out.println("└──────────────────────┘");
    }

    public void showAdminMenu() {
        clearScreen();
        showAdminDashboard();
    }

    public void showManagerMenu() {
        printHeader("PETROLEUM MANAGEMENT SYSTEM");
        System.out.println("\n┌────────────────────────┐");
        System.out.println("│      Manager Menu      │");
        System.out.println("├────────────────────────┤");
        System.out.println("│  1. New Reading Entry  │");
        System.out.println("│  2. View Today's Record│");
        System.out.println("│  3. Check Inventory    │");
        System.out.println("│  4. Logout             │");
        System.out.println("└────────────────────────┘");
    }

    public void clearScreen() {
        System.out.println("\nPress Enter to continue...");
        try {
            System.in.read();
            // Clear input buffer
            while (System.in.available() > 0) {
                System.in.read();
            }
            // Clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (Exception e) {
            // If there's an error reading input, just clear the screen
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    public void showAdminDashboard() {
        RecordManager recordManager = RecordManager.getInstance();
        Inventory inventory = Inventory.getInstance();
        
        // Calculate totals from RecordManager
        double totalPetrolLiters = 0;
        double totalDieselLiters = 0;
        double totalPetrolAmount = 0;
        double totalDieselAmount = 0;
        
        for (DailyRecord record : recordManager.getRecords()) {
            totalPetrolLiters += record.getTotalPetrolLiters();
            totalDieselLiters += record.getTotalDieselLiters();
            totalPetrolAmount += record.getTotalPetrolPrice();
            totalDieselAmount += record.getTotalDieselPrice();
        }
        
        double totalLiters = totalPetrolLiters + totalDieselLiters;
        double totalAmount = totalPetrolAmount + totalDieselAmount;
        
        // Display dashboard
        printHeader("PETROLEUM MANAGEMENT SYSTEM");
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                     ADMIN DASHBOARD                             ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Current Inventory:                                             ║");
        System.out.printf("║   Petrol Stock: %-8.2f liters                                ║\n", 
            inventory.getPetrolStock());
        System.out.printf("║   Diesel Stock: %-8.2f liters                                ║\n", 
            inventory.getDieselStock());
        System.out.println("╟────────────────────────────────────────────────────────────────╢");
        System.out.println("║ Total Sales:                                                   ║");
        System.out.printf("║   Petrol: %-8.2f L (PKR %-8.2f)                            ║\n", 
            totalPetrolLiters, totalPetrolAmount);
        System.out.printf("║   Diesel: %-8.2f L (PKR %-8.2f)                            ║\n", 
            totalDieselLiters, totalDieselAmount);
        System.out.println("╟────────────────────────────────────────────────────────────────╢");
        System.out.printf("║   Total: %-8.2f L (PKR %-8.2f)                             ║\n", 
            totalLiters, totalAmount);
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        System.out.println("\nAdmin Menu:");
        System.out.println("1. Create Manager Account");
        System.out.println("2. View All Users");
        System.out.println("3. View Daily Records");
        System.out.println("4. Check Inventory");
        System.out.println("5. Add Stock");
        System.out.println("6. View Total Sales");
        System.out.println("7. Logout");
    }
} 