package org.example;

import javax.swing.*;
import java.awt.*;
import javax.swing.SwingUtilities;

public class ElevatorGUI extends JFrame {
    private final ElevatorSystem elevatorSystem;
    private final ElevatorPanel elevatorPanel;

    // Конструктор
    public ElevatorGUI() {
        // Создаем 2 лифта
        elevatorSystem = new ElevatorSystem(2);
        elevatorPanel = new ElevatorPanel(elevatorSystem);

        setTitle("Elevator Control System");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(elevatorPanel, BorderLayout.CENTER);

        Timer timer = new Timer(100, _ -> elevatorPanel.update());
        timer.start();

        // Запускаем поток для генерации случайных запросов
        new Thread(() -> {
            while (true) {
                int floor = (int) (Math.random() * 10);
                elevatorSystem.requestElevator(floor);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("Поток прерван " + e.getMessage());
                    return;
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ElevatorGUI gui = new ElevatorGUI();
            gui.setVisible(true);
        });
    }
}
