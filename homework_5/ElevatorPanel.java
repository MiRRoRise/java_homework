package org.example;

import javax.swing.*;
import java.awt.*;

class ElevatorPanel extends JPanel {
    private final ElevatorSystem elevatorSystem;

    // Конструктор
    public ElevatorPanel(ElevatorSystem elevatorSystem) {
        this.elevatorSystem = elevatorSystem;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int elevatorWidth = 50;
        int elevatorHeight = 50;
        int numberOfFloors = 10;

        // Рисуем этажи
        for (int i = 0; i < numberOfFloors; i++) {
            g.drawLine(0, getHeight() - (i + 1) * (getHeight() / numberOfFloors), getWidth(), getHeight() - (i + 1) * (getHeight() / numberOfFloors));
            g.drawString("Floor " + (i + 1), 10, getHeight() - (i + 1) * (getHeight() / numberOfFloors) + 15);
        }

        // Рисуем лифты
        for (int i = 0; i < elevatorSystem.getElevators().size(); i++) {
            Elevator elevator = elevatorSystem.getElevators().get(i);
            int currentFloor = elevator.getCurrentFloor();
            int destinationFloor = elevator.getDestinationFloor();
            g.setColor(Color.PINK);
            g.fillRect((i + 1) * (getWidth() / 3) - elevatorWidth / 2, getHeight() - (currentFloor) * (getHeight() / numberOfFloors) - elevatorHeight, elevatorWidth, elevatorHeight);

            // Отображаем этаж назначения
            String floorText = String.valueOf(destinationFloor);

            FontMetrics metrics = g.getFontMetrics();
            int textWidth = metrics.stringWidth(floorText);
            int textHeight = metrics.getHeight();
            int textX = (i + 1) * (getWidth() / 3) - textWidth / 2; // Центрируем текст по ширине лифта
            int textY = getHeight() - (currentFloor) * (getHeight() / numberOfFloors) - elevatorHeight / 2 + textHeight / 2 - 40;

            g.setColor(Color.BLACK);
            g.drawString(floorText, textX, textY);
        }
    }

    // Перерисовка
    public void update() {
        repaint();
    }
}
