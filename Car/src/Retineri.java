//import JavaDatabaseBible.ch17.Xbeans.SQLQueryBeanTest;

import Xbeans.SQLQueryBeanTest;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Retineri extends javax.swing.JFrame {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    ResultSet rs2 = null;
    PreparedStatement pst2 = null;
    ResultSet rs3 = null;
    PreparedStatement pst3 = null;
    PreparedStatement pstdelete = null;
    PreparedStatement pststergMembri = null;
    String Table_click2;
    String Table_click3;
    Integer suma_taxa = 0;
    Integer suma_dob = 0;
    Integer suma_rata = 0;
    Integer suma_fond = 0;
    Integer suma_ret = 0;
    Integer ultima_luna = 0;
    String  path = System.getProperty("user.dir");
    String curent, lcurenta,acurent;
    int idcurent = 0;
  
    
    public Retineri() {
        initComponents();
        conn = javaconnect.ConnecrDb();
        Update_table();
        Update_table2();
        LunaCurenta();
        //FillCombo();
        //CurrentDate();
    }

    public void close(){
         WindowEvent winClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING); 
         Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
         }
    
    private void Update_table(){
    try{
       String sql = "select marca,nume,taxa,dobanda,rata,fond,retinere from Cotizatii where id_luna = '"+Table_click2+"' order by nume asc";
        // String sql = "select id,an,luna,nume,marca,retinere from Cotizatii where id_luna = '"+Table_click2+"'";
       // String sql = "select c.id,c.an,c.luna,c.nume,c.prenume,c.marca,c.retinere from Cotizatii c, luni l where c.id_luna = '"+Table_click2+"'";
       // String sql = "select id,id_luna,an,luna,nume,prenume,marca,retinere from Cotizatii";
        pst = conn.prepareStatement(sql);
        rs = pst.executeQuery();
        Tabel_membri.setModel(DbUtils.resultSetToTableModel(rs));
        Tabel_membri.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Tabel_membri.getColumnModel().getColumn(0).setPreferredWidth(40);
        Tabel_membri.getColumnModel().getColumn(1).setPreferredWidth(190);
        Tabel_membri.getColumnModel().getColumn(2).setPreferredWidth(40);
        Tabel_membri.getColumnModel().getColumn(3).setPreferredWidth(60);
        Tabel_membri.getColumnModel().getColumn(4).setPreferredWidth(40); 
        Tabel_membri.getColumnModel().getColumn(5).setPreferredWidth(40);
        Tabel_membri.getColumnModel().getColumn(6).setPreferredWidth(50);
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        Tabel_membri.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
        Tabel_membri.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        Tabel_membri.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        Tabel_membri.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        Tabel_membri.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        Tabel_membri.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);  
    }
    catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }finally{
    try{
        rs.close();
        pst.close();
    }catch(Exception e){}
    }
    }
 
    private void Update_table2(){
    try{
        String sql2 = "select id_luni,an_luni,luna_luni from luni order by id_luni desc ";
        pst2 = conn.prepareStatement(sql2);
        rs2 = pst2.executeQuery();
        Tabel_luni.setModel(DbUtils.resultSetToTableModel(rs2));
        suma_retineri();
    }
    catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }finally{
    try{
        rs2.close();
        pst2.close();
    }catch(Exception e){}
    }
    }
    
    private void suma_retineri(){
    try{
        String suma = "select sum(taxa),sum(dobanda),sum(rata),sum(fond),sum(retinere) from Cotizatii  where id_luna = '"+Table_click2+"' ";
        pst3 = conn.prepareStatement(suma);
        rs3 = pst3.executeQuery();
        suma_taxa = rs3.getInt(1);
        suma_dob = rs3.getInt(2);
        suma_rata = rs3.getInt(3);
        suma_fond = rs3.getInt(4);
        suma_ret = rs3.getInt(5);
        txt_staxa.setText(suma_taxa+"");
        txt_sdob.setText(suma_dob+"");
        txt_srata.setText(suma_rata+"");
        txt_sfond.setText(suma_fond+"");
        txt_sret.setText(suma_ret+"");
            }
    catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }finally{
    try{
        rs3.close();
        pst3.close();
    }catch(Exception e){}
    }
    }
    
    private void LunaCurenta(){
    try{
    String sqlcurent = "Select * from Firma";
    pst = conn.prepareStatement(sqlcurent);
    rs = pst.executeQuery();
    while(rs.next()){
    curent = rs.getString("id_luni");
    lcurenta = rs.getString("luna_luni");
    acurent = rs.getString("an_luni");
    txt_idlcur.setText(curent);
    txt_lcur.setText(lcurenta);
    txt_acur.setText(acurent);
    idcurent = Integer.parseInt(curent);
    }
    }catch (Exception e){
    JOptionPane.showMessageDialog(null, e);
    } 
    }
    
    private void FillCombo(){
    try{
    String sql1 = "select * from luni ";
    pst = conn.prepareStatement(sql1);
    rs = pst.executeQuery();
    while(rs.next()){
    String id = rs.getString("id_luni");
    //ComboBox_id.addItem(id);
    }
    }catch (Exception e){
    JOptionPane.showMessageDialog(null, e);
    }    
    }
    
    private void Adaug_luna(){
        try{
       
        String sterg = "Delete from Manevra";
        pst = conn.prepareStatement(sterg);
        pst.execute();
        String adaug_luna = "Insert into Manevra select * from Cotizatii where id_luna = '"+Table_click3+"'";
     //   String adaug_luna = "Insert into Manevra select * from Cotizatii ";    
        pst = conn.prepareStatement(adaug_luna);
        pst.execute();
        String actualizare = "update Manevra set id_luna = 3,luna = 11";
        pst = conn.prepareStatement(actualizare);
        pst.execute();
     
        }catch (Exception e){
        JOptionPane.showMessageDialog(null, e);
        } 
    }
    
    private void Clear_luna(){
    try{
        txt_id1.setText("");
        txt_an1.setText("");
        txt_luna1.setText("");
    }catch (Exception e){
    JOptionPane.showMessageDialog(null, e);
    } 
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabel_membri = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txt_an1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_id1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabel_luni = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        txt_luna1 = new javax.swing.JTextField();
        btnAdaugr_luni = new javax.swing.JButton();
        btnModific_luni = new javax.swing.JButton();
        btnSterg_luni = new javax.swing.JButton();
        btnClear_luni = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_dobanda = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_fond = new javax.swing.JTextField();
        txt_rata = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txt_marca = new javax.swing.JTextField();
        txt_taxa = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_nume = new javax.swing.JTextField();
        txt_retinere = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        txt_sret = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        btnSterg_membri = new javax.swing.JButton();
        btnModific_membri = new javax.swing.JButton();
        btnAdaug_membri = new javax.swing.JButton();
        btnClear_membri = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btn_Tiparire = new javax.swing.JButton();
        btn_export = new javax.swing.JButton();
        btn_Pdf = new javax.swing.JButton();
        txt_sfond = new javax.swing.JTextField();
        txt_srata = new javax.swing.JTextField();
        txt_sdob = new javax.swing.JTextField();
        txt_staxa = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_idlcur = new javax.swing.JTextField();
        txt_lcur = new javax.swing.JTextField();
        txt_acur = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("C.A.R. Centrul de plasament nr. 1");
        setPreferredSize(new java.awt.Dimension(1020, 641));

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(998, 700));

        jPanel1.setPreferredSize(new java.awt.Dimension(993, 700));

        Tabel_membri.setModel(new javax.swing.table.DefaultTableModel(
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
        Tabel_membri.getTableHeader().setReorderingAllowed(false);
        Tabel_membri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabel_membriMouseClicked(evt);
            }
        });
        Tabel_membri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Tabel_membriKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(Tabel_membri);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Luni de lucru"));

        jLabel15.setText("Luna");

        jLabel13.setText("Id");

        txt_id1.setEditable(false);

        Tabel_luni.setModel(new javax.swing.table.DefaultTableModel(
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
        Tabel_luni.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabel_luniMouseClicked(evt);
            }
        });
        Tabel_luni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Tabel_luniKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(Tabel_luni);

        jLabel14.setText("An");

        btnAdaugr_luni.setText("Salvez");
        btnAdaugr_luni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdaugr_luniActionPerformed(evt);
            }
        });

        btnModific_luni.setText("Modific");
        btnModific_luni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModific_luniActionPerformed(evt);
            }
        });

        btnSterg_luni.setText("Sterg");
        btnSterg_luni.setPreferredSize(new java.awt.Dimension(65, 23));
        btnSterg_luni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSterg_luniActionPerformed(evt);
            }
        });

        btnClear_luni.setText("Adaug");
        btnClear_luni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear_luniActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel14))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_luna1)
                                    .addComponent(txt_an1)
                                    .addComponent(txt_id1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnSterg_luni, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnModific_luni, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAdaugr_luni, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnClear_luni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_an1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_luna1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addComponent(btnClear_luni)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAdaugr_luni)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModific_luni)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSterg_luni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Date despre membri si retineri"));

        jLabel8.setText("Total retinere");

        jLabel9.setText("Taxa de inscriere");

        jLabel10.setText("Dobanda");

        jLabel6.setText("Marca");

        jLabel12.setText("Depunere fond");

        txt_taxa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_taxaActionPerformed(evt);
            }
        });

        jLabel11.setText("Rata imprumut");

        jLabel3.setText("Nume");

        txt_id.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_retinere, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txt_id)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addGap(10, 10, 10)
                                .addComponent(txt_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_taxa, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_nume, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_fond, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_rata, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_dobanda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_taxa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_dobanda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_rata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_fond, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_retinere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txt_sret.setEditable(false);

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnSterg_membri.setText("Sterg");
        btnSterg_membri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSterg_membriActionPerformed(evt);
            }
        });

        btnModific_membri.setText("Modific");
        btnModific_membri.setPreferredSize(new java.awt.Dimension(59, 23));
        btnModific_membri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModific_membriActionPerformed(evt);
            }
        });

        btnAdaug_membri.setText("Salvez");
        btnAdaug_membri.setPreferredSize(new java.awt.Dimension(65, 23));
        btnAdaug_membri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdaug_membriActionPerformed(evt);
            }
        });

        btnClear_membri.setText("Adaug");
        btnClear_membri.setPreferredSize(new java.awt.Dimension(65, 23));
        btnClear_membri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear_membriActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnClear_membri, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .addComponent(btnSterg_membri, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnModific_membri, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnAdaug_membri, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear_membri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdaug_membri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSterg_membri)
                    .addComponent(btnModific_membri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_Tiparire.setText("Tiparire");
        btn_Tiparire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TiparireActionPerformed(evt);
            }
        });

        btn_export.setText("Export");
        btn_export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exportActionPerformed(evt);
            }
        });

        btn_Pdf.setText("Pdf");
        btn_Pdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PdfActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_Pdf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Tiparire)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_export)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 9, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Pdf)
                    .addComponent(btn_Tiparire)
                    .addComponent(btn_export))
                .addContainerGap())
        );

        txt_sfond.setEditable(false);

        txt_srata.setEditable(false);

        txt_sdob.setEditable(false);

        txt_staxa.setEditable(false);

        jLabel4.setText("Luna curenta:");

        txt_idlcur.setEditable(false);
        txt_idlcur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_idlcurMouseClicked(evt);
            }
        });
        txt_idlcur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idlcurActionPerformed(evt);
            }
        });

        jButton2.setText("Iesire");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_lcur, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_acur))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_idlcur, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_staxa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_sdob, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_srata, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_sfond, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_sret, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_idlcur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_lcur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_acur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_staxa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_sdob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_srata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_sfond, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_sret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane1.addTab("Retineri                             ", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(1016, 679));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Tabel_membriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabel_membriMouseClicked
         try{
       int row = Tabel_membri.getSelectedRow();
       String Table_click = (Tabel_membri.getModel().getValueAt(row, 0).toString());
       String sql = "select * from Cotizatii where marca='"+Table_click+"'  and id_luna = '"+Table_click2+"' ";
       pst = conn.prepareStatement(sql);
       rs = pst.executeQuery();
       if(rs.next()){
           
       String add1 = rs.getString("taxa");
       txt_taxa.setText(add1);
       String add2 = rs.getString("dobanda");
       txt_dobanda.setText(add2);
       String add3 = rs.getString("nume");
       txt_nume.setText(add3);
       String add4 = rs.getString("rata");
       txt_rata.setText(add4);
       String add5 = rs.getString("id");
       txt_id.setText(add5);
       String add6 = rs.getString("marca");
       txt_marca.setText(add6);
       String add7 = rs.getString("retinere");
       txt_retinere.setText(add7);
       String add8 = rs.getString("fond");
       txt_fond.setText(add8);
       }
       }catch(Exception e){
       JOptionPane.showMessageDialog(null, e);
    }//GEN-LAST:event_Tabel_membriMouseClicked
}
    private void Tabel_membriKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabel_membriKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
        try{
       int row = Tabel_membri.getSelectedRow();
       String Table_click = (Tabel_membri.getModel().getValueAt(row, 0).toString());
       String sql = "select * from Cotizatii where marca='"+Table_click+"' and id_luna = '"+Table_click2+"'  ";
        pst = conn.prepareStatement(sql);
       rs = pst.executeQuery();
       if(rs.next()){
           
       String add1 = rs.getString("taxa");
       txt_taxa.setText(add1);
       String add2 = rs.getString("dobanda");
       txt_dobanda.setText(add2);
       String add7 = rs.getString("rata");
       txt_rata.setText(add7);
       String add8 = rs.getString("fond");
       txt_fond.setText(add8);    
       String add3 = rs.getString("nume");
       txt_nume.setText(add3);
       String add4 = rs.getString("id");
       txt_id.setText(add4);
       String add5 = rs.getString("marca");
       txt_marca.setText(add5);
       String add6 = rs.getString("retinere");
       txt_retinere.setText(add6);
       }
       }catch(Exception e){
       JOptionPane.showMessageDialog(null, e);
       }
        }
    }//GEN-LAST:event_Tabel_membriKeyReleased

    private void btnAdaug_membriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdaug_membriActionPerformed
        try{
        String delete = "delete from manevra";
        pstdelete = conn.prepareStatement(delete);
        pstdelete.execute();
        
        String sql = "INSERT into manevra (an,luna,id_luna,nume,marca,taxa,dobanda,rata,fond,retinere) values (?,?,?,?,?,?,?,?,?,?)";    
        pst = conn.prepareStatement(sql);
        pst.setString(1, txt_acur.getText());
        pst.setString(2, txt_lcur.getText());
        pst.setString(3, txt_idlcur.getText());
        pst.setString(4, txt_nume.getText());
        pst.setString(5, txt_marca.getText());
        pst.setString(6, txt_taxa.getText());
        pst.setString(7, txt_dobanda.getText());
        pst.setString(8, txt_rata.getText());
        pst.setString(9, txt_fond.getText());
        pst.setString(10, txt_retinere.getText());
        pst.execute();
        
        String adaug_pers = "Insert into Cotizatii (an,luna,id_luna,nume,marca,taxa,dobanda,rata,fond,retinere) select an,luna,id_luna,nume,marca,taxa,dobanda,rata,fond,retinere from Manevra ";
        pst = conn.prepareStatement(adaug_pers);
        pst.execute();
        
        JOptionPane.showMessageDialog(null, "Introdus");
        }
        catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
        Update_table();
    }//GEN-LAST:event_btnAdaug_membriActionPerformed

    private void btnSterg_membriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSterg_membriActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "Do you really want to delete?","Delete confirmation",JOptionPane.YES_NO_OPTION);
        if(p==0){ 
        String sql = "delete from Cotizatii where id = ?";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_id.getText());
            pst.execute();
             JOptionPane.showMessageDialog(null, "Deleted");
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        }
        Update_table();}
    }//GEN-LAST:event_btnSterg_membriActionPerformed

    private void btnModific_membriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModific_membriActionPerformed
       try{
            String value1 = txt_taxa.getText();
            String value2 = txt_dobanda.getText();
            String value3 = txt_nume.getText();
            String value4 = txt_rata.getText();
            String value5 = txt_id.getText();
            String value6 = txt_marca.getText();
            String value7 = txt_retinere.getText();
            String value8 = txt_fond.getText();
//            String sql = "update Cotizatii set an = '"+value1+"', luna = '"+value2+"', nume = '"+value3+"', id = '"+value5+"', marca = '"+value6+"', retinere = '"+value7+"' where id = '"+value5+"'";
             String sql = "update Cotizatii set nume = '"+value3+"', taxa = '"+value1+"',dobanda = '"+value2+"',rata = '"+value4+"',fond = '"+value8+"', marca = '"+value6+"', retinere = '"+value7+"' where id = '"+value5+"' and id_luna='"+Table_click2+"' ";
            pst = conn.prepareStatement(sql);
            pst.execute();
            Update_table();
            JOptionPane.showMessageDialog(null, "Date modificate");
            
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        }
        
    }//GEN-LAST:event_btnModific_membriActionPerformed

    private void btnClear_membriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear_membriActionPerformed
        txt_marca.setText("");
        txt_nume.setText("");
        txt_taxa.setText("");
        txt_dobanda.setText("");
        txt_rata.setText("");
        txt_fond.setText("");
        txt_retinere.setText("");
    }//GEN-LAST:event_btnClear_membriActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void Tabel_luniKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabel_luniKeyReleased
         if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
        try{
       int row = Tabel_luni.getSelectedRow();
       Table_click2 = (Tabel_luni.getModel().getValueAt(row, 0).toString());
       String sql2 = "select * from luni where id_luni='"+Table_click2+"' ";
       pst2 = conn.prepareStatement(sql2);
       rs2 = pst2.executeQuery();
       if(rs2.next()){
           
       String add1 = rs2.getString("id_luni");
       txt_id1.setText(add1);
       String add2 = rs2.getString("an_luni");
       txt_an1.setText(add2);
       String add3 = rs2.getString("luna_luni");
       txt_luna1.setText(add3);
       Update_table();
       suma_retineri();
    
       }
       }catch(Exception e){
       JOptionPane.showMessageDialog(null, e);
       }
        }
    }//GEN-LAST:event_Tabel_luniKeyReleased

    private void Tabel_luniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabel_luniMouseClicked
          try{
       int row = Tabel_luni.getSelectedRow();
       Table_click2 = (Tabel_luni.getModel().getValueAt(row, 0).toString());       
       String sql2 = "select * from luni where id_luni='"+Table_click2+"' ";
       pst2 = conn.prepareStatement(sql2);
       rs2 = pst2.executeQuery();
       if(rs2.next()){
           
       String add1 = rs2.getString("id_luni");
       txt_id1.setText(add1);
       String add2 = rs2.getString("an_luni");
       txt_an1.setText(add2);
       String add3 = rs2.getString("luna_luni");
       txt_luna1.setText(add3);
       Update_table();
       suma_retineri();
        }
           }catch(Exception e){
       JOptionPane.showMessageDialog(null, e);
       }
    }//GEN-LAST:event_Tabel_luniMouseClicked

    private void btnAdaugr_luniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdaugr_luniActionPerformed
       try{
        idcurent++; 
        txt_id1.setText(idcurent+"");
        String sql = "INSERT into luni (id_luni,an_luni,luna_luni) values (?,?,?)";    
              
        pst = conn.prepareStatement(sql);
        pst.setString(1, txt_id1.getText());
        pst.setString(2, txt_an1.getText());
        pst.setString(3, txt_luna1.getText());
               
        pst.execute();
        
        String Table_click3 = txt_id1.getText().toString();
       
        int newID = Integer.parseInt(Table_click3);
        int oldID = newID - 1; 
         try{
        
       String sterg = "Delete from Manevra";
       pst = conn.prepareStatement(sterg);
       pst.execute();
     
       String adaug_luna = "Insert into Manevra select * from Cotizatii where id_luna = '"+oldID+"'";
       pst = conn.prepareStatement(adaug_luna);
       pst.execute();
    
       String actualizare = "update Manevra set id_luna = '"+newID+"',an = '"+Integer.parseInt(txt_an1.getText())+"', luna = '"+Integer.parseInt(txt_luna1.getText())+"', taxa = 0";
       pst = conn.prepareStatement(actualizare);
       pst.execute();
       
       String adaug_luna1 = "Insert into Cotizatii (an,luna,nume,marca,cnp,id_luna,dobanda,rata,fond,retinere) select an,luna,nume,marca,cnp,id_luna,dobanda,rata,fond,retinere from Manevra ";
       pst = conn.prepareStatement(adaug_luna1);
       pst.execute();
    
       String actFirma = "update Firma set id_luni = '"+newID+"',an_luni = '"+Integer.parseInt(txt_an1.getText())+"', luna_luni = '"+Integer.parseInt(txt_luna1.getText())+"'";
       pst = conn.prepareStatement(actFirma);
       pst.execute();        
       
        }catch (Exception e){
        JOptionPane.showMessageDialog(null, e);
        } 
        
        JOptionPane.showMessageDialog(null, "Luna noua introdusa");
        }
        catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
        Update_table2();
        LunaCurenta();
    }//GEN-LAST:event_btnAdaugr_luniActionPerformed

    private void btnClear_luniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear_luniActionPerformed

        txt_id1.setText("");
        txt_an1.setText("");
        txt_luna1.setText("");
    }//GEN-LAST:event_btnClear_luniActionPerformed

    private void btnSterg_luniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSterg_luniActionPerformed
      int p = JOptionPane.showConfirmDialog(null, "Atentie se poate sterge doar ultima luna! Sigur vreti sa stergeti?","Delete confirmation",JOptionPane.YES_NO_OPTION);
        if(p==0){ 
//        JOptionPane.showMessageDialog(null, idcurent);  
        
        int lcur = 0;
        int acur = 0;
        
        String sql = "delete from luni where id_luni = ?";
        String stergMembri = "delete from Cotizatii where id_luna = ?";    
        try{
          pst = conn.prepareStatement(sql);
          pst.setInt(1,idcurent);
          pst.execute();
          
          pststergMembri = conn.prepareStatement(stergMembri);
          pststergMembri.setInt(1,idcurent);
          pststergMembri.execute();
          
          idcurent--;
          Update_table2(); 
          
          String sql2 = "select * from luni where id_luni='"+idcurent+"' ";
          pst2 = conn.prepareStatement(sql2);
          rs2 = pst2.executeQuery();
          if(rs2.next()){
           
          String add1 = rs2.getString("id_luni");
          txt_id1.setText(add1);
          String add2 = rs2.getString("an_luni");
          txt_an1.setText(add2);
          String add3 = rs2.getString("luna_luni");
          txt_luna1.setText(add3);
          Update_table();
          suma_retineri();
        }  
          
          String actFirma = "update Firma set id_luni = '"+idcurent+"',an_luni = '"+Integer.parseInt(txt_an1.getText())+"', luna_luni = '"+Integer.parseInt(txt_luna1.getText())+"'";
          pst = conn.prepareStatement(actFirma);
          pst.execute();             
        LunaCurenta();
        JOptionPane.showMessageDialog(null, "Luna a fost stearsa");
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        }     
      }
    }//GEN-LAST:event_btnSterg_luniActionPerformed

    private void btnModific_luniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModific_luniActionPerformed
       try{
            String value1 = txt_id1.getText(); 
            String value2 = txt_an1.getText();
            String value3 = txt_luna1.getText();
            String sql = "update luni set id_luni = '"+value1+"', an_luni = '"+value2+"', luna_luni = '"+value3+"' where id_luni = '"+value1+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Date modificate");
            
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        }
        Update_table2();
    }//GEN-LAST:event_btnModific_luniActionPerformed

    private void btn_TiparireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TiparireActionPerformed
         try {
             
        String stergp = "Delete from Print";
        pst = conn.prepareStatement(stergp);
        pst.execute();
        
        String stergm = "Delete from Manevra";
        pst = conn.prepareStatement(stergm);
        pst.execute();

        String adaug_lunam = "Insert into Manevra select * from Cotizatii where id_luna = '"+Table_click2+"' order by Cotizatii.nume asc";
        pst = conn.prepareStatement(adaug_lunam);
        pst.execute();	 
        
        String adaug_luna = "Insert into Print select * from Manevra order by Manevra.nume asc";
        pst = conn.prepareStatement(adaug_luna);
        pst.execute();	
        
//        String report = "C:\\Users\\Bretan\\Dropbox\\Car\\Altele\\Tabel1.jrxml";
//        String report = "D:\\Car\\Altele\\Tabel1.jrxml";
             String report = "/home/bogdanfasie/IdeaProjects/Car/Altele/Tabel1.jrxml";
        JasperReport jr = JasperCompileManager.compileReport(report);
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
        JasperViewer.viewReport(jp);
    }//GEN-LAST:event_btn_TiparireActionPerformed
