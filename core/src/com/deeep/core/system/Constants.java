package com.deeep.core.system;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/29/13
 * Time: 10:21 AM
 * TODO put all the constants in here so everything is organized and well
 */
public class Constants {
    /** Scaling factor. The lower the scale the more blocks fit on the screen */
    public static final float SCALE = 1f;
    /** The block size in pixels */
    public static final float BLOCK_SIZE = 40;
    /** The virtual width in pixels, we are targeting this resolution */
    public static final float VIRTUAL_WIDTH = 800;
    /** The virtual height in pixels, we are targeting this resolution */
    public static final float VIRTUAL_HEIGHT = 480;
    /** The aspect we are trying to keep */
    public static final float VIRTUAL_ASPECT = VIRTUAL_WIDTH / VIRTUAL_HEIGHT;
}
