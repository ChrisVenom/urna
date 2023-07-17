package telas;

/**
 *
 * @author CHRISTIAN
 */

import classes.Eleitores;
import java.sql.*;
import dal.ModuloConexao;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
// A linha abaixo importa recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;

public class TelaEleitores extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String fotopath = null;
    
    /**
     * Creates new form TelaEleitores
     */
    public TelaEleitores() {
        initComponents();
        conexao = ModuloConexao.conector();
    }
    
    // Instanciando um objeto da classe Eleitores
    Eleitores e = new Eleitores.EleitoresBuilder(88990077)
            .nome("Gustavo")
            .senha("discordocraque")
            .situacao("Apto")
            .imagem("a definir")
            .perfil("Usuário")
            .criarEleitores();
    
    // Método para adicionar eleitores
    public void adicionar(){
        String sql = "insert into eleitores(titulo, nome, senha, situacao, imagem, perfil) values(?, ?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtEleTitulo.getText());
            pst.setString(2, txtEleNome.getText());
            pst.setString(3, txtEleSenha.getText());
            // O combo box tem q ser convertido para String
            pst.setString(4, cboEleSituacao.getSelectedItem().toString());
            pst.setString(5, fotopath); // Grava o caminho da imagem
            pst.setString(6, cboElePerfil.getSelectedItem().toString());
            // Validação dos campos obrigatórios
            if ((txtEleNome.getText().isEmpty()) || (txtEleSenha.getText().isEmpty()) || (fotopath.isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                // A linha abaixo atualiza a tabela clientes com os dados do formulário
                // A estrutura abaixo é usada para confirmar a inserção dos dados na tabela
                int adicionado = pst.executeUpdate();
                // A linha abaixo serve de apoio ao entendimento da lógica
                // System.out.println(adicionado);
                if (adicionado > 0) {
                    // As linhas abaixo "limpam" os campos
                    txtEleTitulo.setText(null);
                    txtEleNome.setText(null);
                    txtEleSenha.setText(null);
                    ImageIcon icon = new ImageIcon("src/icones/user.png");
                    lblFoto.setIcon(icon);
                    JOptionPane.showMessageDialog(null, "Eleitor adicionado com sucesso!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // Método para pesquisar eleitores pelo nome com filto
    public void pesquisar_eleitores(){
        String sql = "select * from eleitores where nome like ?";
        try {
            pst = conexao.prepareStatement(sql);
            // Passando o conteúdo da caixa de pesquisa para o ?
            // Atenção ao "%" - que é a continuação da String sql
            pst.setString(1, txtElePesquisar.getText() + "%");
            rs = pst.executeQuery();
            // A linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblEleitores.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // Método para setar os campos do formulário com o conteúdo da tabela
    public void setar_campos(){
        int setar = tblEleitores.getSelectedRow();
        txtEleTitulo.setText(tblEleitores.getModel().getValueAt(setar, 0).toString());
        txtEleNome.setText(tblEleitores.getModel().getValueAt(setar, 1).toString());
        txtEleSenha.setText(tblEleitores.getModel().getValueAt(setar, 2).toString());
        cboEleSituacao.setSelectedItem(tblEleitores.getModel().getValueAt(setar, 3).toString());
        cboElePerfil.setSelectedItem(tblEleitores.getModel().getValueAt(setar, 5));
        ImageIcon icon = new ImageIcon(tblEleitores.getModel().getValueAt(setar, 4).toString());
        icon.setImage(icon.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), 1));
        fotopath = tblEleitores.getModel().getValueAt(setar, 4).toString();
        ImageIcon user = new ImageIcon("src/icones/user.png");
        String semfoto = "nenhum";
        if (icon.equals(semfoto)){
            lblFoto.setIcon(user);
        } else {
            lblFoto.setIcon(icon);
        }
        // A linha abaixo desabilita o botão adicionar
        btnEleAdicionar.setEnabled(false);
        // A linha abaixo desabilita o campo titulo
        txtEleTitulo.setEnabled(false);
    }
    
    // Método para alterar dados do eleitor
    public void alterar(){
        String sql = "update eleitores set nome=?, senha=?, situacao=?, imagem=?, perfil=? where titulo=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtEleNome.getText());
            pst.setString(2, txtEleSenha.getText());
            pst.setString(3, cboEleSituacao.getSelectedItem().toString());
            pst.setString(4, fotopath);
            pst.setString(5, cboElePerfil.getSelectedItem().toString());
            pst.setString(6, txtEleTitulo.getText());
            if ((txtEleNome.getText().isEmpty()) || (txtEleSenha.getText().isEmpty()) || (fotopath.isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                // A linha abaixo atualiza a tabela clientes com os dados do formulário
                // A estrutura abaixo é usada para confirmar a alteração dos dados na tabela
                int adicionado = pst.executeUpdate();
                // A linha abaixo serve de apoio ao entendimento da lógica
                // System.out.println(adicionado);
                if (adicionado > 0) {
                    // As linhas abaixo "limpam" os campos
                    txtEleTitulo.setText(null);
                    txtEleNome.setText(null);
                    txtEleSenha.setText(null);
                    ImageIcon icon = new ImageIcon("src/icones/user.png");
                    lblFoto.setIcon(icon);
                    JOptionPane.showMessageDialog(null, "Dados do eleitor alterados com sucesso!");
                    btnEleAdicionar.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // Método para remover eleitores
    public void remover(){
        // A estrutura abaixo confirma a remoção do eleitor
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este eleitor?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from eleitores where titulo=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtEleTitulo.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Eleitor removido com sucesso!");
                    // As linhas abaixo "limpam" os campos
                    txtEleTitulo.setText(null);
                    txtEleNome.setText(null);
                    txtEleSenha.setText(null);
                    ImageIcon icon = new ImageIcon("src/icones/user.png");
                    lblFoto.setIcon(icon);
                    // Reabilitando o botão adicionar
                    btnEleAdicionar.setEnabled(true);
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
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEleitores = new javax.swing.JTable();
        lblFoto = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtEleNome = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtEleTitulo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtEleSenha = new javax.swing.JTextField();
        cboEleSituacao = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        btnEleAdicionar = new javax.swing.JButton();
        btnEleAlterar = new javax.swing.JButton();
        btnEleRemover = new javax.swing.JButton();
        txtElePesquisar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cboElePerfil = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cadastro - Eleitores");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/search.png"))); // NOI18N
        jLabel1.setToolTipText("Pesquisar");

        jLabel2.setText("* Campos obrigatórios");

        tblEleitores.setModel(new javax.swing.table.DefaultTableModel(
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
        tblEleitores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEleitoresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEleitores);

        lblFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/user.png"))); // NOI18N
        lblFoto.setToolTipText("Foto");
        lblFoto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblFotoMouseClicked(evt);
            }
        });

        jLabel4.setText("*Nome:");

        jLabel5.setText("*Título:");

        jLabel6.setText("*Senha:");

        cboEleSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Apto", "Inápto" }));

        jLabel7.setText("*Situação:");

        btnEleAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/add.png"))); // NOI18N
        btnEleAdicionar.setToolTipText("Adicionar");
        btnEleAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEleAdicionar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnEleAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEleAdicionarActionPerformed(evt);
            }
        });

        btnEleAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/edit.png"))); // NOI18N
        btnEleAlterar.setToolTipText("Alterar");
        btnEleAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEleAlterar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnEleAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEleAlterarActionPerformed(evt);
            }
        });

        btnEleRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/delete.png"))); // NOI18N
        btnEleRemover.setToolTipText("Remover");
        btnEleRemover.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEleRemover.setPreferredSize(new java.awt.Dimension(80, 80));
        btnEleRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEleRemoverActionPerformed(evt);
            }
        });

        txtElePesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtElePesquisarKeyReleased(evt);
            }
        });

        jLabel3.setText("*Perfil:");

        cboElePerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Adm", "Usuário" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtElePesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtEleNome, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtEleSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(cboElePerfil, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel7)
                                        .addGap(2, 2, 2)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtEleTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(cboEleSituacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(btnEleAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(142, 142, 142)
                        .addComponent(btnEleAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(137, 137, 137)
                        .addComponent(btnEleRemover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2))
                    .addComponent(txtElePesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFoto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtEleNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtEleTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEleSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboEleSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel3)
                    .addComponent(cboElePerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnEleAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEleAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEleRemover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        setBounds(0, 0, 780, 454);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEleAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEleAlterarActionPerformed
        // Chamando o método para alterar dados do eleitor
        alterar();
    }//GEN-LAST:event_btnEleAlterarActionPerformed

    private void btnEleAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEleAdicionarActionPerformed
        // Chamando o método para adicionar eleitores
        adicionar();
    }//GEN-LAST:event_btnEleAdicionarActionPerformed

    private void btnEleRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEleRemoverActionPerformed
        // Chamando o método para remover eleitor
        remover();
    }//GEN-LAST:event_btnEleRemoverActionPerformed

    // O evento abaixo é do tipo "enquanto for digitando" 
    private void txtElePesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtElePesquisarKeyReleased
        // Chamando o método para pesquisar eleitores
        pesquisar_eleitores();
    }//GEN-LAST:event_txtElePesquisarKeyReleased

    //  Evento que será usado para setar os campos da tabela (clicando com o mouse)
    private void tblEleitoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEleitoresMouseClicked
        // Chamando o método para setar os campos do formulário com os dados da tabela
        setar_campos();
    }//GEN-LAST:event_tblEleitoresMouseClicked

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
    private javax.swing.JButton btnEleAdicionar;
    private javax.swing.JButton btnEleAlterar;
    private javax.swing.JButton btnEleRemover;
    private javax.swing.JComboBox<String> cboElePerfil;
    private javax.swing.JComboBox<String> cboEleSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JTable tblEleitores;
    private javax.swing.JTextField txtEleNome;
    private javax.swing.JTextField txtElePesquisar;
    private javax.swing.JTextField txtEleSenha;
    private javax.swing.JTextField txtEleTitulo;
    // End of variables declaration//GEN-END:variables
}
