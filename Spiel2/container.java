package Spiel2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import Spiel2.Game.Level;

public class container extends JPanel {
    private JSplitPane splitPane;
    private JPanel LeftPane;

    public JPanel getLeftPane() {
        return LeftPane;
    }

    public void setLeftPane(JPanel leftPane) {
        LeftPane = leftPane;
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }

    public void setSplitPane(JSplitPane splitPane) {
        this.splitPane = splitPane;
    }

    private upper upper;

    public upper getUpper() {
        return upper;
    }

    public void setUpper(upper upper) {
        this.upper = upper;
    }

    private static lower lower;

    public lower getLower() {
        return lower;
    }

    public void setLower(lower lower) {
        container.lower = lower;
    }

    private settings sett;

    public settings getSett() {
        return sett;
    }

    public GridBagConstraints gbc;


    public container() {
        // setPreferredSize(new Dimension(1000, 800));
        setLayout(new GridBagLayout());
        upper = new upper(Level.TRAINING);
        lower = new lower(this);
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(upper);
        splitPane.setBottomComponent(lower);
        splitPane.setMinimumSize(new Dimension(400, 600));
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);
        LeftPane = new JPanel();
        LeftPane.setLayout(new BorderLayout());
        // LeftPane.add(splitPane);
        // size of upper pane has to be < than size of scroll pane
        // upper.setPreferredSize(new Dimension(300,300));
        // upper.getScroll_table().setPreferredSize(new Dimension(500,600));
        // lower.setMinimumSize(new Dimension(800,500));
        // splitPane.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.weighty = 1;
        add(LeftPane, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.4;


        sett = new settings(this);

        add(sett, gbc);
        setFocusable(false);
        splitPane.setFocusable(false);
        upper.setFocusable(false);
        settings.getTrain_b().setFocusable(false);
        settings.getMed_b().setFocusable(false);
        settings.getHard_b().setFocusable(false);
        setVisible(true);
 
       

    }

}

