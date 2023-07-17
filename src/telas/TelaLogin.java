// Pacote onde estão localizadas as telas
package telas;

// Bibliotecas que serão usadas no projeto
import java.sql.*; // Importando tudo da biblioteca sql
import dal.ModuloConexao; // Importando o módulo de conexão que está no pacote .dal
import javax.swing.JOptionPane; // Importando a biblioteca JOptionPane (painel do java)

/**
 *
 * @author CHRISTIAN
 */
public class TelaLogin extends javax.swing.JFrame {

    // Variáveis especiais do sql para conexão
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // Método logar
    public void logar() {
        String sql = "select * from eleitores where nome=? and senha=?"; // Pesquisa no banco de dados o nome e a senha q forem digitados nos campos de texto
        try {
            // As linhas abaixo preparam a consulta ao banco de dados em função do que for digitado nas caixas de texto
            // O "?" é substituido pelo conteúdo das variáveis, ou seja, o que for digitado no campo de texto
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeLogin.getText()); // Aqui ele seleciona o primeiro ? e pega o que estiver escrito no campo de texto login
            String captura = new String(txtSenhaLogin.getPassword()); // Essa linha é responsável por pegar a senha escrita no campo de senha e atribuir a string captura
            pst.setString(2, captura);
            // A linha abaixo executa a query (consulta do banco de dados)
            rs = pst.executeQuery();
            // Se existir um eleitor e senha correspondente
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "LOGADO!"); // Exibe uma msg informando que está logado
                String perfil = rs.getString(6);
                String situacao = rs.getString(4);
                if(perfil.equals("Usuário")){
                    TelaPrincipal principal = new TelaPrincipal(); // Aqui estanciamos uma tela principal
                    principal.setVisible(true); // Aqui tornamos visível a tela principal
                    principal.votar.setar_eleitor(txtNomeLogin.getText()); // Esta linha é responsável por selecionar o nome do eleitor na tela votar
                    principal.menCad.setEnabled(false);
                    principal.menOpcResultados.setEnabled(false);
                    if(situacao.equals("Inápto")){
                        principal.menOpcVote.setEnabled(false);
                    }
                    this.dispose(); // Fecha a tela login
                    conexao.close(); // Encerra a conexão com o Banco de dados nesta tela
                }
                else {
                    TelaPrincipal principal = new TelaPrincipal(); // Aqui estanciamos uma tela principal
                    principal.setVisible(true); // Aqui tornamos visível a tela principal
                    principal.votar.setar_eleitor(txtNomeLogin.getText()); // Esta linha é responsável por selecionar o nome do eleitor na tela votar
                    if(situacao.equals("Inápto")){
                        principal.menOpcVote.setEnabled(false);
                    }
                    this.dispose(); // Fecha a tela login
                    conexao.close(); // Encerra a conexão com o Banco de dados nesta tela
                }
            } else {
                // Se não existir retorno, ele informa que houve alguma exceção
                JOptionPane.showMessageDialog(null, "Eleitor e/ou senha inválida!");
            }
        } catch (Exception e) {
            // Essa parte serve para verificar se houve algum erro de conexão com o banco de dados
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * Creates new form TelaLogin
     */
    // Construtor da tela de login
    public TelaLogin() {
        initComponents();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/icones/urna.png")).getImage());
        conexao = ModuloConexao.conector();
        // a linha abaixo serve de apoio ao Status da conexão
        // System.out.println(conexao);
        // As linhas abaixo servem para verificar se há ou não conexão com o banco de dados
        if (conexao != null) { // se houver conexão
            // ele mostra o ícone verdinho
            lblDB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dbok.png")));
        } else { // se não houver conexão
            // ele mostra o ícone vermelho
            lblDB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dberror.png")));
        }
    }

    public String nomelogin;
    public String senhalogin;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtNomeLogin = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtSenhaLogin = new javax.swing.JPasswordField();
        btnLogar = new javax.swing.JButton();
        lblDB = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login de votação");

        jLabel1.setText("NOME:");

        jLabel2.setText("SENHA:");

        btnLogar.setBackground(new java.awt.Color(51, 204, 0));
        btnLogar.setForeground(new java.awt.Color(255, 255, 255));
        btnLogar.setText("LOGAR");
        btnLogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogarActionPerformed(evt);
            }
        });

        lblDB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dberror.png"))); // NOI18N
        lblDB.setToolTipText("Bando de dados");

        btnCancelar.setBackground(new java.awt.Color(255, 51, 51));
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtNomeLogin))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtSenhaLogin)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(lblDB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addGap(35, 35, 35)
                .addComponent(btnLogar)
                .addGap(56, 56, 56))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNomeLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtSenhaLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogar)
                    .addComponent(lblDB)
                    .addComponent(btnCancelar))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Botão cancelar
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // Botão cancelar fecha a tela login
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    // Botão logar
    private void btnLogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogarActionPerformed
        // Botão logar verifica sua senha e seu nome e libera ou não a tela de votação
        // Chama o método logar
        logar(); // aqui é onde o método logar é executado
    }//GEN-LAST:event_btnLogarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnLogar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblDB;
    private javax.swing.JTextField txtNomeLogin;
    private javax.swing.JPasswordField txtSenhaLogin;
    // End of variables declaration//GEN-END:variables
}
