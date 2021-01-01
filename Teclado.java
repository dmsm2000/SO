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

        JButton botaoA = new JButton("Abrir porta");
        JButton botaoF = new JButton("Fechar porta");
        JButton botaoR = new JButton("Reiniciar");
        JButton botaoL = new JButton("Limpar casa de banho");
        JButton botaoC = new JButton("Cancelar");
        this.comChave = new JRadioButton("Com chave");

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
        janela.setSize(400, 150);
        janela.setLocation(400, 0);
        janela.setVisible(true);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "Abrir porta":
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
            case "Fechar porta":
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
            case "Limpar casa de banho":
                try {
                    buffer.setKey("L");
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
