import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import java.util.concurrent.Semaphore;

public class Teclado implements ActionListener, Runnable {
    private JFrame janela;
    private Semaphore semMT;
    private Buffer buffer;
    private JRadioButton comChave;

    public Teclado(Semaphore semMT, Buffer buffer) {
        this.semMT = semMT;
        this.buffer = buffer;
    }

    public void mostraJanela() {
        janela = new JFrame("Teclado");
        janela.getContentPane().setLayout(new FlowLayout());

        JButton botaoA = new JButton("Abrir Porta");
        JButton botaoF = new JButton("Fechar Porta");
        JButton botaoR = new JButton("Reiniciar");
        JButton botaoL = new JButton("Limpar Casa de Banho");
        JButton botaoC = new JButton("Cancelar");
        this.comChave = new JRadioButton("Com Chave");

        botaoA.addActionListener(this);
        botaoF.addActionListener(this);
        botaoR.addActionListener(this);
        botaoL.addActionListener(this);
        botaoC.addActionListener(this);

        janela.add(botaoA);
        janela.add(botaoF);
        janela.add(botaoR);
        janela.add(botaoL);
        janela.add(botaoC);
        janela.add(comChave);

        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setTitle("Teclado");
        janela.setSize(500, 150);
        janela.setLocation(300, 0);
        janela.setVisible(true);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "Abrir Porta":
                if (!comChave.isSelected()) {
                    try {
                        buffer.setKey("A");
                    } catch (IOException e1) {
                    }
                } else {
                    try {
                        buffer.setKey("a");
                    } catch (IOException e1) {
                    }
                }
                break;
            case "Fechar Porta":
                if (!comChave.isSelected()) {
                    try {
                        buffer.setKey("F");
                    } catch (IOException e1) {
                    }
                } else {
                    try {
                        buffer.setKey("f");
                    } catch (IOException e1) {
                    }
                }
                break;
            case "Reiniciar":
                try {
                    buffer.setKey("R");
                } catch (IOException e1) {
                }
                if (this.comChave.isSelected()) {
                    this.comChave.doClick();
                }
                break;
            case "Limpar Casa de Banho":
                try {
                    if (!comChave.isSelected()) {
                        buffer.setKey("L");
                    }
                } catch (IOException e1) {
                }
                break;
            case "Cancelar":
                try {
                    buffer.setKey("C");
                } catch (IOException e1) {
                }
                break;
        }

        semMT.release();
    }

    @Override
    public void run() {
        this.mostraJanela();
    }
}
