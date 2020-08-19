package com.antplatform.admin.common.utils.captcha;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.ImageObserver;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author: maoyan
 * @date: 2020/7/16 15:52:52
 * @description:
 */
public class GifEncoder {

    /**
     * image size
     */
    protected int width;

    protected int height;

    /**
     * transparent color if given
     */
    protected Color transparent = null;

    /**
     * transparent index in color table
     */
    protected int transIndex;

    /**
     * no repeat
     */
    protected int repeat = -1;

    /**
     * frame delay (hundredths)
     */
    protected int delay = 0;

    /**
     * ready to output frames
     */
    protected boolean started = false;

    protected OutputStream out;

    /**
     * current frame
     */
    protected BufferedImage image;

    /**
     * BGR byte array from frame
     */
    protected byte[] pixels;

    /**
     * converted frame indexed to palette
     */
    protected byte[] indexedPixels;

    /**
     * number of bit planes
     */
    protected int colorDepth;

    /**
     *  RGB palette
     */
    protected byte[] colorTab;

    /**
     * active palette entries
     */
    protected boolean[] usedEntry = new boolean[256];

    /**
     * color table size (bits-1)
     */
    protected int palSize = 7;

    /**
     * disposal code (-1 = use default)
     */
    protected int dispose = -1;

    /**
     * close stream when finished
     */
    protected boolean closeStream = false;

    protected boolean firstFrame = true;

    /**
     * if false, get size from first frame
     */
    protected boolean sizeSet = false;

    /**
     * default sample interval for quantizer
     */
    protected int sample = 10;

    /**
     * Sets the delay time between each frame, or changes it
     * for subsequent frames (applies to last frame added).
     *
     * @param ms int delay time in milliseconds
     */
    public void setDelay(int ms) {
        this.delay = Math.round(ms / 10.0F);
    }

    /**
     * Sets the GIF frame disposal code for the last added frame
     * and any subsequent frames.  Default is 0 if no transparent
     * color has been set, otherwise 2.
     * @param code int disposal code.
     */
    public void setDispose(int code) {
        if (code >= 0) {
            this.dispose = code;
        }
    }

    /**
     * Sets the number of times the set of GIF frames
     * should be played.  Default is 1; 0 means play
     * indefinitely.  Must be invoked before the first
     * image is added.
     *
     * @param iter int number of iterations.
     * @return
     */
    public void setRepeat(int iter) {
        if (iter >= 0) {
            this.repeat = iter;
        }
    }

    /**
     * Sets the transparent color for the last added frame
     * and any subsequent frames.
     * Since all colors are subject to modification
     * in the quantization process, the color in the final
     * palette for each frame closest to the given color
     * becomes the transparent color for that frame.
     * May be set to null to indicate no transparent color.
     *
     * @param c Color to be treated as transparent on display.
     */
    public void setTransparent(Color c) {
        this.transparent = c;
    }

    /**
     * Adds next GIF frame.  The frame is not written immediately, but is
     * actually deferred until the next frame is received so that timing
     * data can be inserted.  Invoking <code>finish()</code> flushes all
     * frames.  If <code>setSize</code> was not invoked, the size of the
     * first image is used for all subsequent frames.
     *
     * @param im BufferedImage containing frame to write.
     * @return true if successful.
     */
    public boolean addFrame(BufferedImage im) {
        if (im != null && this.started) {
            boolean ok = true;
            try {
                if (!this.sizeSet) {
                    // use first frame's size
                    this.setSize(im.getWidth(), im.getHeight());
                }
                this.image = im;
                // convert to correct format if necessary
                this.getImagePixels();
                // build color table & map pixels
                this.analyzePixels();
                if (this.firstFrame) {
                    // logical screen descriptior
                    this.writeLSD();
                    // global color table
                    this.writePalette();
                    if (this.repeat >= 0) {
                        // use NS app extension to indicate reps
                        this.writeNetscapeExt();
                    }
                }
                // write graphic control extension
                this.writeGraphicCtrlExt();
                // image descriptor
                this.writeImageDesc();
                if (!this.firstFrame) {
                    // local color table
                    this.writePalette();
                }
                // encode and write pixel data
                this.writePixels();
                this.firstFrame = false;
            } catch (IOException arg3) {
                ok = false;
            }
            return ok;
        } else {
            return false;
        }
    }

