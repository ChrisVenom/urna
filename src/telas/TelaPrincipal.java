package telas; // Pacote telas

import javax.swing.JOptionPane; // Importando a biblioteca JOptionPane (painel do java)

/**
 *
 * @author CHRISTIAN
 */

public class TelaPrincipal extends javax.swing.JFrame {

    // Esta classe está estanciada aqui pq usamos ela na tela de login
    TelaVotar votar = new TelaVotar();
    
    /**
     * Creates new form TelaPrincipal
     */
    // Construtor
    public TelaPrincipal() {
        initComponents();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/icones/urna.png")).getImage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        menCad = new javax.swing.JMenu();
        menCadEle = new javax.swing.JMenuItem();
        menCadPart = new javax.swing.JMenuItem();
        menCadCand = new javax.swing.JMenuItem();
        menOpc = new javax.swing.JMenu();
        menOpcVote = new javax.swing.JMenuItem();
        menOpcResultados = new javax.swing.JMenuItem();
        menOpcSair = new javax.swing.JMenuItem();
        menAju = new javax.swing.JMenu();
        menAjuInfo = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Urna Eletrônica 2.0");

        javax.swing.GroupLayout desktopLayout = new javax.swing.GroupLayout(desktop);
        desktop.setLayout(desktopLayout);
        desktopLayout.setHorizontalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 780, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );

        menCad.setText("Cadastros");

        menCadEle.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        menCadEle.setText("Eleitores");
        menCadEle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menCadEleActionPerformed(evt);
            }
        });
        menCad.add(menCadEle);

        menCadPart.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
        menCadPart.setText("Partidos");
        menCadPart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menCadPartActionPerformed(evt);
            }
        });
        menCad.add(menCadPart);

        menCadCand.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        menCadCand.setText("Candidatos");
        menCadCand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menCadCandActionPerformed(evt);
            }
        });
        menCad.add(menCadCand);

        jMenuBar1.add(menCad);

        menOpc.setText("Opções");

        menOpcVote.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_MASK));
        menOpcVote.setText("Votar");
        menOpcVote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menOpcVoteActionPerformed(evt);
            }
        });
        menOpc.add(menOpcVote);

        menOpcResultados.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK));
        menOpcResultados.setText("Resultados");
        menOpcResultados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menOpcResultadosActionPerformed(evt);
            }
        });
        menOpc.add(menOpcResultados);

        menOpcSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        menOpcSair.setText("Sair");
        menOpcSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menOpcSairActionPerformed(evt);
            }
        });
        menOpc.add(menOpcSair);

        jMenuBar1.add(menOpc);

        menAju.setText("Ajuda");

        menAjuInfo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        menAjuInfo.setText("Informações");
        menAjuInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menAjuInfoActionPerformed(evt);
            }
        });
        menAju.add(menAjuInfo);

        jMenuBar1.add(menAju);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop)
        );

        setSize(new java.awt.Dimension(798, 527));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Quando clicar no menu Eleitor acontece o que está nas linhas abaixo
    private void menCadEleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menCadEleActionPerformed
        // Chamando a tela de cadastrar eleitores
        TelaEleitores eleitores = new TelaEleitores(); // Estanciando uma tela de eleitores
        eleitores.setVisible(true); // Aqui deixamos ela visível
        desktop.add(eleitores); // Adicionamos ela ao desktop
    }//GEN-LAST:event_menCadEleActionPerformed

    // Quando clicar no meno Partidos acontece o que está nas linhas abaixo
    private void menCadPartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menCadPartActionPerformed
        // Chamando a tela de cadastrar partidos
        TelaPartidos partidos = new TelaPartidos(); // Estanciando uma tela de partidos
        partidos.setVisible(true); // Aqui deixamos ela visível
        desktop.add(partidos); // Adicionamos ela ao desktop
    }//GEN-LAST:event_menCadPartActionPerformed

    // ...
    private void menCadCandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menCadCandActionPerformed
        // Chamando a tela de cadastrar candidatos
        TelaCandidatos candidatos = new TelaCandidatos();
        candidatos.setVisible(true);
        desktop.add(candidatos);
    }//GEN-LAST:event_menCadCandActionPerformed

    private void menOpcSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menOpcSairActionPerformed
        // Exibe uma caixa de diálogo
        int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Atenção!", JOptionPane.YES_NO_OPTION); // Pergunta se vc quer mesmo fechar a tela principal
        if (sair == JOptionPane.YES_OPTION) { // Se o usuário clicar em sim
            System.exit(0); // Encerra o programa, fechando a tela principal
        }
    }//GEN-LAST:event_menOpcSairActionPerformed

    private void menOpcVoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menOpcVoteActionPerformed
        // Chamando a tela de votação
        votar.setVisible(true);
        desktop.add(votar);
    }//GEN-LAST:event_menOpcVoteActionPerformed

    private void menAjuInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menAjuInfoActionPerformed
        // Janelinha de informações
        JOptionPane.showMessageDialog(null, "Programa criado e desenvolvido por Christian Morgado : christian-silva@live.com");
    }//GEN-LAST:event_menAjuInfoActionPerformed

    private void menOpcResultadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menOpcResultadosActionPerformed
        // Chamando a tela de resultados das eleições
        TelaResultados resultados = new TelaResultados();
        resultados.setVisible(true);
        desktop.add(resultados);
    }//GEN-LAST:event_menOpcResultadosActionPerformed

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
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JDesktopPane desktop;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu menAju;
    private javax.swing.JMenuItem menAjuInfo;
    protected javax.swing.JMenu menCad;
    protected javax.swing.JMenuItem menCadCand;
    protected javax.swing.JMenuItem menCadEle;
    protected javax.swing.JMenuItem menCadPart;
    private javax.swing.JMenu menOpc;
    protected javax.swing.JMenuItem menOpcResultados;
    private javax.swing.JMenuItem menOpcSair;
    protected javax.swing.JMenuItem menOpcVote;
    // End of variables declaration//GEN-END:variables
}