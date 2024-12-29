package petroleum_managemnt_system;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class DailyRecord {
    private Date date;
    private String oilMan;
    private String time;
    private List<NozzleReading> petrolNozzles;
    private List<NozzleReading> dieselNozzles;
    private double petrolPrice;
    private double dieselPrice;
    private double totalPetrolLiters;
    private double totalDieselLiters;
    private double totalPetrolPrice;
    private double totalDieselPrice;

    public DailyRecord(String oilMan, String time) {
        this.date = new Date();
        this.oilMan = oilMan;
        this.time = time;
        this.petrolNozzles = new ArrayList<>();
        this.dieselNozzles = new ArrayList<>();

        // Initialize 4 petrol nozzles
        for (int i = 1; i <= 4; i++) {
            petrolNozzles.add(new NozzleReading("P" + i));
        }

        // Initialize 4 diesel nozzles
        for (int i = 1; i <= 4; i++) {
            dieselNozzles.add(new NozzleReading("D" + i));
        }
        this.totalPetrolLiters = 0;
        this.totalDieselLiters = 0;
        this.totalPetrolPrice = 0;
        this.totalDieselPrice = 0;
    }

    // Getters and Setters
    public Date getDate() {
        return date;
    }

    public String getOilMan() {
        return oilMan;
    }

    public String getTime() {
        return time;
    }

    public List<NozzleReading> getPetrolNozzles() {
        return petrolNozzles;
    }

    public List<NozzleReading> getDieselNozzles() {
        return dieselNozzles;
    }

    public double getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(double petrolPrice) {
        this.petrolPrice = petrolPrice;
    }

    public double getDieselPrice() {
        return dieselPrice;
    }

    public void setDieselPrice(double dieselPrice) {
        this.dieselPrice = dieselPrice;
    }

    public double getTotalPetrolLiters() {
        return totalPetrolLiters;
    }

    public double getTotalDieselLiters() {
        return totalDieselLiters;
    }

    public double getTotalPetrolPrice() {
        return totalPetrolPrice;
    }

    public double getTotalDieselPrice() {
        return totalDieselPrice;
    }

    public double getTotalLiters() {
        return totalPetrolLiters + totalDieselLiters;
    }

    public double getTotalPrice() {
        return totalPetrolPrice + totalDieselPrice;
    }

    // Add methods to calculate totals
    public void calculateTotals() {
        // Calculate total petrol liters
        totalPetrolLiters = petrolNozzles.stream()
                .mapToDouble(NozzleReading::getLiters)
                .sum();

        // Calculate total diesel liters
        totalDieselLiters = dieselNozzles.stream()
                .mapToDouble(NozzleReading::getLiters)
                .sum();

        // Calculate total prices
        totalPetrolPrice = totalPetrolLiters * petrolPrice;
        totalDieselPrice = totalDieselLiters * dieselPrice;
    }
}