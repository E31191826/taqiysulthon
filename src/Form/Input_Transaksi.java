package Form;
import Connect_DB.koneksinya;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.*;
/**
 *
 * @author Rudiono
 */
public class Input_Transaksi extends javax.swing.JFrame {

    ResultSet Rs;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Creates new form Form_Trans_Detail
     */
int dragxmouse;
int dragymouse;
    public Input_Transaksi() {
        initComponents();
        Datatabel();
    }
    
    private void Autonomor(){
        String sql = "select max(id_transaksi) from data_transaksi";
        try{
            Statement state  = koneksinya.GetConnection().createStatement();
            Rs = state.executeQuery(sql);
            while (Rs.next()){
                int a = Rs.getInt(1);
                txtid.setText("00"+ Integer.toString(a+1));
            }
        }catch (Exception e){
            System.out.println(""+ e.getMessage());
        }
    }
    
    public void Tabelklik(){
        int pilih = tabeldetail.getSelectedRow();
        String id_transaksi = (String) tabeldetail.getValueAt(pilih, 0);
        txtid.setText(id_transaksi);
        String id_produk = (String) tabeldetail.getValueAt(pilih, 1);
        txtidbarang.setText(id_produk);
        String nama_produk = (String) tabeldetail.getValueAt(pilih, 2);
        txtnamabarang.setText(nama_produk);
        String harga = (String) tabeldetail.getValueAt(pilih, 3);
        txtharga.setText(harga);
        String jumlah = (String) tabeldetail.getValueAt(pilih, 4);
        txtjumlah.setText(jumlah);
        String total = (String) tabeldetail.getValueAt(pilih, 5);
        txttotal.setText(total);
        
    }
    
    public static Date AmbilTanggal(JTable table , int kolom){
        JTable tabeltrans = table;
        String tgl = String.valueOf(tabeltrans.getValueAt(tabeltrans.getSelectedRow(), kolom));
        Date tanggal = null;
        try {
            tanggal = new SimpleDateFormat("yyyy-MM-dd").parse(tgl);
        } catch (ParseException ex) {
            Logger.getLogger(Input_Transaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tanggal;
    }
 
    
    
    
    public void Reset(){
        txtid.setText(null);
        txtidbarang.setText(null);
        txtnamabarang.setText(null);
        txtharga.setText(null);
        txtjumlah.setText(null);
        txttotal.setText(null);
        datetransaksi.setDate(null);
    }
    
    public String idbarang, namabarang, hargabarang;
    public String getIdbarang(){
        return idbarang;
    }
    
    public String getNamabarang(){
        return namabarang;
    }
    
    public String getHargabarang(){
        return hargabarang;
    }
    
    public void Itemterpilih(){
        DataProduk fk = new DataProduk();
        fk.ftd = this;
        txtidbarang.setText(idbarang);
        txtnamabarang.setText(namabarang);
        txtharga.setText(hargabarang);
    }
    
    public void Datatabel(){
       DefaultTableModel tabel = new DefaultTableModel();
       tabel.addColumn("ID TRANSAKSI");
       tabel.addColumn("ID PRODUK");
       tabel.addColumn("NAMA PRODUK");
       tabel.addColumn("HARGA");
       tabel.addColumn("JUMLAH");
       tabel.addColumn("TOTAL");
       tabel.addColumn("TGL TRANSAKSI");
       try{
           Statement state  = koneksinya.GetConnection().createStatement();
           Rs = state.executeQuery("Select * from data_transaksi");
           while(Rs.next()){
               tabel.addRow(new Object[]{
                   Rs.getString(1),
                   Rs.getString(2),
                   Rs.getString(3),
                   Rs.getString(4),
                   Rs.getString(5),
                   Rs.getString(6),
                   Rs.getString(7)
               });
               tabeldetail.setModel(tabel);
           }
           state.close();
           Rs.close();
       }
       catch(Exception e){
           System.out.println(e);
       }
    }
    
    public void Simpan(){
        String id_transaksi = txtid.getText();
        String id_produk = txtidbarang.getText();
        String nama_produk = txtnamabarang.getText();
        int harga = Integer.parseInt(txtharga.getText());
        int jumlah_beli = Integer.parseInt(txtjumlah.getText());
        int total_bayar = (harga * jumlah_beli);
        txttotal.setText(Integer.toString(total_bayar));
        String tgl = String.valueOf(format.format(datetransaksi.getDate()));
        try{
            Statement state  = koneksinya.GetConnection().createStatement();
            state.executeUpdate("insert into data_transaksi values ('"+id_transaksi+"'"
                    + ",'"+id_produk+"'"
                    + ",'"+nama_produk+"',"
                    + "'"+harga+"'"
                    + ",'"+jumlah_beli+"'"
                    + ", '"+total_bayar+"'"
                    + ",'"+tgl+"')");
            JOptionPane.showMessageDialog(this, "Data Berhasil Disimpan !");
            Reset();
            state.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, "Data Gagal Disimpan !");
        }
    }
    
    public void Update(){
        String id = txtid.getText();
        String id_produk = txtidbarang.getText();
        String nama_produk = txtnamabarang.getText();
        int harga = Integer.parseInt(txtharga.getText());
        int jumlah = Integer.parseInt(txtjumlah.getText());
        int total = (harga * jumlah);
        txttotal.setText(Integer.toString(total));
        String tgl = String.valueOf(format.format(datetransaksi.getDate()));
        try{
            Statement state  = koneksinya.GetConnection().createStatement();
            state.executeUpdate("update data_transaksi set id_produk='"+id_produk
                    +"', nama_produk='"+nama_produk
                    +"', harga='"+harga
                    +"', jumlah_beli='"+jumlah
                    +"', total_bayar='"+total
                    +"', tgl_transaksi='"+tgl
                    +"' where id_transaksi='"+id+"';");
            JOptionPane.showMessageDialog(this, "Data Berhasil Diupdate !");
            Reset();
            state.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, "Data Gagal Diupdate !");
        }
    }
    
