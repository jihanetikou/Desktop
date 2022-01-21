package Forms2;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import dao.ServerDaoRemote;
import entities.User;
import entities.SmartPhone;


import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.awt.event.ActionEvent;

import javax.swing.JTable;

import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.toedter.calendar.JDateChooser;
import javax.swing.ImageIcon;

public class GestionUser extends JFrame {
	private DefaultTableModel model;
    private int id = -1;
    private JTextField nomInput;
    private int i;
    private Long idS;
    private String[] l= null;
    private String[] l2= null;
    private String type;
    private String bloc=null;
    private Long idB;
    private JTable table;
    private JTextField prenomInput;
    private JTextField emailInput;
    private JTextField telInput;
    private JDateChooser dateChooser;
    
    public static ServerDaoRemote lookUpSalleDaoRemote() throws NamingException {
		Hashtable<Object, Object> jndiProperties = new Hashtable<Object, Object>();
		
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:4200");
		final Context context = new InitialContext(jndiProperties);

		return (ServerDaoRemote) context.lookup("ejb:WebEarWeb/Server/ServiceDao!dao.ServerDaoRemote");}
	/**
	 * Launch the application.
	 */
 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					GestionUser frame = new GestionUser();
					frame.setVisible(true);
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void recharger() {
        model.setRowCount(0);
        try {
        	ServerDaoRemote stub = lookUpSalleDaoRemote();
			List<User> users =stub.findAllUsers();
            for (User u : users) {
                model.addRow(new Object[]{
                    u.getIdUser(),
                    u.getNom(),
                    u.getPrenom(),
                    u.getEmail(),
                    u.getTel(),
                    u.getDate()
                       
                }
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

	/**
	 * Create the frame.
	 * 
	 */
	
	public GestionUser() {
		setTitle("GS User");
		
		getContentPane().setBackground(new Color(204, 51, 255));
		//updateComboBoxBloc();
		
	
		setResizable(true);
		setBounds(100, 100, 824, 435);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setBounds(30, 130, 512, 257);
		getContentPane().add(scrollPane);
////////////////////////////////////////////////////////	
		table = new JTable();
		table.setForeground(new Color(0, 0, 0));
		table.setBackground(new Color(173, 255, 47));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				i=table.getSelectedRow();
				 idB=Long.valueOf(model.getValueAt(table.getSelectedRow(), 0).toString());
				 System.out.println(idB.toString());
				nomInput.setText(model.getValueAt(i, 1).toString());
				prenomInput.setText(model.getValueAt(i, 2).toString());
				emailInput.setText(model.getValueAt(i, 3).toString());
				telInput.setText(model.getValueAt(i, 4).toString());	
				
			}
		});
		model=new DefaultTableModel();
		Object[] column = {"Id_User","Nom","Prenom","Email","Tel","Date_Naissance"};
		Object[] row=new Object[0];
		model.setColumnIdentifiers(column);
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		JButton update = new JButton("modifier");
		update.setBounds(676, 110, 124, 31);
		update.setBackground(new Color(0, 0, 0));
		update.setForeground(new Color(240, 248, 255));
		update.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JButton delete = new JButton("supprimer");
		delete.setBounds(676, 70, 124, 29);
		delete.setFont(new Font("Tahoma", Font.BOLD, 11));
		delete.setForeground(new Color(240, 255, 255));
		delete.setBackground(new Color(0, 0, 0));
		
		
		JButton add = new JButton("ajouter");
		add.setBounds(676, 24, 124, 31);
		getContentPane().add(add);
		add.setToolTipText("add");
		add.setFont(new Font("Tahoma", Font.BOLD, 11));
		add.setForeground(new Color(240, 248, 255));
		add.setBackground(new Color(0, 0, 0));
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ServerDaoRemote stub = lookUpSalleDaoRemote();
					String nom=nomInput.getText();
					String prenom=prenomInput.getText();
					String email=emailInput.getText();
					String tel=telInput.getText();
					Date dateNais=dateChooser.getDate();

					User b=new User(nom,prenom,email,tel,dateNais);
					stub.create(b);
					recharger();
					 nomInput.setText("");
						prenomInput.setText("");
						emailInput.setText("");
						telInput.setText("");
						dateChooser.setCalendar(null);
					
									
				} catch (NamingException s) {
					s.printStackTrace();
				}
			}
				

			
		});
		getContentPane().add(update);
		getContentPane().add(delete);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Date  :");
		lblNewLabel_1_1_1_1.setBounds(432, 63, 124, 14);
		getContentPane().add(lblNewLabel_1_1_1_1);
		lblNewLabel_1_1_1_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblPrenom = new JLabel("Prenom :");
		lblPrenom.setBounds(225, 30, 77, 14);
		getContentPane().add(lblPrenom);
		lblPrenom.setForeground(new Color(0, 0, 0));
		lblPrenom.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblNewLabel = new JLabel("Nom :");
		lblNewLabel.setBounds(30, 30, 53, 14);
		getContentPane().add(lblNewLabel);
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		nomInput = new JTextField();
		nomInput.setBounds(78, 29, 114, 20);
		getContentPane().add(nomInput);
		nomInput.setColumns(10);
		
		prenomInput = new JTextField();
		prenomInput.setBounds(290, 29, 114, 20);
		getContentPane().add(prenomInput);
		prenomInput.setColumns(10);
		
		emailInput = new JTextField();
		emailInput.setBounds(498, 29, 114, 20);
		getContentPane().add(emailInput);
		emailInput.setColumns(10);
		
		telInput = new JTextField();
		telInput.setBounds(261, 62, 114, 20);
		getContentPane().add(telInput);
		telInput.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Email :");
		lblNewLabel_1_1.setBounds(434, 30, 53, 14);
		getContentPane().add(lblNewLabel_1_1);
		lblNewLabel_1_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Tel :");
		lblNewLabel_1_1_1.setBounds(198, 63, 53, 14);
		getContentPane().add(lblNewLabel_1_1_1);
		lblNewLabel_1_1_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(508, 63, 114, 20);
		getContentPane().add(dateChooser);
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ServerDaoRemote stub = lookUpSalleDaoRemote();
					  System.out.println(idB);
					  stub.delete(stub.findUserById(idB));
					
					 recharger();
					 nomInput.setText("");
						prenomInput.setText("");
						emailInput.setText("");
						telInput.setText("");
						dateChooser.setCalendar(null);
					
									
				} catch (NamingException s) {
					s.printStackTrace();
				}
				
			}
		});
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ServerDaoRemote stub = lookUpSalleDaoRemote();
					
					 stub.update(stub.findUserById(idB),nomInput.getText(),prenomInput.getText(),emailInput.getText(),telInput.getText(),dateChooser.getDate());
						
					 recharger();
					
					 nomInput.setText("");
						prenomInput.setText("");
						emailInput.setText("");
						telInput.setText("");
						dateChooser.setCalendar(null);
					
									
				} catch (NamingException s) {
					s.printStackTrace();
				}
			}
		});
		recharger();
		
		
	}
}
