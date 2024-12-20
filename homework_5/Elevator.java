package org.example;

import java.util.LinkedList;
import java.util.Queue;

class Elevator implements Runnable {
    private int currentFloor = -1;
    private int destinationFloor;
    private final Queue<Integer> requests = new LinkedList<>();
    private final Object lock = new Object();

    // Добавление запроса на вызов лифта
    public void requestElevator(int floor) {
        synchronized (lock) {
            // Проверка существования запроса
            if (!requests.contains(floor)) {
                requests.add(floor);
                lock.notify();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            Integer destination;

            synchronized (lock) {
                // Ожидаем запрос
                while (requests.isEmpty()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Поток прерван " + e.getMessage());
                        return;
                    }
                }
                // Извлекаем следующий запрос из очереди
                destination = requests.poll();
            }
            if (destination != null) {
                moveToFloor(destination);
            }
        }
    }

    // Перемещаемся к заданному этажу
    private void moveToFloor(int destination) {
        destinationFloor = destination;
        boolean movingUp = destination > currentFloor;
        destination = destination - 1;

        while (currentFloor != destination) {
            // Проверяем наличие попутного вызова на текущем этаже
            checkRequest();

            // Перемещение лифта по этажам
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Поток прерван " + e.getMessage());
                return;
            }
            if (movingUp) {
                currentFloor++;
            } else {
                currentFloor--;
            }
        }
    }

    // Метод для проверки и обслуживания попутных вызовов
    private void checkRequest() {
        synchronized (lock) {
            // Проверка наличия запросов на текущем этаже
            if (requests.contains(currentFloor + 1)) {
                System.out.println("Лифт останавливается на этаже: " + (currentFloor + 1));
                requests.remove(currentFloor + 1);
            }
        }
    }

    // Возвращает текущий этаж
    public int getCurrentFloor() {
        return currentFloor + 1;
    }

    // Возвращает этаж назначения
    public int getDestinationFloor() {
        return destinationFloor + 1;
    }
}