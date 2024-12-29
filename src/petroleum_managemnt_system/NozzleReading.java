package petroleum_managemnt_system;
public class NozzleReading {
    private String nozzleId;
    private double initialReading;
    private double finalReading;
    private double liters;
    private double price;
    
    public NozzleReading(String nozzleId) {
        this.nozzleId = nozzleId;
        this.initialReading = 0;
        this.finalReading = 0;
        this.liters = 0;
        this.price = 0;
    }

    public void calculateLiters() {
        this.liters = this.finalReading - this.initialReading;
    }

    // Getters and Setters
    public String getNozzleId() { return nozzleId; }
    public double getInitialReading() { return initialReading; }
    public void setInitialReading(double initialReading) { this.initialReading = initialReading; }
    public double getFinalReading() { return finalReading; }
    public void setFinalReading(double finalReading) { 
        this.finalReading = finalReading;
        calculateLiters();
    }
    public double getLiters() { return liters; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
} 