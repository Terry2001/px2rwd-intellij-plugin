package com.sunqian.action;

import com.intellij.openapi.project.Project;
import com.sunqian.constvalue.ConstValue;

import javax.swing.*;
import java.awt.event.*;

public class SetPX2REMTools extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField base_value;
    private JCheckBox showCalculationProcessInCheckBox;
    private ConstValue constValue;

    public SetPX2REMTools(Project project) {
        constValue = ConstValue.getInstance(project);
        setTitle("Plug-in Settings");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        base_value.setText(constValue.getRemBaseValue()+"");
        showCalculationProcessInCheckBox.setSelected(constValue.getShowCalculationProcess() != null ? constValue.getShowCalculationProcess() : false);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        String value = base_value.getText();
        try{
            constValue.setShowCalculationProcess(showCalculationProcessInCheckBox.isSelected());
            double rem = Double.parseDouble(value);
            if(rem > 0){
                constValue.setRemBaseValue(rem+"");
                dispose();
            }
        }
        catch (Exception e){

        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        /*SetPX2REMTools dialog = new SetPX2REMTools();
        dialog.pack();
        dialog.setSize(300,150);
        int windowWidth = dialog.getWidth(); //获得窗口宽
        int windowHeight = dialog.getHeight(); //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        int screenWidth = kit.getScreenSize().width; //获取屏幕的宽
        int screenHeight = kit.getScreenSize().height; //获取屏幕的高
        dialog.setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2);//设置窗口居中显示
        dialog.setVisible(true);
        System.exit(0);*/
    }
}
