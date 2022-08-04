import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Employee {
    private JPanel Main;
    private JTextField txtname;
    private JTextField txtsalary;
    private JTextField txtmobile;
    private JButton saveButton;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextArea txtid;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee");
        frame.setContentPane(new Employee().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

   Connection con;
    PreparedStatement pst;

    public void connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/rbcompany" , "root" , "");
            System.out.println("Success");
        }
        catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

//    void table_load(){
//        try{
//            pst = con.prepareStatement("select * from employee");
//            ResultSet rs = pst.executeQuery();
//            table1.setModel(DbUtils.resultSetToTableModel(rs));
//        }
//        catch (SQLException e){
//            e.printStackTrace();
//        }
//    }

    public Employee() {
        connect();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empname , salary ,mobile;
                empname = txtname.getText();
                salary = txtsalary.getText();
                mobile = txtmobile.getText();

                try{
                    pst = con.prepareStatement("insert into employee(empname , salary , mobile) values (?,?,?)");
                    pst.setString(1,empname);
                    pst.setString(2,salary);
                    pst.setString(3,mobile);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null , "record added");
//                    table_load();
                    txtname.setText("");
                    txtsalary.setText("");
                    txtmobile.setText("");
                }
                catch(SQLException e1){
                    e1.printStackTrace();
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String empid = txtid.getText() ;
                    pst = con.prepareStatement("select empname, salary , mobile from employee where id= ?");
                    pst.setString(1,empid);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()== true)
                    {
                        String empname = rs.getString(1);
                        String empsalary = rs.getString(2);
                        String empmobile = rs.getString(3);

                        txtname.setText(empname);
                        txtsalary.setText(empsalary);
                        txtmobile.setText(empmobile);
                    }
                    else{
                        txtname.setText("");
                        txtsalary.setText("");
                        txtmobile.setText("");
                        JOptionPane.showMessageDialog(null, "invalid id");
                    }

                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empid , empname , salary ,mobile;
                empid = txtid.getText();
                empname = txtname.getText();
                salary = txtsalary.getText();
                mobile = txtmobile.getText();


                try{
                    pst = con.prepareStatement("update employee set empname = ?, salary = ?, mobile = ? where id = ? ");
                    pst.setString(1,empname);
                    pst.setString(2,salary);
                    pst.setString(3,mobile);
                    pst.setString(4,empid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Update!");


                }
                catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empid;
                empid = txtid.getText();
                try{
                    pst = con.prepareStatement("delete from employee where id=?");
                    pst.setString(1,empid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "record deleted!");
                }
                catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
    }
}

