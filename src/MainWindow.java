import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.*;

// Esta classe é responsável pela janela que representa o Main
public class MainWindow {

    static JFrame janela;

    public static void mostrarJanela(Buffer buffer) {

        janela = new JFrame("Main");

        janela.getContentPane().setLayout(new FlowLayout());

        String[] colunas = { "Estado", "Modo", "Chave", "inserido", "Preço", "Tempo (s)" };

        Object[][] dados = { { "Estado", "Modo", "Chave", "Inserido", "Preco", "Tempo (s)" },
                { buffer.getEstado().toString(), buffer.getModo().toString(), buffer.getChave().toString(),
                        buffer.getAmmount(), buffer.getPrice(), buffer.getTimeToClean() / 1000 } };

        JTable tabela = new JTable(dados, colunas);

        janela.add(tabela);
        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setTitle("Main");
        janela.setSize(500, 100);
        janela.setLocation(300, 151);
        janela.setVisible(true);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void updateLabels(Buffer buffer) {
        janela.setVisible(false);

        janela = new JFrame("Main");

        janela.getContentPane().setLayout(new FlowLayout());

        String[] colunas = { "Estado", "Modo", "Chave", "inserido", "Preço", "Tempo (s)" };

        Object[][] dados = { { "Estado", "Modo", "Chave", "Inserido", "Preco", "Tempo (s)" },
                { buffer.getEstado().toString(), buffer.getModo().toString(), buffer.getChave().toString(),
                        buffer.getAmmount(), buffer.getPrice(), buffer.getTimeToClean() / 1000 } };

        JTable tabela = new JTable(dados, colunas);

        janela.add(tabela);
        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setTitle("Main");
        janela.setSize(500, 100);
        janela.setLocation(300, 151);
        janela.setVisible(true);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static JFrame getJanela() {
        return janela;
    }
}
