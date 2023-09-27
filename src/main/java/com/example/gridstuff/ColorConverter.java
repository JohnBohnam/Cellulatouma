package com.example.gridstuff;

import javafx.scene.paint.Color;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public interface ColorConverter<T> extends Function<T, Color> {
    private static double clip(double x) {
        return Math.min(1, Math.max(0, x));
    }
    static ColorConverter<Boolean> mapConverter(Map<Boolean, Color> colorMap) {
        return new ColorConverter<Boolean>() {
            Map<Boolean, Color> map = colorMap;

            @Override
            public Color apply(Boolean aBoolean) {
                return map.get(aBoolean);
            }
        };
    }

    ColorConverter<Double> continuousGrayConverter = new ColorConverter<Double>() {
        @Override
        public Color apply(Double aDouble) {
            return Color.gray(clip(aDouble));
        }
    };

    ColorConverter<Complex> complexRainbowConverter = new ColorConverter<Complex>() {
        @Override
        public Color apply(Complex complex) {
            //System.out.println(complex.getAngle());
            return Color.hsb(clip((2*complex.getAngle())/(Math.PI))*360, clip(Math.log(complex.modulus()+1)), clip(Math.log(complex.modulus()+1)));
        }
    };

    ColorConverter<Complex> complexGrayConverter = new ColorConverter<Complex>() {
        @Override
        public Color apply(Complex complex) {
            return Color.gray(Math.max(Math.min(complex.real(), 1), 0));
        }
    };
}
