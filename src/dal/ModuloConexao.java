package dal; // Pacote dal

import java.sql.*; // Importando a biblioteca sql e o * significa que é tudo relacionado a biblioteca

/**
 *
 * @author CHRISTIAN
 */
public class ModuloConexao {
    // metodo responsavel por estabelecer a conexão com o banco de dados
    public static Connection conector() {
        java.sql.Connection conexao = null;
        // a linha abaixo chama o driver que eu importei
        String driver = "com.mysql.cj.jdbc.Driver";
        // armazenando informações referente ao banco de dados
        String url = "jdbc:mysql://localhost:3306/urna";
        String user = "root";
        String password = "Chinatsu*34";
        // Estabelecendo a conexão com o DB
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}