import java.util.HashMap;
import java.util.Map;

// 1. Custom Exceptions mapped to domain-specific failures
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class InvalidRoomTypeException extends InvalidBookingException {
    public InvalidRoomTypeException(String message) {
        super(message);
    }
}

class InsufficientInventoryException extends InvalidBookingException {
    public InsufficientInventoryException(String message) {
        super(message);
    }
}

// 2. The core System Manager
class HotelBookingSystem {
    private Map<String, Integer> inventory;

    public HotelBookingSystem() {
        // Initialize mock inventory
        inventory = new HashMap<>();
        inventory.put("Standard", 10);
        inventory.put("Deluxe", 5);
        inventory.put("Suite", 2);
    }

    public void bookRoom(String guestName, String roomType, int numRooms) throws InvalidBookingException {
        // Fail-Fast Check 1: Input Validation
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }
        if (numRooms <= 0) {
            throw new InvalidBookingException("Number of rooms requested must be greater than zero.");
        }

        // Fail-Fast Check 2: Validate Room Type
        if (!inventory.containsKey(roomType)) {
            throw new InvalidRoomTypeException("Invalid room type selected: '" + roomType + "'.");
        }

        // Fail-Fast Check 3: Guard System State (Prevent negative inventory)
        int availableRooms = inventory.get(roomType);
        if (numRooms > availableRooms) {
            throw new InsufficientInventoryException("Cannot book " + numRooms + " '" + roomType + "' room(s). Only " + availableRooms + " available.");
        }

        // If all validations pass, process the booking (Happy Path)
        inventory.put(roomType, availableRooms - numRooms);
        System.out.println("SUCCESS: Booking confirmed for " + guestName + " -> " + numRooms + " " + roomType + " room(s).");
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + inventory);
    }
}

// 3. Main execution class testing the Use Case
public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {
        HotelBookingSystem system = new HotelBookingSystem();
        
        System.out.println("=== Hotel Booking App Initialized ===");
        system.displayInventory();

        // Scenario 1: Valid Booking (Happy Path)
        System.out.println("\n[Scenario 1] Valid Booking...");
        try {
            system.bookRoom("Alice", "Standard", 2);
        } catch (InvalidBookingException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        // Scenario 2: Invalid Input (Zero Rooms)
        System.out.println("\n[Scenario 2] Invalid Input (Zero rooms)...");
        try {
            system.bookRoom("Bob", "Deluxe", 0);
        } catch (InvalidBookingException e) {
            System.out.println("REJECTED: " + e.getMessage());
        }

        // Scenario 3: Invalid Room Type
        System.out.println("\n[Scenario 3] Invalid Room Type...");
        try {
            system.bookRoom("Charlie", "Presidential", 1);
        } catch (InvalidBookingException e) {
            System.out.println("REJECTED: " + e.getMessage());
        }

        // Scenario 4: Insufficient Inventory (Overbooking)
        System.out.println("\n[Scenario 4] Insufficient Inventory (Booking 5 Suites when only 2 exist)...");
        try {
            system.bookRoom("Diana", "Suite", 5);
        } catch (InvalidBookingException e) {
            System.out.println("REJECTED: " + e.getMessage());
        }

        // Verify System Stability
        System.out.println("\n=== Final System State ===");
        system.displayInventory();
        System.out.println("System remained stable and recovered gracefully from invalid inputs!");
    }
}
