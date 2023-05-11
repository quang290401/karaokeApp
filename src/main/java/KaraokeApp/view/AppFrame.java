/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package KaraokeApp.view;

import KaraokeApp.model.Customer;
import KaraokeApp.model.FoodConsumed;
import KaraokeApp.model.TypeRoom;
import KaraokeApp.model.roomNo;
import KaraokeApp.service.CustomerService;
import KaraokeApp.service.LoadImg;
import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author forre
 */
public class AppFrame extends javax.swing.JFrame {

    int onof = 0;
    boolean flagOfaddCell=false;
    CustomerService dataInCp = new CustomerService();
    CustomerService service = new CustomerService();
    ArrayList<Customer> lstCusAll = service.getLstCustomer();
    ArrayList<Customer> lstCusByDay;
    ArrayList<TypeRoom> lstRoomType = service.getLstRoomType();
    ArrayList<roomNo> lstRoomNoNow = service.getLstRoomNo();

    ArrayList<Customer> cusDataRealtime;

    ArrayList<roomNo> lstRoomNoFull = dataInCp.getLstRoomNo();
    ArrayList<roomNo> lstRoomNoTemp = new ArrayList<>();

    DefaultTableModel modelTable = new DefaultTableModel();
    DefaultComboBoxModel modelCboTypeRoom = new DefaultComboBoxModel();
    DefaultComboBoxModel modelCboRoomNo = new DefaultComboBoxModel();

    public int pos = -1;
    public int i = 0;
    public String dayNow;
    public String monthNow;
    public String yearNow;
    public String hourNow;
    public String minuteNow;
    public String secondNow;
    public String timeStamp;

    public AppFrame() {
        initComponents();
        loadDataInToCboRoomType();
        setDefaultDate();
        disPlayBaseOndate();
        loadDataIntoCboRoomNoWithFilter();
    }

    public void clearAll() {
        txtName.setText("");
        lblID.setText("");
        txtDOB.setText("");
        cboGender.setSelectedItem("Nam");
        cboTypeRoom.setSelectedIndex(0);
        txtHIn.setText("");
        txtMIn.setText("");
        lblStt.setText("");
        lblDateOfbooking.setText(timeStamp);
    }

    public void addCell() {
        if (onof == 0) {
            //String id;
            String mes = "";
            int count = 0;
            String name = txtName.getText();
            String DOB = txtDOB.getText();
            String gender = (String) cboGender.getSelectedItem();
            TypeRoom roomType = new TypeRoom();
            roomNo roomNo = new roomNo();
            FoodConsumed food = new FoodConsumed();
            if (!name.strip().equals("") && !DOB.strip().equals("") && !txtHIn.getText()
                    .strip().equals("") && !txtMIn.getText().strip().equals("")
                    && cboRoomNo.getSelectedIndex() != -1 && !name.matches(".*\\d.*")) {
                for (TypeRoom roomTypeObj : lstRoomType) {
                    if (roomTypeObj.getNameRoomType().equals(cboTypeRoom.getSelectedItem())) {
                        roomType = roomTypeObj;
                        break;
                    }
                }
                for (roomNo lstRoomNoObj : lstRoomNoFull) {
                    if (lstRoomNoObj.getNameRoom().equals(cboRoomNo.getSelectedItem())) {
                        roomNo = lstRoomNoObj;
                    }
                }

                try {
                    i++;
                    String hourIn = txtHIn.getText();
                    String minuteIn = txtMIn.getText();
                    int hour = Integer.parseInt(hourIn);
                    int minute = Integer.parseInt(minuteIn);
                    Customer cusForPushDown = service.addDataToDb(new Customer(name, "", DOB, gender, roomType, roomNo, hourIn, minuteIn, "", "", "Not yet", lblDateOfbooking.getText(), ""), lstRoomType, lstRoomNoFull);
                    cusDataRealtime.add(cusForPushDown);
                    loadDataIntoCboRoomNoWithFilter();
                    modelTable.addRow(new Object[]{i, cusForPushDown.getName(), cusForPushDown.getIdBill(), cusForPushDown.getDOB(), cusForPushDown.getGender(), cusForPushDown.getRoomType().getNameRoomType(), cusForPushDown.getRoomNo().getNameRoom(), cusForPushDown.getHourIn() + ":" + cusForPushDown.getMinuteIn(), "", cusForPushDown.getDate(), cusForPushDown.getStt()});
                    JOptionPane.showMessageDialog(this,"Thêm thành công !");
                    flagOfaddCell=true;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Gio nhap vao không dúng dinh dang");
                }
            } else if (name.strip().equals("") || DOB.strip().equals("") || cboRoomNo.getSelectedIndex() == -1 || txtHIn.getText().strip().equals("") || txtMIn.getText().strip().equals("")||name.matches(".*\\d.*")) {
                if (name.strip().equals("")) {
                    mes += "Vui lòng nhâp tên khách hàng !";
                    count++;
                }
                if (DOB.strip().equals("")) {
                    mes += "\nVui lòng nhâp ngày sinh khách hàng";
                    count++;
                }
                if (txtHIn.getText().strip().equals("") || txtMIn.getText().strip().equals("")) {
                    mes += "\n Vui lòng nhâp thoi gian khách hàng vào !";
                    count++;
                }
                if (!name.strip().equals("")) {
                    if (name.matches(".*\\d.*")) {
                        count++;
                        mes += "\n Tên phai là chu";
                    }
                }
                if (cboRoomNo.getSelectedIndex() == -1) {
                    count++;
                    mes += "\n Vui lòng nhâp so phòng";
                }

                if (count > 0) {
                    JOptionPane.showMessageDialog(this, mes);
                    mes = "";
                    count = 0;
                }

            }

        } else {
            JOptionPane.showMessageDialog(this, "Vui long refresh lai du lieu");
        }
    }

