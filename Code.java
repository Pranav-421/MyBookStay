import java.util.*;
import java.util.concurrent.*;

class Inventory {
    private final Map<String, Integer> availability = new ConcurrentHashMap<>();
    private final Set<String> allocatedRooms = Collections.synchronizedSet(new HashSet<>());

    public void addRoomType(String type, int count) {
        availability.put(type, count);
    }

    public synchronized String allocateRoom(String type) {
        int currentCount = availability.getOrDefault(type, 0);
        if (currentCount > 0) {
            String roomId = type.substring(0, 1).toUpperCase() + "-" + (100 + currentCount);
            availability.put(type, currentCount - 1);
            allocatedRooms.add(roomId);
            return roomId;
        }
        return null;
    }

    public void displayStatus() {
        System.out.println("\n--- Final Inventory Status ---");
        availability.forEach((type, count) -> 
            System.out.println(type + ": " + count + " available."));
        System.out.println("Total Rooms Allocated: " + allocatedRooms.size());
        System.out.println("Allocated IDs: " + allocatedRooms);
    }
}

class BookingProcessor implements Runnable {
    private final String guestName;
    private final String roomType;
    private final Inventory inventory;

    public BookingProcessor(String guestName, String roomType, Inventory inventory) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " processing request for: " + guestName);
        String roomId = inventory.allocateRoom(roomType);
        
        if (roomId != null) {
            System.out.println("SUCCESS: " + guestName + " assigned Room " + roomId);
        } else {
            System.out.println("FAILED: No availability for " + guestName);
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) throws InterruptedException {
        Inventory inventory = new Inventory();
        // Only 2 Deluxe rooms available for 4 simultaneous requests
        inventory.addRoomType("Deluxe", 2);

        System.out.println("Starting Concurrent Booking Simulation...");
        System.out.println("Initial Inventory: Deluxe = 2\n");

        ExecutorService executor = Executors.newFixedThreadPool(4);

        String[] guests = {"Alice", "Bob", "Charlie", "Diana"};

        for (String guest : guests) {
            executor.execute(new BookingProcessor(guest, "Deluxe", inventory));
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        inventory.displayStatus();
        
        System.out.println("\nSimulation Complete: Thread safety ensured no double-booking occurred.");
    }
}
