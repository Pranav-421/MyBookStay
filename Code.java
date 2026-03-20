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

    @Override
    public String toString() {
        return "Reservation [Guest: " + guestName + ", Room Type: " + roomType + "]";
    }
}

class BookingRequestQueue {
    private Queue<Reservation> requestQueue = new LinkedList<>();

    public void addBookingRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request received for: " + reservation.getGuestName());
    }

    public void displayQueue() {
        System.out.println("\n--- Current Booking Request Queue (FIFO) ---");
        if (requestQueue.isEmpty()) {
            System.out.println("Queue is empty.");
        } else {
            for (Reservation res : requestQueue) {
                System.out.println(res);
            }
        }
        System.out.println("--------------------------------------------");
    }

    public Queue<Reservation> getQueue() {
        return requestQueue;
    }
}

public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        System.out.println("Simulating incoming booking requests...");
        
        bookingQueue.addBookingRequest(new Reservation("Alice", "Deluxe"));
        bookingQueue.addBookingRequest(new Reservation("Bob", "Suite"));
        bookingQueue.addBookingRequest(new Reservation("Charlie", "Deluxe"));
        bookingQueue.addBookingRequest(new Reservation("Diana", "Standard"));

        bookingQueue.displayQueue();

        System.out.println("Requests are now queued and ready for sequential processing.");
        System.out.println("Note: No inventory has been modified at this stage.");
    }
}
