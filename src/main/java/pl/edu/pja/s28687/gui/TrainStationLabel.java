package pl.edu.pja.s28687.gui;

import pl.edu.pja.s28687.TrainStation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TrainStationLabel extends  JLabel implements MouseListener {
    private int x;
    private int y;
    public TrainStationLabel(TrainStation ts){
        initialize(ts);
    }

    private void initialize(TrainStation ts){
        this.x = (int) ts.getCoordinates().getX();
        this.y = (int) ts.getCoordinates().getY();
        setBackground(new Color(206, 206, 206, 125));
        this.setOpaque(false);
        this.setFont(new Font("Arial", Font.BOLD, 10));
        this.setText(ts.toString());
        setBounds(x - 2,
                y - 2,
                5,
                5);
//        setVisible(true);
        setToolTipText(ts.toString());
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        setVisible(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setVisible(false);
    }
}
