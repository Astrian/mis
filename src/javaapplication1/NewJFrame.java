/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class NewJFrame extends javax.swing.JFrame {
    
    private int checkBorrowed(String isbn){
        db con = new db();
        ResultSet test = con.cha("select * from borrow where borrowuser = '"+this.borrowId.getText()+"' and isbn = '"+isbn+"'");
        if(test==null){
            JOptionPane.showMessageDialog(null, "读取数据库失败", "读取数据库失败", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        try{
            if(!test.next()){
                return 0;
            } 
        }catch(Exception e) {
            e.printStackTrace();
        }
        return 1; 
    }
    
    String loginUser = null;
    int borrowedBooks;
    //String nextStep = null;
    
    /**
     * Creates new form NewJFrame
     */
    private void numberChanger(int opt, String book){
        db con = new db();
        ResultSet left = con.cha("select * from book where isbn = "+book);
        
        try{
            if(left.next()){
                int leftNum = left.getInt("bleft");
                if(opt == 1){
                    leftNum--;
                }else if(opt == 0){
                    leftNum++;
                }
                //System.out.println(leftNum);
                con.runSql("update book set bleft = "+leftNum+" where isbn = "+book);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void listBorrowable(Object Isbn){
        if(Isbn != "0"){
            borrowedBooks = 0;
        }
        int i=0;
        
        db con=new db();
        ResultSet test;
        if(Isbn == "0"){
            test=con.cha("select * from book");
            for(i=0;i<100;i++){
                this.borrowablelist.getModel().setValueAt(" ", i, 0);
                this.borrowablelist.getModel().setValueAt(" ", i, 1);
                this.borrowedlist.getModel().setValueAt(" ", i, 0);
                this.borrowedlist.getModel().setValueAt(" ", i, 1);
            }
        }else{
            test=con.cha("select * from book where isbn = "+Isbn);
            for(i=0;i<100;i++){
                this.borrowablelist.getModel().setValueAt(" ", i, 0);
                this.borrowablelist.getModel().setValueAt(" ", i, 1);
            }
        }
        if(test==null){
            JOptionPane.showMessageDialog(null, "读取数据库失败", "读取数据库失败", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try{
            int j=0;
            int k=0;
            this.allReturnBtn.setEnabled(false);
            while (test.next()) {
                //ResultSet borrowed = con.cha("select * from borrow where borrowuser = '"+this.borrowId.getText()+"' and isbn = '"+test.getInt("isbn")+"'");
                if(checkBorrowed(test.getString("isbn"))==1 && Isbn=="0"){
                    this.borrowedlist.getModel().setValueAt(test.getString("isbn"), j, 0);
                    this.borrowedlist.getModel().setValueAt(test.getString("bname"), j, 1);
                    j++;
                    borrowedBooks ++;
                }else if(checkBorrowed(test.getString("isbn"))==0 && test.getInt("bleft")>0){
                    this.borrowablelist.getModel().setValueAt(test.getString("isbn"), k, 0);
                    this.borrowablelist.getModel().setValueAt(test.getString("bname"), k, 1);
                    k++;
                }
                if(j>0){
                    this.allReturnBtn.setEnabled(true);
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void checkBorrow(){
        db con=new db();
        ResultSet test=con.cha("select * from libuser where id="+this.borrowId.getText());
        if(test==null){
            JOptionPane.showMessageDialog(null, "读取数据库失败", "读取数据库失败", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try{
            if(!test.next()){
                JOptionPane.showMessageDialog(null, "无法找到该用户", "操作已停止", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else{
                int freezed;
                freezed = test.getInt("isforzed");
                if(freezed == 0){
                    this.borrowablelist.setEnabled(true);
                    this.borrowedlist.setEnabled(true);
                    //this.borrowButton.setEnabled(true);
                    this.searchByIsbnBtn.setEnabled(true);
                    //this.allReturnBtn.setEnabled(true);
                    this.userInfo.setText("<html>"+test.getString("name")+" / 账户正常使用 / 最大可借阅 "+test.getString("allowborrow")+" 本");
                    listBorrowable("0");
                }
                else{
                    this.userInfo.setText("<html>"+test.getString("name")+" / <span color='red'>账户被冻结</span> / 最大可借阅 "+test.getString("allowborrow")+" 本");
                }
            }
                //JOptionPane.showMessageDialog(null, "登录失败", "登录失败", JOptionPane.ERROR_MESSAGE);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void operateAuth(int sensitive, String nextStep){
        if( loginUser==null ){
            login loginForAddAdmin = new login(this,nextStep);
            loginForAddAdmin.show(true);
            return;
        }else if(sensitive == 1){
            CheckPwd checkPwdWin = new CheckPwd(this.loginUser, this, nextStep);
            checkPwdWin.show(true);
            return;
        }
        ContinueAction(nextStep);
    }
    
    public void ContinueAction(String nextStep){
        if (nextStep == "addAdmin"){
            addAdmin addAdminWin = new addAdmin();
            addAdminWin.show(true);
        }
        if (nextStep == "changePwd"){
            changePwd changePwdWin = new changePwd(loginUser);
            changePwdWin.show(true);
        }
        if (nextStep == "delAdmin"){
            delAdmin delAdminWindow = new delAdmin();
            delAdminWindow.show(true);
        }
        if (nextStep == "bookManage"){
            bookContent bookContentWindow = new bookContent();
            bookContentWindow.show(true);
        }
        if (nextStep == "userManage"){
            userManage userManageWindow = new userManage();
            userManageWindow.show(true);
        }
        if (nextStep == "borrow"){
            checkBorrow();
        }
    }
    
    public NewJFrame() {
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

        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        borrowId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        userInfo = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        borrowablelist = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        borrowedlist = new javax.swing.JTable();
        borrowButton = new javax.swing.JButton();
        returnButton = new javax.swing.JButton();
        searchByIsbnBtn = new javax.swing.JButton();
        allReturnBtn = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        bookManage = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        userManage = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        Loginout = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        login_info = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        borrowId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrowIdActionPerformed(evt);
            }
        });
        borrowId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                borrowIdKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                borrowIdKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Noto Sans CJK SC", 0, 13)); // NOI18N
        jLabel6.setText("用户 ID");

        jLabel7.setFont(new java.awt.Font("Noto Sans CJK SC", 0, 13)); // NOI18N
        jLabel7.setText("用户信息");

        userInfo.setFont(new java.awt.Font("Noto Sans CJK SC", 0, 13)); // NOI18N
        userInfo.setText("暂无…");

        borrowablelist.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ISBN", "书名"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        borrowablelist.setEnabled(false);
        borrowablelist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                borrowablelistMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(borrowablelist);
        if (borrowablelist.getColumnModel().getColumnCount() > 0) {
            borrowablelist.getColumnModel().getColumn(0).setResizable(false);
            borrowablelist.getColumnModel().getColumn(0).setPreferredWidth(1);
            borrowablelist.getColumnModel().getColumn(1).setResizable(false);
            borrowablelist.getColumnModel().getColumn(1).setPreferredWidth(70);
        }

        jLabel9.setFont(new java.awt.Font("Noto Sans CJK SC", 0, 13)); // NOI18N
        jLabel9.setText("可借书目");

        borrowedlist.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ISBN", "书名"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        borrowedlist.setEnabled(false);
        borrowedlist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                borrowedlistMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(borrowedlist);
        if (borrowedlist.getColumnModel().getColumnCount() > 0) {
            borrowedlist.getColumnModel().getColumn(0).setResizable(false);
            borrowedlist.getColumnModel().getColumn(0).setPreferredWidth(1);
            borrowedlist.getColumnModel().getColumn(1).setResizable(false);
            borrowedlist.getColumnModel().getColumn(1).setPreferredWidth(70);
        }

        borrowButton.setText("借→");
        borrowButton.setEnabled(false);
        borrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrowButtonActionPerformed(evt);
            }
        });

        returnButton.setText("←还");
        returnButton.setEnabled(false);
        returnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnButtonActionPerformed(evt);
            }
        });

        searchByIsbnBtn.setFont(new java.awt.Font("Noto Sans CJK SC", 0, 13)); // NOI18N
        searchByIsbnBtn.setText("通过 ISBN 号搜索");
        searchByIsbnBtn.setEnabled(false);
        searchByIsbnBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchByIsbnBtnActionPerformed(evt);
            }
        });

        allReturnBtn.setFont(new java.awt.Font("Noto Sans CJK SC", 0, 13)); // NOI18N
        allReturnBtn.setText("全部归还");
        allReturnBtn.setEnabled(false);
        allReturnBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allReturnBtnActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Noto Sans CJK SC", 0, 13)); // NOI18N
        jLabel10.setText("已借书目");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(borrowId, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(userInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(searchByIsbnBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(borrowButton)
                                    .addComponent(returnButton)))
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(allReturnBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))))
                .addGap(24, 24, 24))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(borrowId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userInfo))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addComponent(borrowButton)
                        .addGap(18, 18, 18)
                        .addComponent(returnButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchByIsbnBtn)
                    .addComponent(allReturnBtn))
                .addGap(24, 24, 24))
        );

        jTabbedPane3.addTab("借阅", jPanel3);

        jLabel2.setFont(new java.awt.Font("Noto Sans CJK SC", 0, 18)); // NOI18N
        jLabel2.setText("书籍管理");

        jLabel5.setFont(new java.awt.Font("Noto Sans CJK SC", 0, 13)); // NOI18N
        jLabel5.setText("对书籍进行新增、管理以及删除等操作。");

        bookManage.setFont(new java.awt.Font("Noto Sans CJK SC", 0, 13)); // NOI18N
        bookManage.setText("书籍管理");
        bookManage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookManageActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Noto Sans CJK SC", 0, 18)); // NOI18N
        jLabel11.setText("用户管理");

        jLabel12.setFont(new java.awt.Font("Noto Sans CJK SC", 0, 13)); // NOI18N
        jLabel12.setText("对图书借阅者进行管理。");

        userManage.setFont(new java.awt.Font("Noto Sans CJK SC", 0, 13)); // NOI18N
        userManage.setText("用户管理");
        userManage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userManageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userManage)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(bookManage)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2))
                .addContainerGap(517, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookManage)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userManage)
                .addContainerGap(384, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("图书与用户", jPanel2);

        Loginout.setText("登录到系统");
        Loginout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginoutActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Noto Sans CJK SC", 0, 18)); // NOI18N
        jLabel1.setText("管理员登录");

        login_info.setFont(new java.awt.Font("Noto Sans CJK SC", 0, 12)); // NOI18N
        login_info.setText("登录到书目管理系统");

        jLabel3.setFont(new java.awt.Font("Noto Sans CJK SC", 0, 18)); // NOI18N
        jLabel3.setText("修改管理员");

        jLabel4.setFont(new java.awt.Font("Noto Sans CJK SC", 0, 12)); // NOI18N
        jLabel4.setText("新增、注销管理员账户，以及修改管理员账户资料");

        jButton2.setText("新增管理员");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("修改密码");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("注销管理员");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addComponent(login_info)
                    .addComponent(jLabel1)
                    .addComponent(Loginout)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap(425, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(login_info)
                .addGap(10, 10, 10)
                .addComponent(Loginout)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(386, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("管理员", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LoginoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginoutActionPerformed
        // TODO add your handling code here:
        if(loginUser == null){
            login Login = new login(this,"nothing");
            Login.show(true);
        }else{
            logout Logout = new logout(this);
            Logout.show(true);
        }
    }//GEN-LAST:event_LoginoutActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        operateAuth(1, "addAdmin");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        //nextStep = "changePwd";
        operateAuth(1, "changePwd");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        //nextStep = "delAdmin";
        operateAuth(1, "delAdmin");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void bookManageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookManageActionPerformed
        // TODO add your handling code here:
        operateAuth(0, "bookManage");
    }//GEN-LAST:event_bookManageActionPerformed

    private void borrowIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_borrowIdActionPerformed

    private void returnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnButtonActionPerformed
        // TODO add your handling code here:
        String borrowBook = this.borrowedlist.getValueAt(this.borrowedlist.getSelectedRow(), 0).toString();
        db con = new db();
        con.runSql("delete from borrow where borrowuser = '"+this.borrowId.getText()+"' and isbn = '"+borrowBook+"'");
        numberChanger(0,borrowBook);
        checkBorrow();
    }//GEN-LAST:event_returnButtonActionPerformed

    private void searchByIsbnBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchByIsbnBtnActionPerformed
        // TODO add your handling code here:
        searchByIsbn searchByIsbnWindow = new searchByIsbn(this);
        searchByIsbnWindow.show(true);
    }//GEN-LAST:event_searchByIsbnBtnActionPerformed

    private void userManageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userManageActionPerformed
        // TODO add your handling code here:
        operateAuth(0, "userManage");
    }//GEN-LAST:event_userManageActionPerformed

    private void borrowIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_borrowIdKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_borrowIdKeyTyped

    private void borrowIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_borrowIdKeyPressed
        // TODO add your handling code here:
        this.userInfo.setText("暂无…");
        this.borrowablelist.setEnabled(false);
        this.borrowedlist.setEnabled(false);
        this.borrowButton.setEnabled(false);
        this.searchByIsbnBtn.setEnabled(false);
        this.allReturnBtn.setEnabled(false);
        if(evt.getKeyChar()==10){
            operateAuth(0, "borrow");
        }
    }//GEN-LAST:event_borrowIdKeyPressed

    private void borrowablelistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_borrowablelistMouseClicked
        // TODO add your handling code here:
        this.borrowButton.setEnabled(false);
        if(this.borrowablelist.getValueAt(this.borrowablelist.getSelectedRow(), 0).toString() != " "){
            this.borrowButton.setEnabled(true);
        }
    }//GEN-LAST:event_borrowablelistMouseClicked

    private void borrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowButtonActionPerformed
        // TODO add your handling code here:
        String borrowBook = this.borrowablelist.getValueAt(this.borrowablelist.getSelectedRow(), 0).toString();
        
        db con = new db();
        ResultSet test = con.cha("select * from libuser where id = "+this.borrowId.getText());
        try{
            test.next();
            if(borrowedBooks < test.getInt("allowborrow")){
                con.runSql("insert into borrow (borrowuser, isbn) values ('"+this.borrowId.getText()+"','"+borrowBook+"')");
                numberChanger(1,borrowBook);
                checkBorrow();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_borrowButtonActionPerformed

    private void borrowedlistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_borrowedlistMouseClicked
        // TODO add your handling code here:
        this.returnButton.setEnabled(false);
        if(this.borrowedlist.getValueAt(this.borrowedlist.getSelectedRow(), 0).toString() != " "){
            this.returnButton.setEnabled(true);
        }
    }//GEN-LAST:event_borrowedlistMouseClicked

    private void allReturnBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allReturnBtnActionPerformed
        // TODO add your handling code here:
        db con = new db();
        ResultSet test = con.cha("select * from borrow where borrowuser = '"+this.borrowId.getText()+"'");
        ResultSet test1;
        try{
            long book;
            int number;
            while(test.next()){
                book = test.getLong("isbn");
                test1 = con.cha("select * from book where isbn = "+book);
                test1.next();
                number = test1.getInt("bleft");
                number++;
                con.runSql("update book set bleft = "+number+" where isbn = "+book);
                con.runSql("delete from borrow where borrowuser = '"+this.borrowId.getText()+"'");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        checkBorrow();
    }//GEN-LAST:event_allReturnBtnActionPerformed

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
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton Loginout;
    private javax.swing.JButton allReturnBtn;
    private javax.swing.JButton bookManage;
    private javax.swing.JButton borrowButton;
    private javax.swing.JTextField borrowId;
    private javax.swing.JTable borrowablelist;
    private javax.swing.JTable borrowedlist;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    public javax.swing.JLabel login_info;
    private javax.swing.JButton returnButton;
    private javax.swing.JButton searchByIsbnBtn;
    private javax.swing.JLabel userInfo;
    private javax.swing.JButton userManage;
    // End of variables declaration//GEN-END:variables
}
