
import java.text.DecimalFormat;
abstract class Configurator {
    DecimalFormat df = new DecimalFormat("#.00"); // Format to two decimal places
    protected StringBuilder configurationSummary; // Configuration summary
    protected double basePrice; // Base price of the system
    protected double runningTotal; // Running total

    public Configurator(double basePrice) {
        this.basePrice = basePrice;
        this.runningTotal = basePrice; // Set the running total
        this.configurationSummary = new StringBuilder("""
                Computer Configuration:
                Audio: Integrated Audio
                Speakers: 2 Piece Powered Speaker Set
                Keyboard: USB Wired Entry Keyboard
                Mouse: USB Optical Mouse
                """); // Set the configuration summary starting string
    }

    public abstract void addProcessor(int option); // Add processor

    public abstract void addMemory(int option); // Add memory

    public abstract void addGraphicsCard(int option); // Add graphics card

    public void addHardDrive(String option, double price) {
        configurationSummary.append("Hard Drive: ").append(option).append("\n");
        runningTotal += price; // Add hard drive
    }

    public void addOpticalDrive(String option, double price) { // Add physical drive
        configurationSummary.append("Optical Drive: ").append(option).append("\n");
        runningTotal += price; // Add optical Drive
    }

    public void addOperatingSystem(String option, double price) { // Add Operating System
        configurationSummary.append("Operating System: ").append(option).append("\n");
        runningTotal += price; // Add operating system
    }

    public void addApplicationPackage(String packageName, double price) { // Choose application package
        configurationSummary.append("Application Package: ").append(packageName).append("\n");
        runningTotal += price; // Add application package
    }

    public void addSalesTax(double taxRate) { // Calculate salex tax
        configurationSummary.append("\n");
        double salesTax = runningTotal * taxRate;
        configurationSummary.append("Sales Tax (").append(df.format(taxRate * 100)).append("%): $").append(df.format(salesTax)).append("\n");
        runningTotal += salesTax; // Calculate sales tax
    }

    public void addShippingAndHandling(double rate) { // Calculate shipping and handling
        double shippingAndHandling = runningTotal * rate;
        configurationSummary.append("Shipping & Handling (").append(df.format(rate * 100)).append("%): $").append(df.format(shippingAndHandling)).append("\n");
        runningTotal += shippingAndHandling; // Calculate shipping and handling
    }

    public String getConfigurationSummary() {
        return configurationSummary.toString(); // Get Configuration summary
    }

    public double getTotalPrice() {
        return runningTotal; // Get Total Price
    }

    public void resetConfiguration() {
        configurationSummary = new StringBuilder("Configuration:\n");
        runningTotal = basePrice; // Reset running total and configuration summary
    }
}