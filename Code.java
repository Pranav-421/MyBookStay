abstract class Room {
    private String roomType;
    private int numberOfBeds;
    private double price;

    public Room(String roomType, int numberOfBeds, double price) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getPrice() {
        return price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Price: $" + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 100.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 150.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite", 3, 300.0);
    }
}

public class UseCase2RoomInitialization {
    public static void main(String[] args) {
        System.out.println("Hotel Booking System v2.0\n");

        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        int availableSingleRooms = 5;
        int availableDoubleRooms = 3;
        int availableSuiteRooms = 2;

        System.out.println("--- Room Catalog & Availability ---");
        
        singleRoom.displayDetails();
        System.out.println("Currently Available: " + availableSingleRooms + "\n");

        doubleRoom.displayDetails();
        System.out.println("Currently Available: " + availableDoubleRooms + "\n");

        suiteRoom.displayDetails();
        System.out.println("Currently Available: " + availableSuiteRooms + "\n");
    }
}
