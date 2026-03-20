import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
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

    @Override
    public String toString() {
        return String.format("Guest: %-10s | Room Type: %-10s | Room ID: %-5s", guestName, roomType, roomId);
    }
}

class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void recordBooking(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getHistory() {
        return Collections.unmodifiableList(history);
    }
}

class BookingReportService {
    private BookingHistory bookingHistory;

    public BookingReportService(BookingHistory bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    public void generateSummaryReport() {
        List<Reservation> records = bookingHistory.getHistory();
        System.out.println("\n==============================================");
        System.out.println("        HOTEL BOOKING SUMMARY REPORT          ");
        System.out.println("==============================================");
        
        if (records.isEmpty()) {
            System.out.println("No confirmed bookings found.");
        } else {
            Map<String, Integer> typeCount = new HashMap<>();
            for (Reservation res : records) {
                System.out.println(res);
                typeCount.put(res.getRoomType(), typeCount.getOrDefault(res.getRoomType(), 0) + 1);
            }
            
            System.out.println("----------------------------------------------");
            System.out.println("Total Bookings: " + records.size());
            typeCount.forEach((type, count) -> 
                System.out.println(type + " Bookings: " + count)
            );
        }
        System.out.println("==============================================\n");
    }
}

public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService(history);

        System.out.println("Simulating booking confirmations...");
        
        // Simulating confirmed reservations being added to history
        history.recordBooking(new Reservation("Alice", "Deluxe", "D-101"));
        history.recordBooking(new Reservation("Bob", "Suite", "S-201"));
        history.recordBooking(new Reservation("Charlie", "Deluxe", "D-102"));
        history.recordBooking(new Reservation("Diana", "Standard", "T-301"));

        // Admin requests the report
        reportService.generateSummaryReport();
        
        System.out.println("Audit trail maintained in insertion order.");
        System.out.println("Reporting logic successfully isolated from data storage.");
    }
}