    public void Delete(){
        String id = txtid.getText();
        try {
            Statement state  = koneksinya.GetConnection().createStatement();
            state.executeUpdate("delete from data_transaksi where id_transaksi='"+id+"';");
            JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus !");
            Reset();
            state.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data Gagal Dihapus !");
        }
    }
    
    public void CariTransaksi(){
       DefaultTableModel tabel = new DefaultTableModel();
       tabel.addColumn("ID TRANSAKSI");
       tabel.addColumn("ID PRODUK");
       tabel.addColumn("NAMA PRODUK");
       tabel.addColumn("HARGA SATUAN");
       tabel.addColumn("JUMLAH BELI");
       tabel.addColumn("TOTAL");
       tabel.addColumn("TGL TRANSAKSI");
       
       String kunci = txtkunci.getText();
       try{
           Statement state  = koneksinya.GetConnection().createStatement();
           Rs = state.executeQuery("Select * from data_transaksi where nama_produk like '%"+kunci+"%'");
           while(Rs.next()){
               tabel.addRow(new Object[]{
                   Rs.getString(1),
                   Rs.getString(2),
                   Rs.getString(3),
                   Rs.getString(4),
                   Rs.getString(5),
                   Rs.getString(6),
                   Rs.getString(7)
               });
               tabeldetail.setModel(tabel);
           }
           txtkunci.setText(null);
           state.close();
           Rs.close();
       }
       catch(Exception e){
           JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan !");
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
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabeldetail = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        txtidbarang = new javax.swing.JTextField();
        txtnamabarang = new javax.swing.JTextField();
        txtharga = new javax.swing.JTextField();
        txtjumlah = new javax.swing.JTextField();
        txttotal = new javax.swing.JTextField();
        btngetid = new javax.swing.JButton();
        btngetidbarang = new javax.swing.JButton();
        btnhitung = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        datetransaksi = new com.toedter.calendar.JDateChooser();
        btnsimpan = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        btncetak = new javax.swing.JButton();
        txtkunci = new javax.swing.JTextField();
        btncari = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(1, 103, 242));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 0));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/isi.png"))); // NOI18N
        jLabel10.setText("HALAMAN TRANSAKSI");

        jLabel11.setForeground(new java.awt.Color(255, 255, 0));
        jLabel11.setText("Toko N3 Jaya Elektronik");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Browser Stop 24 h p8.png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(409, 409, 409)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(24, 24, 24)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabeldetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID TRANSAKSI", "ID PRODUK", "NAMA PRODUK", "HARGA", "JUMLAH", "TOTAL", "TGL TRANSAKSI"
            }
        ));
        tabeldetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabeldetailMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabeldetail);

        jPanel2.setBackground(new java.awt.Color(51, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Transaksi"));

        jLabel1.setText("ID TRANSAKSI");

        jLabel2.setText("NAMA PRODUK");

        jLabel3.setText("ID BARANG");

        jLabel4.setText("HARGA SATUAN");

        jLabel5.setText("JUMLAH BELI");

        jLabel6.setText("TOTAL");

        txtid.setEnabled(false);

        txtidbarang.setEnabled(false);

        txtnamabarang.setEnabled(false);

        txtharga.setEnabled(false);

        txttotal.setEnabled(false);

        btngetid.setText("GET ID");
        btngetid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngetidActionPerformed(evt);
            }
        });

        btngetidbarang.setText("GET ID");
        btngetidbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngetidbarangActionPerformed(evt);
            }
        });

        btnhitung.setText("HITUNG");
        btnhitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhitungActionPerformed(evt);
            }
        });

        jLabel9.setText("TGL TRANSAKSI");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtharga)
                                .addComponent(txtjumlah)
                                .addComponent(txtid)
                                .addComponent(txttotal)
                                .addComponent(datetransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                            .addComponent(txtnamabarang, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btngetid)
                            .addComponent(btngetidbarang)
                            .addComponent(btnhitung)))
                    .addComponent(txtidbarang, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btngetid)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtidbarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btngetidbarang)
                        .addComponent(txtnamabarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnhitung)
                    .addComponent(jLabel6))
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(datetransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnsimpan.setBackground(new java.awt.Color(51, 255, 0));
        btnsimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Save Blue 24 n p8.png"))); // NOI18N
        btnsimpan.setText("SIMPAN");
        btnsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsimpanActionPerformed(evt);
            }
        });

        btnupdate.setBackground(new java.awt.Color(51, 255, 0));
        btnupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Edit.png"))); // NOI18N
        btnupdate.setText("UPDATE");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        btndelete.setBackground(new java.awt.Color(51, 255, 0));
        btndelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Delete.png"))); // NOI18N
        btndelete.setText("DELETE");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        btncetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Print-2.png"))); // NOI18N
        btncetak.setText("CETAK");
        btncetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncetakActionPerformed(evt);
            }
        });

        btncari.setBackground(new java.awt.Color(51, 255, 0));
        btncari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Search 24 h p8.png"))); // NOI18N
        btncari.setText("CARI");
        btncari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btncari, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtkunci)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(btnsimpan)
                        .addGap(18, 18, 18)
                        .addComponent(btnupdate)
                        .addGap(18, 18, 18)
                        .addComponent(btndelete)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncetak)
                        .addGap(29, 29, 29))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE))))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtkunci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncari)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncetak)))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsimpan)
                    .addComponent(btnupdate)
                    .addComponent(btndelete))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btngetidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngetidActionPerformed
        // TODO add your handling code here:
        Autonomor();
    }//GEN-LAST:event_btngetidActionPerformed

    private void btnhitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhitungActionPerformed
        // TODO add your handling code here:
        int harga = Integer.parseInt(txtharga.getText());
        int jumlah = Integer.parseInt(txtjumlah.getText());
        int total = (harga * jumlah);
        txttotal.setText(Integer.toString(total));
    }//GEN-LAST:event_btnhitungActionPerformed

    private void btngetidbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngetidbarangActionPerformed
        // TODO add your handling code here:
        DataProduk fo = new DataProduk();
        fo.ftd = this;
        fo.setVisible(true);
    }//GEN-LAST:event_btngetidbarangActionPerformed

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpanActionPerformed
        // TODO add your handling code here:
        Simpan();
        Reset();
        Datatabel();
    }//GEN-LAST:event_btnsimpanActionPerformed

    private void tabeldetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabeldetailMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==1){
            Tabelklik();
            datetransaksi.setDate(AmbilTanggal(tabeldetail, 6));
        }
    }//GEN-LAST:event_tabeldetailMouseClicked

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // TODO add your handling code here:
        Update();
        Reset();
        Datatabel();
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
        Delete();
        Datatabel();
        Reset();
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btncariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncariActionPerformed
        // TODO add your handling code here:
        CariTransaksi();
    }//GEN-LAST:event_btncariActionPerformed

    private void btncetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncetakActionPerformed
        // TODO add your handling code here:
        String currentDir = System.getProperty("user.dir");
        java.io.File namaReport = new java.io.File(currentDir+"/src/Report/LaporanTransaksi.jasper");
        try {
            net.sf.jasperreports.engine.JasperReport jasper;
            jasper =(net.sf.jasperreports.engine.JasperReport)
            net.sf.jasperreports.engine.util.JRLoader.loadObject(namaReport.getPath());
            net.sf.jasperreports.engine.JasperPrint jp;
            jp = net.sf.jasperreports.engine.JasperFillManager.fillReport(jasper, null, koneksinya.GetConnection());
            net.sf.jasperreports.view.JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_btncetakActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        
        this.setLocation(x - dragxmouse ,y - dragymouse);
        System.out.println(x+","+y);
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        // TODO add your handling code here:
        dragxmouse = evt.getX();
        dragymouse = evt.getY();

    }//GEN-LAST:event_jPanel1MousePressed

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
            java.util.logging.Logger.getLogger(Input_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Input_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Input_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Input_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Input_Transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncari;
    private javax.swing.JButton btncetak;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btngetid;
    private javax.swing.JButton btngetidbarang;
    private javax.swing.JButton btnhitung;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btnupdate;
    private com.toedter.calendar.JDateChooser datetransaksi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabeldetail;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtidbarang;
    private javax.swing.JTextField txtjumlah;
    private javax.swing.JTextField txtkunci;
    private javax.swing.JTextField txtnamabarang;
    private javax.swing.JTextField txttotal;
    // End of variables declaration//GEN-END:variables
}
