package at.searles.colorview.utils;

/**
 * special color model with yellow as own component.
 */
public class RybColors {
    private static final float[][] keyColors = {
            new float[]{1.00f, 0.00f, 0.00f}, // * Red
            new float[]{1.00f, 0.25f, 0.00f}, // *** Vermilion
            new float[]{1.00f, 0.50f, 0.00f}, // ** Orange
            new float[]{1.00f, 0.75f, 0.00f}, // *** Amber
            new float[]{1.00f, 1.00f, 0.00f}, // * Yellow
            new float[]{0.50f, 1.00f, 0.00f}, // *** Chartreuse
            new float[]{0.00f, 1.00f, 0.00f}, // ** Green
            new float[]{0.00f, 1.00f, 1.00f}, // *** Aqua
            new float[]{0.00f, 0.00f, 1.00f}, // * Blue
            new float[]{0.50f, 0.00f, 1.00f}, // *** Violet
            new float[]{1.00f, 0.00f, 1.00f}, // ** Purple
            new float[]{1.00f, 0.00f, 0.50f} // *** Magenta
    };

    public static int colorSegmentsCount() {
        return keyColors.length;
    }

    public static float red(float hue) {
        int i0 = (int) (hue * keyColors.length);
        float fraction = hue * keyColors.length - i0;

        int i1 = (i0 + 1) % keyColors.length;

        return keyColors[i0][0] * (1 - fraction) + keyColors[i1][0] * fraction;
    }

    public static float green(float hue) {
        int i0 = (int) (hue * keyColors.length);
        float fraction = hue * keyColors.length - i0;

        int i1 = (i0 + 1) % keyColors.length;

        return keyColors[i0][1] * (1 - fraction) + keyColors[i1][1] * fraction;
    }

    public static float blue(float hue) {
        int i0 = (int) (hue * keyColors.length);
        float fraction = hue * keyColors.length - i0;

        int i1 = (i0 + 1) % keyColors.length;

        return keyColors[i0][2] * (1 - fraction) + keyColors[i1][2] * fraction;
    }

    public static int color(float hue) {
        int i0 = ((int) (hue * keyColors.length));
        float fraction = hue * keyColors.length - i0;

        i0 = i0 % keyColors.length;
        int i1 = (i0 + 1) % keyColors.length;

        return RgbCommons.rgb2int(
                keyColors[i0][0] * (1 - fraction) + keyColors[i1][0] * fraction,
                keyColors[i0][1] * (1 - fraction) + keyColors[i1][1] * fraction,
                keyColors[i0][2] * (1 - fraction) + keyColors[i1][2] * fraction
        );
    }

    public static float hue(float r, float g, float b) {
        float max = Math.max(r, Math.max(g, b));
        float min = Math.min(r, Math.min(g, b));

        if(max - min == 0) {
            // gray
            return 0f;
        }

        // increase contrast
        r = (r - min) / (max - min);
        g = (g - min) / (max - min);
        b = (b - min) / (max - min);

        assert (r == 1f || g == 1f || b == 1f) && (r == 0f || g == 0f || b == 0f);

        // assert one is 1 and another one is 0.

        int i0 = 0;
        int i1;

        for(;;) {
            i1 = (i0 + 1) % keyColors.length;

            if(inRange(r, keyColors[i0][0], keyColors[i1][0]) && inRange(g, keyColors[i0][1], keyColors[i1][1]) && inRange(b, keyColors[i0][2], keyColors[i1][2])) {
                break;
            }

            if(++i0 >= keyColors.length) {
                throw new IllegalArgumentException();
            }
        }

        float fraction = Math.max(Math.max(
                rangeFraction(r, keyColors[i0][0], keyColors[i1][0]),
                rangeFraction(g, keyColors[i0][1], keyColors[i1][1])),
                rangeFraction(b, keyColors[i0][2], keyColors[i1][2]));

        return (i0 + fraction) / keyColors.length;
    }

    private static boolean inRange(float x, float a, float b) {
        // Convenience from at.searles.math
        if(b < a) {
            float t = b;
            b = a;
            a = t;
        }

        return a <= x && x <= b;
    }

    private static float rangeFraction(float x, float a, float b) {
        // Convenience from at.searles.math
        if(b - a == 0) {
            return 0.f;
        }

        return (x - a) / (b - a);
    }

}
