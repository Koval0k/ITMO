package org.example.client;

import org.example.common.dto.*;
import org.example.client.managers.ClientCommandManager;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private final DatagramSocket datagramSocket = new DatagramSocket();
    Scanner scanner = new Scanner(System.in);
    int port;
    private String login;
    private String password;

    public Client(String host, int port) throws IOException {
        System.out.println("Подключение к серверу через порт " + port);
        this.port = port;
        datagramSocket.setSoTimeout(100000);
    }

    public void start() {
        try {
            authenticateUser();

            ClientCommandManager clientCmdManager = new ClientCommandManager(this);

            while (true) {
                System.out.print("Введите команду: ");
                String input = scanner.nextLine().trim();

                String[] commandParts = input.split(" ");
                String cmdName = commandParts[0].toLowerCase();

                if (input.isEmpty()) {
                    System.out.println("Ошибка: Команда не может быть пустой!");
                    System.out.println("Введите 'help' для получения справочника комманд");
                    continue;
                }

                if (clientCmdManager.hasCommand(cmdName)) {
                    clientCmdManager.executeCommand(cmdName, commandParts);
                    continue;
                }

                if (isProductCommand(cmdName) && commandParts.length < 2) {
                    System.out.print("Для команды необходим id. Пожалуйста введите id: ");
                    String idInput = scanner.nextLine().trim();
                    while (idInput.isEmpty()) {
                        System.out.print("Поле id не может быть пустым. Попробуйте снова: ");
                        idInput = scanner.nextLine().trim();
                    }
                    input += " " + idInput;
                    commandParts = input.split(" "); // обновим массив, если понадобится

                }

                Request request = new Request(input, login, password);

                if (isProductCommand(cmdName)) {
                    request.setProduct(SpaceMarineCreator.inputSpaceMarineFromScript(scanner, Long.parseLong(commandParts[1]), login));
                }

                int attempts = 3;
                boolean success = false;
                while (attempts > 0 && !success) {
                    try {
                        Response response = sendRequest(request);
                        System.out.println("Ответ сервера: " + response.getResponse());
                        success = true;
                    } catch (SocketTimeoutException e) {
                        attempts--;
                        System.out.println("Сервер не отвечает. Осталось попыток: " + attempts);
                        if (attempts == 0) {
                            System.out.println("Не удалось установить подключение. Проверьте работу сервера и попробуйте снова");
                        }
                    } catch (IOException e) {
                        System.out.println("Сетевая ошибка: " + e.getMessage());
                        break;
                    } catch (ClassNotFoundException e) {
                        System.out.println("Некорректный ответ сервера.");
                        break;
                    }
                }

            }
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }


    public Response sendRequest(Request request) throws IOException, ClassNotFoundException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(request);

        InetAddress serverAddress = InetAddress.getByName("localhost");
        DatagramPacket sendPacket = new DatagramPacket(baos.toByteArray(), baos.size(), serverAddress, port);
        datagramSocket.send(sendPacket);
        return getResponse();
    }

    public Response getResponse() throws IOException, ClassNotFoundException {
        byte[] buffer = new byte[4096];
        DatagramPacket inputPacket = new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(inputPacket);

        ObjectInputStream oi = new ObjectInputStream(new ByteArrayInputStream(inputPacket.getData()));
        Response response = (Response) oi.readObject(); // Читаем объект один раз

        return response; // прочитанный объект
    }

    private boolean isProductCommand(String cmdName) {
        return cmdName.equals("insert") ||
                cmdName.equals("replace_if_lowe") ||
                cmdName.equals("update");
    }

    private void authenticateUser(){
        while (true) {
            try {
                System.out.print("Do you want to login or register? (login/register): ");
                String choice = scanner.nextLine().trim().toLowerCase();

                if (!choice.equals("login") && !choice.equals("register")) {
                    System.out.println("Unknown option. Try again.");
                    continue;
                }
                System.out.print("Enter login: ");
                this.login = scanner.nextLine().trim();
                System.out.print("Enter password: ");
                this.password = scanner.nextLine().trim();

                Request request = new Request(choice, login, password);
                Response response = sendRequest(request);
                System.out.println("Server response: " + response.getResponse());

                if (response.getResponse().toLowerCase().contains("success")) {
                    break;
                } else {
                    System.out.println("Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error during authentication: " + e.getMessage());
            }
        }
    }

    public String getLogin(){
        return login;
    }
}
