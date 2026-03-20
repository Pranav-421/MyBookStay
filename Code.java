import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class Inventory {
    private Map<String, Integer> availability = new HashMap<>();
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public void addRoomType(String type, int count) {
        availability.put(type, count);
        allocatedRooms.put(type, new HashSet<>());
    }

    public boolean hasAvailability(String type) {
        return availability.getOrDefault(type, 0) > 0;
    }

    public String allocateRoom(String type) {
        int currentCount = availability.get(type);
        String roomId = type.substring(0, 1).toUpperCase() + "-" + (100 + currentCount);
        
        availability.put(type, currentCount - 1);
        allocatedRooms.get(type).add(roomId);
        return roomId;
    }

    public void displayStatus() {
        System.out.println("\n--- Final Inventory Status ---");
        availability.forEach((type, count) -> {
            System.out.println(type + ": " + count + " available. Assigned: " + allocatedRooms.get(type));
        });
    }
}

class BookingService {
    private Inventory inventory;
    private Queue<Reservation> requestQueue;

    public BookingService(Inventory inventory, Queue<Reservation> queue) {
        this.inventory = inventory;
        this.requestQueue = queue;
    }

    public void processRequests() {
        System.out.println("Processing booking requests from queue...");
        while (!requestQueue.isEmpty()) {
            Reservation request = requestQueue.poll();
            System.out.print("Processing " + request.getGuestName() + " for " + request.getRoomType() + "... ");
            
            if (inventory.hasAvailability(request.getRoomType())) {
                String roomId = inventory.allocateRoom(request.getRoomType());
                System.out.println("CONFIRMED. Room ID: " + roomId);
            } else {
                System.out.println("FAILED. No availability.");
            }
        }
    }
}

public class UseCase6RoomAllocationService {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        inventory.addRoomType("Deluxe", 2);
        inventory.addRoomType("Suite", 1);

        Queue<Reservation> queue = new LinkedList<>();
        queue.add(new Reservation("Alice", "Deluxe"));
        queue.add(new Reservation("Bob", "Suite"));
        queue.add(new Reservation("Charlie", "Deluxe"));
        queue.add(new Reservation("Diana", "Suite")); // Should fail

        BookingService bookingService = new BookingService(inventory, queue);
        bookingService.processRequests();
        
        inventory.displayStatus();
    }
}
