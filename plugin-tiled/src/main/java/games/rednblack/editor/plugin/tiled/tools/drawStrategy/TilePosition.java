package games.rednblack.editor.plugin.tiled.tools.drawStrategy;

public class TilePosition {
    // the 8 bits in an int read from right to left represents the existence of a tile at the postion at
    // some number:-  00101101010110010
    // positions:-             ^^^^^^^^
    //                         76543210
    //    7 0 1
    //    6 T 2
    //    5 4 3
    // .

    public static final int Tp = 0b00000001;
    public static final int TR = 0b00000010;
    public static final int TL = 0b10000000;

    public static final int Ce = 0b00000000;
    public static final int CR = 0b00000100;
    public static final int CL = 0b01000000;

    public static final int Bo = 0b00010000;
    public static final int BR = 0b00001000;
    public static final int BL = 0b00100000;

}
