package src.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.regex.MatchResult;

public class Command {

    private static final String RE_N_SPACE = "\\s+";

    public static final String RE_INTEGER = "(?<=\\s|^)[+-]?[0-9]+(?=\\s|$)";
    public static final String RE_DOUBLE = "(?<=\\s|^)[+-]?([0-9]+(\\.[0-9]+)|\\.[0-9]+)(?=\\s|$)";
    public static final String RE_BOOLEAN = "(?<=\\\\s|^)(?i)(true|false)(?=\\\\s|$)";
    public static final String RE_STRING = "(?<=\\s|^)(?!\\b(DOUBLE|INTEGER|BOOL|STRING)\\b)\\b\\w+\\b(?=\\s|$)";

    private static JSONObject memory;

    private static final Path FOLDER_PATH = Paths.get("C:\\Users\\barbi\\OneDrive\\Documentos\\GitHub\\Interop\\3 - 夢\\data\\memory");
    private static final Path ARCHIVE_PATH = Paths.get("C:\\Users\\barbi\\OneDrive\\Documentos\\GitHub\\Interop\\3 - 夢\\data\\memory\\memory.json");

    @SuppressWarnings("unchecked")
    private static void createMemory(){
        memory = new JSONObject();

        JSONArray functions = new JSONArray();

        JSONObject exit = new JSONObject();
        exit.put("Name", "exit");
        exit.put("Code", 0);
        exit.put("Path", "<BASIC>");

        functions.add(exit);

        JSONArray commands = new JSONArray();

        JSONObject dExit = new JSONObject();
        dExit.put("Code", 0);
        dExit.put("Text", "\\exit");

        JSONObject eExit = new JSONObject();
        eExit.put("Code", 0);
        eExit.put("Text", "sair");

        commands.add(dExit);
        commands.add(eExit);

        memory.put("Functions", functions);
        memory.put("Commands", commands);

        try {
            Files.writeString(ARCHIVE_PATH, memory.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadMemory(){
        if (!Files.exists(ARCHIVE_PATH)) {
            createMemory();
        }
        try {
            JSONParser json_parser = new JSONParser();
            memory = (JSONObject) json_parser.parse(Files.readString(ARCHIVE_PATH));
        } catch (IOException | ParseException e) {
        }
    }

    public static void read(String string){
        string = string.replaceAll(RE_N_SPACE, " ").trim();
        String compiledString = compile(string);
        System.out.println("Recebido: " + string);
        System.out.println("Compilada: " + compiledString);

        String[] splittedString = string.split(" ");
        String[] splittedCompiled = compiledString.split(" ");
        answer(string);
    }

    public static String compile(String string){
        String compiled = string.replaceAll(RE_DOUBLE, "<DOUBLE>");
        compiled = compiled.replaceAll(RE_INTEGER, "<INTEGER>");
        compiled = compiled.replaceAll(RE_BOOLEAN, "<BOOL>");
        compiled = compiled.replaceAll(RE_STRING, "<STRING>");

        return compiled;
    }

    public static void answer(String string){
        JSONArray commands = (JSONArray) memory.get("Commands");
        for (Object command : commands) {
            JSONObject cmd = (JSONObject) command;
            if(cmd.containsValue(string)){
                executeCommand(Integer.valueOf(cmd.get("Code").toString()));
            }
        }
    }

    public static void executeCommand(int code){
        JSONArray functions = (JSONArray) memory.get("Functions");
        for (Object function : functions) {
            JSONObject func = (JSONObject) function;
            if(Integer.valueOf(func.get("Code").toString()) == code){
                String str = (String) func.get("Name");
                String result = str.substring(0, 1).toUpperCase() + str.substring(1);
                run(result);
            }
        }
    }

    public static void compile(String className, String sourceCode){
        try {
            // Caminho da pasta de destino nos Documentos
            // String userHome = System.getProperty("user.home");
            // String documentosPath = userHome + File.separator + "Documentos" + File.separator + "MeuProjeto";
            String documentosPath = "C:\\Users\\barbi\\OneDrive\\Documentos\\GitHub\\Interop\\3 - 夢\\data\\commands";
            File documentosDir = new File(documentosPath);
    
            // Cria o diretório se ele não existir
            if (!documentosDir.exists()) {
                if (!documentosDir.mkdirs()) {
                    System.err.println("Falha ao criar o diretório em: " + documentosPath);
                    return;
                }
            }
    
            // Caminho para o arquivo Exit.java
            File javaFile = new File(documentosDir, className + ".java");
    
            // Código-fonte da classe Exit
            // String sourceCode = """
            //     public class Exit {
            //         public static void exit() {
            //             System.out.println("Encerrando o programa...");
            //             System.exit(0);
            //         }
            //     }
            //     """;
    
            // Escreve o código-fonte no arquivo Exit.java
            try (FileWriter writer = new FileWriter(javaFile)) {
                writer.write(sourceCode);
            }
    
            // Obtém o compilador do Java
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if (compiler == null) {
                System.err.println("Compilador Java não disponível. Certifique-se de estar usando um JDK.");
                return;
            }
    
            // Compila o arquivo Exit.java
            int result = compiler.run(null, null, null, javaFile.getPath());
            if (result != 0) {
                System.err.println("Erro ao compilar " + className + ".java.");
                return;
            }
        } catch (Exception e) {
        }
    }

    public static void run(String className){
        // String userHome = System.getProperty("user.home");
        // String documentosPath = userHome + File.separator + "Documentos" + File.separator + "MeuProjeto";
        String documentosPath = "C:\\Users\\barbi\\OneDrive\\Documentos\\GitHub\\Interop\\3 - 夢\\data\\commands";
        File documentosDir = new File(documentosPath);

        try {
            // Carrega a classe Exit compilada
            File classDir = new File(documentosDir.getPath());
            CustomClassLoader classLoader = new CustomClassLoader(classDir);
            Class<?> exitClass = classLoader.loadClass(className);

            // Obtém o método estático 'exit' e o executa
            Method exitMethod = exitClass.getDeclaredMethod(className.toLowerCase());
            System.out.println("Chamando Exit.exit()...");
            exitMethod.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO
    public static void compileNRun(){
        // compile();
        //run();
    }

}

// Loader personalizado para carregar a classe Exit
class CustomClassLoader extends ClassLoader {
    private final File classDir;

    public CustomClassLoader(File classDir) {
        this.classDir = classDir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            File classFile = new File(classDir, name + ".class");
            byte[] classBytes = java.nio.file.Files.readAllBytes(classFile.toPath());
            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (Exception e) {
            throw new ClassNotFoundException("Classe não encontrada: " + name, e);
        }
    }
}