    //added by alvaro
    public boolean outFlush() {
        boolean ok = true;
        try {
            this.out.flush();
            return ok;
        } catch (IOException arg2) {
            ok = false;
            return ok;
        }
    }

    public byte[] getFrameByteArray() {
        return ((ByteArrayOutputStream) this.out).toByteArray();
    }

    /**
     * Flushes any pending data and closes output file.
     * If writing to an OutputStream, the stream is not
     * closed.
     */
    public boolean finish() {
        if (!this.started) {
            return false;
        } else {
            boolean ok = true;
            this.started = false;
            try {
                // gif trailer
                this.out.write(59);
                this.out.flush();
                if (this.closeStream) {
                    this.out.close();
                }
            } catch (IOException arg2) {
                ok = false;
            }
            return ok;
        }
    }

    public void reset() {
        // reset for subsequent use
        this.transIndex = 0;
        this.out = null;
        this.image = null;
        this.pixels = null;
        this.indexedPixels = null;
        this.colorTab = null;
        this.closeStream = false;
        this.firstFrame = true;
    }

    /**
     * Sets frame rate in frames per second.  Equivalent to
     * <code>setDelay(1000/fps)</code>.
     *
     * @param fps float frame rate (frames per second)
     */
    public void setFrameRate(float fps) {
        if (fps != 0.0F) {
            this.delay = Math.round(100.0F / fps);
        }
    }

    /**
     * Sets quality of color quantization (conversion of images
     * to the maximum 256 colors allowed by the GIF specification).
     * Lower values (minimum = 1) produce better colors, but slow
     * processing significantly.  10 is the default, and produces
     * good color mapping at reasonable speeds.  Values greater
     * than 20 do not yield significant improvements in speed.
     *
     * @param quality int greater than 0.
     * @return
     */
    public void setQuality(int quality) {
        if (quality < 1) {
            quality = 1;
        }
        this.sample = quality;
    }

    /**
     * Sets the GIF frame size.  The default size is the
     * size of the first frame added if this method is
     * not invoked.
     *
     * @param w int frame width.
     * @param h int frame width.
     */
    public void setSize(int w, int h) {
        if (!this.started || this.firstFrame) {
            this.width = w;
            this.height = h;
            if (this.width < 1) {
                this.width = 320;
            }
            if (this.height < 1) {
                this.height = 240;
            }
            this.sizeSet = true;
        }
    }

    /**
     * Initiates GIF file creation on the given stream.  The stream
     * is not closed automatically.
     *
     * @param os OutputStream on which GIF images are written.
     * @return false if initial write failed.
     */
    public boolean start(OutputStream os) {
        if (os == null) {
            return false;
        } else {
            boolean ok = true;
            this.closeStream = false;
            this.out = os;
            try {
                this.writeString("GIF89a");
            } catch (IOException arg3) {
                ok = false;
            }
            return this.started = ok;
        }
    }

    /**
     * Initiates writing of a GIF file with the specified name.
     *
     * @param file String containing output file name.
     * @return false if open or initial write failed.
     */
    public boolean start(String file) {
        boolean ok = true;
        try {
            this.out = new BufferedOutputStream(new FileOutputStream(file));
            ok = this.start(this.out);
            this.closeStream = true;
        } catch (IOException arg3) {
            ok = false;
        }
        return this.started = ok;
    }

