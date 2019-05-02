package at.searles.colorview.utils;

public class RgbCommons {
    public static int rgb2int(float...rgba) {
        int r = (int) clamp(rgba[0] * 256.0f, 0.0f, 255.0f);
        int g = (int) clamp(rgba[1] * 256.0f, 0.0f, 255.0f);
        int b = (int) clamp(rgba[2] * 256.0f, 0.0f, 255.0f);

        int a = rgba.length > 3 ? (int) clamp(rgba[3] * 256.0f, 0.0f, 255.0f) : 255;

        return a << 24 | r << 16 | g << 8 | b;
    }

    private static float clamp(float d, float min, float max) {
        // convenience from at.searles.math
        return Math.min(max, Math.max(min, d));
    }

    public static float[] int2rgb(int argb, float[] rgba) {
        rgba[0] = ((argb >> 16) & 0x0ff) / 255.0f;
        rgba[1] = ((argb >> 8) & 0x0ff) / 255.0f;
        rgba[2] = (argb & 0x0ff) / 255.0f;

        if(rgba.length > 3) {
            rgba[3] = ((argb >> 24) & 0x0ff) / 255.0f;
        }

        return rgba;
    }
}
