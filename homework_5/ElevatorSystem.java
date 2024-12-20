package org.example;

import java.util.ArrayList;
import java.util.List;

class ElevatorSystem {
    private final List<Elevator> elevators;

    // Конструктор
    public ElevatorSystem(int numElevators) {
        elevators = new ArrayList<>();
        for (int i = 0; i < numElevators; i++) {
            Elevator elevator = new Elevator();
            elevators.add(elevator);
            new Thread(elevator).start(); // Запускаем поток для каждого лифта
        }
    }

    // Запрос лифта на определенный этаж
    public void requestElevator(int floor) {
        int nearestElevatorIndex = findElevator(floor);
        elevators.get(nearestElevatorIndex).requestElevator(floor);
    }

    // Поиск ближайшего лифта к заданному этажу
    private int findElevator(int floor) {
        int min = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < elevators.size(); i++) {
            Elevator elevator = elevators.get(i);
            int distance = Math.abs(elevator.getCurrentFloor() - floor);
            if (distance < min) {
                min = distance;
                index = i;
            }
        }
        return index;
    }

    // Получение списка лифтов
    public List<Elevator> getElevators() {
        return elevators;
    }
}
