package petroleum_managemnt_system;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

public class RecordManager {
    private static RecordManager instance;
    private List<DailyRecord> records;
    private MenuManager menuManager;
    private InputValidator inputValidator;
    private Inventory inventory;
    private Map<String, Double> lastPetrolReadings;
    private Map<String, Double> lastDieselReadings;
    
    private RecordManager() {
        records = new ArrayList<>();
        menuManager = MenuManager.getInstance();
        inputValidator = InputValidator.getInstance();
        inventory = Inventory.getInstance();
        lastPetrolReadings = new HashMap<>();
        lastDieselReadings = new HashMap<>();
        for (int i = 1; i <= 4; i++) {
            lastPetrolReadings.put("P" + i, 0.0);
            lastDieselReadings.put("D" + i, 0.0);
        }
    }
    
    public static RecordManager getInstance() {
        if (instance == null) {
            instance = new RecordManager();
        }
        return instance;
    }

    public void addNewRecord() {
        menuManager.printHeader("New Reading Entry");
        
        try {
            // Get oil man name and time
            String oilMan = inputValidator.getValidString("Enter Oil Man Name: ");
            String time = inputValidator.getValidString("Enter Time (e.g., Morning/Evening): ");
            
            // Create new daily record
            DailyRecord record = new DailyRecord(oilMan, time);
            
            // Set fuel prices
            double petrolPrice = inputValidator.getValidDoubleInput("Enter Petrol Price per liter: ");
            double dieselPrice = inputValidator.getValidDoubleInput("Enter Diesel Price per liter: ");
            record.setPetrolPrice(petrolPrice);
            record.setDieselPrice(dieselPrice);
            
            // Get readings for petrol nozzles
            System.out.println("\nPetrol Nozzle Readings:");
            for (NozzleReading nozzle : record.getPetrolNozzles()) {
                System.out.println("\nNozzle " + nozzle.getNozzleId() + ":");
                double initial;
                if (records.isEmpty() || lastPetrolReadings.get(nozzle.getNozzleId()) == 0.0) {
                    initial = inputValidator.getValidDoubleInput("Enter Initial Reading: ");
                } else {
                    initial = lastPetrolReadings.get(nozzle.getNozzleId());
                    System.out.println("Initial Reading (from last entry): " + initial);
                }
                double final_ = inputValidator.getValidDoubleInput("Enter Final Reading: ");
                
                if (final_ < initial) {
                    throw new PetroleumSystemException("Final reading cannot be less than initial reading!");
                }
                
                nozzle.setInitialReading(initial);
                nozzle.setFinalReading(final_);
                lastPetrolReadings.put(nozzle.getNozzleId(), final_);
            }
            
            // Get readings for diesel nozzles
            System.out.println("\nDiesel Nozzle Readings:");
            for (NozzleReading nozzle : record.getDieselNozzles()) {
                System.out.println("\nNozzle " + nozzle.getNozzleId() + ":");
                double initial;
                if (records.isEmpty() || lastDieselReadings.get(nozzle.getNozzleId()) == 0.0) {
                    initial = inputValidator.getValidDoubleInput("Enter Initial Reading: ");
                } else {
                    initial = lastDieselReadings.get(nozzle.getNozzleId());
                    System.out.println("Initial Reading (from last entry): " + initial);
                }
                double final_ = inputValidator.getValidDoubleInput("Enter Final Reading: ");
                
                if (final_ < initial) {
                    throw new PetroleumSystemException("Final reading cannot be less than initial reading!");
                }
                
                nozzle.setInitialReading(initial);
                nozzle.setFinalReading(final_);
                lastDieselReadings.put(nozzle.getNozzleId(), final_);
            }
            
            // Calculate totals
            record.calculateTotals();
            
            // Check if there's enough stock before updating
            if (record.getTotalPetrolLiters() > inventory.getPetrolStock()) {
                throw new PetroleumSystemException("Insufficient petrol stock! Available: " + 
                    inventory.getPetrolStock() + " liters");
            }
            if (record.getTotalDieselLiters() > inventory.getDieselStock()) {
                throw new PetroleumSystemException("Insufficient diesel stock! Available: " + 
                    inventory.getDieselStock() + " liters");
            }
            
            // Update inventory
            inventory.updateStock(record.getTotalPetrolLiters(), record.getTotalDieselLiters());
            
            // Add record to list
            records.add(record);
            
            System.out.println("\nRecord added successfully!");
            displayRecord(record);
            
        } catch (PetroleumSystemException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewTodayRecord() {
        menuManager.printHeader("Today's Records");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(new Date());
        
        boolean foundRecords = false;
        
        System.out.println("\nDate: " + today);
        
        for (DailyRecord record : records) {
            if (dateFormat.format(record.getDate()).equals(today)) {
                displayRecord(record);
                foundRecords = true;
            }
        }
        
        if (!foundRecords) {
            System.out.println("\nNo records found for today.");
        }
    }
    
    private void displayRecord(DailyRecord record) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│              Reading Details               │");
        System.out.println("├────────────────────────────────────────────┤");
        System.out.printf("│ Date: %-37s│\n", dateFormat.format(record.getDate()));
        System.out.printf("│ Time: %-37s│\n", record.getTime());
        System.out.printf("│ Oil Man: %-35s│\n", record.getOilMan());
        System.out.println("├────────────────────────────────────────────┤");
        System.out.println("│ Petrol Nozzle Readings:                    │");
        
        for (NozzleReading nozzle : record.getPetrolNozzles()) {
            System.out.printf("│ %s - Initial: %-8.2f Final: %-8.2f Liters: %-6.2f│\n",
                    nozzle.getNozzleId(), nozzle.getInitialReading(),
                    nozzle.getFinalReading(), nozzle.getLiters());
        }
        
        System.out.println("├────────────────────────────────────────────┤");
        System.out.println("│ Diesel Nozzle Readings:                    │");
        
        for (NozzleReading nozzle : record.getDieselNozzles()) {
            System.out.printf("│ %s - Initial: %-8.2f Final: %-8.2f Liters: %-6.2f│\n",
                    nozzle.getNozzleId(), nozzle.getInitialReading(),
                    nozzle.getFinalReading(), nozzle.getLiters());
        }
        
        System.out.println("├─────────────────────────────────────────────┤");
        System.out.println("│ Summary:                                    │");
        System.out.printf("│ Total Petrol: %-8.2f liters             │\n", record.getTotalPetrolLiters());
        System.out.printf("│ Total Diesel: %-8.2f liters             │\n", record.getTotalDieselLiters());
        System.out.printf("│ Petrol Price: PKR %-8.2f per liter      │\n", record.getPetrolPrice());
        System.out.printf("│ Diesel Price: PKR %-8.2f per liter      │\n", record.getDieselPrice());
        System.out.printf("│ Total Petrol Amount: PKR %-8.2f         │\n", record.getTotalPetrolPrice());
        System.out.printf("│ Total Diesel Amount: PKR %-8.2f         │\n", record.getTotalDieselPrice());
        System.out.printf("│ Total Amount: PKR %-8.2f                │\n", record.getTotalPrice());
        System.out.println("├────────────────────────────────────────────┤");
        System.out.println("│ Current Inventory Status:                  │");
        System.out.printf("│ Remaining Petrol: %-8.2f liters          │\n", inventory.getPetrolStock());
        System.out.printf("│ Remaining Diesel: %-8.2f liters          │\n", inventory.getDieselStock());
        System.out.println("└────────────────────────────────────────────┘");
    }

    public List<DailyRecord> getRecords() {
        return records;
    }

    public void viewTotalSales() {
        menuManager.printHeader("TOTAL SALES SUMMARY");
        
        double totalPetrolLiters = 0;
        double totalDieselLiters = 0;
        double totalPetrolAmount = 0;
        double totalDieselAmount = 0;
        
        for (DailyRecord record : records) {
            totalPetrolLiters += record.getTotalPetrolLiters();
            totalDieselLiters += record.getTotalDieselLiters();
            totalPetrolAmount += record.getTotalPetrolPrice();
            totalDieselAmount += record.getTotalDieselPrice();
        }
        
        double totalLiters = totalPetrolLiters + totalDieselLiters;
        double totalAmount = totalPetrolAmount + totalDieselAmount;
        
        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│              Sales Summary                 │");
        System.out.println("├─────────────────────────────────���──────────┤");
        System.out.println("│ Petrol Sales:                             │");
        System.out.printf("│   Total Liters: %-8.2f                   │\n", totalPetrolLiters);
        System.out.printf("│   Total Amount: PKR %-8.2f              │\n", totalPetrolAmount);
        System.out.println("├────────────────────────────────────────────┤");
        System.out.println("│ Diesel Sales:                             │");
        System.out.printf("│   Total Liters: %-8.2f                   │\n", totalDieselLiters);
        System.out.printf("│   Total Amount: PKR %-8.2f              │\n", totalDieselAmount);
        System.out.println("├────────────────────────────────────────────┤");
        System.out.println("│ Overall Total:                            │");
        System.out.printf("│   Total Liters: %-8.2f                   │\n", totalLiters);
        System.out.printf("│   Total Amount: PKR %-8.2f              │\n", totalAmount);
        System.out.println("└────────────────────────────────��───────────┘");
    }
} 