    /**
     * Analyzes image colors and creates color map.
     */
    protected void analyzePixels() {
        int len = this.pixels.length;
        int nPix = len / 3;
        this.indexedPixels = new byte[nPix];
        Quant nq = new Quant(this.pixels, len, this.sample);
        this.colorTab = nq.process();
        int k;
        for (k = 0; k < this.colorTab.length; k += 3) {
            byte i = this.colorTab[k];
            this.colorTab[k] = this.colorTab[k + 2];
            this.colorTab[k + 2] = i;
            this.usedEntry[k / 3] = false;
        }
        k = 0;
        for (int arg6 = 0; arg6 < nPix; ++arg6) {
            int index = nq.map(this.pixels[k++] & 255, this.pixels[k++] & 255, this.pixels[k++] & 255);
            this.usedEntry[index] = true;
            this.indexedPixels[arg6] = (byte) index;
        }
        this.pixels = null;
        this.colorDepth = 8;
        this.palSize = 7;
        if (this.transparent != null) {
            this.transIndex = this.findClosest(this.transparent);
        }
    }

    protected int findClosest(Color c) {
        if (this.colorTab == null) {
            return -1;
        } else {
            int r = c.getRed();
            int g = c.getGreen();
            int b = c.getBlue();
            int minpos = 0;
            int dmin = 16777216;
            int len = this.colorTab.length;
            for (int i = 0; i < len; ++i) {
                int dr = r - (this.colorTab[i++] & 255);
                int dg = g - (this.colorTab[i++] & 255);
                int db = b - (this.colorTab[i] & 255);
                int d = dr * dr + dg * dg + db * db;
                int index = i / 3;
                if (this.usedEntry[index] && d < dmin) {
                    dmin = d;
                    minpos = index;
                }
            }
            return minpos;
        }
    }

    protected void getImagePixels() {
        int w = this.image.getWidth();
        int h = this.image.getHeight();
        int type = this.image.getType();
        if (w != this.width || h != this.height || type != 5) {
            BufferedImage temp = new BufferedImage(this.width, this.height, 5);
            Graphics2D g = temp.createGraphics();
            g.drawImage(this.image, 0, 0, (ImageObserver) null);
            this.image = temp;
        }
        this.pixels = ((DataBufferByte) this.image.getRaster().getDataBuffer()).getData();
    }

    protected void writeGraphicCtrlExt() throws IOException {
        this.out.write(33);
        this.out.write(249);
        this.out.write(4);
        byte transp;
        int disp;
        if (this.transparent == null) {
            transp = 0;
            disp = 0;
        } else {
            transp = 1;
            disp = 2;
        }
        if (this.dispose >= 0) {
            disp = this.dispose & 7;
        }
        disp <<= 2;
        this.out.write(disp | transp);
        this.writeShort(this.delay);
        this.out.write(this.transIndex);
        this.out.write(0);
    }

    protected void writeImageDesc() throws IOException {
        this.out.write(44);
        this.writeShort(0);
        this.writeShort(0);
        this.writeShort(this.width);
        this.writeShort(this.height);
        if (this.firstFrame) {
            this.out.write(0);
        } else {
            this.out.write(128 | this.palSize);
        }
    }

    protected void writeLSD() throws IOException {
        this.writeShort(this.width);
        this.writeShort(this.height);
        this.out.write(240 | this.palSize);
        this.out.write(0);
        this.out.write(0);
    }

    protected void writeNetscapeExt() throws IOException {
        this.out.write(33);
        this.out.write(255);
        this.out.write(11);
        this.writeString("NETSCAPE2.0");
        this.out.write(3);
        this.out.write(1);
        this.writeShort(this.repeat);
        this.out.write(0);
    }

    protected void writePalette() throws IOException {
        this.out.write(this.colorTab, 0, this.colorTab.length);
        int n = 768 - this.colorTab.length;
        for (int i = 0; i < n; ++i) {
            this.out.write(0);
        }
    }

    protected void writePixels() throws IOException {
        Encoder encoder = new Encoder(this.width, this.height, this.indexedPixels, this.colorDepth);
        encoder.encode(this.out);
    }

    protected void writeShort(int value) throws IOException {
        this.out.write(value & 255);
        this.out.write(value >> 8 & 255);
    }

    protected void writeString(String s) throws IOException {
        for (int i = 0; i < s.length(); ++i) {
            this.out.write((byte) s.charAt(i));
        }
    }
}
