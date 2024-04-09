package com.mike;

public class BedrockBlock {
    int x;
    int y;
    int z;
    boolean shouldBeBedrock;

    public BedrockBlock(int x, int y, int z, boolean shouldBeBedrock) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.shouldBeBedrock = shouldBeBedrock;
    }

    @Override
    public String toString() {
        return "BedrockBlock{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", shouldBeBedrock=" + shouldBeBedrock +
                '}';
    }
}
