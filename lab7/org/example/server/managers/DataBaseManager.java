package org.example.server.managers;

import org.example.common.spacemarine.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class DataBaseManager {
        private static final String URL = "jdbc:postgresql://pg:5432/studs";
        private static final String USER = "s466197";
        private static final String PASSWORD = "CTotAf9klDf0Kypw";

        private static Connection conn;

    public static void connect() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection is ready");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error while connecting to database");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

        public static HashMap<Long, SpaceMarine> getSpaceMarines() throws SQLException {
            HashMap<Long, SpaceMarine> collection = new HashMap<>();
            String getSpaceMarine = "SELECT * FROM SpaceMarines";
            PreparedStatement stmt = conn.prepareStatement(getSpaceMarine);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                        long id = rs.getLong("id");
                        String name = rs.getString("name");
                        int x = rs.getInt("x");
                        long y = rs.getLong("y");
                        LocalDateTime creationDate = LocalDateTime.parse(rs.getString("creationDate"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        double health = rs.getDouble("health");
                        String heartCountStr = rs.getString("heartCount");
                        Integer heartCount = (heartCountStr == null) ? null : Integer.parseInt(heartCountStr);
                        String achievements = rs.getString("achievements").isEmpty() ? null : rs.getString("achievements");
                        Weapon weaponType = rs.getString("weaponType").isEmpty() ? null : Weapon.valueOf(rs.getString("weaponType"));
                        String chapterName = rs.getString("chapterName");
                        String chapterWorld = rs.getString("chapterWorld");
                        String user = rs.getString("username");
                        SpaceMarine spaceMarine = new SpaceMarine(name, health, heartCount, achievements, new Coordinates(x,y), new Chapter(chapterName, chapterWorld), weaponType, id, creationDate, user);
                        collection.put(spaceMarine.getId(), spaceMarine);
                }
                stmt.close();
                return collection;
        }

        public static boolean insertSpaceMarine(SpaceMarine spaceMarine) { //добавляет объект в БД и возвращает сгенерированный id
            String insertSpaceMarine = "INSERT INTO SpaceMarines (id, name, x, y, creationDate, health, heartCount, achievements, weaponType, chapterName, chapterWorld, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement stmt = conn.prepareStatement(insertSpaceMarine, Statement.RETURN_GENERATED_KEYS);
                stmt.setLong(1, spaceMarine.getId());
                stmt.setString(2, spaceMarine.getName());
                stmt.setInt(3, spaceMarine.getCoordinates().getX());
                stmt.setLong(4, spaceMarine.getCoordinates().getY());
                stmt.setTimestamp(5, Timestamp.valueOf(spaceMarine.getCreationDate()));
                stmt.setDouble(6, spaceMarine.getHealth());
                stmt.setInt(7, spaceMarine.getHeartCount());
                stmt.setString(8, spaceMarine.getAchievements());
                stmt.setString(9, spaceMarine.getWeapontype().toString());
                if(spaceMarine.getChapter() == null){
                    stmt.setString(10, null);
                    stmt.setString(11, null);
                }else{
                    stmt.setString(10, spaceMarine.getChapter().getName());
                    stmt.setString(11, spaceMarine.getChapter().getWorld());
                }
                stmt.setString(12, spaceMarine.getUser());
                stmt.executeUpdate();

                stmt.close();
                return true;
            }catch (SQLException e){
                System.out.println("Ошибка при добавлении элемента:" + e.getMessage());
                return false;
            }
        }

        public static boolean removeSpaceMarineById(Long id, String user) {
            String removeById = "DELETE FROM SpaceMarines WHERE id = ? AND username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(removeById)) {
                        stmt.setLong(1, id);
                        stmt.setString(2, user);
                        int rowsAffected = stmt.executeUpdate();

                        return rowsAffected > 0;
                } catch (SQLException e) {
                        System.out.println("Ошибка при удалении продукта по id: " + e.getMessage());
                        return false;
                }
        }

        public static boolean insertUser(String login, String password){
            String insertUser = "INSERT INTO Users (login, password) VALUES (?,?)";
            try(PreparedStatement stmt = conn.prepareStatement(insertUser)){
                stmt.setString(1, login);
                stmt.setString(2, md5(password));
                stmt.executeUpdate();
            } catch (SQLException e) {
                    System.out.println("Ошибка при добавлении нового пользователя: " + e.getMessage());
            }
            return true;
        }

    public static boolean checkUserCredentials(String login, String rawPassword) {
        String hashed = md5(rawPassword);
        String usersByUsername = "SELECT * FROM Users WHERE login = ?";
        try (PreparedStatement stmt = conn.prepareStatement(usersByUsername)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password").equals(hashed);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : digest) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean clearCollection(String user){
        String clearCollection = "DELETE FROM SpaceMarines WHERE username = ?";
        try(PreparedStatement stmt = conn.prepareStatement(clearCollection)){
            stmt.setString(1, user);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        }catch(SQLException e){
            System.out.println("Ошибка при очищении коллекции: " + e.getMessage());
            return false;
        }
    }

    public static boolean replaceSpaceMarine(SpaceMarine oldMarine, SpaceMarine newMarine){
        String replaceSpaceMarine = "UPDATE space_marine name = ?, health = ?, heart_count = ?, achievements = ?, " +
                "x = ?, y = ?, chapterName = ?, chapterWorld = ?, " +
                "username = ?, weapon_type = ?, creation_date = ? WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(replaceSpaceMarine);
            stmt.setString(1, newMarine.getName());
            stmt.setDouble(2, newMarine.getHealth());
            stmt.setInt(3, newMarine.getHeartCount());
            stmt.setString(4, newMarine.getAchievements());
            stmt.setDouble(5, newMarine.getCoordinates().getX());
            stmt.setDouble(6, newMarine.getCoordinates().getY());
            stmt.setString(7, newMarine.getChapter().getName());
            stmt.setString(8, newMarine.getChapter().getWorld());
            stmt.setString(9, newMarine.getUser());
            stmt.setString(10, newMarine.getWeapontype().toString());
            stmt.setTimestamp(11, Timestamp.valueOf(oldMarine.getCreationDate()));
            stmt.setLong(12, oldMarine.getId());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        }catch (SQLException e){
            System.out.println("Ошибка при обновлении элемента: " + e.getMessage());
            return false;
        }
    }
}
