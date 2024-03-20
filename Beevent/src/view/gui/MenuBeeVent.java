/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import model.validations.ConexionOracle;

public class MenuBeeVent extends javax.swing.JFrame {

    private java.util.Date beginningDate;
    private java.util.Date endingDate;
    int variableRecibida;
    public int userId = BeeVentGui.userId;
    public int placeId = PlacesEvent.placeId;

    public MenuBeeVent(int variable) {
        this.variableRecibida = variable; // Recibes la variable y la asignas a una variable local
        System.out.println("La variable recibida es: " + variableRecibida);
    }

    ConexionOracle conexion = new ConexionOracle();

    private Connection conectarBaseDatos() throws SQLException {
        return conexion.conectar();
    }

    public MenuBeeVent() {
        initComponents();
        initConfig();

    }

    private boolean validateDate(Date beginningDate, Date endingDate) {
        Date today = new Date();
        return (endingDate.compareTo(beginningDate) > 0 && beginningDate.compareTo(today) > 0);
    }

    private boolean validateEntries(String title, String descriptions) {
        int titleLength = title.length();
        int descriptionsLength = descriptions.length();

        return titleLength > 5 && descriptionsLength > 10;
    }

    private void initConfig() {
        jLabelSerraparera.setVisible(false);
        jLabelSendoCarbon.setVisible(false);
        jLabelConejo.setVisible(false);
    }

    public void setUser(String usuario) {
        // Mostrar el nombre de usuario en el jTextFieldUserMenu
        jLabeljWelcome.setText(usuario.toUpperCase());
    }

    public void setSerrapareraVisible(boolean visible) {
        jLabelSerraparera.setVisible(visible);
    }

    public void setSendoCarbonVisible(boolean visible) {
        jLabelSendoCarbon.setVisible(visible);
    }

    public void setConejoBuenoVisible(boolean visible) {
        jLabelConejo.setVisible(visible);
    }

    private boolean checkReservations(Date beginningDate, Date endingDate) {
        String sSQL = "SELECT COUNT(*) FROM reservations WHERE beginning_date <= ? AND ending_date >= ?";

        try (Connection con = conectarBaseDatos(); PreparedStatement pstmt = con.prepareStatement(sSQL)) {

            // Convertir java.util.Date a java.sql.Date
            java.sql.Date sqlBeginningDate = new java.sql.Date(beginningDate.getTime());
            java.sql.Date sqlEndingDate = new java.sql.Date(endingDate.getTime());

            // Establecer los valores de los parámetros en la consulta
            pstmt.setDate(1, sqlEndingDate); // La fecha de inicio de la reserva debe ser antes o igual a la fecha final del rango
            pstmt.setDate(2, sqlBeginningDate); // La fecha de fin de la reserva debe ser después o igual a la fecha inicial del rango

            // Ejecutar la consulta y obtener el resultado
            ResultSet rs = pstmt.executeQuery();

            // Procesar el resultado
            rs.next();
            int count = rs.getInt(1);

            // Devolver true si hay reservaciones dentro del rango de fechas
            return count > 0;

        } catch (SQLException e) {
            System.out.println("Error al ejecutar consulta: " + e.getMessage());
            e.printStackTrace(); // Imprimir la traza de la excepción para obtener más detalles
            return false; // Devuelve false si ocurre una excepción
        }
    }

    private boolean insertEvent(String title, String descriptions, Date beginningDate, Date endingDate) {
        System.out.println(checkReservations(beginningDate, endingDate));
        String sSQL = "INSERT INTO Events (event_id, title, descriptions, beginning_date, ending_date, review, user_id, place_id ) "
                + "VALUES ((SELECT COALESCE(MAX(event_id), 0) + 1 FROM Events), ?, ?, ?, ?, ?, ?, ?)";

        // Obtener la conexión a la base de datos
        try (Connection con = conectarBaseDatos(); PreparedStatement pstmt = con.prepareStatement(sSQL)) {
            java.sql.Date sqlDate = new java.sql.Date(beginningDate.getTime());
            java.sql.Date sqlDateFinal = new java.sql.Date(endingDate.getTime());

            String review = null;

            // Establecer los valores de los parámetros en la consulta
            pstmt.setString(1, title);
            pstmt.setString(2, descriptions);
            pstmt.setDate(3, sqlDate);
            pstmt.setDate(4, sqlDateFinal);
            pstmt.setString(5, review);
            pstmt.setInt(6, userId);
            pstmt.setInt(7, placeId);

            // Ejecutar la consulta de inserción
            int rowsInserted = pstmt.executeUpdate();

            return rowsInserted > 0; // Devuelve true si se insertó al menos una fila
        } catch (SQLException e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
            return false; // Devuelve false si ocurre una excepción
        }

    }

