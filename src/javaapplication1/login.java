/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;
import javaapplication1.db;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class login extends javax.swing.JFrame {
    /**
     * Creates new form login
     */
    
    public NewJFrame aa;
    
    String nextStep;
    
    public login(NewJFrame xx, String nextStep) {
        initComponents();
        
        this.aa=xx;
        this.nextStep = nextStep;
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
        Username = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        CheckDisplayPwd = new javax.swing.JCheckBox();
        Pwd = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        jLabel1.setText("欢迎回来！");

        jLabel2.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel2.setText("使用管理员账户登录图书借阅系统");

        jLabel3.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel3.setText("用户名");

        jLabel4.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel4.setText("密码");

        CheckDisplayPwd.setText("显示密码（您的登录仍然安全）");
        CheckDisplayPwd.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CheckDisplayPwdStateChanged(evt);
            }
        });
        CheckDisplayPwd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckDisplayPwdActionPerformed(evt);
            }
        });

        Pwd.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        Pwd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PwdActionPerformed(evt);
            }
        });
        Pwd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PwdKeyPressed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("微软雅黑", 1, 12)); // NOI18N
        jButton1.setText("登录");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(Username, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(CheckDisplayPwd)
                            .addComponent(Pwd, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CheckDisplayPwd)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startCheck(){
        db con=new db();
        ResultSet test=con.cha("select * from admin where name='"+this.Username.getText()+"'");
        if(test==null){
            JOptionPane.showMessageDialog(null, "读取数据库失败", "读取数据库失败", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try{
            if(!test.next()){
                JOptionPane.showMessageDialog(null, "登录失败", "登录失败", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String pwd=test.getString("pwd");
            
            if(this.Pwd.getText().equals(pwd)){
                //JOptionPane.showMessageDialog(null, "登录成功", "登录成功", JOptionPane.PLAIN_MESSAGE);
                this.aa.login_info.setText("欢迎您，"+this.Username.getText());
                this.aa.Loginout.setText("退出登录");
                this.aa.loginUser = this.Username.getText();
                this.aa.ContinueAction(nextStep);
                this.dispose();
            }
            else
                JOptionPane.showMessageDialog(null, "登录失败", "登录失败", JOptionPane.ERROR_MESSAGE);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        startCheck();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void PwdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PwdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PwdActionPerformed

    private void CheckDisplayPwdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckDisplayPwdActionPerformed
        // TODO add your handling code here:
        if(this.CheckDisplayPwd.isSelected()){
          this.Pwd.setEchoChar((char)0);
        }else{
          this.Pwd.setEchoChar('*');
        }
    }//GEN-LAST:event_CheckDisplayPwdActionPerformed

    private void CheckDisplayPwdStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CheckDisplayPwdStateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_CheckDisplayPwdStateChanged

    private void PwdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PwdKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyChar()==10){
            startCheck();
        }
    }//GEN-LAST:event_PwdKeyPressed

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
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CheckDisplayPwd;
    private javax.swing.JPasswordField Pwd;
    private javax.swing.JTextField Username;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
