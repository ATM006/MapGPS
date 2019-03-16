package serialPort;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gps.GPS;
import serialException.ExceptionWriter;
import serialException.NoSuchPort;
import serialException.NotASerialPort;
import serialException.PortInUse;
import serialException.ReadDataFromSerialPortFailure;
import serialException.SerialPortInputStreamCloseFailure;
import serialException.SerialPortParameterFailure;
import serialException.TooManyListeners;


/**
 * ���������ʾ��
 * @author Zhong
 *
 */
public class DataView extends Frame {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Client client = null;

	private List<String> commList = null;	//������ö˿ں�
	private SerialPort serialPort = null;	//���洮�ڶ���
	
	private Font font = new Font("΢���ź�", Font.BOLD, 25);
	
	private Label tem = new Label("��������", Label.CENTER);	//����
	private Label hum = new Label("��������", Label.CENTER);	//γ��
	private Label pa = new Label("��������", Label.CENTER);	//����
	private Label rain = new Label("��������", Label.CENTER);	//ʱ��
//	private Label win_sp = new Label("��������", Label.CENTER);	//����
//	private Label win_dir = new Label("��������", Label.CENTER);	//����
	
	private Choice commChoice = new Choice();	//����ѡ��������
	private Choice bpsChoice = new Choice();	//������ѡ��
	
	private Button openSerialButton = new Button("�򿪴���");
	
	Image offScreen = null;	//�ػ�ʱ�Ļ���
	
	//����window��icon
	Toolkit toolKit = getToolkit();
	Image icon = toolKit.getImage(DataView.class.getResource("computer.png"));

	/**
	 * ��Ĺ��췽��
	 * @param client
	 */
	public DataView(Client client) {
		this.client = client;
		commList = SerialTool.findPort();	//�����ʼ��ʱ��ɨ��һ����Ч����
	}
	
	/**
	 * ���˵�������ʾ��
	 * ���Label����ť��������������¼�������
	 */
	public void dataFrame() {
		this.setBounds(client.LOC_X, client.LOC_Y, client.WIDTH, client.HEIGHT);
		this.setTitle("Map������Ŀ");
		this.setIconImage(icon);
		this.setBackground(Color.white);
		this.setLayout(null);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				if (serialPort != null) {
					//�����˳�ʱ�رմ����ͷ���Դ
					SerialTool.closePort(serialPort);
				}
				System.exit(0);
			}
			
		});
		
		tem.setBounds(140, 103, 225, 50);
		tem.setBackground(Color.black);
		tem.setFont(font);
		tem.setForeground(Color.white);
		add(tem);
		
		hum.setBounds(520, 103, 225, 50);
		hum.setBackground(Color.black);
		hum.setFont(font);
		hum.setForeground(Color.white);
		add(hum);
		
		pa.setBounds(140, 193, 225, 50);
		pa.setBackground(Color.black);
		pa.setFont(font);
		pa.setForeground(Color.white);
		add(pa);

		rain.setBounds(520, 193, 225, 50);
		rain.setBackground(Color.black);
		rain.setFont(font);
		rain.setForeground(Color.white);
		add(rain);
		
