import java.io.*;

public class JavaPythonCommunication {
    public static void main(String[] args) {
        try {
            // Inicia o processo Python
            // usei "where python" para achar o path
            ProcessBuilder pb = new ProcessBuilder("C:\\Users\\barbi\\AppData\\Local\\Programs\\Python\\Python313\\python.exe", "script.py");
            Process process = pb.start();
            

            // Streams para comunicação
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String userCommand;

            System.out.println("Digite um comando (ou 'sair' para encerrar):");

            while (true) {
                // Recebe o comando do usuário
                System.out.print("Comando: ");
                userCommand = userInput.readLine();

                // Envia o comando ao Python
                writer.write(userCommand);
                writer.newLine();
                writer.flush(); // Garante que o comando seja enviado imediatamente

                // Lê a resposta do Python
                String pythonResponse = reader.readLine();
                if (pythonResponse == null) {
                    System.out.println("O processo Python foi encerrado.");
                    break;
                }

                System.out.println("Python respondeu: " + pythonResponse);

                if ("sair".equalsIgnoreCase(userCommand)) {
                    // Envia comando de encerramento ao Python
                    writer.write(userCommand);
                    writer.newLine();
                    writer.flush();
                    break;
                }
            }

            // Fecha os streams e o processo
            writer.close();
            reader.close();
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}