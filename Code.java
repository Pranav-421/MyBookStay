import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean isCancelled;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    @Override
    public String toString() {
        return String.format("ID: %-5s | Guest: %-8s | Type: %-8s | Status: %s", 
            roomId, guestName, roomType, (isCancelled ? "CANCELLED" : "CONFIRMED"));
    }
}

class Inventory {
    private Map<String, Integer> availability = new HashMap<>();
    private Stack<String> releasedRooms = new Stack<>();

    public void addRoomType(String type, int count) {
        availability.put(type, count);
    }

    public void incrementAvailability(String type, String roomId) {
        availability.put(type, availability.get(type) + 1);
        releasedRooms.push(roomId);
    }

    public void displayStatus() {
        System.out.println("Current Inventory Counts: " + availability);
        System.out.println("Recently Released Rooms (Stack): " + releasedRooms);
    }
}

class CancellationService {
    private Inventory inventory;
    private Map<String, Reservation> activeBookings;

    public CancellationService(Inventory inventory, Map<String, Reservation> activeBookings) {
        this.inventory = inventory;
        this.activeBookings = activeBookings;
    }

    public void cancelBooking(String roomId) {
        System.out.println("\nAttempting to cancel booking for Room ID: " + roomId);
        
        Reservation reservation = activeBookings.get(roomId);

        if (reservation == null) {
            System.out.println("Error: Reservation for Room ID " + roomId + " does not exist.");
            return;
        }

        if (reservation.isCancelled()) {
            System.out.println("Error: Reservation for Room ID " + roomId + " is already cancelled.");
            return;
        }

        reservation.setCancelled(true);
        inventory.incrementAvailability(reservation.getRoomType(), roomId);
        
        System.out.println("SUCCESS: Cancellation processed. Inventory rolled back for " + reservation.getRoomType());
    }
}

public class UseCase10BookingCancellation {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        inventory.addRoomType("Deluxe", 2);
        inventory.addRoomType("Suite", 0);

        Map<String, Reservation> bookingHistory = new HashMap<>();
        
        Reservation res1 = new Reservation("Alice", "Deluxe", "D-101");
        Reservation res2 = new Reservation("Bob", "Suite", "S-201");
        
        bookingHistory.put(res1.getRoomId(), res1);
        bookingHistory.put(res2.getRoomId(), res2);

        CancellationService cancellationService = new CancellationService(inventory, bookingHistory);

        System.out.println("--- Initial State ---");
        inventory.displayStatus();

        cancellationService.cancelBooking("D-101");
        cancellationService.cancelBooking("S-201");
        cancellationService.cancelBooking("D-999"); // Non-existent

        System.out.println("\n--- Final State After Rollback ---");
        inventory.displayStatus();
        
        System.out.println("\nUpdated Booking Records:");
        bookingHistory.values().forEach(System.out::println);
    }
}
