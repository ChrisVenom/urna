package telas;

/**
 *
 * @author CHRISTIAN
 */

import classes.Candidatos;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import java.util.Date;
import java.text.DateFormat;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.sql.*;
import dal.ModuloConexao;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
// A linha abaixo importa recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;

public class TelaResultados extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    /**
     * Creates new form TelaResultados
     */
    public TelaResultados() {
        initComponents();
        conexao = ModuloConexao.conector();
    }
    
    // Método para pesquisar os candidatos
    private void pesquisar() {
        String sql = "select nome, cargo, numero, partido, coligacao, votos from candidatos order by cargo, votos DESC";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                rs = pst.executeQuery();
                // A linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
                tblResultados.setModel(DbUtils.resultSetToTableModel(rs));
            } else {
                JOptionPane.showMessageDialog(null, "ERROR404!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // Método para zerar a votação
    private void zerar(){
        // A Estrutura abaixo confirma se deseja ou não zerar o votos
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja zerar todos os votos?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION){
            String sql = "update candidatos set votos = 0";
            try {
                pst = conexao.prepareStatement(sql);
                int zerado = pst.executeUpdate();
                if (zerado > 0){
                    JOptionPane.showMessageDialog(null, "Votação zerada!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    // Método para printar PDF
    private void printarPDF(){
        // Aqui cria o documento PDF
        Document doc = new Document();
        // Aqui coloca o nome do arquivo
        String arquivoPDF = "relatorio.pdf";
        String sql = "select nome, cargo, numero, partido, coligacao, votos from candidatos order by cargo, votos DESC";
        // Aqui é criada uma lista de candidatos
        List<Candidatos> candidatos = new ArrayList<Candidatos>();
        
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(arquivoPDF));
            doc.open();
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            // Criando um parágrafo "p" e adicionando um título
            Paragraph p = new Paragraph("Resultado da Eleição");
            p.setAlignment(1);
            doc.add(p);
            // Criando uma data
            Date data = new Date();
            // Formatando a data
            DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
            p = new Paragraph(formatador.format(data));
            p.setAlignment(1);
            doc.add(p);
            // Adicionando uma linha para não agarrar na tabela
            p = new Paragraph(" ");
            doc.add(p);
            
            // Aqui cria a tabela
            PdfPTable table = new PdfPTable(6);
            // Aumenta o tamanho de todas as células
            table.setWidthPercentage(100);
            
            // Aqui cria as colunas da tabela
            PdfPCell cel1 = new PdfPCell(new Paragraph("Nome"));
            cel1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cel2 = new PdfPCell(new Paragraph("Cargo"));
            cel2.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cel3 = new PdfPCell(new Paragraph("Número"));
            cel3.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cel4 = new PdfPCell(new Paragraph("Partido"));
            cel4.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cel5 = new PdfPCell(new Paragraph("Coligação"));
            cel5.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cel6 = new PdfPCell(new Paragraph("Votos"));
            cel6.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            // Aqui adiciona as colunas na tabela
            table.addCell(cel1);
            table.addCell(cel2);
            table.addCell(cel3);
            table.addCell(cel4);
            table.addCell(cel5);
            table.addCell(cel6);
            
            // Enquanto tiver dados na query, ele vai armazenando numa instância da classe Candidatos e depois adiciona na lista
            while (rs.next()){
                Candidatos candidato = new Candidatos();
                candidato.setNome(rs.getString("nome"));
                candidato.setCargo(rs.getString("cargo"));
                candidato.setNumero(rs.getInt("numero"));
                candidato.setPartido(rs.getString("partido"));
                candidato.setColigacao(rs.getString("coligacao"));
                candidato.setVotos(rs.getInt("votos"));
                
                candidatos.add(candidato);
            }
            
            // Aqui percorremos a lista e adicionamos na tabela do PDF
            for (Candidatos c : candidatos){
                cel1 = new PdfPCell(new Paragraph(c.getNome()));
                cel1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cel2 = new PdfPCell(new Paragraph(c.getCargo()));
                cel2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cel3 = new PdfPCell(new Paragraph(c.getNumero()+""));
                cel3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cel4 = new PdfPCell(new Paragraph(c.getPartido()));
                cel4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cel5 = new PdfPCell(new Paragraph(c.getColigacao()));
                cel5.setHorizontalAlignment(Element.ALIGN_CENTER);
                cel6 = new PdfPCell(new Paragraph(c.getVotos()+""));
                cel6.setHorizontalAlignment(Element.ALIGN_CENTER);
            
                table.addCell(cel1);
                table.addCell(cel2);
                table.addCell(cel3);
                table.addCell(cel4);
                table.addCell(cel5);
                table.addCell(cel6);
            }
            
            doc.add(table);
            doc.close();
            Desktop.getDesktop().open(new File(arquivoPDF));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblResultados = new javax.swing.JTable();
        btnResPesquisar = new javax.swing.JButton();
        btnResZerar = new javax.swing.JButton();
        btnResPrint = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Resultados das Eleições");

        tblResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblResultados);

        btnResPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/search_bottom.png"))); // NOI18N
        btnResPesquisar.setToolTipText("Visualizar");
        btnResPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnResPesquisar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnResPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResPesquisarActionPerformed(evt);
            }
        });

        btnResZerar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/zerar.png"))); // NOI18N
        btnResZerar.setToolTipText("Zerar eleição");
        btnResZerar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnResZerar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnResZerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResZerarActionPerformed(evt);
            }
        });

        btnResPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/print.png"))); // NOI18N
        btnResPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnResPrint.setPreferredSize(new java.awt.Dimension(80, 80));
        btnResPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(btnResPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnResZerar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(134, 134, 134)
                .addComponent(btnResPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(121, 121, 121))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnResPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnResPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnResZerar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(35, 35, 35))
        );

        btnResPrint.getAccessibleContext().setAccessibleDescription("Printar PDF");

        setBounds(0, 0, 780, 454);
    }// </editor-fold>//GEN-END:initComponents

    private void btnResPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResPesquisarActionPerformed
        // Chamando o método pesquisar
        pesquisar();
    }//GEN-LAST:event_btnResPesquisarActionPerformed

    private void btnResZerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResZerarActionPerformed
        // Chamando o método zerar
        zerar();
    }//GEN-LAST:event_btnResZerarActionPerformed

    private void btnResPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResPrintActionPerformed
        // Chamando o método printar pdf
        printarPDF();
    }//GEN-LAST:event_btnResPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnResPesquisar;
    private javax.swing.JButton btnResPrint;
    private javax.swing.JButton btnResZerar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblResultados;
    // End of variables declaration//GEN-END:variables
}
