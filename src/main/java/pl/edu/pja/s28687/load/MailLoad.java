package pl.edu.pja.s28687.load;

import java.util.Set;

public class MailLoad extends Load implements ICountable {
    private final int count;

    public MailLoad(int id, int count) {
        super(id, count * 0.0001); // 100g per item
        this.count = count;
    }

    @Override
    public Set<LoadType> flags() {
        return Set.of(LoadType.MAIL);
    }

    @Override
    public String getBasicInfo() {
        return "ID : " + getId() + " Mail load,  Count: " + count;
    }

    @Override
    public String getFullInfo() {
        return getBasicInfo() + "\n" + getDescription();
    }

    @Override
    public int getCount() {
        return count;
    }
}