catch(Exception e){
       JOptionPane.showMessageDialog(null, e);
       }
    }
    private void btn_exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportActionPerformed
 
        try{
        
            String sterg = "Delete from Manevra";
            pst = conn.prepareStatement(sterg);
            pst.execute();

            String adaug_luna = "Insert into Manevra select * from Cotizatii where id_luna = '"+Table_click2+"'";
            pst = conn.prepareStatement(adaug_luna);
            pst.execute();	   

            SQLQueryBeanTest.emain();
            
            JOptionPane.showMessageDialog(null, "A fost generat fisierul Membri.xml in directorul Rezultate");
        
        }catch(Exception e){
       JOptionPane.showMessageDialog(null, e);
       }
        
    }//GEN-LAST:event_btn_exportActionPerformed

    private void btn_PdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PdfActionPerformed

        try{
        
            String sterg = "Delete from Print";
            pst = conn.prepareStatement(sterg);
            pst.execute();

            String pdf = "Insert into print select * from Cotizatii where id_luna = '"+Table_click2+"'";
            pst = conn.prepareStatement(pdf);
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "A fost generat fisierul Borderou.pdf in directorul Rezultate");
            
        }catch(Exception e){
       JOptionPane.showMessageDialog(null, e);
       }
        
    ExportCar.t();
    }//GEN-LAST:event_btn_PdfActionPerformed

    private void txt_idlcurMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_idlcurMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idlcurMouseClicked

    private void txt_idlcurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idlcurActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idlcurActionPerformed

    private void txt_taxaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_taxaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_taxaActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Retineri().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabel_luni;
    private javax.swing.JTable Tabel_membri;
    private javax.swing.JButton btnAdaug_membri;
    private javax.swing.JButton btnAdaugr_luni;
    private javax.swing.JButton btnClear_luni;
    private javax.swing.JButton btnClear_membri;
    private javax.swing.JButton btnModific_luni;
    private javax.swing.JButton btnModific_membri;
    private javax.swing.JButton btnSterg_luni;
    private javax.swing.JButton btnSterg_membri;
    private javax.swing.JButton btn_Pdf;
    private javax.swing.JButton btn_Tiparire;
    private javax.swing.JButton btn_export;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txt_acur;
    private javax.swing.JTextField txt_an1;
    private javax.swing.JTextField txt_dobanda;
    private javax.swing.JTextField txt_fond;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_id1;
    private javax.swing.JTextField txt_idlcur;
    private javax.swing.JTextField txt_lcur;
    private javax.swing.JTextField txt_luna1;
    private javax.swing.JTextField txt_marca;
    private javax.swing.JTextField txt_nume;
    private javax.swing.JTextField txt_rata;
    private javax.swing.JTextField txt_retinere;
    private javax.swing.JTextField txt_sdob;
    private javax.swing.JTextField txt_sfond;
    private javax.swing.JTextField txt_srata;
    private javax.swing.JTextField txt_sret;
    private javax.swing.JTextField txt_staxa;
    private javax.swing.JTextField txt_taxa;
    // End of variables declaration//GEN-END:variables
}
