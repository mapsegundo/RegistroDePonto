/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ponto.telas;

import br.com.ponto.DAO.BatidaDAO;
import br.com.ponto.DAO.FuncionarioDAO;
import br.com.ponto.entidade.Batida;
import br.com.ponto.entidade.Funcionario;
import br.com.ponto.sdk.CisBiox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author Shall
 */
public class RegistrarBatida extends javax.swing.JFrame {

    private static byte[] testeDigital;
    private static String nomeFuncionario;
    private static Funcionario funcionario;
    private javax.swing.Timer timer;

    /**
     * Creates new form Batida
     */
    public RegistrarBatida() {
        initComponents();
    }

    class hora implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Calendar now = Calendar.getInstance();
            jlHora.setText(String.format("%1$tH:%1$tM:%1$tS", now));
        }
    }
    
    public void Batida(Funcionario funcionario){
        try{
        Batida batida = new Batida();
        BatidaDAO dao = new BatidaDAO();
        batida.setBatFkFuncionarioId(funcionario);
        dao.gerarBatida(batida);
        } catch(Exception e){
            System.out.println("Erro ao tentar cadastrar no banco: "+e);
        }
        
    }

    public void verificarBiometria() {
        nomeFuncionario = null;
        Thread etapaCaptura = null;
        Thread etapaCompara = null;
        try {
            etapaCaptura = new Thread(pegaBatida);
            etapaCompara = new Thread(comparaBatida);

            etapaCaptura.start();
            etapaCaptura.join();
            etapaCompara.start();
            etapaCompara.join();

            if (nomeFuncionario != null) {
                System.out.println("teste batida reconhecida: "+ nomeFuncionario);
                Batida(funcionario);
            } else {
                //JOptionPane.showMessageDialog(null, "Digital não encontrada");
                System.out.println("teste batida nao reconhecida");
            }
            testeDigital = null;
        } catch (InterruptedException e) {
            System.out.println("Erro:" + e);
        }

    }

    public void telaConfirmacao(String funcionario) {
        txtConfirmacao.setText(funcionario);
        dialogConfirmacao.setSize(550, 200);
        dialogConfirmacao.setLocationRelativeTo(this);
        
        dialogNaoConfirmado.setSize(550, 200);
        dialogNaoConfirmado.setLocationRelativeTo(this);
        
        if(funcionario != null){
        dialogConfirmacao.setVisible(true);
        } else{
            dialogNaoConfirmado.setVisible(true);
        }
        Date minhaHora = new Date();
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
        txtHora.setText(formataHora.format(minhaHora));

        timer = new Timer(1 * 2000, new ActionListener() {
            public void actionPerformed(ActionEvent ev) {

                dialogConfirmacao.dispose();
                dialogNaoConfirmado.dispose();
            }
        });
        timer.setRepeats(false);//the timer should only go off once

        timer.start();
        
    }

    private static Runnable pegaBatida = new Runnable() {
        @Override
        public void run() {
            try {
                CisBiox biox = new CisBiox();

                biox.iniciar();

                testeDigital = biox.capturarDigital();

                if (biox.getResultado() != 1) {
                    biox.finalizar();
                    return;
                }

                int iRetorno = biox.finalizar();

                if (iRetorno != 1) {
                    JOptionPane.showMessageDialog(null, "Erro: " + CisBiox.mensagem(iRetorno));
                    return;
                }

            } catch (Exception e) {

            }
        }
    };

    private static Runnable comparaBatida = new Runnable() {
        @Override
        public void run() {
            try {

                CisBiox biox = new CisBiox();

                biox.iniciar();

                FuncionarioDAO dao = new FuncionarioDAO();
                List<Funcionario> listaCadastrados = dao.listarTodosFuncionariosComBiometria();

                Funcionario funcionarioEncontrado = null;
                for (Funcionario funcionario : listaCadastrados) {
                    if (biox.compararDigital(funcionario.getFuncDigital1(), testeDigital) == 1) {
                        funcionarioEncontrado = funcionario;

                        break;
                    }
                    if (biox.compararDigital(funcionario.getFuncDigital2(), testeDigital) == 1) {
                        funcionarioEncontrado = funcionario;

                        break;
                    }
                }
                biox.finalizar();
                biox.cancelarLeitura();

                if (funcionarioEncontrado != null) {
                    //JOptionPane.showMessageDialog(null, "Bem vindo " + funcionarioEncontrado.getFuncNome());
                    nomeFuncionario = funcionarioEncontrado.getFuncNome();
                    funcionario = funcionarioEncontrado;
                }
            } catch (Exception e) {

            }
        }
    };

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialogConfirmacao = new javax.swing.JDialog();
        txtConfirmacao = new javax.swing.JTextField();
        txtHora = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        dialogNaoConfirmado = new javax.swing.JDialog();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jlData = new javax.swing.JLabel();
        jlHora = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        dialogConfirmacao.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dialogConfirmacao.setTitle("Confirmação");
        dialogConfirmacao.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                dialogConfirmacaoWindowClosed(evt);
            }
        });

        txtConfirmacao.setEditable(false);
        txtConfirmacao.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtConfirmacao.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        txtHora.setEditable(false);
        txtHora.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel6.setText("Hora da Batida");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ponto/imagens/btnConfirmar.png"))); // NOI18N
        jLabel7.setText("BATIDA REGISTRADA");

        javax.swing.GroupLayout dialogConfirmacaoLayout = new javax.swing.GroupLayout(dialogConfirmacao.getContentPane());
        dialogConfirmacao.getContentPane().setLayout(dialogConfirmacaoLayout);
        dialogConfirmacaoLayout.setHorizontalGroup(
            dialogConfirmacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogConfirmacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogConfirmacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                    .addComponent(txtConfirmacao)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogConfirmacaoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        dialogConfirmacaoLayout.setVerticalGroup(
            dialogConfirmacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogConfirmacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(dialogConfirmacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtConfirmacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dialogNaoConfirmado.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dialogNaoConfirmado.setTitle("Nao Encontrado");
        dialogNaoConfirmado.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                dialogNaoConfirmadoWindowClosed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ponto/imagens/botaExcluir.png"))); // NOI18N
        jLabel8.setText("DIGITAL NÃO ENCONTRADA");

        javax.swing.GroupLayout dialogNaoConfirmadoLayout = new javax.swing.GroupLayout(dialogNaoConfirmado.getContentPane());
        dialogNaoConfirmado.getContentPane().setLayout(dialogNaoConfirmadoLayout);
        dialogNaoConfirmadoLayout.setHorizontalGroup(
            dialogNaoConfirmadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogNaoConfirmadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        dialogNaoConfirmadoLayout.setVerticalGroup(
            dialogNaoConfirmadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogNaoConfirmadoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel8)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Batida");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ponto/imagens/tituloBiometria.png"))); // NOI18N
        jLabel1.setText("Sistem de Batida");

        jlData.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jlHora.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Hora:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Data:");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ponto/imagens/leitorBiometrico.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Insira sua digital no leitor");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlData, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlHora, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlData, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlHora, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(24, 24, 24)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        //Data
        Date dataSistema = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        jlData.setText(formato.format(dataSistema));

        //Hora
        Timer time = new Timer(1000, new hora());
        time.start();

        verificarBiometria();
        telaConfirmacao(nomeFuncionario);
        
    }//GEN-LAST:event_formWindowOpened

    private void dialogConfirmacaoWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dialogConfirmacaoWindowClosed
        verificarBiometria();
        telaConfirmacao(nomeFuncionario);
    }//GEN-LAST:event_dialogConfirmacaoWindowClosed

    private void dialogNaoConfirmadoWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dialogNaoConfirmadoWindowClosed
        verificarBiometria();
        telaConfirmacao(nomeFuncionario);
    }//GEN-LAST:event_dialogNaoConfirmadoWindowClosed

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
            java.util.logging.Logger.getLogger(RegistrarBatida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrarBatida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrarBatida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrarBatida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistrarBatida().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog dialogConfirmacao;
    private javax.swing.JDialog dialogNaoConfirmado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jlData;
    private javax.swing.JLabel jlHora;
    private javax.swing.JTextField txtConfirmacao;
    private javax.swing.JTextField txtHora;
    // End of variables declaration//GEN-END:variables
}
