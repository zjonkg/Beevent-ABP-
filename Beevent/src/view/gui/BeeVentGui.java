/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.gui;

import model.validations.UserDataValidations;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.validations.ConexionOracle;
import java.sql.Timestamp;

/*
/**
 *
 * @author angelvintos
 */
public class BeeVentGui extends javax.swing.JFrame {

    String sSQL = "SELECT user_id ,password FROM USERS WHERE username = ?";
    String sSQL2 = "SELECT email FROM Users WHERE email = ? ";
    String sSQL3 = "SELECT username FROM Users WHERE username = ? ";

    ConexionOracle conexion = new ConexionOracle();

    private Connection conectarBaseDatos() throws SQLException {
        return conexion.conectar();
    }

    private String email;
    private String emailDB;
    private String userDB;
    public static int userId;
    private String passwordDB;

    static boolean isLogin = false;

    /**
     * Creates new form BeeVentGui
     */
    public BeeVentGui() {
        initComponents();
        initConfig();
    }

    private void initConfig() {
        jLabelUser.setVisible(false);
        jLabelEmail.setVisible(false);
        jLabelPassword.setVisible(false);
        jTextFieldUser.setVisible(false);
        jPasswordField.setVisible(false);
        jLabelEmail.setVisible(false);
        jTextFieldPhone.setVisible(false);
        jLabelPhone.setVisible(false);
        jTextFieldEmail.setVisible(false);
        jButtonforgottenpassword.setVisible(false);
    }

