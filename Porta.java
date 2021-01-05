import java.awt.*;
import java.io.IOException;

import javax.swing.*;

import java.util.concurrent.Semaphore;

public class Porta implements Runnable {

    private JFrame janela;
    private Semaphore semMP;
    private Buffer buffer;
    private JLabel label;

    public Porta(Semaphore semMP, Buffer buffer) {
        this.semMP = semMP;
        this.buffer = buffer;
    }

    public void mostraJanela() {
        janela = new JFrame("Porta");
        janela.getContentPane().setLayout(new FlowLayout());

        this.label = new JLabel("Fechada");

        janela.add(this.label);

        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setTitle("Porta");
        janela.setSize(300, 150);
        janela.setLocation(0, 151);
        janela.setVisible(true);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void openOrCloseDoor() throws InterruptedException, IOException {
        this.buffer.openOrCloseDoor();
        this.label.setText(buffer.isDoorOpen() ? "Aberta" : "Fechada");
    }

    @Override
    public void run() {
        this.mostraJanela();
    }

}
