/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ponto.telas;

import br.com.ponto.DAO.FuncionarioDAO;
import br.com.ponto.entidade.Funcionario;
import br.com.ponto.sdk.CisBiox;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Shall
 */
public class TesteDigital extends javax.swing.JFrame {

    private static byte[] digital1;
    private static byte[] digital2;

    private static Runnable lerDigital1 = new Runnable() {
        @Override
        public void run() {
            CisBiox biox = new CisBiox();

            digital1 = biox.capturarDigital();

            if (biox.getResultado() != 1) {
                biox.finalizar();
                return;
            }

            int iRetorno = biox.finalizar();

            if (iRetorno != 1) {
                JOptionPane.showMessageDialog(null, "Erro: " + CisBiox.mensagem(iRetorno));
                return;
            }

            JOptionPane.showMessageDialog(null, CisBiox.mensagem(iRetorno));
           
        }
    };

    private static Runnable lerDigital2 = new Runnable() {
        @Override
        public void run() {
            CisBiox biox = new CisBiox();

            digital2 = biox.capturarDigital();

            if (biox.getResultado() != 1) {
                biox.finalizar();
                return;
            }

            int iRetorno = biox.finalizar();

            if (iRetorno != 1) {
                JOptionPane.showMessageDialog(null, "Erro: " + CisBiox.mensagem(iRetorno));
                return;
            }

            JOptionPane.showMessageDialog(null, CisBiox.mensagem(iRetorno));
            //setTxtDigital2(digital2);
        }
    };

    /**
     * Creates new form TesteDigital
     */
    public TesteDigital() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnDigital1 = new javax.swing.JButton();
        btnDigital2 = new javax.swing.JButton();
        btnComparar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbFuncionario = new javax.swing.JComboBox<>();
        txtDigital1 = new javax.swing.JTextField();
        txtDigital2 = new javax.swing.JTextField();
        btnCadastrar = new javax.swing.JButton();
        btnLimparDigitais = new javax.swing.JButton();
        btnMostrarDigitais = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Biometria");
        setResizable(false);

        btnDigital1.setText("Digital1");
        btnDigital1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDigital1ActionPerformed(evt);
            }
        });

        btnDigital2.setText("Digital2");
        btnDigital2.setEnabled(false);
        btnDigital2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDigital2ActionPerformed(evt);
            }
        });

        btnComparar.setText("Comparar");
        btnComparar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompararActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setText("Insira Sua Digital");

        cbFuncionario.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                cbFuncionarioAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        txtDigital1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDigital1ActionPerformed(evt);
            }
        });

        btnCadastrar.setText("Cadastrar");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        btnLimparDigitais.setText("Limpar Digitais");
        btnLimparDigitais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparDigitaisActionPerformed(evt);
            }
        });

        btnMostrarDigitais.setText("Mostrar Digitais");
        btnMostrarDigitais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarDigitaisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbFuncionario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCadastrar))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnDigital1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtDigital1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnDigital2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtDigital2)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnComparar)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnLimparDigitais, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnMostrarDigitais, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnComparar)
                    .addComponent(btnDigital1)
                    .addComponent(txtDigital1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDigital2)
                    .addComponent(txtDigital2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimparDigitais)
                    .addComponent(btnMostrarDigitais))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadastrar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDigital1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDigital1ActionPerformed
        CisBiox biox = new CisBiox();

        int iRetorno = biox.iniciar();

        if (iRetorno != 1) {
            JOptionPane.showMessageDialog(null, "Erro: " + CisBiox.mensagem(iRetorno));
            return;
        }
        new Thread(lerDigital1).start();
        
        
    }//GEN-LAST:event_btnDigital1ActionPerformed

    private void btnDigital2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDigital2ActionPerformed
        CisBiox biox = new CisBiox();

        int iRetorno = biox.iniciar();

        if (iRetorno != 1) {
            JOptionPane.showMessageDialog(null, "Erro: " + CisBiox.mensagem(iRetorno));
            return;
        }
        new Thread(lerDigital2).start();
        
    }//GEN-LAST:event_btnDigital2ActionPerformed

    private void btnCompararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompararActionPerformed

        // Instanciar a DLL
        CisBiox biox = new CisBiox();

        biox.iniciar();
        
        int iRetorno = biox.compararDigital(digital1, digital2);

        switch (iRetorno) {
            case 1:
                JOptionPane.showMessageDialog(null, "As digitais são iguais");
                break;
            case -2:
                JOptionPane.showMessageDialog(null, "As digitais são diferentes");
                break;
            default:
                JOptionPane.showMessageDialog(null, "Erro: " + Integer.toString(iRetorno));
                break;
        }
        biox.finalizar();
    }//GEN-LAST:event_btnCompararActionPerformed

    private void cbFuncionarioAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_cbFuncionarioAncestorAdded
        try {
            cbFuncionario.removeAllItems();
            FuncionarioDAO dao = new FuncionarioDAO();
            List<Funcionario> listaFuncionarios = dao.listarTodosFuncionarios();
            for (Funcionario funcionario : listaFuncionarios) {
                cbFuncionario.addItem(funcionario);
            }
        } catch (Exception e) {

        }
    }//GEN-LAST:event_cbFuncionarioAncestorAdded

    private void txtDigital1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDigital1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDigital1ActionPerformed

    private void btnLimparDigitaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparDigitaisActionPerformed
        txtDigital1.setText("");
        txtDigital2.setText("");
    }//GEN-LAST:event_btnLimparDigitaisActionPerformed

    private void btnMostrarDigitaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarDigitaisActionPerformed
        txtDigital1.setText(digital1.toString());
        txtDigital2.setText(digital2.toString());
    }//GEN-LAST:event_btnMostrarDigitaisActionPerformed

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        try {
            
            Funcionario funcionario = (Funcionario) cbFuncionario.getSelectedItem();
            funcionario.setFuncDigital1(digital1);
            funcionario.setFuncDigital2(digital2);

            FuncionarioDAO dao = new FuncionarioDAO();
            dao.alterar(funcionario);

            JOptionPane.showMessageDialog(null, "Ordem de serviço alterada com sucesso");
            limparCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar OS: " + e);
        }
    }//GEN-LAST:event_btnCadastrarActionPerformed

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
            java.util.logging.Logger.getLogger(TesteDigital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TesteDigital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TesteDigital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TesteDigital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TesteDigital().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnComparar;
    private javax.swing.JButton btnDigital1;
    private javax.swing.JButton btnDigital2;
    private javax.swing.JButton btnLimparDigitais;
    private javax.swing.JButton btnMostrarDigitais;
    private javax.swing.JComboBox<Object> cbFuncionario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtDigital1;
    private javax.swing.JTextField txtDigital2;
    // End of variables declaration//GEN-END:variables

    private void limparCampos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