    public void setDefaultDate() {
        timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        dayNow = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        monthNow = new SimpleDateFormat("MM").format(Calendar.getInstance().getTime());
        yearNow = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
        hourNow = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
        minuteNow = new SimpleDateFormat("mm").format(Calendar.getInstance().getTime());
        secondNow = new SimpleDateFormat("ss").format(Calendar.getInstance().getTime());
        cboDay.setSelectedItem(dayNow);
        cboMonth.setSelectedItem(monthNow);
        cboYear.setSelectedItem(yearNow);
        lblDateOfbooking.setText(timeStamp);
    }

    public void disPlayBaseOndate() {
        i = 0;
        String day = (String) cboDay.getSelectedItem();
        String month = (String) cboMonth.getSelectedItem();
        String year = (String) cboYear.getSelectedItem();
        cusDataRealtime = service.getCusByDate(day, month, year, lstRoomType, lstRoomNoFull);
        lstCusByDay = new ArrayList<>();
        modelTable = (DefaultTableModel) table.getModel();
        modelTable.setRowCount(0);
        for (Customer cusOb : cusDataRealtime) {
            i++;
            lstCusByDay.add(cusOb);
            modelTable.addRow(new Object[]{i, cusOb.getName(), cusOb.getIdBill(), cusOb.getDOB(), cusOb.getGender(), cusOb.getRoomType().getNameRoomType(), cusOb.getRoomNo().getNameRoom(), cusOb.getHourIn() + ":" + cusOb.getMinuteIn(), cusOb.getHourOut() + ":" + cusOb.getMinuteOut(), cusOb.getStt(), cusOb.getDate()});
        }
    }

    public void loadDataInToCboRoomType() {
        modelCboTypeRoom = (DefaultComboBoxModel) cboTypeRoom.getModel();
        for (TypeRoom RoomTypeOb : lstRoomType) {
            modelCboTypeRoom.addElement(RoomTypeOb.getNameRoomType());
        }
    }

    public void loadDataIntoRoomNo() {
        modelCboRoomNo = (DefaultComboBoxModel) cboRoomNo.getModel();
        modelCboRoomNo.removeAllElements();
        for (roomNo RoomNoOb : lstRoomNoFull) {
            modelCboRoomNo.addElement(RoomNoOb.getNameRoom());
        }
    }

