import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.*;

public class MainWindow {

    static JFrame janela;

    public static void mostrarJanela(Buffer buffer) {

        janela = new JFrame("Main");

        janela.getContentPane().setLayout(new FlowLayout());

        String[] colunas = { "Estado", "Modo", "Chave", "inserido", "Preço", "Tempo de limpeza" };

        Object[][] dados = { { "Estado", "Modo", "Chave", "Inserido", "Preco", "Tempo" },
                { buffer.getEstado().toString(), buffer.getModo().toString(), buffer.getChave().toString(),
                        buffer.getAmmount(), buffer.getPrice(), buffer.getTimeToClean() } };

        JTable tabela = new JTable(dados, colunas);

        janela.add(tabela);
        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setTitle("Main");
        janela.setSize(500, 150);
        janela.setLocation(1200, 0);
        janela.setVisible(true);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void updateLabels(Buffer buffer) {
        janela.setVisible(false);

        janela = new JFrame("Main");

        janela.getContentPane().setLayout(new FlowLayout());

        String[] colunas = { "Estado", "Modo", "Chave", "Dinheiro inserido", "Preço", "Tempo de limpeza" };

        Object[][] dados = { { "Estado", "Modo", "Chave", "Inserido", "Preco", "Tempo" },
                { buffer.getEstado().toString(), buffer.getModo().toString(), buffer.getChave().toString(),
                        buffer.getAmmount(), buffer.getPrice(), buffer.getTimeToClean() } };

        JTable tabela = new JTable(dados, colunas);

        janela.add(tabela);
        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setTitle("Main");
        janela.setSize(500, 150);
        janela.setLocation(1200, 0);
        janela.setVisible(true);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static JFrame getJanela() {
        return janela;
    }
}
