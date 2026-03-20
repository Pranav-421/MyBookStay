import java.util.*;

class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return name + " ($" + cost + ")";
    }
}

class AddOnServiceManager {
    private Map<String, List<Service>> reservationAddOns = new HashMap<>();

    public void addServiceToReservation(String reservationId, Service service) {
        reservationAddOns.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
        System.out.println("Added " + service.getName() + " to Reservation: " + reservationId);
    }

    public void displayAddOns(String reservationId) {
        List<Service> services = reservationAddOns.getOrDefault(reservationId, new ArrayList<>());
        System.out.println("\n--- Add-On Services for " + reservationId + " ---");
        if (services.isEmpty()) {
            System.out.println("No add-ons selected.");
        } else {
            double totalCost = 0;
            for (Service s : services) {
                System.out.println("- " + s);
                totalCost += s.getCost();
            }
            System.out.println("Total Add-On Cost: $" + totalCost);
        }
        System.out.println("------------------------------------------");
    }
}

public class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {
        AddOnServiceManager manager = new AddOnServiceManager();

        Service wifi = new Service("High-Speed WiFi", 15.0);
        Service breakfast = new Service("Buffet Breakfast", 25.0);
        Service spa = new Service("Spa Treatment", 50.0);

        String resId1 = "D-105";
        String resId2 = "S-102";

        System.out.println("Simulating Add-On selections...");
        manager.addServiceToReservation(resId1, wifi);
        manager.addServiceToReservation(resId1, breakfast);
        
        manager.addServiceToReservation(resId2, spa);
        manager.addServiceToReservation(resId2, breakfast);

        manager.displayAddOns(resId1);
        manager.displayAddOns(resId2);

        System.out.println("Add-on services managed independently of core inventory and booking logic.");
    }
}