    private boolean insertReservations(Date beginningDate, Date endingDate) {
        // Obtener la conexión a la base de datos
        try (Connection con = conectarBaseDatos()) {
            // Obtener el próximo ID de reserva
            int nextReservationId;
            try (PreparedStatement pstmt = con.prepareStatement("SELECT COALESCE(MAX(reservation_id), 0) + 1 FROM Reservations"); ResultSet rs = pstmt.executeQuery()) {
                rs.next();
                nextReservationId = rs.getInt(1);
            }

            // Consulta de inserción con el ID de reserva obtenido previamente
            String sSQL = "INSERT INTO Reservations (reservation_id, beginning_date, ending_date, place_id, user_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(sSQL)) {
                pstmt.setInt(1, nextReservationId);
                pstmt.setDate(2, new java.sql.Date(beginningDate.getTime()));
                pstmt.setDate(3, new java.sql.Date(endingDate.getTime()));
                pstmt.setInt(4, placeId);
                pstmt.setInt(5, userId);

                // Ejecutar la consulta de inserción
                int rowsInserted = pstmt.executeUpdate();

                return rowsInserted > 0; // Devuelve true si se insertó al menos una fila
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar reserva: " + e.getMessage());
            System.out.println("TILIn");
            return false; // Devuelve false si ocurre una excepción
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

        jPanel1 = new javax.swing.JPanel();
        jPanelBookEvent = new javax.swing.JPanel();
        jTextFieldNameEvent = new javax.swing.JTextField();
        jLabelNameEvent = new javax.swing.JLabel();
        jButtonPlaces = new javax.swing.JButton();
        jLabelDescription = new javax.swing.JLabel();
        jTextFieldDescription = new javax.swing.JTextField();
        jTextBeginDate = new java.awt.Label();
        jLabelBookEvent = new javax.swing.JLabel();
        jLabeljUserMenu = new javax.swing.JLabel();
        jLabelLogoMenu = new javax.swing.JLabel();
        jLabeljWelcome = new javax.swing.JLabel();
        jButtonConfirm = new javax.swing.JButton();
        jDateChooserStart = new com.toedter.calendar.JDateChooser();
        jDateChooserFinish = new com.toedter.calendar.JDateChooser();
        jLabelSerraparera = new javax.swing.JLabel();
        jLabelSendoCarbon = new javax.swing.JLabel();
        jLabelConejo = new javax.swing.JLabel();
        jTextEndingDate = new java.awt.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(229, 248, 255));
        setMinimumSize(new java.awt.Dimension(1024, 760));

        jPanel1.setBackground(new java.awt.Color(229, 248, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1044, Short.MAX_VALUE)
        );

        jPanelBookEvent.setBackground(new java.awt.Color(229, 248, 255));
        jPanelBookEvent.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelNameEvent.setText("NAME OF EVENT");

        jButtonPlaces.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.borderColor"));
        jButtonPlaces.setText("PLACES");
        jButtonPlaces.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPlacesActionPerformed(evt);
            }
        });

        jLabelDescription.setText("DESCRIPTION");

        jTextBeginDate.setName(""); // NOI18N
        jTextBeginDate.setText("First date");

        jLabelBookEvent.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabelBookEvent.setText("BOOK YOUR EVENT");
        jLabelBookEvent.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabeljUserMenu.setFont(new java.awt.Font("Segoe UI Semibold", 0, 36)); // NOI18N
        jLabeljUserMenu.setText("WELCOME");

        jLabelLogoMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/gui/Images/BEEVENTpequeño.png"))); // NOI18N

        jLabeljWelcome.setFont(new java.awt.Font("Segoe UI Semibold", 0, 36)); // NOI18N
        String texto = "Texto en mayúsculas";
        jLabeljWelcome.setText(texto.toUpperCase());

        jButtonConfirm.setText("Book Event");
        jButtonConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmActionPerformed(evt);
            }
        });

        jLabelSerraparera.setText("Serraparera");

        jLabelSendoCarbon.setText("Sendo Carbon");

        jLabelConejo.setText("Conejo bueno");

        jTextEndingDate.setName(""); // NOI18N
        jTextEndingDate.setText("Last date");

        javax.swing.GroupLayout jPanelBookEventLayout = new javax.swing.GroupLayout(jPanelBookEvent);
        jPanelBookEvent.setLayout(jPanelBookEventLayout);
        jPanelBookEventLayout.setHorizontalGroup(
            jPanelBookEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBookEventLayout.createSequentialGroup()
                .addGroup(jPanelBookEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBookEventLayout.createSequentialGroup()
                        .addGap(261, 261, 261)
                        .addComponent(jLabelBookEvent))
                    .addGroup(jPanelBookEventLayout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addGroup(jPanelBookEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonPlaces, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelBookEventLayout.createSequentialGroup()
                                .addComponent(jLabelNameEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(jTextFieldNameEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelSerraparera)
                            .addGroup(jPanelBookEventLayout.createSequentialGroup()
                                .addGap(138, 138, 138)
                                .addComponent(jLabelSendoCarbon)
                                .addGap(50, 50, 50)
                                .addComponent(jLabelConejo))))
                    .addGroup(jPanelBookEventLayout.createSequentialGroup()
                        .addGap(210, 210, 210)
                        .addGroup(jPanelBookEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelBookEventLayout.createSequentialGroup()
                                .addComponent(jTextEndingDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooserFinish, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelBookEventLayout.createSequentialGroup()
                                .addComponent(jTextBeginDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooserStart, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBookEventLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelBookEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelBookEventLayout.createSequentialGroup()
                                .addGap(126, 126, 126)
                                .addComponent(jLabelDescription))
                            .addComponent(jTextFieldDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)))
                .addGap(0, 166, Short.MAX_VALUE))
            .addGroup(jPanelBookEventLayout.createSequentialGroup()
                .addGroup(jPanelBookEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBookEventLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabelLogoMenu)
                        .addGap(49, 49, 49)
                        .addComponent(jLabeljUserMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabeljWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelBookEventLayout.createSequentialGroup()
                        .addGap(310, 310, 310)
                        .addComponent(jButtonConfirm)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelBookEventLayout.setVerticalGroup(
            jPanelBookEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBookEventLayout.createSequentialGroup()
                .addGroup(jPanelBookEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBookEventLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelLogoMenu))
                    .addGroup(jPanelBookEventLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanelBookEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabeljWelcome, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                            .addComponent(jLabeljUserMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(13, 13, 13)
                .addComponent(jLabelBookEvent)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addGroup(jPanelBookEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNameEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNameEvent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(jButtonPlaces, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelBookEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSerraparera)
                    .addComponent(jLabelSendoCarbon)
                    .addComponent(jLabelConejo))
                .addGap(32, 32, 32)
                .addGroup(jPanelBookEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooserStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextBeginDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelBookEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooserFinish, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextEndingDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jButtonConfirm)
                .addGap(29, 29, 29))
        );

        jTextBeginDate.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelBookEvent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(155, 155, 155))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanelBookEvent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButtonPlacesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPlacesActionPerformed
        // TODO add your handling code here:
        PlacesEvent abrir = new PlacesEvent(this);
        abrir.setVisible(true);
        this.setVisible(false);

    }//GEN-LAST:event_jButtonPlacesActionPerformed

    private void jButtonConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmActionPerformed
        // TODO add your handling code here:
        boolean validations = true;
        String title = jTextFieldNameEvent.getText();
        String descriptions = jTextFieldDescription.getText();

        beginningDate = jDateChooserStart.getDate();
        endingDate = jDateChooserFinish.getDate();
        if (!validateEntries(title, descriptions)) {
            JOptionPane.showMessageDialog(this, "Titulo y descripcion no válidos, intenta ser más descriptivo");
            validations = false;
        }

        if (beginningDate == null || endingDate == null) {
            JOptionPane.showMessageDialog(this, "No has ingresado fecha en uno de los campos");
        }
        if (!validateDate(beginningDate, endingDate)) {
            JOptionPane.showMessageDialog(this, "Fechas ingresadas son incorrectas");
            validations = false;
        }

        // Aquí puedes hacer lo que necesites con la fecha seleccionada
        // Por ejemplo, puedes guardarla en una base de datos, mostrarla en otro componente, etc.
        // Mostrar un mensaje de éxito al usuario
        placeId = PlacesEvent.placeId;

        if (validations) {
            if (insertEvent(title, descriptions, beginningDate, endingDate)) {
                insertReservations(beginningDate, endingDate);
                JOptionPane.showMessageDialog(this, "Evento reservado correctamente");
                jTextFieldNameEvent.setText("");
                jTextFieldDescription.setText("");
                jDateChooserStart.setDate(null);
                jDateChooserFinish.setDate(null);
            }

        }

        // Limpiar la fecha seleccionada para futuros usos

    }//GEN-LAST:event_jButtonConfirmActionPerformed

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
            java.util.logging.Logger.getLogger(MenuBeeVent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuBeeVent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuBeeVent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuBeeVent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MenuBeeVent().setVisible(true);
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonConfirm;
    private javax.swing.JButton jButtonPlaces;
    private com.toedter.calendar.JDateChooser jDateChooserFinish;
    private com.toedter.calendar.JDateChooser jDateChooserStart;
    private javax.swing.JLabel jLabelBookEvent;
    private javax.swing.JLabel jLabelConejo;
    private javax.swing.JLabel jLabelDescription;
    private javax.swing.JLabel jLabelLogoMenu;
    private javax.swing.JLabel jLabelNameEvent;
    private javax.swing.JLabel jLabelSendoCarbon;
    private javax.swing.JLabel jLabelSerraparera;
    private javax.swing.JLabel jLabeljUserMenu;
    private javax.swing.JLabel jLabeljWelcome;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelBookEvent;
    private java.awt.Label jTextBeginDate;
    private java.awt.Label jTextEndingDate;
    private javax.swing.JTextField jTextFieldDescription;
    private javax.swing.JTextField jTextFieldNameEvent;
    // End of variables declaration//GEN-END:variables

}
