package com.joker.mp3;

public class ID3v2TextFrameData extends AbstractID3v2FrameData {
    protected EncodedText text;

    public ID3v2TextFrameData(boolean unsynchronisation) {
        super(unsynchronisation);
    }

    public ID3v2TextFrameData(boolean unsynchronisation, EncodedText text) {
        super(unsynchronisation);
        this.text = text;
    }

    public ID3v2TextFrameData(boolean unsynchronisation, byte[] bytes) throws InvalidDataException {
        super(unsynchronisation);
        synchroniseAndUnpackFrameData(bytes);
    }

    protected void unpackFrameData(byte[] bytes) throws InvalidDataException {
        text = new EncodedText(bytes[0], BufferTools.copyBuffer(bytes, 1, bytes.length - 1));
    }

    protected byte[] packFrameData() {
        byte[] bytes = new byte[getLength()];
        if (text != null) bytes[0] = text.getTextEncoding();
        else bytes[0] = 0;
        if (text != null && text.toBytes().length > 0) {
            BufferTools.copyIntoByteBuffer(text.toBytes(), 0, text.toBytes().length, bytes, 1);
        }
        return bytes;
    }

    protected int getLength() {
        int length = 1;
        if (text != null) length += text.toBytes().length;
        return length;
    }

    public EncodedText getText() {
        return text;
    }

    public void setText(EncodedText text) {
        this.text = text;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ID3v2TextFrameData)) return false;
        if (!super.equals(obj)) return false;
        ID3v2TextFrameData other = (ID3v2TextFrameData) obj;
        if (text == null) {
            if (other.text != null) return false;
        } else if (other.text == null) return false;
        else if (!text.equals(other.text)) return false;
        return true;
    }
}