//		win_sp.setBounds(140, 283, 225, 50);
//		win_sp.setBackground(Color.black);
//		win_sp.setFont(font);
//		win_sp.setForeground(Color.white);
//		add(win_sp);
//		
//		win_dir.setBounds(520, 283, 225, 50);
//		win_dir.setBackground(Color.black);
//		win_dir.setFont(font);
//		win_dir.setForeground(Color.white);
//		add(win_dir);
//		
		//��Ӵ���ѡ��ѡ��
		commChoice.setBounds(160, 397, 200, 200);
		//����Ƿ��п��ô��ڣ��������ѡ����
		if (commList == null || commList.size()<1) {
			JOptionPane.showMessageDialog(null, "û����������Ч���ڣ�", "����", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			for (String s : commList) {
				commChoice.add(s);
			}
		}
		add(commChoice);
		
		//��Ӳ�����ѡ��
		bpsChoice.setBounds(526, 396, 200, 200);
		
		bpsChoice.add("9600");
//		bpsChoice.add("14400");
//		bpsChoice.add("19200");
//		bpsChoice.add("115200");
		add(bpsChoice);
		
		//��Ӵ򿪴��ڰ�ť
		openSerialButton.setBounds(250, 490, 300, 50);
		openSerialButton.setBackground(Color.lightGray);
		openSerialButton.setFont(new Font("΢���ź�", Font.BOLD, 20));
		openSerialButton.setForeground(Color.darkGray);
		add(openSerialButton);
		//��Ӵ򿪴��ڰ�ť���¼�����
		openSerialButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				//��ȡ��������
				String commName = commChoice.getSelectedItem();			
				//��ȡ������
				String bpsStr = bpsChoice.getSelectedItem();
				
				//��鴮�������Ƿ��ȡ��ȷ
				if (commName == null || commName.equals("")) {
					JOptionPane.showMessageDialog(null, "û����������Ч���ڣ�", "����", JOptionPane.INFORMATION_MESSAGE);			
				}
				else {
					//��鲨�����Ƿ��ȡ��ȷ
					if (bpsStr == null || bpsStr.equals("")) {
						JOptionPane.showMessageDialog(null, "�����ʻ�ȡ����", "����", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						//�������������ʾ���ȡ��ȷʱ
						int bps = Integer.parseInt(bpsStr);
						try {
							
							//��ȡָ���˿����������ʵĴ��ڶ���
							serialPort = SerialTool.openPort(commName, bps);
							//�ڸô��ڶ�������Ӽ�����
							SerialTool.addListener(serialPort, new SerialListener());
							//�����ɹ�������ʾ
							JOptionPane.showMessageDialog(null, "�����ɹ����Ժ���ʾ������ݣ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
							
						} catch (SerialPortParameterFailure | NotASerialPort | NoSuchPort | PortInUse | TooManyListeners e1) {
							//��������ʱʹ��һ��Dialog��ʾ����Ĵ�����Ϣ
							JOptionPane.showMessageDialog(null, e1, "����", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
				
			}
		});
		
		
		this.setResizable(false);
		
		new Thread(new RepaintThread()).start();	//�����ػ��߳�
		
	}
	
	/**
	 * �������������Ԫ��
	 */
	public void paint(Graphics g) {
		Color c = g.getColor();
		
		g.setColor(Color.black);
		g.setFont(new Font("΢���ź�", Font.BOLD, 25));
		g.drawString(" ���ȣ� ", 45, 130);

		g.setColor(Color.black);
		g.setFont(new Font("΢���ź�", Font.BOLD, 25));
		g.drawString(" γ�ȣ� ", 425, 130);
		
		g.setColor(Color.black);
		g.setFont(new Font("΢���ź�", Font.BOLD, 25));
		g.drawString(" ���ڣ� ", 45, 220);
		
		g.setColor(Color.black);
		g.setFont(new Font("΢���ź�", Font.BOLD, 25));
		g.drawString(" ʱ�䣺 ", 425, 220);
		
//		g.setColor(Color.black);
//		g.setFont(new Font("΢���ź�", Font.BOLD, 25));
//		g.drawString(" ���٣� ", 45, 310);
//		
//		g.setColor(Color.black);
//		g.setFont(new Font("΢���ź�", Font.BOLD, 25));
//		g.drawString(" ���� ", 425, 310);
		
		g.setColor(Color.gray);
		g.setFont(new Font("΢���ź�", Font.BOLD, 20));
		g.drawString(" ����ѡ�� ", 45, 410);
		
		g.setColor(Color.gray);
		g.setFont(new Font("΢���ź�", Font.BOLD, 20));
		g.drawString(" �����ʣ� ", 425, 410);
		
	}
	
	/**
	 * ˫���巽ʽ�ػ������Ԫ�����
	 */
	public void update(Graphics g) {
		if (offScreen == null)	offScreen = this.createImage(Client.WIDTH, Client.HEIGHT);
		Graphics gOffScreen = offScreen.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.white);
		gOffScreen.fillRect(0, 0, Client.WIDTH, Client.HEIGHT);	//�ػ���������
		this.paint(gOffScreen);	//�ػ�����Ԫ��
		gOffScreen.setColor(c);
		g.drawImage(offScreen, 0, 0, null);	//���»��õĻ�����������ԭ������
	}
	
	/*
	 * �ػ��̣߳�ÿ��30�����ػ�һ�Σ�
	 */
	private class RepaintThread implements Runnable {
		public void run() {
			while(true) {
				//�����ػ�����
				repaint();
				
				
				
				//ɨ����ô���
				commList = SerialTool.findPort();
				if (commList != null && commList.size()>0) {
					
					//�����ɨ�赽�Ŀ��ô���
					for (String s : commList) {
						
						//�ô������Ƿ��Ѵ��ڣ���ʼĬ��Ϊ�����ڣ���commList����ڵ���commChoice�ﲻ���ڣ�������ӣ�
						boolean commExist = false;	
						
						for (int i=0; i<commChoice.getItemCount(); i++) {
							if (s.equals(commChoice.getItem(i))) {
								//��ǰɨ�赽�Ĵ������Ѿ��ڳ�ʼɨ��ʱ����
								commExist = true;
								break;
							}					
						}
						
						if (commExist) {
							//��ǰɨ�赽�Ĵ������Ѿ��ڳ�ʼɨ��ʱ���ڣ�ֱ�ӽ�����һ��ѭ��
							continue;
						}
						else {
							//��������������´����������ô��������б�
							commChoice.add(s);
						}
					}
					
					//�Ƴ��Ѿ������õĴ���
					for (int i=0; i<commChoice.getItemCount(); i++) {
						
						//�ô����Ƿ���ʧЧ����ʼĬ��Ϊ�Ѿ�ʧЧ����commChoice����ڵ���commList�ﲻ���ڣ����Ѿ�ʧЧ��
						boolean commNotExist = true;	
						
						for (String s : commList) {
							if (s.equals(commChoice.getItem(i))) {
								commNotExist = false;	
								break;
							}
						}
						
						if (commNotExist) {
							//System.out.println("remove" + commChoice.getItem(i));
							commChoice.remove(i);
						}
						else {
							continue;
						}
					}
					
				}
				else {
					//���ɨ�赽��commListΪ�գ����Ƴ��������д���
					commChoice.removeAll();
				}

				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					String err = ExceptionWriter.getErrorInfoFromException(e);
					JOptionPane.showMessageDialog(null, err, "����", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
			}
		}
		
	}
	
	/**
	 * ���ڲ�����ʽ����һ�����ڼ�����
	 * @author zhong
	 *
	 */
	private class SerialListener implements SerialPortEventListener {
		
		/**
		 * �����ص��Ĵ����¼�
		 */
	    public void serialEvent(SerialPortEvent serialPortEvent) {
	    	
	        switch (serialPortEvent.getEventType()) {

	            case SerialPortEvent.BI: // 10 ͨѶ�ж�
	            	JOptionPane.showMessageDialog(null, "�봮���豸ͨѶ�ж�", "����", JOptionPane.INFORMATION_MESSAGE);
	            	break;

	            case SerialPortEvent.OE: // 7 ��λ�����������

	            case SerialPortEvent.FE: // 9 ֡����

	            case SerialPortEvent.PE: // 8 ��żУ�����

	            case SerialPortEvent.CD: // 6 �ز����

	            case SerialPortEvent.CTS: // 3 �������������

	            case SerialPortEvent.DSR: // 4 ����������׼������

	            case SerialPortEvent.RI: // 5 ����ָʾ

	            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 ��������������
	            	break;
	            
	            case SerialPortEvent.DATA_AVAILABLE: // 1 ���ڴ��ڿ�������
	            	
	            	//System.out.println("found data");
					byte[] data = null;
					
				if (serialPort == null) {
					JOptionPane.showMessageDialog(null, "���ڶ���Ϊ�գ�����ʧ�ܣ�", "����", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					String s2="$GPRMC,125530.026,V,3423.2475,N,10858.3416,E,0.00,0.00,020717,,,N*7E";
					//String s2="$GPGGA,161229.478,4323.2475,N,10858.3416,W,1,07,1,1.0,9.0,M,7.3,M,,0000,*18";
					
					GPS gps = new GPS();
//					 try {
//						data = SerialTool.readFromPort(serialPort);
//					} catch (ReadDataFromSerialPortFailure | SerialPortInputStreamCloseFailure e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}	//��ȡ���ݣ������ֽ�����
//					 try {
//						gps.gps_parse(data);
//					} catch (UnsupportedEncodingException | SQLException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					
					data = s2.getBytes();
					
					if(gps.checksum(data)==true)
					{
						try {
							gps.gps_parse(data);
						} catch (UnsupportedEncodingException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					
					
				
						//��������
						if (data[0] == '$') {	//�����ݵĵ�һ���ַ���*��ʱ��ʾ���ݽ�����ɣ���ʼ����
							if (data.length < 1) {	//��������Ƿ������ȷ
								JOptionPane.showMessageDialog(null, "���ݽ������̳��������豸�����", "����", JOptionPane.INFORMATION_MESSAGE);
								
								System.exit(0);
							}
							else {
								
								try {
									//���½���Labelֵ
									/*for (int i=0; i<elements.length; i++) {
										System.out.println(elements[i]);
									}*/
									//System.out.println("win_dir: " + elements[5]);
									
									
									tem.setText(gps.info.getJing() + " "+gps.info.getJ());
									hum.setText(gps.info.getWei()+" "+gps.info.getW());
									pa.setText(gps.info.getYear()+"-"+gps.info.getMonth()+"-"+gps.info.getDay());
									rain.setText(gps.info.getHour()+":"+gps.info.getMinute()+":"+gps.info.getSecond());

								} catch (ArrayIndexOutOfBoundsException e) {
									JOptionPane.showMessageDialog(null, "���ݽ������̳������½�������ʧ�ܣ������豸�����", "����", JOptionPane.INFORMATION_MESSAGE);
									System.exit(0);
								}
							}	
						}
					}	
		            
					break;
	
	        }

	    }

	}
	
	
}
