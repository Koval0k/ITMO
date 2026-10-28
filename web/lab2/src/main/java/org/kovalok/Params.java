package org.kovalok;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

public class Params {
    private static final Pattern NUM = Pattern.compile("-?\\d{1,3}(\\.\\d{1,10})?");
    private static final Set<Double> YS = Set.of(-3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0);
    private static final Set<Double> RS = Set.of(1.0, 1.5, 2.0, 2.5, 3.0);

    private final double x, y, r;

    public Params(String query) {
        if (query == null || query.isEmpty()) throw new ValidationException("missing query string");
        Map<String, String> m = split(query);
        x = number(m, "x");
        y = number(m, "y");
        r = number(m, "r");
        if (x < -5 || x > 3)     throw new ValidationException("x is out of range");
        if (!YS.contains(y))     throw new ValidationException("y is not allowed");
        if (!RS.contains(r))     throw new ValidationException("r is not allowed");
    }

    private static double number(Map<String, String> m, String key) {
        String v = m.get(key);
        if (v == null || v.isBlank()) throw new ValidationException(key + " is missing");
        if (!NUM.matcher(v).matches()) throw new ValidationException(key + " is not a number");
        return Double.parseDouble(v);
    }

    private static Map<String, String> split(String query) {
        Map<String, String> map = new HashMap<>();
        for (String pair : query.split("&")) {
            int i = pair.indexOf('=');
            if (i <= 0) continue;
            try {
                map.put(URLDecoder.decode(pair.substring(0, i), StandardCharsets.UTF_8),
                        URLDecoder.decode(pair.substring(i + 1), StandardCharsets.UTF_8));
            } catch (IllegalArgumentException e) {
                throw new ValidationException("bad url encoding");
            }
        }
        return map;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getR() { return r; }
}