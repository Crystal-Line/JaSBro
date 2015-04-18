package jasbro.util.itemEditor.usableItemEffectPanel;

import jasbro.game.items.usableItemEffects.UsableItemAddGold;
import jasbro.game.items.usableItemEffects.UsableItemEffect;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class UsableItemAddGoldPanel extends JPanel {
	private UsableItemAddGold itemEffect;

	public UsableItemAddGoldPanel(UsableItemEffect usableItemEffect) {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("left:default"),
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		add(new JLabel(usableItemEffect.getName()), "1, 1, left, center");
		this.itemEffect = (UsableItemAddGold)usableItemEffect;

		final JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, null, null, 1));
		spinner.setValue(itemEffect.getAmount());
		add(spinner, "2, 1, fill, top");
		spinner.addChangeListener(new ChangeListener() {				
			@Override
			public void stateChanged(ChangeEvent e) {
				itemEffect.setAmount((int)spinner.getValue());
			}
		});
	}
}
