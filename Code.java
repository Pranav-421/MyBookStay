import java.io.*;
import java.util.*;

class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Integer> availability = new HashMap<>();

    public void addRoomType(String type, int count) {
        availability.put(type, count);
    }

    public void displayStatus() {
        System.out.println("Inventory State: " + availability);
    }
}

class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    private String guestName;
    private String roomType;

    public Booking(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Booking [Guest: " + guestName + ", Room: " + roomType + "]";
    }
}

class PersistenceService {
    private static final String FILE_NAME = "hotel_data.ser";

    public void saveSystemState(Inventory inventory, List<Booking> history) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            oos.writeObject(history);
            System.out.println("System state persisted successfully to " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error saving state: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public Object[] loadSystemState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No persistence file found. Starting with fresh state.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Inventory inventory = (Inventory) ois.readObject();
            List<Booking> history = (List<Booking>) ois.readObject();
            System.out.println("System state recovered successfully from " + FILE_NAME);
            return new Object[]{inventory, history};
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading state: " + e.getMessage());
            return null;
        }
    }
}

public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {
        PersistenceService persistenceService = new PersistenceService();
        Inventory inventory;
        List<Booking> bookingHistory;

        // Attempt recovery
        Object[] recoveredData = persistenceService.loadSystemState();

        if (recoveredData != null) {
            inventory = (Inventory) recoveredData[0];
            bookingHistory = (List<Booking>) recoveredData[1];
        } else {
            // Initializing new state if no file exists
            inventory = new Inventory();
            inventory.addRoomType("Deluxe", 5);
            inventory.addRoomType("Suite", 2);
            bookingHistory = new ArrayList<>();
            
            // Simulating some initial activity
            bookingHistory.add(new Booking("Alice", "Deluxe"));
            System.out.println("Initialized new system state.");
        }

        System.out.println("\n--- Current System State ---");
        inventory.displayStatus();
        System.out.println("Booking History: " + bookingHistory);

        // Simulate shutdown and save
        System.out.println("\nSimulating system shutdown...");
        persistenceService.saveSystemState(inventory, bookingHistory);
        
        System.out.println("\nProcess complete. Run the program again to verify recovery.");
    }
}
