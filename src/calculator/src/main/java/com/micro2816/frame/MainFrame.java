/*
 * JDK's License:
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * The program's License:
 * Since the program obeys to Apache License. Please see more details about Apache License.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.micro2816.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.alibaba.fastjson.util.IOUtils;
import com.micro2816.action.CommandPermutationIteratorHandler;
import com.micro2816.action.MyPermutationIteratorHandler;
import com.micro2816.action.PermutationAction;
import com.micro2816.action.PermutationIteratorBean;
import com.micro2816.common.ValidationResult;
import com.micro2816.tools.CommandUtils;

/**
 * <p>
 * MainFrame class implements a GUI for the users. It is the entrance of this program.
 * <p>
 * @author  Micro He
 * @see     javax.swing.JFrame
 * @since   JDK1.6
 * @copyright Micro2816 Corporation Limited Copyright (c) 2019
 */

public class MainFrame extends JFrame {
    
    private static final long serialVersionUID = -3092345997700276128L;
    public static final Logger logger = Logger.getLogger(MainFrame.class);
    private JPanel contentPane;
    private final static PermutationAction permutationAction = new PermutationAction();
    private JLabel lbl_inputFormatTip;
    private JLabel lbl_inputFormatTip2;
    private JTextField tf_input;
    private JButton btn_calc;
    private JButton btn_store;
    private JTextArea ta_log;
    private JFileChooser fc_chooseFilePath;
    private String separatedLine = "\r\n*************************************************\r\n";
    
