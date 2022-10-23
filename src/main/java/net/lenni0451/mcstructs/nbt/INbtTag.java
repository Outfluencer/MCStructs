package net.lenni0451.mcstructs.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface INbtTag {

    int getId();

    void read(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    void write(final DataOutput out) throws IOException;

    String toString();

}