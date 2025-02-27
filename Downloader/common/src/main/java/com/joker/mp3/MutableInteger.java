package com.joker.mp3;

public class MutableInteger {
    private int value;

    public MutableInteger(int value) {
        this.value = value;
    }

    public void increment() {
        value++;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof MutableInteger)) return false;
        if (super.equals(obj)) return true;
        MutableInteger other = (MutableInteger) obj;
        return value == other.value;
    }
}