    public void loadDataIntoCboRoomNoWithFilter() {
        dayNow = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        monthNow = new SimpleDateFormat("MM").format(Calendar.getInstance().getTime());
        yearNow = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
        modelCboRoomNo = (DefaultComboBoxModel) cboRoomNo.getModel();
        modelCboRoomNo.removeAllElements();
        if (cboDay.getSelectedItem().equals(dayNow)
                && cboMonth.getSelectedItem().equals(monthNow)
                && cboYear.getSelectedItem().equals(yearNow)) {
            //remove room is used
            for (Customer cusOb : cusDataRealtime) {
                if (cusOb.getStt().equals("Not yet")) {
                    lstRoomNoNow.remove(cusOb.getRoomNo());

                }
            }

            for (roomNo roomNoOb : lstRoomNoNow) {
                if (roomNoOb.getTypeRoom().getNameRoomType().equals(cboTypeRoom.getSelectedItem())) {
                    modelCboRoomNo.addElement(roomNoOb.getNameRoom());
                }
            }

        } else {

            for (roomNo roomNoOb : lstRoomNoNow) {
                if (roomNoOb.getTypeRoom().equals(cboTypeRoom.getSelectedItem())) {
                    modelCboRoomNo.addElement(roomNoOb.getNameRoom());
                }
            }
        }

    }

    public void loadDataIntoTableCustomerList() {
        int ii = 0;
        modelTable.setRowCount(0);
        modelTable = (DefaultTableModel) table.getModel();
        for (Customer cusOb : lstCusAll) {
            ii++;
            modelTable.addRow(new Object[]{ii, cusOb.getName(), cusOb.getIdBill(), cusOb.getDOB(), cusOb.getGender(), cusOb.getRoomType(), cusOb.getRoomNo(), cusOb.getHourIn() + ":" + cusOb.getMinuteIn(), cusOb.getHourOut() + ":" + cusOb.getMinuteOut(), null, cusOb.getDate()});
        }
    }

    public void disPlayData() {
        txtName.setText(cusDataRealtime.get(pos).getName());
        lblID.setText(cusDataRealtime.get(pos).getIdBill());
        txtDOB.setText(cusDataRealtime.get(pos).getDOB());
        cboGender.setSelectedItem(cusDataRealtime.get(pos).getGender());
        cboTypeRoom.setSelectedItem(cusDataRealtime.get(pos).getRoomType().getNameRoomType());
        cboRoomNo.setSelectedItem(cusDataRealtime.get(pos).getRoomNo().getNameRoom());
        txtHIn.setText((cusDataRealtime.get(pos).getHourIn()));
        txtMIn.setText((cusDataRealtime.get(pos).getMinuteIn()));
        lblStt.setText(cusDataRealtime.get(pos).getStt());
        lblDateOfbooking.setText(cusDataRealtime.get(pos).getDate());
    }

    public void fixCusBillToSql() {
        roomNo roomNoTraceChange = cusDataRealtime.get(pos).getRoomNo();
        String mes = "";
        int count = 0;
        String name = txtName.getText();
        String DOB = txtDOB.getText();
        if (name.strip().equals("") || DOB.strip().equals("")) {
            mes += "Vui long nhap day du thong tin khach hang";
            count++;
        }
        String gender = (String) cboGender.getSelectedItem();
        TypeRoom roomType = new TypeRoom();
        roomNo roomNo = new roomNo();
        for (TypeRoom roomTypeObj : lstRoomType) {
            if (roomTypeObj.getNameRoomType().equals(cboTypeRoom.getSelectedItem())) {
                roomType = roomTypeObj;
                break;
            }
        }
        for (roomNo lstRoomNoObj : lstRoomNoFull) {
            if (lstRoomNoObj.getNameRoom().equals(cboRoomNo.getSelectedItem())) {
                roomNo = lstRoomNoObj;
            }
        }
        FoodConsumed food = new FoodConsumed();
        try {
            String hourIn = txtHIn.getText();
            String minuteIn = txtMIn.getText();
            int hour = Integer.parseInt(hourIn);
            int minute = Integer.parseInt(minuteIn);
            String idBillPara = lblID.getText();
            service.updateCusToSql(idBillPara, new Customer(name, "", DOB, gender, roomType, roomNo, hourIn, minuteIn, "", "", "Not yet", lblDateOfbooking.getText(), ""), lstRoomType, lstRoomNoFull);
            cusDataRealtime.get(pos).setName(name);
            cusDataRealtime.get(pos).setDOB(DOB);
            cusDataRealtime.get(pos).setGender(gender);
            cusDataRealtime.get(pos).setRoomType(roomType);
            cusDataRealtime.get(pos).setRoomNo(roomNo);
            lstRoomNoNow.add(roomNoTraceChange);
            modelTable.setValueAt(name, pos, 1);//name
            modelTable.setValueAt(idBillPara, pos, 2);//name
            modelTable.setValueAt(DOB, pos, 3);//dob
            modelTable.setValueAt(gender, pos, 4);//gender
            modelTable.setValueAt(roomType.getNameRoomType(), pos, 5);//roomtype
            modelTable.setValueAt(roomNo.getNameRoom(), pos, 6);//roomno
            JOptionPane.showMessageDialog(this, "Them du lieu thanh cong");
        } catch (Exception e) {
            mes += "\n Vui long nhap dung dinh dang ngay";
            count++;
        }
        if (count > 0) {
            JOptionPane.showMessageDialog(this, mes);
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

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        cboDay = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        cboMonth = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        cboYear = new javax.swing.JComboBox<>();
        btnFilter = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtHIn = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMIn = new javax.swing.JTextField();
        btnGenBill = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnNow = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblDateOfbooking = new javax.swing.JLabel();
        cboRoomNo = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblStt = new javax.swing.JLabel();
        cboGender = new javax.swing.JComboBox<>();
        lblID = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cboTypeRoom = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnIncomeOnDate = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        btnNewBill = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnAddFood = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        btnRefresh = new javax.swing.JButton();
        txtDOB = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 153, 255));
        setForeground(java.awt.Color.orange);

