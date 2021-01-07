import java.awt.*;
import java.io.IOException;

import javax.swing.*;

import java.util.concurrent.Semaphore;

public class Porta implements Runnable {

    private JFrame janela;
    private Semaphore semMP;
    private Buffer buffer;
    private JLabel gif;

    public Porta(Semaphore semMP, Buffer buffer) {
        this.semMP = semMP;
        this.buffer = buffer;
        this.gif = new JLabel();
    }

    public void mostraJanela() throws InterruptedException {
        janela = new JFrame("Porta");
        janela.getContentPane().setLayout(new FlowLayout());
        gif.setIcon(new ImageIcon(getClass().getResource("img/portaFechada.png")));

        janela.add(gif);

        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setTitle("Porta");
        janela.setSize(300, 300);
        janela.setLocation(0, 151);
        janela.setVisible(true);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void openOrCloseDoor() throws InterruptedException, IOException {
        this.buffer.openOrCloseDoor();
        this.gif.setIcon(new ImageIcon(
                getClass().getResource(buffer.isDoorOpen() ? "img/portaAberta.png" : "img/portaFechada.png")));
    }

    @Override
    public void run() {
        try {
            this.mostraJanela();
        } catch (InterruptedException e1) {
        }

        while (true) {
            try {
                semMP.acquire();
                this.openOrCloseDoor();
            } catch (InterruptedException | IOException e) {
            }
        }

    }

}
