package Forms2;



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
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EtchedBorder;
import javax.swing.ImageIcon;
import java.awt.SystemColor;

public class GestionSmartPhone extends JFrame {
	private DefaultTableModel model;
    private int id = -1;
    private JTextField imeiField;
    private int i;
    private Long idS;
    private String[] l= null;
    private String[] l2= null;
    private String type;
    private String user=null;
    private Long idB;
    private JTable table;
    
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
					GestionSmartPhone frame = new GestionSmartPhone();
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
			List<SmartPhone> smartphones =stub.findAllSmartPhones();
            for (SmartPhone s : smartphones) {
                model.addRow(new Object[]{
                    s.getIdSP(),
                    s.getImei(),
                    s.getUser().getNom(),     
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
	
	public GestionSmartPhone() {
		setTitle("GS Smartphones");
		
		getContentPane().setBackground(new Color(102, 153, 255));
		//updateComboBoxBloc();
		
		
		setResizable(true);
		setBounds(100, 100, 824, 435);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 51, 255));
		panel.setBounds(10, 151, 240, 102);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Imei :");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 26, 53, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Utilisateur :");
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 51, 93, 24);
		panel.add(lblNewLabel_1);
		
		imeiField = new JTextField();
		imeiField.setBounds(113, 25, 86, 20);
		panel.add(imeiField);
		imeiField.setColumns(10);
		//update comboBox
		try {
			ServerDaoRemote stub = lookUpSalleDaoRemote();
			List<User> b =stub.findAllUsers();
			l=new String[b.size()];
			
			for(int i=0;i<b.size();i++) {
		      l[i]=b.get(i).getNom();
			}
			
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		 JComboBox UserComboBox = new JComboBox(l);
		 UserComboBox.addItemListener(new ItemListener() {
		 	public void itemStateChanged(ItemEvent e) {
		 		if (e.getStateChange() == ItemEvent.SELECTED) {
					user=e.getItem().toString();
					}
		 		
				

		 	}
		 });
		UserComboBox.setBounds(113, 53, 86, 24);
		panel.add(UserComboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setBounds(260, 78, 538, 316);
		getContentPane().add(scrollPane);
////////////////////////////////////////////////////////	
		table = new JTable();
		table.setForeground(new Color(0, 0, 0));
		table.setBackground(new Color(173, 255, 47));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 i=table.getSelectedRow();
				 idS=Long.valueOf(model.getValueAt(table.getSelectedRow(), 0).toString());
				 System.out.println(i);
				imeiField.setText(model.getValueAt(i, 1).toString());
				UserComboBox.setSelectedItem((model.getValueAt(i, 3).toString()));
				
			}
		});
		model=new DefaultTableModel();
		Object[] column = {"Id_SmartPhone","Imei","User"};
		Object[] row=new Object[0];
		model.setColumnIdentifiers(column);
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		JButton update = new JButton("UPDATE");
		update.setBounds(418, 31, 136, 36);
		update.setForeground(SystemColor.window);
		update.setFont(new Font("Tahoma", Font.BOLD, 11));
		update.setBackground(new Color(102, 51, 255));
		
		
		JButton add = new JButton("ADD");
		add.setBounds(271, 31, 136, 36);
		getContentPane().add(add);
		add.setFont(new Font("Tahoma", Font.BOLD, 11));
		add.setForeground(SystemColor.window);
		add.setBackground(new Color(102, 51, 255));
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ServerDaoRemote stub = lookUpSalleDaoRemote();
					String imei=imeiField.getText();
					List<User> users =stub.findAllUsers();
					for(User u: users) {
						if(u.getNom().equals(user)) 
							idB=u.getIdUser();
							}
					
					
					SmartPhone s=new SmartPhone(imei);
					stub.addSmartPhone(s, idB);
					recharger();
					imeiField.setText("");
					
								
				} catch (NamingException s) {
					s.printStackTrace();
				}
			}
				

			
		});
		getContentPane().add(update);
		
		JButton delete = new JButton("DELETE");
		delete.setBounds(566, 31, 136, 36);
		getContentPane().add(delete);
		delete.setForeground(SystemColor.window);
		delete.setFont(new Font("Tahoma", Font.BOLD, 11));
		delete.setBackground(new Color(102, 51, 255));
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ServerDaoRemote stub = lookUpSalleDaoRemote();
					
					
					  stub.delete(stub.findSmartPhoneById(idS));
					
					  recharger();
					  imeiField.setText("");
									
				} catch (NamingException s) {
					s.printStackTrace();
				}
				
			}
		});
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ServerDaoRemote stub = lookUpSalleDaoRemote();
					
					
					
					List<User> users =stub.findAllUsers();
					for(User u: users) {
						if(u.getNom().equals(UserComboBox.getSelectedItem().toString())) 
							idB=u.getIdUser();
							}
					
				
					 SmartPhone s=new SmartPhone(idS,imeiField.getText());
					  stub.update(s, idB);
					
					  recharger();
					  imeiField.setText("");
					
									
				} catch (NamingException s) {
					s.printStackTrace();
				}
			}
		});
		recharger();
		
		
	}
}
