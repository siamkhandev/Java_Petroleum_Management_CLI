package petroleum_managemnt_system;

// Manages the fuel stock levels
public class Inventory {
    // Single instance for the whole system
    private static Inventory instance;
    // Current petrol stock in liters
    private double petrolStock;
    // Current diesel stock in liters
    private double dieselStock;
    
    private Inventory() {
        petrolStock = 0;
        dieselStock = 0;
    }
    
    // Returns the single inventory instance
    public static Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory();
        }
        return instance;
    }
    
    // Reduces stock when fuel is sold
    public void updateStock(double petrolSold, double dieselSold) throws PetroleumSystemException {
        if (petrolSold > petrolStock) {
            throw new PetroleumSystemException("Insufficient petrol stock! Available: " + petrolStock + " liters");
        }
        if (dieselSold > dieselStock) {
            throw new PetroleumSystemException("Insufficient diesel stock! Available: " + dieselStock + " liters");
        }
        
        petrolStock -= petrolSold;
        dieselStock -= dieselSold;
    }
    
    // Adds new stock to inventory
    public void addStock(double petrol, double diesel) throws PetroleumSystemException {
        if (petrol < 0 || diesel < 0) {
            throw new PetroleumSystemException("Stock values cannot be negative!");
        }
        
        petrolStock += petrol;
        dieselStock += diesel;
    }
    
    // Sets starting stock levels
    public void setInitialStock(double petrol, double diesel) throws PetroleumSystemException {
        if (petrol < 0 || diesel < 0) {
            throw new PetroleumSystemException("Initial stock values cannot be negative!");
        }
        
        this.petrolStock = petrol;
        this.dieselStock = diesel;
    }
    
    // Get current petrol stock
    public double getPetrolStock() {
        return petrolStock;
    }
    
    // Get current diesel stock
    public double getDieselStock() {
        return dieselStock;
    }
    
    // Shows current stock levels
    public void checkInventory() {
        MenuManager menuManager = MenuManager.getInstance();
        menuManager.printHeader("CURRENT INVENTORY STATUS");
        System.out.println("\n┌────────────────────────────────┐");
        System.out.println("│         Stock Details          │");
        System.out.println("├────────────────────────────────┤");
        System.out.printf("│ Petrol Stock: %-16.2f │\n", petrolStock);
        System.out.printf("│ Diesel Stock: %-16.2f │\n", dieselStock);
        System.out.println("└────────────────────────────────┘");
    }
    
    // Handles adding new stock
    public void addStock() {
        MenuManager menuManager = MenuManager.getInstance();
        InputValidator inputValidator = InputValidator.getInstance();
        
        menuManager.printHeader("Add Stock");
        try {
            double petrol = inputValidator.getValidDoubleInput("Enter Petrol stock to add (liters): ");
            double diesel = inputValidator.getValidDoubleInput("Enter Diesel stock to add (liters): ");
            
            addStock(petrol, diesel);
            System.out.println("Stock added successfully!");
            checkInventory(); // Show updated inventory
        } catch (PetroleumSystemException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
} 