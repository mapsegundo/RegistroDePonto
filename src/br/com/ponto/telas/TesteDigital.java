/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ponto.telas;

import br.com.ponto.DAO.FuncionarioDAO;
import br.com.ponto.entidade.Funcionario;
import br.com.ponto.sdk.CisBiox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Shall
 */
public class TesteDigital extends javax.swing.JFrame {

    private static byte[] digital1;
    private static byte[] digital2;
    private static byte[] testeDigital;
    private static String nomeFuncionario;
    private javax.swing.Timer timer;

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
            //setTxtDigital2(digital2);
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
                } else {
                    JOptionPane.showMessageDialog(null, "Digital não encontrada");
                }
            } catch (Exception e) {

            }
        }
    };

    public void verificarBiometria() throws InterruptedException {
        nomeFuncionario = null;
        Thread etapaCaptura = new Thread(pegaBatida);
        Thread etapaCompara = new Thread(comparaBatida);

        etapaCaptura.start();
        etapaCaptura.join();
        etapaCompara.start();
        etapaCompara.join();
        telaConfirmacao(nomeFuncionario);
    }

    public void telaConfirmacao(String funcionario) {
        txtConfirmacao.setText(funcionario);
        dialogConfirmacao.setSize(550, 200);
        dialogConfirmacao.setLocationRelativeTo(this);
        dialogConfirmacao.show();
        Date minhaHora = new Date();
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
        txtHora.setText(formataHora.format(minhaHora));

        timer = new Timer(1 * 2000, new ActionListener() {
            public void actionPerformed(ActionEvent ev) {

                dialogConfirmacao.dispose();
            }
        });
        timer.setRepeats(false);//the timer should only go off once

        timer.start();
    }

    public List<Funcionario> listarBiometriasCadastradas() {
        try {
            FuncionarioDAO dao = new FuncionarioDAO();
            List<Funcionario> listaCadastrados = dao.listarTodosFuncionariosComBiometria();

            //Criando o objeto que vai guardar os registros e a estrutura da tabela
            DefaultTableModel modelo = (DefaultTableModel) tabelaCadastrados.getModel();

            modelo.setNumRows(0);

            //Percorrer os registros que estão na listaOS
            for (Funcionario funcionario : listaCadastrados) {
                modelo.addRow(new Object[]{
                    funcionario.getFuncPkId(),
                    funcionario,
                    funcionario.getFuncDigital1(),
                    funcionario.getFuncDigital2()
                });
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void limparDigitais() {
        digital1 = null;
        digital2 = null;
    }

    public void verificarDigital() {
        new Thread(lerDigital1).stop();
        CisBiox biox = new CisBiox();

        int iRetorno = biox.iniciar();

        if (iRetorno != 1) {
            JOptionPane.showMessageDialog(null, "Erro: " + CisBiox.mensagem(iRetorno));
            return;
        }
        btnCancelarLeitura.setEnabled(true);
        new Thread(lerDigital1).start();
    }

    public void cancelarLeitor() {
        // Instanciar a DLL
        CisBiox biox = new CisBiox();

        // Cancelar a leitura 
        biox.cancelarLeitura();

        btnCancelarLeitura.setEnabled(false);
    }

    public void popularComboBoxFuncionarios() {
        try {
            cbFuncionario.removeAllItems();
            FuncionarioDAO dao = new FuncionarioDAO();
            List<Funcionario> listaClientes = dao.listarTodosFuncionarios();

            for (Funcionario funcionario : listaClientes) {
                cbFuncionario.addItem(funcionario);
            }
        } catch (Exception e) {

        }
    }

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

        dialogConfirmacao = new javax.swing.JDialog();
        txtConfirmacao = new javax.swing.JTextField();
        txtHora = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbFuncionario = new javax.swing.JComboBox<>();
        btnDigital2 = new javax.swing.JButton();
        btnDigital1 = new javax.swing.JButton();
        btnCancelarLeitura = new javax.swing.JButton();
        btnCadastrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnVerificar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaCadastrados = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtFiltro = new javax.swing.JTextField();
        btnFiltro = new javax.swing.JButton();

        dialogConfirmacao.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dialogConfirmacao.setTitle("Confirmação");

        txtConfirmacao.setEditable(false);
        txtConfirmacao.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtConfirmacao.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        txtHora.setEditable(false);
        txtHora.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel4.setText("Hora da Batida");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("BATIDA REGISTRADA");

        javax.swing.GroupLayout dialogConfirmacaoLayout = new javax.swing.GroupLayout(dialogConfirmacao.getContentPane());
        dialogConfirmacao.getContentPane().setLayout(dialogConfirmacaoLayout);
        dialogConfirmacaoLayout.setHorizontalGroup(
            dialogConfirmacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogConfirmacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogConfirmacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                    .addComponent(txtConfirmacao)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogConfirmacaoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        dialogConfirmacaoLayout.setVerticalGroup(
            dialogConfirmacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogConfirmacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(dialogConfirmacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtConfirmacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Biometria");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Sistema Biometrico");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Área de Cadastro"));
        jPanel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Lista de Funcionários");

        cbFuncionario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbFuncionario.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                cbFuncionarioAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        btnDigital2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnDigital2.setText("Cadastrar Digital 2");
        btnDigital2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDigital2ActionPerformed(evt);
            }
        });

        btnDigital1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnDigital1.setText("Cadastrar Digital 1");
        btnDigital1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDigital1ActionPerformed(evt);
            }
        });

        btnCancelarLeitura.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnCancelarLeitura.setText("Cancelar Leitor");
        btnCancelarLeitura.setEnabled(false);
        btnCancelarLeitura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarLeituraActionPerformed(evt);
            }
        });

        btnCadastrar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnCadastrar.setText("Cadastrar");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDigital2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDigital1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelarLeitura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCadastrar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbFuncionario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDigital1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDigital2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelarLeitura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadastrar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Área de Verificação"));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Clique no botão verificar e insira sua digital");

        btnVerificar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnVerificar.setText("Verificar Biometria");
        btnVerificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(btnVerificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(btnVerificar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabelaCadastrados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Funcionário", "Digital 1", "Digital 2"
            }
        ));
        tabelaCadastrados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaCadastradosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaCadastrados);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Área de Filtragem"));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Filtrar por nome:");

        txtFiltro.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btnFiltro.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnFiltro.setText("Filtrar");
        btnFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltro))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(178, 178, 178))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
        btnCancelarLeitura.setEnabled(true);
        new Thread(lerDigital1).start();


    }//GEN-LAST:event_btnDigital1ActionPerformed

    private void btnDigital2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDigital2ActionPerformed
        CisBiox biox = new CisBiox();

        int iRetorno = biox.iniciar();

        if (iRetorno != 1) {
            JOptionPane.showMessageDialog(null, "Erro: " + CisBiox.mensagem(iRetorno));
            return;
        }
        btnCancelarLeitura.setEnabled(true);
        new Thread(lerDigital2).start();

    }//GEN-LAST:event_btnDigital2ActionPerformed

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        try {

            Funcionario funcionario = (Funcionario) cbFuncionario.getSelectedItem();
            funcionario.setFuncDigital1(digital1);
            funcionario.setFuncDigital2(digital2);

            FuncionarioDAO dao = new FuncionarioDAO();
            dao.alterar(funcionario);

            JOptionPane.showMessageDialog(null, "Biometrias cadastradas com sucesso");
            limparDigitais();
            cancelarLeitor();
            cbFuncionario.removeAllItems();
            popularComboBoxFuncionarios();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar as biometrias: " + e);
        }
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void btnVerificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerificarActionPerformed

        try {
            verificarBiometria();
            /*try {
            FuncionarioDAO dao = new FuncionarioDAO();
            CisBiox biox = new CisBiox();
            List<Funcionario> funcionarios = dao.listarTodosFuncionariosComBiometria();
            biox.iniciar();
            System.out.println(funcionarios);
            System.out.println(digital1);
            // loop em busca da digital pega anteriormente nos alunos da base
            Funcionario funcionarioEncontrado = null;
            for (Funcionario funcionario : funcionarios) {
            if (biox.compararDigital(funcionario.getFuncDigital1(), digital1) == 1) {
            funcionarioEncontrado = funcionario;
            break;
            }
            if (biox.compararDigital(funcionario.getFuncDigital2(), digital1) == 1) {
            funcionarioEncontrado = funcionario;
            break;
            }
            }
            System.out.println(funcionarioEncontrado);
            biox.finalizar();
            if (funcionarioEncontrado != null) {
            //JOptionPane.showMessageDialog(null, "Bem vindo " + funcionarioEncontrado.getFuncNome());
            telaConfirmacao(funcionarioEncontrado.getFuncNome());
            } else {
            JOptionPane.showMessageDialog(null, "Digital não encontrada");
            verificarDigital();
            }
            
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar digital: " + e);
            }*/
        } catch (InterruptedException ex) {
            Logger.getLogger(TesteDigital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnVerificarActionPerformed

    private void cbFuncionarioAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_cbFuncionarioAncestorAdded
        popularComboBoxFuncionarios();
    }//GEN-LAST:event_cbFuncionarioAncestorAdded

    private void btnCancelarLeituraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarLeituraActionPerformed
        cancelarLeitor();
    }//GEN-LAST:event_btnCancelarLeituraActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        listarBiometriasCadastradas();
    }//GEN-LAST:event_formWindowActivated

    private void tabelaCadastradosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaCadastradosMouseClicked
        try {
            cbFuncionario.getModel().setSelectedItem(tabelaCadastrados.getValueAt(tabelaCadastrados.getSelectedRow(), 1));
            Funcionario funcionario = (Funcionario) cbFuncionario.getSelectedItem();
            digital1 = funcionario.getFuncDigital1();
            digital2 = funcionario.getFuncDigital2();
            System.out.println(funcionario);
            System.out.println(digital1);
            System.out.println(digital2);
        } catch (Exception e) {

        }
    }//GEN-LAST:event_tabelaCadastradosMouseClicked

    private void btnFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltroActionPerformed
        List<Funcionario> listaFuncionarios = new ArrayList<Funcionario>();
        try {
            FuncionarioDAO dao = new FuncionarioDAO();
            String nome = "%"+txtFiltro.getText().toUpperCase()+"%";
            listaFuncionarios = dao.consultarPorFuncionario(nome);
            DefaultTableModel modelo = (DefaultTableModel) tabelaCadastrados.getModel();
            modelo.setNumRows(0);
            for (Funcionario funcionario : listaFuncionarios) {
                modelo.addRow(new Object[]{
                    funcionario.getFuncPkId(),
                    funcionario,
                    funcionario.getFuncDigital1(),
                    funcionario.getFuncDigital2()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Funcionário não encontrado");
            listarBiometriasCadastradas();
        }
    }//GEN-LAST:event_btnFiltroActionPerformed

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
    private javax.swing.JButton btnCancelarLeitura;
    private javax.swing.JButton btnDigital1;
    private javax.swing.JButton btnDigital2;
    private javax.swing.JButton btnFiltro;
    private javax.swing.JButton btnVerificar;
    private javax.swing.JComboBox<Object> cbFuncionario;
    private javax.swing.JDialog dialogConfirmacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaCadastrados;
    private javax.swing.JTextField txtConfirmacao;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JTextField txtHora;
    // End of variables declaration//GEN-END:variables

}