    private boolean validateUser(String password) {
        try {
            Connection con = conectarBaseDatos();
            PreparedStatement pstmt = con.prepareStatement(sSQL);

            // Establece el valor del parámetro en la consulta
            pstmt.setString(1, email);

            try (ResultSet res = pstmt.executeQuery()) {
                if (res.next()) {
                    // Recoge la contraseña del resultado
                    userId = res.getInt("user_id");
                    passwordDB = res.getString("password");
                    
                } else {
                    try {
                        // Código que podría lanzar NullPointerException
                        // Por ejemplo:
                        // Object obj = data.getClass();
                    } catch (NullPointerException e) {

                    }
                    System.out.println("No se encontró ninguna contraseña para el email proporcionado.");
                }
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return password.equals(passwordDB);

    }

    private boolean validateRegister(String email) {
        try {
            Connection con = conectarBaseDatos();
            PreparedStatement pstmt = con.prepareStatement(sSQL2);

            // Establece el valor del parámetro en la consulta
            pstmt.setString(1, email);

            try (ResultSet res = pstmt.executeQuery()) {
                if (res.next()) {
                    emailDB = res.getString("email");
                } else {
                    try {
                        // Código que podría lanzar NullPointerException
                        // Por ejemplo:
                        // Object obj = data.getClass();
                    } catch (NullPointerException e) {

                    }
                    System.out.println("No se encontró ninguna contraseña para el email proporcionado.");
                }
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return email.equals(emailDB);
    }

    private boolean insertUser(String username, String email, String password) {
        String sSQL6 = "INSERT INTO Users (user_id, username, email, password, register_date) "
                + "VALUES ((SELECT COALESCE(MAX(user_id), 0) + 1 FROM Users), ?, ?, ?, ?)";

        Timestamp registerDate = new Timestamp(System.currentTimeMillis());
        // Obtener la conexión a la base de datos
        try (Connection con = conectarBaseDatos(); PreparedStatement pstmt = con.prepareStatement(sSQL6)) {

            // Establecer los valores de los parámetros en la consulta
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setTimestamp(4, registerDate);

            // Ejecutar la consulta de inserción
            int rowsInserted = pstmt.executeUpdate();

            return rowsInserted > 0; // Devuelve true si se insertó al menos una fila
        } catch (SQLException e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
            return false; // Devuelve false si ocurre una excepción
        }
    }

    private boolean validateUserRegister(String user) {
        try {
            Connection con = conectarBaseDatos();
            PreparedStatement pstmt = con.prepareStatement(sSQL3);

            // Establece el valor del parámetro en la consulta
            pstmt.setString(1, user);

            try (ResultSet res = pstmt.executeQuery()) {
                if (res.next()) {
                    userDB = res.getString("username");
                    System.out.println(userDB);
                    return user.equals(userDB);

                } else {
                    try {
                    } catch (NullPointerException e) {

                    }
                    return false;
                }
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonLogin = new javax.swing.JButton();
        jButtonRegister = new javax.swing.JButton();
        jLabelPassword = new javax.swing.JLabel();
        jTextFieldUser = new javax.swing.JTextField();
        jLabelUser = new javax.swing.JLabel();
        jLabelEmail = new javax.swing.JLabel();
        jPasswordField = new javax.swing.JPasswordField();
        jTextFieldEmail = new javax.swing.JTextField();
        jLabelLogo = new javax.swing.JLabel();
        jButtonSubmit = new javax.swing.JButton();
        jLabelPhone = new javax.swing.JLabel();
        jTextFieldPhone = new javax.swing.JTextField();
        jButtonforgottenpassword = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(229, 248, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonLogin.setText("Login");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 350, 100, 30));

        jButtonRegister.setText("Register");
        jButtonRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegisterActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonRegister, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 400, 100, 30));

        jLabelPassword.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelPassword.setText("Password");
        jPanel1.add(jLabelPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 480, 50, 20));
        jPanel1.add(jTextFieldUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 470, 260, 30));

        jLabelUser.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelUser.setText("User");
        jPanel1.add(jLabelUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 50, 20));

        jLabelEmail.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelEmail.setText("Email");
        jPanel1.add(jLabelEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, 50, 20));
        jPanel1.add(jPasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 470, 260, 30));
        jPanel1.add(jTextFieldEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 520, 260, 30));

        jLabelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/gui/Images/BEEVENT (2).png"))); // NOI18N
        jPanel1.add(jLabelLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, -20, 510, 510));

        jButtonSubmit.setText("Submit");
        jButtonSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSubmitActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonSubmit, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 620, -1, -1));

        jLabelPhone.setText("Phone");
        jPanel1.add(jLabelPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 530, -1, -1));
        jPanel1.add(jTextFieldPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 520, 260, 30));

        jButtonforgottenpassword.setText("Have you forgotten the password?");
        jButtonforgottenpassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonforgottenpasswordActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonforgottenpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 620, -1, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        // TODO add your handling code here:
        isLogin = true;
        jLabelUser.setVisible(true);
        jLabelPassword.setVisible(true);
        jTextFieldUser.setVisible(true);
        jPasswordField.setVisible(true);
        jLabelEmail.setVisible(false);
        jTextFieldEmail.setVisible(false);
        jTextFieldPhone.setVisible(false);
        jLabelPhone.setVisible(false);
        jButtonforgottenpassword.setVisible(false);


    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void jButtonRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegisterActionPerformed

        // TODO add your handling code here:
        isLogin = false;
        jLabelUser.setVisible(true);
        jLabelPassword.setVisible(true);
        jTextFieldUser.setVisible(true);
        jPasswordField.setVisible(true);
        jLabelEmail.setVisible(true);
        jTextFieldEmail.setVisible(true);
        jTextFieldPhone.setVisible(true);
        jLabelPhone.setVisible(true);
        jButtonforgottenpassword.setVisible(false);
    }//GEN-LAST:event_jButtonRegisterActionPerformed

    private void jButtonSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSubmitActionPerformed
        // TODO add your handling code here:

        if (isLogin) {
            email = jTextFieldUser.getText();
            String password = jPasswordField.getText();
            if (validateUser(password)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.");
                MenuBeeVent nuevoFrame = new MenuBeeVent();
                nuevoFrame.setUser(jTextFieldUser.getText());
                nuevoFrame.setVisible(true);

                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Nombre de usuario o contraseña incorrectos.");
                jButtonforgottenpassword.setVisible(true);
            }
        } else {

            String user = jTextFieldUser.getText();
            email = jTextFieldEmail.getText();
            String password = jPasswordField.getText();

            if (UserDataValidations.checkEmail(email)) {
                if (!validateRegister(email)) {
                    if (!validateUserRegister(user)) {
                        System.out.println("FUNCIONA CHAVAL");
                        if (insertUser(user, email, password)) {
                            JOptionPane.showMessageDialog(this, "Usuario registrado correctamente");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Usuario ya registrado");
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Correo electrónico ya registrado");
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "Correo electrónico inválido");
            }

        }
    }//GEN-LAST:event_jButtonSubmitActionPerformed

    private void jButtonforgottenpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonforgottenpasswordActionPerformed
        // TODO add your handling code here:
        ForgottenPassWord nuevoFrame = new ForgottenPassWord();
        nuevoFrame.setVisible(true);
        this.setVisible(false);


    }//GEN-LAST:event_jButtonforgottenpasswordActionPerformed

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
            java.util.logging.Logger.getLogger(BeeVentGui.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BeeVentGui.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BeeVentGui.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BeeVentGui.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new BeeVentGui().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonRegister;
    private javax.swing.JButton jButtonSubmit;
    private javax.swing.JButton jButtonforgottenpassword;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelLogo;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelPhone;
    private javax.swing.JLabel jLabelUser;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldPhone;
    private javax.swing.JTextField jTextFieldUser;
    // End of variables declaration//GEN-END:variables
}
