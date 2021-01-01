import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JOptionPane;

public class Buffer {

    private float price;
    private int timeToClean;
    private Modos modo;
    private Estado estado;
    private float ammount;
    private boolean doorOpen;
    private Chave chave;
    private String key;
    private String doc;

    public Buffer(String name) throws IOException {
        this.doc = name;
        Path path = Paths.get(doc + ".txt");
        if (!Files.exists(path)) {
            System.err.println("ERRO: O ficheiro " + doc + ".txt" + " nao existe!!");
            throw new IOException();
        }
        List<String> linhas = Files.readAllLines(path);
        this.estado = Estado.Livre;
        this.modo = Modos.Usar;
        this.price = Float.parseFloat(linhas.get(0));
        this.timeToClean = (int) Math.ceil(Float.parseFloat(linhas.get(1))) * 1000;
        this.ammount = 0;
        this.doorOpen = false;
        this.chave = Chave.Neutra;
        this.key = "";
    }

    public Buffer() {
        this.estado = Estado.Livre;
        this.modo = Modos.Usar;
        this.price = (float) 5.0;
        this.timeToClean = 10000;
        this.ammount = 0;
        this.doorOpen = false;
        this.chave = Chave.Neutra;
        this.key = "";
    }

    public void setKey(String key) throws IOException {
        this.key = key;
        Log.writeLog(key + " pressionada", true);
    }

    public String getKey() {
        return this.key;
    }

    public void openOrCloseDoor() throws InterruptedException, IOException {
        this.doorOpen = doorOpen ? false : true;
        Log.writeLog(this.doorOpen ? "Abriu" : "Fechou" + " a porta", true);
    }

    public float getChange() throws InterruptedException, IOException {
        return this.getAmmount() - this.getPrice();
    }

    public void cleanMoedeiro() throws InterruptedException, IOException {
        this.setAmmount(0);
        Log.writeLog("Moedeiro limpo", true);
    }

    public Chave getChave() {
        return chave;
    }

    public void setChave(Chave chave) throws InterruptedException, IOException {
        this.chave = chave;
        Log.writeLog("Chave alterada para: " + this.chave, true);
    }

    public float getAmmount() {
        return ammount;
    }

    public void setAmmount(float ammount) throws IOException {
        this.ammount = ammount;
        Log.writeLog(String.valueOf(this.ammount) + " € inseridos", true);
    }

    public boolean isDoorOpen() {
        return doorOpen;
    }

    public Modos getModo() {
        return modo;
    }

    public void setModo(Modos modo) throws InterruptedException, IOException {
        this.modo = modo;
        Log.writeLog("Modo alterado para: " + this.modo, true);
    }

    public float getPrice() {
        return price;
    }

    public int getTimeToClean() {
        return timeToClean;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) throws InterruptedException, IOException {
        this.estado = estado;
        Log.writeLog("Estado alterado para: " + this.estado, true);
    }

    public void setDoorOpen(boolean b) throws IOException {
        this.doorOpen = b;
        Log.writeLog(this.doorOpen ? "Abriu" : "Fechou" + " a porta", true);
    }

    public void setTimeToClean(int i) {
        this.timeToClean = i;
    }

    private void setPrice(float price) {
        this.price = price;
    }

    public void clean() throws InterruptedException, IOException {
        if (this.ammount > 0) {
            this.cleanMoedeiro();
            JOptionPane.showMessageDialog(MainWindow.getJanela(), "Devolvendo dinheiro inserido...");
        }
        this.cleanMoedeiro();
        JOptionPane.showMessageDialog(MainWindow.getJanela(),
                "A desinfetar, aguarde " + String.valueOf(this.getTimeToClean() / 1000) + " segundos");
        Thread.sleep(this.getTimeToClean());
        JOptionPane.showMessageDialog(MainWindow.getJanela(), "Casa de banho pronta a usar!");
    }

    public void reset() throws InterruptedException, IOException {

        try {
            Path path = Paths.get(this.doc + ".txt");
            if (!Files.exists(path)) {
                System.err.println("ERRO: O ficheiro " + this.doc + ".txt" + " nao existe!!");
                throw new IOException();
            }

            List<String> linhas = Files.readAllLines(path);

            this.setPrice(Float.parseFloat(linhas.get(0)));
            this.setTimeToClean((int) Math.ceil(Float.parseFloat(linhas.get(1))));

        } catch (IOException e) {
            this.setPrice((float) 5.0);
            this.setTimeToClean(10000);
        }

        this.setEstado(Estado.Livre);
        this.setModo(Modos.Usar);
        this.cleanMoedeiro();
        this.setChave(Chave.Neutra);
        this.setKey("");

        Log.writeLog("Sistema reiniciado....", false);
    }

}
