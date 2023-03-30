package pl.edu.pja.s28687.gui;

import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.logistics.*;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class TrainSetLabel extends JTextArea {
    private Locomotive loc;
    public TrainSetLabel(Locomotive loc) {
        this.loc = loc;
        initialize();
//        super(text);
    }

    private void initialize(){

        this.setBackground(new Color(206, 206, 206, 125));
        this.setFont(new Font("Arial", Font.BOLD, 10));
        this.setText(labelText());
//        this.setBounds(0, 0, 1400, 200);
        this.setSize(600, 200);
        this.setAlignmentX(LEFT_ALIGNMENT);
//        this.setLayout(null);
        this.setVisible(true);
        this.setOpaque(false);
    }

    private String labelText(){
        String s = new StringBuilder()
                .append("Name: ")
                .append(this.loc.getLocName())
                .append(" ID: ")
                .append(this.loc.getId())
                .append("\nSource : ")
                .append(this.loc.getSourceStation())
                .append(" - ")
                .append(this.loc.getDestStation())
                .append("\nSpeed : ")
                .append(this.loc.getCurrentSpeed())
                .append(" km/h")
                .append("\nTrip :")
                .append(loc.getRoad().size()).append(" stops !")
                .append(loc.getRoad().stream().map(RailroadLink::getDistance).reduce(BigDecimal::add).orElse(BigDecimal.ZERO))
                .append(" KM distance")
//                .append(loc.getRoad())
                .append("\nCurrent segment : ")
                .append(loc.getCurrentSegment())
                .append("\nCurrent station : ")
                .append(loc.getLastTrainStation())
                .append("\nTrip progress % : ")
                .append(loc.getCurrentTripProgress())
                .toString();
        return s;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        setText(labelText());
//        this.setSize(600, 200);
        this.setAlignmentX(LEFT_ALIGNMENT);
    }
}