        jLayeredPane1.setBackground(new java.awt.Color(145, 196, 244));
        jLayeredPane1.setOpaque(true);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Họ Tên", "ID hóa don", "Ngày sinh", "Gioi tính", "Loai phòng", "Phòng sô", "Gio vào", "Gio di", "Trang thai thanh toan", "Ngày"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        cboDay.setBackground(new java.awt.Color(156, 243, 165));
        cboDay.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cboDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel16.setText("Tháng");

        cboMonth.setBackground(new java.awt.Color(156, 243, 165));
        cboMonth.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cboMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel17.setText("Năm");

        cboYear.setBackground(new java.awt.Color(156, 243, 165));
        cboYear.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cboYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2020", "2021", "2022", "2023" }));

        btnFilter.setBackground(new java.awt.Color(156, 243, 165));
        btnFilter.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnFilter.setText("Lọc Dữ Liệu");
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel15.setText("Ngày");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setText("Giờ");

        txtHIn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtHIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHInActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setText("Phút");

        txtMIn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        btnGenBill.setBackground(new java.awt.Color(156, 243, 165));
        btnGenBill.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnGenBill.setText("Tạo Hóa Đơn");
        btnGenBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenBillActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(156, 243, 165));
        jButton12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton12.setText("Tìm Kiếm");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Phòng Số");

        btnNow.setBackground(new java.awt.Color(156, 243, 165));
        btnNow.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNow.setText("Nhập Giờ");
        btnNow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNowActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(156, 243, 165));
        jButton13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton13.setText("Sửa");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setText("Ngày Vào");

        LoadImg loadImg = new LoadImg();
        ImageIcon imgIconNew = new ImageIcon(loadImg.getDirToImg());
        Image imgNew = imgIconNew.getImage();
        Image newImgNew =  imgNew.getScaledInstance(400,70,Image.SCALE_SMOOTH);
        ImageIcon newImageIconNew = new ImageIcon(newImgNew);
        jLabel7.setIcon(newImageIconNew);
        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        lblDateOfbooking.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblDateOfbooking.setText("#bookingdate  here");

        cboRoomNo.setBackground(new java.awt.Color(156, 243, 165));
        cboRoomNo.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel20.setText("Trạng Thái");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Giới Tính");

        lblStt.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblStt.setText("#status here");

        cboGender.setBackground(new java.awt.Color(156, 243, 165));
        cboGender.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cboGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nu", " " }));

        lblID.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblID.setText("#ID  here");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Loại Phòng");

        cboTypeRoom.setBackground(new java.awt.Color(156, 243, 165));
        cboTypeRoom.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cboTypeRoom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTypeRoomItemStateChanged(evt);
            }
        });
        cboTypeRoom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboTypeRoomMouseClicked(evt);
            }
        });
        cboTypeRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTypeRoomActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Họ Tên");

        btnIncomeOnDate.setBackground(new java.awt.Color(156, 243, 165));
        btnIncomeOnDate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnIncomeOnDate.setText("Doanh Thu");
        btnIncomeOnDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncomeOnDateActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setText("ID Hóa Đơn");

        btnNewBill.setBackground(new java.awt.Color(156, 243, 165));
        btnNewBill.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNewBill.setText("Tạo Mới");
        btnNewBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewBillActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Ngày Sinh");

        btnAddFood.setBackground(new java.awt.Color(156, 243, 165));
        btnAddFood.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAddFood.setText("Thêm Dịch Vụ");
        btnAddFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFoodActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Giờ Khách Vào:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setText("Thêm Đồ Ăn");

        txtName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        btnRefresh.setBackground(new java.awt.Color(156, 243, 165));
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        txtDOB.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLayeredPane1.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(cboDay, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel16, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(cboMonth, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel17, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(cboYear, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(btnFilter, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel15, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txtHIn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txtMIn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(btnGenBill, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jButton12, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(btnNow, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jButton13, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel19, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lblDateOfbooking, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(cboRoomNo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel20, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lblStt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(cboGender, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lblID, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(cboTypeRoom, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(btnIncomeOnDate, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel13, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(btnNewBill, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(btnAddFood, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txtName, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(btnRefresh, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txtDOB, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(btnAddFood))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(40, 40, 40)
                        .addComponent(lblDateOfbooking, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(cboGender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jLayeredPane1Layout.createSequentialGroup()
                            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel6)
                                .addComponent(jLabel8)
                                .addComponent(jLabel9))
                            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                    .addGap(39, 39, 39)
                                    .addComponent(txtDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cboTypeRoom, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cboRoomNo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPane1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtHIn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPane1Layout.createSequentialGroup()
                                        .addComponent(jLabel20)
                                        .addGap(36, 36, 36)
                                        .addComponent(lblStt)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMIn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnGenBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnNewBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(btnNow))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnIncomeOnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel13))
                        .addGap(33, 33, 33)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblID)))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(cboMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(btnFilter))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        jLayeredPane1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddFood, cboGender, cboRoomNo, cboTypeRoom, txtDOB});

        jLayeredPane1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton12, jButton13});

        jLayeredPane1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnIncomeOnDate, btnRefresh});

        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel15)
                                .addComponent(cboDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel16)
                                .addComponent(cboMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17)
                                .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnFilter)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(lblID))
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(cboGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(cboTypeRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cboRoomNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(txtHIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txtMIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNow))
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(lblStt))
                        .addGap(11, 11, 11)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(lblDateOfbooking))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAddFood)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnIncomeOnDate)
                            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNewBill, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRefresh)
                            .addComponent(jButton12)
                            .addComponent(btnGenBill))))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLayeredPane1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnIncomeOnDate, btnRefresh});

        jLayeredPane1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton12, jButton13});

        jLayeredPane1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnGenBill, btnNewBill});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenBillActionPerformed
        // TODO add your handling code here:
        roomNo roomTemp = cusDataRealtime.get(pos).getRoomNo();
        hourNow = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
        minuteNow = new SimpleDateFormat("mm").format(Calendar.getInstance().getTime());
        if (cusDataRealtime.get(pos).getStt().strip().equals("Checked")) {
            finalBillView finalBillDisplay = new finalBillView(pos, cboDay.getSelectedItem().toString(), cboMonth.getSelectedItem().toString(), cboYear.getSelectedItem().toString(), lstRoomType, lstRoomNoFull);
            finalBillDisplay.setVisible(true);
        } else {
            try {
                modelTable = (DefaultTableModel) table.getModel();
                billFrame billDisplay = new billFrame(pos, hourNow, minuteNow, cboDay.getSelectedItem().toString(), cboMonth.getSelectedItem().toString(), cboYear.getSelectedItem().toString(), lstRoomType, lstRoomNoFull, modelTable, lstRoomNoNow, cusDataRealtime);
                billDisplay.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Vui long tao thong tin truoc khi tao hoa don");
            }
        }
    }//GEN-LAST:event_btnGenBillActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        SearchFrame searchDisplay = new SearchFrame(cusDataRealtime, table);
        searchDisplay.setVisible(true);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void btnAddFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFoodActionPerformed
        // TODO add your handling code here:
        try {
            addFoodFrame foodDisplay = new addFoodFrame(pos, cusDataRealtime);
            foodDisplay.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Chon hoa don truoc khi them dich vu");
        }
    }//GEN-LAST:event_btnAddFoodActionPerformed

    private void btnNewBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewBillActionPerformed
        // TODO add your handling code here:
        addCell();
        if(flagOfaddCell==true){
        clearAll();
        setDefaultDate();
        disPlayBaseOndate();
        loadDataIntoCboRoomNoWithFilter();
        onof = 0;
        flagOfaddCell=false;
        }
    }//GEN-LAST:event_btnNewBillActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // TODO add your handling code here:
        modelCboRoomNo = (DefaultComboBoxModel) cboRoomNo.getModel();

        onof = 1;
        pos = table.getSelectedRow();
        cboTypeRoom.setSelectedItem(cusDataRealtime.get(pos).getRoomType().getNameRoomType());

        if (cusDataRealtime.get(pos).getMoneyTook().equalsIgnoreCase("")) {
            modelCboRoomNo.removeAllElements();
            loadDataIntoCboRoomNoWithFilter();
        } else {
            loadDataIntoCboRoomNoWithFilter();
            modelCboRoomNo.removeAllElements();
        }

