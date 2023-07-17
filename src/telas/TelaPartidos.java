package telas;

/**
 *
 * @author CHRISTIAN
 */

import classes.Partidos;
import java.sql.*;
import dal.ModuloConexao;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
// A linha abaixo importa recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;

public class TelaPartidos extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String fotopath = null;
    
    /**
     * Creates new form TelaPartidos
     */
    public TelaPartidos() {
        initComponents();
        conexao = ModuloConexao.conector();
    }
    
    // Instanciando um objeto da classe Partidos
    Partidos p = new Partidos.PartidosBuilder(56)
            .nome("PRONA")
            .sigla("PRONA")
            .imagem("a definir")
            .criarPartidos();
    
    // Método para adicionar partidos
    public void adicionar(){
        String sql = "insert into partidos(nome, sigla, numero, imagem) values(?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPartNome.getText());
            pst.setString(2, txtPartSigla.getText());
            pst.setString(3, txtPartNumero.getText());
            pst.setString(4, fotopath);
            // Validação dos campos obrigatórios
            if ((txtPartNome.getText().isEmpty()) || (txtPartNumero.getText().isEmpty()) || (fotopath.isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                // A linha abaixo atualiza a tabela clientes com os dados do formulário
                // A estrutura abaixo é usada para confirmar a inserção dos dados na tabela
                int adicionado = pst.executeUpdate();
                // A linha abaixo serve de apoio ao entendimento da lógica
                // System.out.println(adicionado);
                if (adicionado > 0) {
                    // As linhas abaixo "limpam" os campos
                    txtPartNome.setText(null);
                    txtPartSigla.setText(null);
                    txtPartNumero.setText(null);
                    ImageIcon icon = new ImageIcon("src/icones/user.png");
                    lblFoto.setIcon(icon);
                    JOptionPane.showMessageDialog(null, "Partido adicionado com sucesso!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // Método para pesquisar partidos pela sigla com filto
    public void pesquisar_partidos(){
        String sql = "select * from partidos where sigla like ?";
        try {
            pst = conexao.prepareStatement(sql);
            // Passando o conteúdo da caixa de pesquisa para o ?
            // Atenção ao "%" - que é a continuação da String sql
            pst.setString(1, txtPartPesquisar.getText() + "%");
            rs = pst.executeQuery();
            // A linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblPartidos.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // Método para setar os campos do formulário com o conteúdo da tabela
    public void setar_campos(){
        int setar = tblPartidos.getSelectedRow();
        txtPartNome.setText(tblPartidos.getModel().getValueAt(setar, 0).toString());
        txtPartSigla.setText(tblPartidos.getModel().getValueAt(setar, 1).toString());
        txtPartNumero.setText(tblPartidos.getModel().getValueAt(setar, 2).toString());
        ImageIcon icon = new ImageIcon(tblPartidos.getModel().getValueAt(setar, 3).toString());
        icon.setImage(icon.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), 1));
        fotopath = tblPartidos.getModel().getValueAt(setar, 3).toString();
        ImageIcon user = new ImageIcon("src/icones/user.png");
        String semfoto = "nenhum";
        if (icon.equals(semfoto)){
            lblFoto.setIcon(user);
        } else {
            lblFoto.setIcon(icon);
        }
        // A linha abaixo desabilita o botão adicionar
        btnPartAdicionar.setEnabled(false);
    }
    
    // Método para alterar dados do partido
    public void alterar(){
        String sql = "update partidos set nome=?, sigla=?, numero=?, imagem=? where numero=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPartNome.getText());
            pst.setString(2, txtPartSigla.getText());
            pst.setString(3, txtPartNumero.getText());
            pst.setString(4, fotopath);
            pst.setString(5, txtPartNumero.getText());
            if ((txtPartNome.getText().isEmpty()) || (txtPartNumero.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                // A linha abaixo atualiza a tabela clientes com os dados do formulário
                // A estrutura abaixo é usada para confirmar a alteração dos dados na tabela
                int adicionado = pst.executeUpdate();
                // A linha abaixo serve de apoio ao entendimento da lógica
                // System.out.println(adicionado);
                if (adicionado > 0) {
                    // As linhas abaixo "limpam" os campos
                    txtPartNome.setText(null);
                    txtPartSigla.setText(null);
                    txtPartNumero.setText(null);
                    ImageIcon icon = new ImageIcon("src/icones/user.png");
                    lblFoto.setIcon(icon);
                    JOptionPane.showMessageDialog(null, "Dados do partido alterados com sucesso!");
                    btnPartAdicionar.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // Método para remover partidos
    public void remover(){
        // A estrutura abaixo confirma a remoção do partido
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este partido?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from partidos where numero=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtPartNumero.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Partido removido com sucesso!");
                    // As linhas abaixo "limpam" os campos
                    txtPartNome.setText(null);
                    txtPartSigla.setText(null);
                    txtPartNumero.setText(null);
                    ImageIcon icon = new ImageIcon("src/icones/user.png");
                    lblFoto.setIcon(icon);
                    // Reabilitando o botão adicionar
                    btnPartAdicionar.setEnabled(true);
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

        txtPartPesquisar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPartidos = new javax.swing.JTable();
        lblFoto = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtPartNome = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtPartNumero = new javax.swing.JTextField();
        txtPartSigla = new javax.swing.JTextField();
        btnPartAdicionar = new javax.swing.JButton();
        btnPartAlterar = new javax.swing.JButton();
        btnPartRemover = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cadastro - Partidos");

        txtPartPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPartPesquisarKeyReleased(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/search.png"))); // NOI18N
        jLabel1.setToolTipText("Pesquisar");

        jLabel2.setText("* Campos obrigatórios");

        tblPartidos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPartidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPartidosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPartidos);

        lblFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/group.png"))); // NOI18N
        lblFoto.setToolTipText("Foto");
        lblFoto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblFotoMouseClicked(evt);
            }
        });

        jLabel4.setText("* Nome:");

        jLabel5.setText("Sigla:");

        jLabel6.setText("* Número:");

        btnPartAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/add.png"))); // NOI18N
        btnPartAdicionar.setToolTipText("Adicionar");
        btnPartAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPartAdicionar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnPartAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPartAdicionarActionPerformed(evt);
            }
        });

        btnPartAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/edit.png"))); // NOI18N
        btnPartAlterar.setToolTipText("Alterar");
        btnPartAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPartAlterar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnPartAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPartAlterarActionPerformed(evt);
            }
        });

        btnPartRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/delete.png"))); // NOI18N
        btnPartRemover.setToolTipText("Remover");
        btnPartRemover.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPartRemover.setPreferredSize(new java.awt.Dimension(80, 80));
        btnPartRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPartRemoverActionPerformed(evt);
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
                        .addComponent(txtPartPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtPartNome))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtPartSigla, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtPartNumero)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(btnPartAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139)
                .addComponent(btnPartAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(146, 146, 146)
                .addComponent(btnPartRemover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(133, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPartPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPartNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(txtPartNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPartSigla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPartAdicionar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPartAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPartRemover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        setBounds(0, 0, 780, 454);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPartAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPartAdicionarActionPerformed
        // Método para adicionar partidos
        adicionar();
    }//GEN-LAST:event_btnPartAdicionarActionPerformed

    private void btnPartAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPartAlterarActionPerformed
        // Método para alterar dados do partido
        alterar();
    }//GEN-LAST:event_btnPartAlterarActionPerformed

    private void btnPartRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPartRemoverActionPerformed
        // Método para remover partido
        remover();
    }//GEN-LAST:event_btnPartRemoverActionPerformed

    // O evento abaixo é do tipo "enquanto for digitando" 
    private void txtPartPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPartPesquisarKeyReleased
        // Chamando o método para pesquisar partidos
        pesquisar_partidos();
    }//GEN-LAST:event_txtPartPesquisarKeyReleased

    // Evento que será usado para setar os campos da tabela (clicando com o mouse)
    private void tblPartidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPartidosMouseClicked
        // Chamando o método para setar os campos do formulário com os dados da tabela
        setar_campos();
    }//GEN-LAST:event_tblPartidosMouseClicked

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
    private javax.swing.JButton btnPartAdicionar;
    private javax.swing.JButton btnPartAlterar;
    private javax.swing.JButton btnPartRemover;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JTable tblPartidos;
    private javax.swing.JTextField txtPartNome;
    private javax.swing.JTextField txtPartNumero;
    private javax.swing.JTextField txtPartPesquisar;
    private javax.swing.JTextField txtPartSigla;
    // End of variables declaration//GEN-END:variables
}
