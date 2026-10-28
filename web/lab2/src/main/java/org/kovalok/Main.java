package org.kovalok;

import com.fastcgi.FCGIInterface;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        var fcgi = new FCGIInterface();
        while (fcgi.FCGIaccept() >= 0) {
            long start = System.nanoTime();
            try {
                var p = new Params(System.getProperty("QUERY_STRING"));
                boolean hit = calculate(p.getX(), p.getY(), p.getR());
                long elapsed = System.nanoTime() - start;
                send(200, "OK", "{\"now\":%d,\"time\":%d,\"result\":%b}"
                        .formatted(System.currentTimeMillis(), elapsed, hit));
            } catch (ValidationException e) {
                send(400, "Bad Request", "{\"now\":%d,\"reason\":\"%s\"}"
                        .formatted(System.currentTimeMillis(), e.getMessage()));
            } catch (Exception e) {
                send(500, "Internal Server Error", "{\"now\":%d,\"reason\":\"internal error\"}"
                        .formatted(System.currentTimeMillis()));
            }
        }
    }

    private static void send(int code, String reason, String json) {
        byte[] body = json.getBytes(StandardCharsets.UTF_8);
        byte[] head = ("HTTP/1.1 " + code + " " + reason + "\r\n"
                + "Content-Type: application/json; charset=UTF-8\r\n"
                + "Content-Length: " + body.length + "\r\n"
                + "Connection: close\r\n\r\n")
                .getBytes(StandardCharsets.UTF_8);
        System.out.write(head, 0, head.length);
        System.out.write(body, 0, body.length);
        System.out.flush();
    }

    private static boolean calculate(double x, double y, double r) {
        if (x > 0 && y > 0) return false;                       // 1-я четверть
        if (x <= 0 && y >= 0) return x * x + y * y <= r * r;    // 2-я: четверть круга
        if (x <= 0 && y < 0)  return y >= -0.5 * x - 0.5 * r;   // 3-я: треугольник
        return x <= r / 2 && y >= -r;                           // 4-я: прямоугольник
    }
}