//        if (cusDataRealtime.get(pos).getMoneyTook().equalsIgnoreCase("")) {
        System.out.println("check room ");
        modelCboRoomNo.addElement(cusDataRealtime.get(pos).getRoomNo().getNameRoom());
        for (roomNo lstOb : lstRoomNoNow) {
            if (lstOb.getNameRoom().equals(cusDataRealtime.get(pos).getRoomNo())) {
                return;
            }
            modelCboRoomNo.removeElement(cusDataRealtime.get(pos).getRoomNo());
            break;
        }
        if (!cboDay.getSelectedItem().equals(dayNow) || !cboMonth.getSelectedItem().equals(monthNow) || !cboYear.getSelectedItem().equals(yearNow)) {
            modelCboRoomNo.addElement(cusDataRealtime.get(pos).getRoomNo().getNameRoom());
        }
        disPlayData();
    }//GEN-LAST:event_tableMouseClicked

    private void txtHInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHInActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHInActionPerformed

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        // TODO add your handling code here:
        disPlayBaseOndate();
    }//GEN-LAST:event_btnFilterActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        clearAll();
        setDefaultDate();
        disPlayBaseOndate();
        loadDataIntoCboRoomNoWithFilter();
        onof = 0;
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void cboTypeRoomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboTypeRoomMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_cboTypeRoomMouseClicked

    private void cboTypeRoomItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTypeRoomItemStateChanged
        // TODO add your handling code here:
        modelCboRoomNo.removeAllElements();
        loadDataIntoCboRoomNoWithFilter();
    }//GEN-LAST:event_cboTypeRoomItemStateChanged

    private void btnNowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNowActionPerformed
        // TODO add your handling code here:
        txtHIn.setText(hourNow);
        txtMIn.setText(minuteNow);
    }//GEN-LAST:event_btnNowActionPerformed

    private void cboTypeRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTypeRoomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTypeRoomActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(this, "Ban muon sua hoa don ?", "Warning", JOptionPane.YES_NO_OPTION);
        if (choice == 0) {
            fixCusBillToSql();
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void btnIncomeOnDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncomeOnDateActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                revenueFrame reVeFrame = new revenueFrame(lstRoomNoFull, lstRoomType);
                reVeFrame.setVisible(true);
            }
        });
    }//GEN-LAST:event_btnIncomeOnDateActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(AppFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(AppFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(AppFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(AppFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new AppFrame().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddFood;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnGenBill;
    private javax.swing.JButton btnIncomeOnDate;
    private javax.swing.JButton btnNewBill;
    private javax.swing.JButton btnNow;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox<String> cboDay;
    private javax.swing.JComboBox<String> cboGender;
    private javax.swing.JComboBox<String> cboMonth;
    private javax.swing.JComboBox<String> cboRoomNo;
    private javax.swing.JComboBox<String> cboTypeRoom;
    private javax.swing.JComboBox<String> cboYear;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDateOfbooking;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblStt;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtDOB;
    private javax.swing.JTextField txtHIn;
    private javax.swing.JTextField txtMIn;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
