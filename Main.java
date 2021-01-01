import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        Semaphore semMT = new Semaphore(0);
        Semaphore semMM = new Semaphore(1);
        Semaphore semMP = new Semaphore(1);
        Buffer buffer = new Buffer("config");

        Teclado teclado = new Teclado(semMT, buffer);
        Porta porta = new Porta(semMT, buffer);
        Moedeiro moedeiro = new Moedeiro(semMM, buffer);

        Thread tt = new Thread(teclado);
        Thread tp = new Thread(porta);
        Thread tm = new Thread(moedeiro);

        tt.start();
        tp.start();
        tm.start();

        MainWindow.mostrarJanela(buffer);

        Log.writeLog("Sistema iniciou", false);

        while (true) {
            semMT.acquire();

            String botao = buffer.getKey();

            switch (botao) {
                case "A": {
                    if (buffer.getAmmount() >= buffer.getPrice() && buffer.getModo() == Modos.Usar
                            && buffer.getEstado() == Estado.Livre && buffer.getChave() == Chave.Neutra) {

                        porta.openOrCloseDoor();

                        String tempKey = "";

                        while (!"F".equals(tempKey) && !"C".equals(tempKey)) {
                            semMT.acquire();
                            tempKey = buffer.getKey();
                        }

                        if ("F".equals(tempKey)) {

                            JOptionPane.showMessageDialog(MainWindow.getJanela(),
                                    "Devolvendo: " + String.valueOf(moedeiro.calculateChange()));

                            moedeiro.cleanMoedeiro();
                            porta.openOrCloseDoor();
                            buffer.setEstado(Estado.Ocupada);

                            MainWindow.updateLabels(buffer);

                            while (!"A".equals(tempKey)) {
                                semMT.acquire();
                                tempKey = buffer.getKey();
                            }

                            porta.openOrCloseDoor();
                            JOptionPane.showMessageDialog(MainWindow.getJanela(), "5 segundos ate a porta fechar...");
                            Thread.sleep(5000);
                            porta.openOrCloseDoor();
                            buffer.setEstado(Estado.Ocupada);
                            buffer.setModo(Modos.Desinfetar);
                            MainWindow.updateLabels(buffer);
                            buffer.clean();
                            buffer.setModo(Modos.Usar);
                            buffer.setEstado(Estado.Livre);
                            MainWindow.updateLabels(buffer);

                        } else if ("C".equals(tempKey)) {
                            porta.openOrCloseDoor();
                            moedeiro.cleanMoedeiro();
                            JOptionPane.showMessageDialog(MainWindow.getJanela(),
                                    "A devolver o dinheiro, operacao cancelada...");
                            MainWindow.updateLabels(buffer);
                        }

                    } else {
                        if (buffer.getAmmount() < buffer.getPrice()) {
                            JOptionPane.showMessageDialog(MainWindow.getJanela(),
                                    "Dinheiro insuficiente, devolvendo dinheiro.");
                        } else if (buffer.getModo() != Modos.Usar) {
                            JOptionPane.showMessageDialog(MainWindow.getJanela(),
                                    "Casa de banho nao esta em modo de uso, devolvendo dinheiro.");
                        } else if (buffer.getEstado() != Estado.Livre) {
                            JOptionPane.showMessageDialog(MainWindow.getJanela(), "Ocupada, devolvendo dinheiro.");
                        } else if (buffer.getChave() != Chave.Neutra) {
                            JOptionPane.showMessageDialog(MainWindow.getJanela(),
                                    "Em manutencao, devolvendo dinheiro.");
                        }
                        moedeiro.cleanMoedeiro();
                        MainWindow.updateLabels(buffer);
                    }
                    break;
                }

                case "a": {
                    if (!buffer.isDoorOpen()) {
                        buffer.setChave(Chave.Aberta);
                        buffer.setEstado(Estado.Ocupada);
                        buffer.setModo(Modos.Manutencao);
                        moedeiro.cleanMoedeiro();
                        porta.openOrCloseDoor();
                        MainWindow.updateLabels(buffer);
                    }
                    break;
                }
                case "f": {
                    if (buffer.isDoorOpen()) {
                        buffer.setChave(Chave.Fechada);
                        buffer.setEstado(Estado.Ocupada);
                        buffer.setModo(Modos.Manutencao);
                        moedeiro.cleanMoedeiro();
                        porta.openOrCloseDoor();
                        MainWindow.updateLabels(buffer);
                    }
                    break;
                }

                case "R": {
                    if (buffer.isDoorOpen() && buffer.getModo() != Modos.Manutencao) {
                        JOptionPane.showMessageDialog(MainWindow.getJanela(), "Cancele a operacao em curso....");
                    } else {
                        buffer.reset();
                        if (buffer.isDoorOpen()) {
                            porta.openOrCloseDoor();
                        }
                        MainWindow.updateLabels(buffer);
                        JOptionPane.showMessageDialog(MainWindow.getJanela(),
                                "O sistema foi reiniciado, pronto para utilizacao.");
                    }
                    break;
                }

                case "L": {
                    if (buffer.getEstado() == Estado.Livre && buffer.getModo() == Modos.Usar && !buffer.isDoorOpen()) {
                        buffer.setEstado(Estado.Ocupada);
                        buffer.setModo(Modos.Desinfetar);
                        MainWindow.updateLabels(buffer);
                        buffer.clean();
                        buffer.setModo(Modos.Usar);
                        buffer.setEstado(Estado.Livre);
                        MainWindow.updateLabels(buffer);
                    } else {
                        JOptionPane.showMessageDialog(MainWindow.getJanela(), "Impossivel iniciar a limpeza.");
                    }
                    break;
                }

                case "C": {
                    if (buffer.getAmmount() > 0) {
                        if (buffer.isDoorOpen()) {
                            porta.openOrCloseDoor();
                        }
                        moedeiro.cleanMoedeiro();
                        JOptionPane.showMessageDialog(MainWindow.getJanela(),
                                "Operacao cancelada, dinheiro devolvido.");
                    }
                    MainWindow.updateLabels(buffer);
                    break;
                }
            }
        }
    }
}
