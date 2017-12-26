package domain.enums;

public enum Status {
    AvailableOnline(1),
    AvailableOffline(2),
    UnavailableOffline(3);

    private final int id;

    Status(int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }
}
