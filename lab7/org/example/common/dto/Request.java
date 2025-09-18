package org.example.common.dto;

import org.example.common.spacemarine.SpaceMarine;
import java.io.Serializable;
import java.util.*;

public class Request implements Serializable {
    private String message;
    private SpaceMarine spaceMarine;
    private String login;
    private String password;

    public Request(String message) {
        this.message = message;
    }

    public Request(String message, String login, String password) {
        this.message = message;
        this.login = login;
        this.password = password;
    }

    public String getMessage() {
        return message;
    }
    public SpaceMarine getProduct() {
        return spaceMarine;
    }
    public void setProduct(SpaceMarine spaceMarine) {
        this.spaceMarine = spaceMarine;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(message, request.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        return "Request{" +
                "message='" + message + '\'' +
                '}';
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}