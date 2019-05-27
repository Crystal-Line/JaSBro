package jasbro.gui.town;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import jasbro.game.housing.*;
import jasbro.gui.objects.div.InventoryPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import jasbro.Jasbro;
import jasbro.gui.GuiUtil;
import jasbro.texts.TextUtil;
import jasbro.util.ConfigHandler;
import jasbro.util.Settings;

public class InteriorDecorationMenu extends JPanel {
	private Logger log = LogManager.getLogger(InteriorDecorationMenu.class);
	private RoomPlanning roomPlanning;
	private JLabel costLabel;
	
	public InteriorDecorationMenu() {
		setOpaque(false);

		double width = ConfigHandler.getResolution(Settings.RESOLUTIONWIDTH);
		double height = ConfigHandler.getResolution(Settings.RESOLUTIONHEIGHT);
		int widthRat = (int) (width / 1280);
		int heightRat = (int) (height / 720);
		int iconSize = (int) (65 * width / 1280);
		int backHomeBtnWidth = (int) (150 * width / 1280);
		int backHomeBtnHeight = (int) (50 * width / 1280);

		ImageIcon homeIcon1 = new ImageIcon("images/buttons/home.png");
		Image homeImage1 = homeIcon1.getImage().getScaledInstance(backHomeBtnWidth, backHomeBtnHeight, java.awt.Image.SCALE_SMOOTH);
		homeIcon1 = new ImageIcon(homeImage1);

		ImageIcon homeIcon2 = new ImageIcon("images/buttons/home hover.png");
		Image homeImage2 = homeIcon2.getImage().getScaledInstance(backHomeBtnWidth, backHomeBtnHeight, java.awt.Image.SCALE_SMOOTH);
		homeIcon2 = new ImageIcon(homeImage2);

		JButton homeButton = new JButton(homeIcon1);
		homeButton.setRolloverIcon(homeIcon2);
		homeButton.setPressedIcon(homeIcon1);
		homeButton.setBounds((int) (15 * width / 1280), (int) (550 * height / 720), backHomeBtnWidth, backHomeBtnHeight);
		homeButton.setBorderPainted(false);
		homeButton.setContentAreaFilled(false);
		homeButton.setFocusPainted(false);
		homeButton.setOpaque(false);
		add(homeButton);
		homeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Jasbro.getInstance().getGui().showHouseManagementScreen();
			}
		});

		ImageIcon backIcon1 = new ImageIcon("images/buttons/back.png");
		Image backImage1 = backIcon1.getImage().getScaledInstance(backHomeBtnWidth, backHomeBtnHeight, java.awt.Image.SCALE_SMOOTH);
		backIcon1 = new ImageIcon(backImage1);

		ImageIcon backIcon2 = new ImageIcon("images/buttons/back hover.png");
		Image backImage2 = backIcon2.getImage().getScaledInstance(backHomeBtnWidth, backHomeBtnHeight, java.awt.Image.SCALE_SMOOTH);
		backIcon2 = new ImageIcon(backImage2);

		JButton backButton = new JButton(backIcon1);
		backButton.setRolloverIcon(backIcon2);
		backButton.setPressedIcon(backIcon1);
		backButton.setBounds((int) (15 * width / 1280), (int) (620 * height / 720), backHomeBtnWidth, backHomeBtnHeight);
		backButton.setBorderPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setFocusPainted(false);
		backButton.setOpaque(false);
		add(backButton);
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Jasbro.getInstance().getGui().showBuildersGuildScreen();
			}
		});

		setLayout(new FormLayout(new ColumnSpec[]{
				ColumnSpec.decode("135dlu"),
				ColumnSpec.decode("pref:grow"),
				ColumnSpec.decode("1dlu:grow(8)"),
				ColumnSpec.decode("pref:grow"),
				ColumnSpec.decode("1dlu:grow(8)"),
				ColumnSpec.decode("pref:grow"),},
				new RowSpec[]{
						RowSpec.decode("default:grow"),
						RowSpec.decode("default:grow(20)"),
						RowSpec.decode("default:grow"),}));

		JPanel housePanel = new JPanel();
		add(housePanel, "3, 2, fill, fill");
		housePanel.setLayout(new FormLayout(new ColumnSpec[]{
				ColumnSpec.decode("default:grow"),},
				new RowSpec[]{
						RowSpec.decode("default:grow"),
						RowSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						RowSpec.decode("default:grow"),
						RowSpec.decode("default:grow"),
						RowSpec.decode("default:grow"),
						RowSpec.decode("default:grow"),
						RowSpec.decode("default:grow"),
						RowSpec.decode("default:grow"),
						RowSpec.decode("default:grow"),
						RowSpec.decode("default:grow"),
						RowSpec.decode("default:grow(40)"),}));
		housePanel.setBackground(GuiUtil.DEFAULTTRANSPARENTCOLOR);
		housePanel.setBorder(GuiUtil.DEFAULTBORDER);

		JLabel lblNewLabel = new JLabel(TextUtil.t("ui.interiordeco"));
		lblNewLabel.setFont(GuiUtil.DEFAULTBOLDFONT);
		housePanel.add(lblNewLabel, "1, 4");

		final JComboBox<House> houseSelectBox = new JComboBox<House>();
		housePanel.add(houseSelectBox, "1, 6, fill, default");
		houseSelectBox.addItem(null);
		houseSelectBox.setSelectedIndex(0);
		for (House house : Jasbro.getInstance().getData().getHouses()) {
			houseSelectBox.addItem(house);
		}

		final JPanel roomPanel = new JPanel();
		roomPanel.setOpaque(false);
		housePanel.add(roomPanel, "1, 8, fill, default");

		JLabel typeLbl = new JLabel(TextUtil.t("ui.interiordeco.newroom"));
		typeLbl.setFont(GuiUtil.DEFAULTBOLDFONT);
		housePanel.add(typeLbl, "1, 10");

		final JPanel roomSlotPanel = new JPanel();
		roomSlotPanel.setOpaque(false);
		housePanel.add(roomSlotPanel, "1, 12, fill, top");

		houseSelectBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				buildRoomChangeGUI(houseSelectBox, roomPanel);
				buildRoomSlotChangeGUI(houseSelectBox, roomSlotPanel);
			}
		});
		
		
		
		JPanel controlPanel = new JPanel();
		controlPanel.setBackground(GuiUtil.DEFAULTTRANSPARENTCOLOR);
		controlPanel.setBorder(GuiUtil.DEFAULTBORDER);
		add(controlPanel, "5, 2, fill, fill");
		controlPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,
				RowSpec.decode("20dlu"),
				FormFactory.PREF_ROWSPEC,
				RowSpec.decode("20dlu"),
				FormFactory.PREF_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		costLabel = new JLabel("Cost changes: 0");
		controlPanel.add(costLabel, "1, 1");
		
		JButton btnReset = new JButton("Reset");
		controlPanel.add(btnReset, "1, 3");
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (roomPlanning != null) {
					roomPlanning.reset();
					int index = houseSelectBox.getSelectedIndex();
					houseSelectBox.setSelectedIndex(0);
					houseSelectBox.setSelectedIndex(index);
					updateCostLabel();
				}
			}
		});
		
		JButton btnBuyChanges = new JButton("Buy changes");
		controlPanel.add(btnBuyChanges, "1, 5");
		btnBuyChanges.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (roomPlanning != null && Jasbro.getInstance().getData().canAfford(roomPlanning.getCosts())) {
					roomPlanning.adoptRoomLayout();
					buildRoomChangeGUI(houseSelectBox, roomPanel);
					buildRoomSlotChangeGUI(houseSelectBox, roomSlotPanel);
					updateCostLabel();
				}
			}
		});
		
		addMouseListener (new MouseAdapter(){
	        public void mouseClicked(MouseEvent e) {
	            if (SwingUtilities.isRightMouseButton(e)) {
	            	Jasbro.getInstance().getGui().showBuildersGuildScreen();
	            }
	        }		
		});
	}

	private void buildRoomSlotChangeGUI(JComboBox<House> houseSelectBox, JPanel slotPanel) {
		final ItemListener slotListener = new ItemListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void itemStateChanged(ItemEvent e) {
				try {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						JComboBox<RoomSlotType> jComboBox = (JComboBox<RoomSlotType>) e.getSource();
						int id = Integer.parseInt(jComboBox.getActionCommand());
						roomPlanning.getNewRoomSlots().remove(id);
						RoomSlotType roomType = (RoomSlotType) jComboBox.getSelectedItem();
						roomPlanning.getNewRoomSlots().add(id, roomType);
						jComboBox.setToolTipText(roomType.getText());
						updateCostLabel();
					}
				} catch (Exception ex) {
					log.error("Error", ex);
				}
			}
		};

		// Generate skeleton layout
		FormLayout fl = new FormLayout(new ColumnSpec[]{
				ColumnSpec.decode("default:grow"),},
				new RowSpec[]{RowSpec.decode("default:grow")});
		slotPanel.removeAll();
		slotPanel.setLayout(fl);

		// Gather data for GUI
		House house = (House) houseSelectBox.getSelectedItem();

		if (house != null) {
			int i = -1;
			for (RoomSlot rs: house.getRoomSlots())
			{
				i++;

				JComboBox<RoomSlotType> typeSelect = new JComboBox<>();
				typeSelect.setRenderer(new DefaultListCellRenderer() {
					public Component getListCellRendererComponent(JList list,
																  Object value, int index, boolean isSelected,
																  boolean hasFocus) {
						JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
						RoomSlotType i_rst = (RoomSlotType) value;
						lbl.setText(i_rst.getText());
						lbl.setToolTipText(i_rst.getText());
						lbl.setForeground(Color.BLACK);
						lbl.setOpaque(false);
						return lbl;
					}
				});

				// Styling the new element
				fl.insertRow(i + 1, RowSpec.decode("default:none"));
				slotPanel.add(typeSelect, "1," + (i + 1) + ", fill, top");
				typeSelect.setBorder(new EmptyBorder( 2, 2, 2, 2));
				typeSelect.setActionCommand(i+"");
				typeSelect.setOpaque(false);

				for (RoomSlotType rst: RoomSlotType.values()) {
					typeSelect.addItem(rst);
				}

				typeSelect.setSelectedItem(rs.getSlotType());
				typeSelect.setToolTipText(rs.getSlotType().getText());

				typeSelect.setEnabled(rs.isAvailable());

				typeSelect.addItemListener(slotListener);
			}
		}

		slotPanel.validate();
		slotPanel.repaint();
	}

	private void buildRoomChangeGUI(JComboBox<House> houseSelectBox, JPanel roomPanel) {
		final ItemListener roomListener = new ItemListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void itemStateChanged(ItemEvent e) {
				try {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						JComboBox<RoomInfo> jComboBox = (JComboBox<RoomInfo>) e.getSource();
						int id = Integer.parseInt(jComboBox.getActionCommand());
						roomPlanning.getNewRooms().remove(id);
						RoomInfo roomInfo = (RoomInfo) jComboBox.getSelectedItem();
						roomPlanning.getNewRooms().add(id, roomInfo);
						jComboBox.setToolTipText(roomInfo.getDescription());
						updateCostLabel();
					}
				} catch (Exception ex) {
					log.error("Error", ex);
				}
			}
		};

		// Generate skeleton layout
		FormLayout fl = new FormLayout(new ColumnSpec[]{
				ColumnSpec.decode("default:grow"),},
				new RowSpec[]{RowSpec.decode("default:grow")});
		roomPanel.removeAll();
		roomPanel.setLayout(fl);

		// Gather data for GUI
		House house = (House) houseSelectBox.getSelectedItem();
		roomPlanning = null;

		if (house != null) {
			roomPlanning = new RoomPlanning(house);
			int i = -1;
			for (RoomSlot roomSlot : house.getRoomSlots()) {
				i++;
				JComboBox<RoomInfo> roomSelect = new JComboBox<>();
				roomSelect.setRenderer(new DefaultListCellRenderer() {
					@SuppressWarnings("rawtypes")
					public Component getListCellRendererComponent(JList list,
																  Object value, int index, boolean isSelected,
																  boolean hasFocus) {
						JLabel label = (JLabel) super.getListCellRendererComponent(
								list, value, index, isSelected, hasFocus);
						RoomInfo roomInfo = (RoomInfo) value;
						label.setText(roomInfo.getText());
						label.setToolTipText(roomInfo.getDescription());
						label.setForeground(Color.BLACK);
						label.setOpaque(false);
						return label;
					}
				});
				fl.insertRow(i + 1, RowSpec.decode("default:none"));
				roomPanel.add(roomSelect, "1," + (i + 1) + ", fill, top");
				roomSelect.setBorder(new EmptyBorder(2, 2, 2, 2));
				roomSelect.setActionCommand(i + "");
				roomSelect.setOpaque(false);

				boolean actualRoomTypeAdded = false;
				for (RoomInfo roomInfo : Jasbro.getInstance().getData().getUnlocks().getAvailableRoomTypes()) {
					if (roomInfo.fitsInSlot(roomSlot.getSlotType())) {
						roomSelect.addItem(roomInfo);
						if (roomInfo.getId().equals(roomSlot.getRoom().getRoomInfo().getId())) {
							roomSelect.setSelectedItem(roomInfo);
							roomSelect.setToolTipText(roomInfo.getDescription());
							actualRoomTypeAdded = true;
						}
					}
				}

				if (!actualRoomTypeAdded) {
					roomSelect.addItem(roomSlot.getRoom().getRoomInfo());
					roomSelect.setSelectedItem(roomSlot.getRoom().getRoomInfo());
					roomSelect.setToolTipText(roomSlot.getRoom().getRoomInfo().getDescription());
				}

				roomSelect.setEnabled(roomSlot.isAvailable());

				roomSelect.addItemListener(roomListener);
			}
		}
		roomPanel.validate();
		roomPanel.repaint();
	}

	public void updateCostLabel() {
		int totalCost = 0;
		if (roomPlanning != null) {
			totalCost += roomPlanning.getCosts();
		}

		costLabel.setText("Cost changes: " + totalCost);
		repaint();
	}
}