    static {
        try {
            String filePath = "D:\\workspace\\java_projects\\log4j.properties";
            if (!(new File(filePath).exists())) {
                filePath = System.getProperty("user.dir") + File.separator + "log4j.properties";
            }
            initLog4j(filePath);
            BasicConfigurator.configure();
            PropertyConfigurator.configure(filePath);
        } catch (Exception ex) {
            
        }
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        Map<String, String> argsMap = CommandUtils.parseArguments(args);
//        System.err.println(JSON.toJSONString(argsMap));
        String uiStr = argsMap.get("--ui");
        if (uiStr == null || "0".equalsIgnoreCase(uiStr)) {
            CommandPermutationIteratorHandler handler = CommandPermutationIteratorHandler.getInstance();
            String systemProperties = argsMap.get("--systemProperties");
            if (StringUtils.isBlank(systemProperties) || (!new File(systemProperties).exists())) {
                throw new IllegalArgumentException("The file named system.properties cannot be found.");
            }
            try {
                permutationAction.loadSystemProperties(systemProperties);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            String text = argsMap.get("--digits");
            ValidationResult result = permutationAction.validate(text);
            if (!result.isStatus()) {
                throw new IllegalArgumentException(result.getMessage());
            }
            
            String filePath = argsMap.get("--outputPath");
            
            PermutationIteratorBean bean = new PermutationIteratorBean();
            bean.digitMap = permutationAction.getDigitsLettersMap();
            bean.intArray = (int[]) result.getData();
            bean.handler = handler;
            bean.limitNumber = permutationAction.getLimitNumber();
            try {
                handler.begin(filePath);
                permutationAction.iterator(bean);
            } catch (Exception ex) {
              ex.printStackTrace();
            } finally {
                handler.dispose();
            }
            return;
        }
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
//            ex.printStackTrace();
//            logger.error("error occurs while setting windows look and it's feel.", ex);
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("failed to initialize GUI!", e);
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainFrame() {
        this.setTitle("calculator(convert digits to letters) - By Micro He");
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 430;
        int height = 360;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - width) >>> 1;
        int y = (screenSize.height - height) >>> 1;
        setBounds(x, y, width, height);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);  // new BorderLayout(0, 0)
        setContentPane(contentPane);
        
        lbl_inputFormatTip = new JLabel("input format must be like this: 2 3 9 7 8.");
        lbl_inputFormatTip2 = new JLabel("input digits:");
        tf_input = new JTextField();
        btn_calc = new JButton("go");
        btn_store = new JButton("save log");
        ta_log = new JTextArea();
        fc_chooseFilePath = new JFileChooser();
        
        lbl_inputFormatTip.setBounds(3, 3, 420, 28);
        lbl_inputFormatTip.setForeground(Color.RED);
        contentPane.add(lbl_inputFormatTip);
        
        lbl_inputFormatTip2.setBounds(3, 34, 96, 28);
        contentPane.add(lbl_inputFormatTip2);
        final MainFrame frame = this;
//        ta_log.setBorder(new EmptyBorder(5, 5, 5, 5));
        btn_store.setBounds(3, 65, 90, 28);
        contentPane.add(btn_store);
        
        fc_chooseFilePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
        
        // action for save log. It can save the log to disk.
        btn_store.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = ta_log.getText();
                if (StringUtils.isBlank(str)) {
                    JOptionPane.showMessageDialog(frame, "log text is blank!", "information tip!", 0);
                    return;
                }
                fc_chooseFilePath.showDialog(frame, "Choose preferred directory");
                File chosenDir = fc_chooseFilePath.getSelectedFile();
                if (chosenDir == null || (!chosenDir.exists())) {
                    ta_log.append(separatedLine);
                    ta_log.append("Any directory is not chosen! system cannot persist data at all.");
                    return;
                }
                String filePath = chosenDir.getAbsolutePath() 
                        + File.separator + "calc-" + System.currentTimeMillis() + ".txt";  
                try {
                    FileUtils.write(new File(filePath), str);
                } catch (IOException e1) {
                    String msg = "failed write log message to disk!";
                    logger.error(msg, e1);
                    JOptionPane.showMessageDialog(frame, msg, "error tip!", 0);
                }
            }
        });
        
        JScrollPane sp_set_ta_log = new javax.swing.JScrollPane(ta_log);
        sp_set_ta_log.setBounds(3, 96, 420, 233);
        sp_set_ta_log.setViewportBorder(new javax.swing.border.TitledBorder(
                javax.swing.UIManager.getBorder("TitledBorder.border"),
                "\u7ED3\u679C",
                4, 2, null, new java.awt.Color(0, 0, 205)));
        contentPane.add(sp_set_ta_log);
        
        tf_input.setBounds(83, 34, 250, 28);
        contentPane.add(tf_input);
        
        btn_calc.setBounds(336, 34, 60, 28);
        contentPane.add(btn_calc);
        
        btn_calc.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = tf_input.getText();
                ValidationResult result = permutationAction.validate(text);
                if (!result.isStatus()) {
                    JOptionPane.showMessageDialog(frame, result.getMessage(), "error tip!", 0);
                    return;
                }
                MyPermutationIteratorHandler handler = MyPermutationIteratorHandler.getInstance();
                handler.setLength(0);
                PermutationIteratorBean bean = new PermutationIteratorBean();
                bean.digitMap = permutationAction.getDigitsLettersMap();
                bean.intArray = (int[]) result.getData();
                bean.handler = handler;
                bean.mainFrame = frame;
                bean.limitNumber = permutationAction.getLimitNumber();
                permutationAction.iterator(bean);
                ta_log.append(separatedLine);
                int position = ta_log.getCaretPosition();
                ta_log.append(handler.getContent());
                ta_log.setCaretPosition(position);
            }
        });
        
        try {
            permutationAction.loadSystemProperties(null);
        } catch (Exception e) {
            String msg = "failed to load system.properties!";
            logger.error(msg, e);
            JOptionPane.showMessageDialog(this, msg, "error tip!", 0);
            System.exit(0);
        }
    }
    
    /**
     * @see return a file chooser
     * @author Micro
     * @since 2019年10月18日 下午10:15:56
     * @return
     * @return JFileChooser
     */
    public JFileChooser getJFileChooser () {
         return this.fc_chooseFilePath;
    }
    
    /**
     * @see initialize log4j
     * @author Micro
     * @since 2019年10月18日 上午9:42:15
     * @param fileDir the file directory of log4j.properties
     * @return void
     */
    public static void initLog4j(String fileDir) {
        Properties prop = null;
        InputStream in =null;
        try {
            prop = new Properties();
            in = new BufferedInputStream(new FileInputStream(fileDir));
            prop.load(in);
            String key = "log4j.appender.File.File";
            String value = prop.getProperty(key);
            if (value != null) {
                File file = new File(value);
                if (!file.getParentFile().exists()) { file.getParentFile().mkdirs(); }
                if (!file.exists()) { file.createNewFile(); }
            }
            
            key = "log4j.appender.R.File";
            value = prop.getProperty(key);
            if (value != null) {
                File file = new File(value);
                if (!file.getParentFile().exists()) { file.getParentFile().mkdirs(); }
                if (!file.exists()) { file.createNewFile(); }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
            IOUtils.close(in);
        }
    }

    /**
     * @see append message to JTextArea
     * @author Micro
     * @since 2019年10月19日 上午8:03:55
     * @param msg
     * @return void
     */
    public void appendMessage(String msg) {
        if (msg == null || msg.length() == 0) { msg = "blank information!"; }
        this.ta_log.append(separatedLine);
        this.ta_log.append(msg);
        this.ta_log.append("\r\n");
    }
}
