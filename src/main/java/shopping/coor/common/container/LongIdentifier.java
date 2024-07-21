package shopping.coor.common.container;

public class LongIdentifier {
    private Long value;

    public LongIdentifier() {
    }

    public LongIdentifier(String idStr) {
        try {
            this.value = Long.parseLong(idStr);
        } catch (Exception ignored) {
        }
    }

    public Long get() {
        return value;
    }
}
