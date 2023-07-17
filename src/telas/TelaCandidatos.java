package telas; // Pacote das telas

/**
 *
 * @author CHRISTIAN
 */

// Importando as bibliotecas
import java.sql.*;
import dal.ModuloConexao;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
// A linha abaixo importa recursos da biblioteca rs2xml.jar (tabela)
import net.proteanit.sql.DbUtils;

public class TelaCandidatos extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String fotopath = null;
    
    /**
     * Creates new form TelaCandidatos
     */
    
    // Construtor
    public TelaCandidatos() {
        initComponents();
        conexao = ModuloConexao.conector(); // Precisamos executar a conexão com o banco de dados ao iniciarmos a classe
    }
    
    // Método para adicionar candidatos
    public void adicionar(){
        String sql = "insert into candidatos(nome, cargo, numero, partido, coligacao, votos, imagem) values(?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCanNome.getText()); // Nome
            pst.setString(2, txtCanCargo.getText()); // Cargo
            pst.setString(3, txtCanNumero.getText()); // Numero
            pst.setString(4, txtCanPartido.getText()); // Partido
            pst.setString(5, txtCanColigacao.getText()); // Coligação
            pst.setString(6, "0"); // Iniciamos os votos com 0, pois o candidato acabou de ser cadastrado
            pst.setString(7, fotopath); // Grava o caminho para a imagem 
            // Validação dos campos obrigatórios
            if ((txtCanNome.getText().isEmpty()) || (txtCanNumero.getText().isEmpty()) || (txtCanPartido.getText().isEmpty()) || (fotopath.isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                // A linha abaixo atualiza a tabela candidatos com os dados do formulário
                // A estrutura abaixo é usada para confirmar a inserção dos dados na tabela
                int adicionado = pst.executeUpdate();
                // A linha abaixo serve de apoio ao entendimento da lógica
                // System.out.println(adicionado);
                if (adicionado > 0) {
                    // As linhas abaixo "limpam" os campos
                    txtCanNome.setText(null);
                    txtCanCargo.setText(null);
                    txtCanNumero.setText(null);
                    txtCanPartido.setText(null);
                    txtCanColigacao.setText(null);
                    ImageIcon icon = new ImageIcon("src/icones/user.png");
                    lblFoto.setIcon(icon);
                    JOptionPane.showMessageDialog(null, "Candidato adicionado com sucesso!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // Método para pesquisar candidatos pelo nome com filto
    public void pesquisar_candidatos(){
        String sql = "select nome, cargo, numero, partido, coligacao, imagem from candidatos where nome like ?"; // like = parecido com
        try {
            pst = conexao.prepareStatement(sql);
            // Passando o conteúdo da caixa de pesquisa para o ?
            // Atenção ao "%" - que é a continuação da String sql
            pst.setString(1, txtCanPesquisa.getText() + "%");
            rs = pst.executeQuery();
            // A linha abaixo usam a biblioteca rs2xml.jar para preencher a tabela
            tblCandidatos.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // Método para setar os campos do formulário com o conteúdo da tabela
    public void setar_campos(){
        int setar = tblCandidatos.getSelectedRow();
        txtCanNome.setText(tblCandidatos.getModel().getValueAt(setar, 0).toString());
        txtCanCargo.setText(tblCandidatos.getModel().getValueAt(setar, 1).toString());
        txtCanNumero.setText(tblCandidatos.getModel().getValueAt(setar, 2).toString());
        txtCanPartido.setText(tblCandidatos.getModel().getValueAt(setar, 3).toString());
        txtCanColigacao.setText(tblCandidatos.getModel().getValueAt(setar, 4).toString());
        ImageIcon icon = new ImageIcon(tblCandidatos.getModel().getValueAt(setar, 5).toString());
        icon.setImage(icon.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), 1));
        fotopath = tblCandidatos.getModel().getValueAt(setar, 5).toString();
        ImageIcon user = new ImageIcon("src/icones/user.png");
        String semfoto = "nenhum";
        if (icon.equals(semfoto)){
            lblFoto.setIcon(user);
        } else {
            lblFoto.setIcon(icon);
        }
        // A linha abaixo desabilita o botão adicionar
        btnCanAdicionar.setEnabled(false);
    }
    
    // Método para alterar dados do candidato
    public void alterar(){
        String sql = "update candidatos set nome=?, cargo=?, numero=?, partido=?, coligacao=?, imagem=? where numero=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCanNome.getText());
            pst.setString(2, txtCanCargo.getText());
            pst.setString(3, txtCanNumero.getText());
            pst.setString(4, txtCanPartido.getText());
            pst.setString(5, txtCanColigacao.getText());
            pst.setString(6, fotopath); // Grava o caminho da imagem
            pst.setString(7, txtCanNumero.getText());
            if ((txtCanNome.getText().isEmpty()) || (txtCanNumero.getText().isEmpty()) || (txtCanPartido.getText().isEmpty()) || (fotopath.isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                // A linha abaixo atualiza a tabela clientes com os dados do formulário
                // A estrutura abaixo é usada para confirmar a alteração dos dados na tabela
                int adicionado = pst.executeUpdate();
                // A linha abaixo serve de apoio ao entendimento da lógica
                // System.out.println(adicionado);
                if (adicionado > 0) {
                    // As linhas abaixo "limpam" os campos
                    txtCanNome.setText(null);
                    txtCanCargo.setText(null);
                    txtCanNumero.setText(null);
                    txtCanPartido.setText(null);
                    txtCanColigacao.setText(null);
                    ImageIcon icon = new ImageIcon("src/icones/user.png");
                    lblFoto.setIcon(icon);
                    JOptionPane.showMessageDialog(null, "Dados do candidato alterados com sucesso!");
                    btnCanAdicionar.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // Método para remover candidatos
    public void remover(){
        // A estrutura abaixo confirma a remoção do candidato
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este candidato?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from candidatos where numero=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCanNumero.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Candidato removido com sucesso!");
                    // As linhas abaixo "limpam" os campos
                    txtCanNome.setText(null);
                    txtCanCargo.setText(null);
                    txtCanNome.setText(null);
                    txtCanPartido.setText(null);
                    txtCanColigacao.setText(null);
                    ImageIcon icon = new ImageIcon("src/icones/user.png");
                    lblFoto.setIcon(icon);
                    // Reabilitando o botão adicionar
                    btnCanAdicionar.setEnabled(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtCanNome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCanCargo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCanNumero = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCanPartido = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCanColigacao = new javax.swing.JTextField();
        txtCanPesquisa = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCandidatos = new javax.swing.JTable();
        lblFoto = new javax.swing.JLabel();
        btnCanAdicionar = new javax.swing.JButton();
        btnCanAlterar = new javax.swing.JButton();
        btnCanRemover = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cadastro - Candidatos");

        jLabel1.setText("*Nome:");

        jLabel2.setText("Cargo:");

        jLabel4.setText("*Número:");

        jLabel5.setText("*Partido:");

        jLabel6.setText("Coligação:");

        txtCanPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCanPesquisaKeyReleased(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/search.png"))); // NOI18N
        jLabel7.setToolTipText("Pesquisar");

        tblCandidatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCandidatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCandidatosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCandidatos);

        lblFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/user.png"))); // NOI18N
        lblFoto.setToolTipText("Foto");
        lblFoto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblFotoMouseClicked(evt);
            }
        });

        btnCanAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/add.png"))); // NOI18N
        btnCanAdicionar.setToolTipText("Adicionar");
        btnCanAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCanAdicionar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCanAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCanAdicionarActionPerformed(evt);
            }
        });

        btnCanAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/edit.png"))); // NOI18N
        btnCanAlterar.setToolTipText("Alterar");
        btnCanAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCanAlterar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCanAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCanAlterarActionPerformed(evt);
            }
        });

        btnCanRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/delete.png"))); // NOI18N
        btnCanRemover.setToolTipText("Remover");
        btnCanRemover.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCanRemover.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCanRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCanRemoverActionPerformed(evt);
            }
        });

        jLabel3.setText("* Campos obrigatórios");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(btnCanAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139)
                .addComponent(btnCanAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(140, 140, 140)
                .addComponent(btnCanRemover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(138, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtCanNome))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtCanCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(txtCanPartido, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(jLabel6)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCanColigacao)
                            .addComponent(txtCanNumero)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtCanPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtCanPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCanNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCanNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtCanCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCanPartido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(txtCanColigacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCanAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCanAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCanRemover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        setBounds(0, 0, 780, 454);
    }// </editor-fold>//GEN-END:initComponents

    // Quando clicar no botão Adicionar acontece o que está nas linhas abaixo
    private void btnCanAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCanAdicionarActionPerformed
        // Chamando método para adicionar candidatos
        adicionar();
    }//GEN-LAST:event_btnCanAdicionarActionPerformed

    private void btnCanAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCanAlterarActionPerformed
        // Chamando método para alterar dados dos candidatos
        alterar();
    }//GEN-LAST:event_btnCanAlterarActionPerformed

    private void btnCanRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCanRemoverActionPerformed
        // Chamando método para remover candidato
        remover();
    }//GEN-LAST:event_btnCanRemoverActionPerformed

    // O evento abaixo é do tipo "enquanto for digitando" 
    private void txtCanPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCanPesquisaKeyReleased
        // Chamando o método pesquisar candidatos
        pesquisar_candidatos();
    }//GEN-LAST:event_txtCanPesquisaKeyReleased

    //  Evento que será usado para setar os campos da tabela (clicando com o mouse)
    private void tblCandidatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCandidatosMouseClicked
        // Chamando o método para setar os campos do formulário com os dados da tabela
        setar_campos();
    }//GEN-LAST:event_tblCandidatosMouseClicked

    private void lblFotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFotoMouseClicked
        // Quando clicar no icone da imagem, ele abre um navegador de arquivos para selecionar a imagem que o usuário quiser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Procurar arquivo");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // Filtrando os arquivos tipo png e jpg
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagem", "jpg", "jpeg", "png");
        
        fileChooser.setFileFilter(filter);
        int retorno = fileChooser.showOpenDialog(this);
        
        if (retorno == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            ImageIcon foto = new ImageIcon(file.getPath());
            foto.setImage(foto.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), 1));
            lblFoto.setIcon(foto);
            fotopath = file.getPath();
        }
    }//GEN-LAST:event_lblFotoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCanAdicionar;
    private javax.swing.JButton btnCanAlterar;
    private javax.swing.JButton btnCanRemover;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JTable tblCandidatos;
    private javax.swing.JTextField txtCanCargo;
    private javax.swing.JTextField txtCanColigacao;
    private javax.swing.JTextField txtCanNome;
    private javax.swing.JTextField txtCanNumero;
    private javax.swing.JTextField txtCanPartido;
    private javax.swing.JTextField txtCanPesquisa;
    // End of variables declaration//GEN-END:variables
}
