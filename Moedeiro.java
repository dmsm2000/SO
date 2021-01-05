import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import java.util.concurrent.Semaphore;

public class Moedeiro implements ActionListener, Runnable {

    private JFrame janela;
    private Semaphore semMM;
    private Buffer buffer;
    private float tempAmmount;
    private JLabel inserido;

    public Moedeiro(Semaphore semMM, Buffer buffer) {
        this.semMM = semMM;
        this.buffer = buffer;
    }

    public void mostraJanela() {
        janela = new JFrame("Moedeiro");
        janela.getContentPane().setLayout(new FlowLayout());

        JButton botao1 = new JButton("1");
        JButton botao5 = new JButton("5");
        JButton botao10 = new JButton("10");
        JButton botaoOK = new JButton("OK");
        JButton botaoClear = new JButton("Limpar");
        this.inserido = new JLabel("Inserido: ");

        botao1.addActionListener(this);
        botao5.addActionListener(this);
        botao10.addActionListener(this);
        botaoOK.addActionListener(this);
        botaoClear.addActionListener(this);

        janela.add(botao1);
        janela.add(botao5);
        janela.add(botao10);
        janela.add(botaoOK);
        janela.add(botaoClear);
        janela.add(this.inserido);

        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setTitle("Moedeiro");
        janela.setSize(300, 150);
        janela.setLocation(0, 0);
        janela.setVisible(true);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "1":
                this.tempAmmount += 1;
                this.inserido.setText("Inserido: " + this.tempAmmount);
                break;
            case "5":
                this.tempAmmount += 5;
                this.inserido.setText("Inserido: " + this.tempAmmount);
                break;
            case "10":
                this.tempAmmount += 10;
                this.inserido.setText("Inserido: " + this.tempAmmount);
                break;
            case "OK":
                if (buffer.getEstado() == Estado.Livre && !buffer.isDoorOpen() && buffer.getModo() == Modos.Usar) {
                    try {
                        this.buffer.setAmmount(tempAmmount);
                        semMM.release();
                    } catch (IOException e1) {
                    }
                    this.tempAmmount = 0;
                    this.inserido.setText("Inserido: " + this.tempAmmount);
                    MainWindow.updateLabels(buffer);
                }
                break;
            case "Limpar":
                this.tempAmmount = 0;
                this.inserido.setText("Inserido: " + this.tempAmmount);
                break;
        }

    }

    public float calculateChange() throws InterruptedException, IOException {
        float change = this.buffer.getChange();
        return change;
    }

    public void cleanMoedeiro() throws InterruptedException, IOException {
        buffer.cleanMoedeiro();
    }

    @Override
    public void run() {
        this.mostraJanela();
    }

}