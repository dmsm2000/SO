import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

// Classe responsável por escrever os logs do programa.

public class Log {

    public static void writeLog(String msg, boolean opc) throws IOException {
        File file = new File("./", "logs.txt");
        try (FileWriter logs = new FileWriter(file, opc)) {
            logs.write(new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis())) + "/"
                    + new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())) + " - " + msg
                    + "\n");
        }
    }
}
