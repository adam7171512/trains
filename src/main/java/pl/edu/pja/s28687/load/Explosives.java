package pl.edu.pja.s28687.Load;

import pl.edu.pja.s28687.Load.Flags;
import pl.edu.pja.s28687.Load.Load;
import pl.edu.pja.s28687.Load.IExplosive;

public class Explosives extends Load<IExplosive> {
    public Explosives(double weight) {
        super(weight);
    }

    @Override
    public void setFlags() {
        flags.add(Flags.EXPLOSIVE);
    }
}
