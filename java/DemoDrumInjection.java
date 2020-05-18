/* *
 * Drum Injection
 *
 *  an injection-pump, inject additive liquid into a rolling dryer, aka drum,
 *   automatically adjust its own speed synced with other aggregates suppliment, 
 *   from a liquid tank.
 *  
 * the pump can get started/stopped manually.
 * the pump speed can NOT be adjusted manually.
 * 
 * other control descriptions can be found inside the dialog window.
 * to exit, press the 'q' key.
 *
 */

package ppptest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import processing.core.PApplet;
import static processing.core.PApplet.ceil;

public class DemoDrumInjection extends PApplet {
  
  static private DemoDrumInjection self;
  
  //=== const 
  
  static final float C_TPH_MAX =   90f;
  static final float C_RPM_MAX = 1800f;
  
  static final int C_COLOR_METAL = 0xFF787878;
  static final int C_COLOR_DUCT  = 0xFFABABAB;
  
  //=== model
  
  static volatile private int vmRoller=0;
  
  static volatile private float vmFeederTPH = 30f;
  static volatile private float vmFeederTPHZero = 0f;
  static volatile private float vmFeederTPHSpan = 60f;
  static volatile private float vmFeederRPM = 900f;
  static volatile private float vmFeederRPMZero = 0f;
  static volatile private float vmFeederRPMSpan = C_RPM_MAX;
  
  static volatile private float vmFeederAdjustStep = 1f;
  
  static volatile private int vmDelayTimeSEC = 10;
  
  static volatile private float vmAdditveBias  = 0.10f;
  static volatile private float vmAdditveBiasL = 0.05f;
  static volatile private float vmAdditveBiasH = 0.25f;
  
  static volatile private float vmPumpTPH = 50f;
  static volatile private float vmPumpTPHZero = 0f;
  static volatile private float vmPumpTPHSpan = 10f;
  static volatile private float vmPumpRPM = 900f;
  static volatile private float vmPumpRPMZero = 0f;
  static volatile private float vmPumpRPMSpan = C_RPM_MAX;
  
  //=== swing ui
  
  //-- swing ** info message
  static final String[] C_DES_MESSAGE = new String[]{
    "## basic usage: \n",
    " 1) bla bla bla.\n",
    " 2) bla bla bla.\n",
    " 3) bla bla bla.\n",
    "## formulas : \n",
    " - blah blah blah.\n",
    " - blah blah blah.\n",
    " - blah blah blah.\n",
    " - blah blah blah.\n",
    " \n",
    "have fun!\n"
  };
  
  static volatile String vmInputDialogBrief = "(?)";
  static volatile String vmInputDialogDefault = "...";
  static volatile String vmLastInputted = "";
  
  //-- swing ** components
  
  static final JFrame O_FRAME = new JFrame();
  
  static final JTextField O_AGG_SUP_TB = new JTextField(" 000TPH ");
  
  static final JButton O_AGG_INC_SW = new JButton("++");
  
  static final JButton O_AGG_DEC_SW = new JButton("--");
  
  static final JTextField O_APG_BIAS_TB  = new JTextField(" 000% ");
    
  static final JTextField O_APG_BIASL_TB = new JTextField(" 000% ");
  
  static final JTextField O_APG_BIASH_TB = new JTextField(" 000% ");
  
  static final JToggleButton O_MNG_FED_SW = new JToggleButton("Feeder");
  
  static final JToggleButton O_MNG_ADP_SW = new JToggleButton("Pump");
  
  static final List<JTextField> O_LES_AGG_MAPPER = 
    Collections.unmodifiableList(Arrays.asList(
      new JTextField(" 0000RPM "),new JTextField(" 0000TPH "),
      new JTextField(" 0000RPM "),new JTextField(" 0000TPH ")
    ));
  
  static final List<JTextField> O_LES_APG_MAPPER = 
    Collections.unmodifiableList(Arrays.asList(
      new JTextField(" 0000RPM "),new JTextField(" 0000TPH "),
      new JTextField(" 0000RPM "),new JTextField(" 0000TPH ")
    ));
  
  static final JTextField O_ADJ_STEP_TB = new JTextField(" 000T ");
  
  static final JTextField O_DELAY_TIME_TB = new JTextField(" 000S ");
  
  //-- swing ** action
  
  static final Runnable T_INPUT_GETTING = new Runnable() {
    @Override public void run() {
      if(!SwingUtilities.isEventDispatchThread())
        {System.err.println(".$ OEDT!!");return;}
      vmLastInputted = JOptionPane.showInputDialog(
        O_FRAME,
        vmInputDialogBrief, vmInputDialogDefault
      );
    }//+++
  };//***eof
  
  static final ActionListener M_NOTCH_LISTENER = new ActionListener() {
    @Override public void actionPerformed(ActionEvent ae) {
      Object lpSouce = ae.getSource();

      //if(lpSource.equals(...))
      
      //-- unhandled
      {System.err.println(
        "O_NOTCH_LISTENER::unhandled:"+lpSouce.toString()
      );}//..?

    }//+++
  };//***
  
  static final MouseAdapter M_MOMENTARY_LISTENER = new MouseAdapter() {
    @Override public void mousePressed(MouseEvent me) {
      Object lpSource = me.getSource();

      //if(lpSource.equals(...))
      
      if(lpSource.equals(O_AGG_INC_SW)){
        vmFeederTPH+=vmFeederAdjustStep;
        vmFeederTPH=PApplet.constrain(vmFeederTPH, 0f, C_TPH_MAX);
        ssRefreshAggregateTPH();
        return;
      }//..?
      
      if(lpSource.equals(O_AGG_DEC_SW)){
        vmFeederTPH-=vmFeederAdjustStep;
        vmFeederTPH=PApplet.constrain(vmFeederTPH, 0f, C_TPH_MAX);
        ssRefreshAggregateTPH();
        return;
      }//..?
      
      //-- unhandled
      System.err.println(
        "O_MOMENTARY_LISTENER::unhandled:"+lpSource.toString()
      );

    }//+++
    @Override public void mouseReleased(MouseEvent me) {

      //-- reset bit

    }//+++
  };//***
  
  static final MouseAdapter M_INPUT_BOX_LISTENER = new MouseAdapter() {
    @Override public void mouseReleased(MouseEvent me) {
      Object lpSource = me.getSource();

      //if(lpSource.equals(...)){vm...=...;T_INPUT_GETTING.Run();
      
      //-- unfilling
      
      if(lpSource.equals(O_AGG_SUP_TB)){
        return;
      }//..?
      
      //-- bias
      
      if(lpSource.equals(O_APG_BIAS_TB)){
        vmInputDialogBrief = "to current aggregate suppliment";
        vmInputDialogDefault = PApplet.nf(ceil(vmAdditveBias*100f),2);
        T_INPUT_GETTING.run();
        float lpRaw = (float)(ccParseInteger(vmLastInputted));
        lpRaw=PApplet.constrain(lpRaw, 0f, 100f);
        vmAdditveBias = lpRaw/100f;
        ssRefreshAdditiveBias();
        return;
      }//..?
      
      if(lpSource.equals(O_APG_BIASH_TB)){
        vmInputDialogBrief = "to sacler span";
        vmInputDialogDefault = PApplet.nf(ceil(vmAdditveBiasH*100f),2);
        T_INPUT_GETTING.run();
        float lpRaw = (float)(ccParseInteger(vmLastInputted));
        lpRaw=PApplet.constrain(lpRaw, 0f, 100f);
        lpRaw = lpRaw/100f;
        if(lpRaw<=vmAdditveBiasL){
          JOptionPane.showMessageDialog(O_DELAY_TIME_TB,
            "high bound value must be bigger than low bound",
            "<!>", JOptionPane.ERROR_MESSAGE
          );
          return;
        }//..?
        vmAdditveBiasH=lpRaw;
        ssRefreshAdditiveBias();
        return;
      }//..?
      
      if(lpSource.equals(O_APG_BIASL_TB)){
        vmInputDialogBrief = "to sacler span";
        vmInputDialogDefault = PApplet.nf(ceil(vmAdditveBiasL*100f),2);
        T_INPUT_GETTING.run();
        float lpRaw = (float)(ccParseInteger(vmLastInputted));
        lpRaw=PApplet.constrain(lpRaw, 0f, 100f);
        lpRaw = lpRaw/100f;
        if(lpRaw>=vmAdditveBiasH){
          JOptionPane.showMessageDialog(O_DELAY_TIME_TB,
            "low bound value must be smaller than high bound",
            "<!>", JOptionPane.ERROR_MESSAGE
          );
          return;
        }//..?
        vmAdditveBiasL=lpRaw;
        ssRefreshAdditiveBias();
        return;
      }//..?
      
      //-- feeder scale
      
      if(lpSource.equals(O_LES_AGG_MAPPER.get(0))){
        vmInputDialogBrief = "motor speed zero value";
        vmInputDialogDefault = PApplet.nfc(vmFeederRPMZero,0);
        T_INPUT_GETTING.run();
        float lpRaw = ccParseFloat(vmLastInputted);
        lpRaw=PApplet.constrain(lpRaw, 0f, C_RPM_MAX);
        if(lpRaw>=vmFeederRPMSpan){
          JOptionPane.showMessageDialog(O_DELAY_TIME_TB,
            "zero value must be lesser than span value",
            "<!>", JOptionPane.ERROR_MESSAGE
          );
          return;
        }//..?
        vmFeederRPMZero = lpRaw;
        ssRefreshFeederScaler();
        return;
      }//..?
      
      if(lpSource.equals(O_LES_AGG_MAPPER.get(2))){
        vmInputDialogBrief = "motor speed span value";
        vmInputDialogDefault = PApplet.nfc(vmFeederRPMSpan,0);
        T_INPUT_GETTING.run();
        float lpRaw = ccParseFloat(vmLastInputted);
        lpRaw=PApplet.constrain(lpRaw, 0f, C_RPM_MAX);
        if(lpRaw<=vmFeederRPMZero){
          JOptionPane.showMessageDialog(O_DELAY_TIME_TB,
            "span value must be greater than zero value",
            "<!>", JOptionPane.ERROR_MESSAGE
          );
          return;
        }//..?
        vmFeederRPMSpan = lpRaw;
        ssRefreshFeederScaler();
        return;
      }//..?
      
      if(lpSource.equals(O_LES_AGG_MAPPER.get(1))){
        vmInputDialogBrief = "aggregate suppliment zero value";
        vmInputDialogDefault = PApplet.nfc(vmFeederTPHZero,1);
        T_INPUT_GETTING.run();
        float lpRaw = ccParseFloat(vmLastInputted);
        lpRaw=PApplet.constrain(lpRaw, 0f, C_TPH_MAX);
        if(lpRaw>=vmFeederTPHSpan){
          JOptionPane.showMessageDialog(O_DELAY_TIME_TB,
            "zero value must be lesser than span value",
            "<!>", JOptionPane.ERROR_MESSAGE
          );
          return;
        }//..?
        vmFeederTPHZero = lpRaw;
        ssRefreshFeederScaler();
        return;
      }//..?
      
      if(lpSource.equals(O_LES_AGG_MAPPER.get(3))){
        vmInputDialogBrief = "aggregate suppliment span value";
        vmInputDialogDefault = PApplet.nfc(vmFeederTPHSpan,1);
        T_INPUT_GETTING.run();
        float lpRaw = ccParseFloat(vmLastInputted);
        lpRaw=PApplet.constrain(lpRaw, 0f, C_TPH_MAX);
        if(lpRaw<=vmFeederTPHZero){
          JOptionPane.showMessageDialog(O_DELAY_TIME_TB,
            "span value must be greater than zero value",
            "<!>", JOptionPane.ERROR_MESSAGE
          );
          return;
        }//..?
        vmFeederTPHSpan = lpRaw;
        ssRefreshFeederScaler();
        return;
      }//..?
      
      //-- pump scaler
      
      if(lpSource.equals(O_LES_APG_MAPPER.get(0))){
        vmInputDialogBrief = "motor speed zero value";
        vmInputDialogDefault = PApplet.nfc(vmPumpRPMZero,0);
        T_INPUT_GETTING.run();
        float lpRaw = ccParseFloat(vmLastInputted);
        lpRaw=PApplet.constrain(lpRaw, 0f, C_RPM_MAX);
        if(lpRaw>=vmPumpRPMSpan){
          JOptionPane.showMessageDialog(O_DELAY_TIME_TB,
            "zero value must be lesser than span value",
            "<!>", JOptionPane.ERROR_MESSAGE
          );
          return;
        }//..?
        vmPumpRPMZero = lpRaw;
        ssRefreshPumpScaler();
        return;
      }//..?
      
      if(lpSource.equals(O_LES_APG_MAPPER.get(2))){
        vmInputDialogBrief = "motor speed span value";
        vmInputDialogDefault = PApplet.nfc(vmPumpRPMSpan,0);
        T_INPUT_GETTING.run();
        float lpRaw = ccParseFloat(vmLastInputted);
        lpRaw=PApplet.constrain(lpRaw, 0f, C_RPM_MAX);
        if(lpRaw<=vmPumpRPMZero){
          JOptionPane.showMessageDialog(O_DELAY_TIME_TB,
            "span value must be greater than zero value",
            "<!>", JOptionPane.ERROR_MESSAGE
          );
          return;
        }//..?
        vmPumpRPMSpan = lpRaw;
        ssRefreshPumpScaler();
        return;
      }//..?
      
      if(lpSource.equals(O_LES_APG_MAPPER.get(1))){
        vmInputDialogBrief = "additive flux zero value";
        vmInputDialogDefault = PApplet.nfc(vmPumpTPHZero,1);
        T_INPUT_GETTING.run();
        float lpRaw = ccParseFloat(vmLastInputted);
        lpRaw=PApplet.constrain(lpRaw, 0f, C_TPH_MAX);
        if(lpRaw>=vmPumpTPHSpan){
          JOptionPane.showMessageDialog(O_DELAY_TIME_TB,
            "zero value must be lesser than span value",
            "<!>", JOptionPane.ERROR_MESSAGE
          );
          return;
        }//..?
        vmPumpTPHZero = lpRaw;
        ssRefreshPumpScaler();
        return;
      }//..?
      
      if(lpSource.equals(O_LES_APG_MAPPER.get(3))){
        vmInputDialogBrief = "additive flux span value";
        vmInputDialogDefault = PApplet.nfc(vmPumpTPHSpan,1);
        T_INPUT_GETTING.run();
        float lpRaw = ccParseFloat(vmLastInputted);
        lpRaw=PApplet.constrain(lpRaw, 0f, C_TPH_MAX);
        if(lpRaw<=vmPumpTPHZero){
          JOptionPane.showMessageDialog(O_DELAY_TIME_TB,
            "span value must be greater than zero value",
            "<!>", JOptionPane.ERROR_MESSAGE
          );
          return;
        }//..?
        vmPumpTPHSpan = lpRaw;
        ssRefreshPumpScaler();
        return;
      }//..?
      
      //-- adjusting 
      
      if(lpSource.equals(O_ADJ_STEP_TB)){
        vmInputDialogBrief = "step of incre/decrement";
        vmInputDialogDefault = PApplet.nfc(vmFeederAdjustStep,0);
        T_INPUT_GETTING.run();
        float lpRaw = (float)(ccParseInteger(vmLastInputted));
        vmFeederAdjustStep = PApplet.constrain(lpRaw, 1f, 10f);
        ssRefreshAdjusting();
        return;
      }//..?
      
      if(lpSource.equals(O_DELAY_TIME_TB)){
        vmInputDialogBrief = "from send-out to arrival";
        vmInputDialogDefault = Integer.toString(vmDelayTimeSEC);
        T_INPUT_GETTING.run();
        int lpRaw = ccParseInteger(vmLastInputted);
        vmDelayTimeSEC = PApplet.constrain(lpRaw, 5, 3600);
        ssRefreshAdjusting();
        return;
      }//..?
      
      //-- unhandled
      System.err.println(
        "O_INPUT_BOX_LISTENER::unhandled:"+lpSource.toString()
      );//..?

    }//+++
  };//***
  
  //-- swing ** steup
  
  static final Runnable T_SWING_INIT = new Runnable() {
    @Override public void run() {
      
      //-- restyle
      try{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }catch(Exception e){
        System.err.println("O_SWING_INIT::"+e.getMessage());
        System.exit(-1);
      }//..?
      
      //-- ui setup
      ccSetupInputBox(O_AGG_SUP_TB);
      O_AGG_SUP_TB.setDisabledTextColor(Color.DARK_GRAY);
      ccSetupInputBox(O_APG_BIAS_TB);
      ccSetupInputBox(O_APG_BIASH_TB);
      ccSetupInputBox(O_APG_BIASL_TB);
      ccSetupInputBox(O_DELAY_TIME_TB);
      ccSetupInputBox(O_ADJ_STEP_TB);
      ccSetupMomentarySwitch(O_AGG_INC_SW);
      ccSetupMomentarySwitch(O_AGG_DEC_SW);
      O_MNG_FED_SW.addActionListener(M_NOTCH_LISTENER);
      O_MNG_ADP_SW.addActionListener(M_NOTCH_LISTENER);
      
      //-- aggregate group
      JPanel lpAggregatePane = new JPanel(new GridLayout(4, 1));
      lpAggregatePane.setBorder(BorderFactory.createTitledBorder("Aggregate"));
      lpAggregatePane.add(new JLabel("Suppliment:"));
      lpAggregatePane.add(O_AGG_SUP_TB);
      lpAggregatePane.add(O_AGG_INC_SW);
      lpAggregatePane.add(O_AGG_DEC_SW);
      
      //-- additive pump group
      JPanel lpPumpPane = new JPanel(new GridLayout(3, 2));
      lpPumpPane.setBorder(BorderFactory.createTitledBorder("Additive"));
      lpPumpPane.add(new JLabel("Bias:"));
      lpPumpPane.add(O_APG_BIAS_TB);
      lpPumpPane.add(new JLabel("Low Bound:"));
      lpPumpPane.add(O_APG_BIASH_TB);
      lpPumpPane.add(new JLabel("High Bound:"));
      lpPumpPane.add(O_APG_BIASL_TB);
      
      //-- mannual group
      JPanel lpMannualPane = new JPanel(new GridLayout(1, 2));
      lpMannualPane.setBorder(BorderFactory.createTitledBorder("Mannual"));
      lpMannualPane.add(O_MNG_FED_SW);
      lpMannualPane.add(O_MNG_ADP_SW);
      
      //-- operate pane
      JPanel lpOperatingPane = new JPanel(new BorderLayout(1,1));
      lpOperatingPane.add(lpAggregatePane,BorderLayout.LINE_START);
      lpOperatingPane.add(lpPumpPane,BorderLayout.CENTER);
      lpOperatingPane.add(lpMannualPane,BorderLayout.PAGE_END);
      
      //-- scaler lane
      for(int i=0;i<4;i++){
        ccSetupInputBox(O_LES_AGG_MAPPER.get(i));
        ccSetupInputBox(O_LES_APG_MAPPER.get(i));
      }//..~
      JPanel lpFeederScalerLane = new JPanel();
      JPanel lpPumpScalerLane = new JPanel();
      ccSetupSclaerLane(lpFeederScalerLane, O_LES_AGG_MAPPER, "Feeder");
      ccSetupSclaerLane(lpPumpScalerLane, O_LES_APG_MAPPER, "Pump");
      
      //-- adjust lane
      JPanel lpAdjustLane 
        = new JPanel(new FlowLayout(FlowLayout.LEADING, 1, 1));
      lpAdjustLane.setBorder(BorderFactory.createTitledBorder("Adjust"));
      lpAdjustLane.add(new JLabel("Step:"));
      lpAdjustLane.add(O_ADJ_STEP_TB);
      lpAdjustLane.add(new JSeparator(SwingConstants.VERTICAL));
      lpAdjustLane.add(new JLabel("Delay:"));
      lpAdjustLane.add(O_DELAY_TIME_TB);
      
      //-- setting pane
      JPanel lpSettingPane = new JPanel(new GridLayout(4,1));
      lpSettingPane.add(lpFeederScalerLane);
      lpSettingPane.add(lpPumpScalerLane);
      lpSettingPane.add(lpAdjustLane);
      
      //-- info pane
      JPanel lpInfoPane = new JPanel(new BorderLayout(1, 1));
      JTextArea lpInfoArea = new JTextArea("How to use:\n");
      lpInfoArea.setEditable(false);
      lpInfoArea.setEnabled(false);
      for(String it : C_DES_MESSAGE){lpInfoArea.append(it);}
      lpInfoPane.add(new JScrollPane(lpInfoArea));
      
      //-- content pane
      JTabbedPane lpContentPane = new JTabbedPane();
      lpContentPane.add("Operating",lpOperatingPane);
      lpContentPane.add("Setting",lpSettingPane);
      lpContentPane.add("Info",lpInfoPane);
      
      //-- pack
      O_FRAME.setTitle(self.getClass().getName());
      O_FRAME.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
      O_FRAME.getContentPane().add(lpContentPane);
      O_FRAME.setPreferredSize(new Dimension(320, 240));
      O_FRAME.setResizable(false);
      O_FRAME.setLocation(240,240);
      O_FRAME.pack();
      O_FRAME.setVisible(true);
      
      //-- post
      ssRefreshAggregateTPH();
      ssRefreshAdditiveBias();
      ssRefreshFeederScaler();
      ssRefreshPumpScaler();
      ssRefreshAdjusting();
      O_MNG_FED_SW.requestFocus();
      self.frame.requestFocus();
      System.out.println(".O_SWING_INIT $ end");
      
    }//+++
  };//***
  
  static final Runnable T_SWING_FLIP = new Runnable() {
    @Override public void run() {
      boolean lpNow = O_FRAME.isVisible();
      O_FRAME.setVisible(!lpNow);
    }//+++
  };//***
  
  //=== refresh
  
  static final void ssRefreshAggregateTPH(){
    O_AGG_SUP_TB.setText(PApplet.nfc(vmFeederTPH, 0)+"t/h");
  }//+++
  
  static final void ssRefreshAdditiveBias(){
    O_APG_BIAS_TB.setText(PApplet.nf(ceil(vmAdditveBias*100f),2)+"%");
    O_APG_BIASH_TB.setText(PApplet.nf(ceil(vmAdditveBiasH*100f),2)+"%");
    O_APG_BIASL_TB.setText(PApplet.nf(ceil(vmAdditveBiasL*100f),2)+"%");
  }//+++
  
  static final void ssRefreshFeederScaler(){
    O_LES_AGG_MAPPER.get(0).setText(PApplet.nfc(vmFeederRPMZero,0)+"r/m");
    O_LES_AGG_MAPPER.get(1).setText(PApplet.nfc(vmFeederTPHZero,1)+"t/h");
    O_LES_AGG_MAPPER.get(2).setText(PApplet.nfc(vmFeederRPMSpan,0)+"r/m");
    O_LES_AGG_MAPPER.get(3).setText(PApplet.nfc(vmFeederTPHSpan,1)+"t/h");
  }//+++
  
  static final void ssRefreshPumpScaler(){
    O_LES_APG_MAPPER.get(0).setText(PApplet.nfc(vmPumpRPMZero,0)+"r/m");
    O_LES_APG_MAPPER.get(1).setText(PApplet.nfc(vmPumpTPHZero,2)+"t/h");
    O_LES_APG_MAPPER.get(2).setText(PApplet.nfc(vmPumpRPMSpan,0)+"r/m");
    O_LES_APG_MAPPER.get(3).setText(PApplet.nfc(vmPumpTPHSpan,2)+"t/h");
  }//+++
  
  static final void ssRefreshAdjusting(){
    O_ADJ_STEP_TB.setText(PApplet.nfc(vmFeederAdjustStep,0)+"T");
    O_DELAY_TIME_TB.setText(Integer.toString(vmDelayTimeSEC)+"S");
  }//+++
  
  //=== factory
  
  static final void ccSetupInputBox(JTextField pxTarget){
    pxTarget.setEditable(false);
    pxTarget.setEnabled(false);
    pxTarget.setColumns(5);
    pxTarget.setBackground(Color.decode("#EEEEEE"));
    pxTarget.setForeground(Color.DARK_GRAY);
    pxTarget.setDisabledTextColor(Color.decode("#336699"));
    pxTarget.setHorizontalAlignment(JTextField.RIGHT);
    pxTarget.addMouseListener(M_INPUT_BOX_LISTENER);
  }//+++

  static final void ccSetupMomentarySwitch(JButton pxTarget){
    pxTarget.setFocusPainted(false);
    pxTarget.setBackground(Color.decode("#E1E1E1"));
    pxTarget.setForeground(Color.decode("#339933"));
    pxTarget.addMouseListener(M_MOMENTARY_LISTENER);
  }//+++
  
  static final
  void ccSetupSclaerLane(JPanel pxLane, List<JTextField> pxMap, String pxTitle){
    pxLane.setLayout(new FlowLayout(FlowLayout.LEADING, 1, 1));
    pxLane.setBorder(BorderFactory.createTitledBorder(pxTitle));
    pxLane.add(pxMap.get(0));
    pxLane.add(pxMap.get(1));
    pxLane.add(new JSeparator(SwingConstants.VERTICAL));
    pxLane.add(new JLabel("->"));
    pxLane.add(new JSeparator(SwingConstants.VERTICAL));
    pxLane.add(pxMap.get(2));
    pxLane.add(pxMap.get(3));
  }//+++
  
  //=== utility
  
  static final boolean ccIsValidString(String pxLine){
    if(pxLine==null){return false;}
    return !pxLine.isEmpty();
  }//+++

  static final float ccToSecond(int pxFrameCount){
    float lpAmplified = ((float)pxFrameCount)*100f/16f;
    int lpCeiled = ceil(lpAmplified);return ((float)(lpCeiled))/100f;
  }//+++

  static final int ccToFrameCount(float pxSecond){
    return (int)(pxSecond*16f);
  }//+++
  
  static final int ccParseInteger(String pxSource){
    int lpRes = 0;
    try {
      lpRes = Integer.valueOf(pxSource);
    } catch (Exception e) {
      lpRes = 0;
    }//..?
    return lpRes;
  }//+++
  
  static final float ccParseFloat(String pxSource){
    float lpRes = 0f;
    try {
      lpRes = Float.valueOf(pxSource);
    } catch (Exception e) {
      lpRes = 0f;
    }//..?
    return lpRes;
  }//+++
  
  //=== local ui
  
  void ccSetLocation(Rectangle pxTarget, int pxX, int pxY){
    pxTarget.x=pxX;
    pxTarget.y=pxY;
  }//+++
  
  void ccSetSize(Rectangle pxTarget, int pxW, int pxH){
    pxTarget.width=pxW;
    pxTarget.height=pxH;
  }//+++
  
  void ccSetBound(Rectangle pxTarget, int pxX, int pxY, int pxW, int pxH){
    ccSetLocation(pxTarget, pxX, pxY);
    ccSetSize(pxTarget, pxW, pxH);
  }//+++
  
  int ccGetCenterX(Rectangle pxTarget){
    return pxTarget.x+pxTarget.width/2;
  }//+++

  int ccGetCenterY(Rectangle pxTarget){
    return pxTarget.y+pxTarget.height/2;
  }//+++

  int ccGetEndX(Rectangle pxTarget){
    return pxTarget.x + pxTarget.width;
  }//+++

  int ccGetEndY(Rectangle pxTarget){
    return pxTarget.y + pxTarget.height;
  }//+++
  
  void ccFollowEast(Rectangle pxSelf, Rectangle pxTarget, int pxOffset){
    pxSelf.x=ccGetEndX(pxTarget)+pxOffset;
    pxSelf.y=pxTarget.y;
  }//+++
  
  void ccFollowSouth(Rectangle pxSelf, Rectangle pxTarget, int pxOffset){
    pxSelf.x=pxTarget.x;
    pxSelf.y=ccGetEndY(pxTarget)+pxOffset;
  }//+++
  
  void ccDrawLineH(int pxY){
    line(0,pxY,width,pxY);
  }//+++
  
  void ccDrawLineV(int pxX){
    line(pxX,0,pxX,height);
  }//+++
  
  void ssDrawDrum(Rectangle pxBound){
    final int lpSupporterWidthPIX = pxBound.width /16;
    final int lpSupporterPartPIX  = pxBound.width / 4;
    final int lpDrumCutPIX        = pxBound.height/ 8;
    pushStyle();
    {
      fill(C_COLOR_METAL);
      rect(
        pxBound.x, pxBound.y+(lpDrumCutPIX),
        pxBound.width, pxBound.height-(lpDrumCutPIX*2)
      );
      rect(
        pxBound.x+lpSupporterPartPIX,pxBound.y,
        lpSupporterWidthPIX,pxBound.height
      );
      rect(
        pxBound.x+lpSupporterPartPIX*3,pxBound.y,
        lpSupporterWidthPIX,pxBound.height
      );
    }
    popStyle();
  }//+++
  
  void ssDrawHopper(Rectangle pxBound){
    final int lpHopperCutPIX  = pxBound.width/4;
    pushStyle();
    {
      fill(C_COLOR_METAL);
      quad(
        pxBound.x, pxBound.y,
        ccGetEndX(pxBound), pxBound.y,
        ccGetEndX(pxBound)-lpHopperCutPIX, ccGetEndY(pxBound),
        pxBound.x+lpHopperCutPIX, ccGetEndY(pxBound)
      );
    }
    popStyle();
  }//+++
  
  void ssDrawTank(Rectangle pxBound){
    pushStyle();
    {
      fill(C_COLOR_METAL);
      rect(pxBound.x, pxBound.y, pxBound.width, pxBound.height);
    }
    popStyle();
  }//+++
  
  void ssDrawPipe(Rectangle pxBound){
    final int lpPipeWidthPIX  = 3;
    pushStyle();
    {
      fill(C_COLOR_DUCT);
      rect(
        pxBound.x, pxBound.y+pxBound.height-lpPipeWidthPIX,
        pxBound.width, lpPipeWidthPIX
      );
      rect(
        ccGetEndX(pxBound)-lpPipeWidthPIX,pxBound.y,
        lpPipeWidthPIX,pxBound.height
      );
    }
    popStyle();
  
  }//+++
  
  void ssDrawBelcon(Rectangle pxBound){
    pushStyle();
    {
      fill(C_COLOR_DUCT);
      rect(
        pxBound.x+pxBound.height/2, pxBound.y,
        pxBound.width-pxBound.height, pxBound.height
      );
      ellipse(
        pxBound.x+pxBound.height/2, ccGetCenterY(pxBound),
        pxBound.height, pxBound.height
      );
      ellipse(
        ccGetEndX(pxBound)-pxBound.height/2, ccGetCenterY(pxBound),
        pxBound.height, pxBound.height
      );
    }
    popStyle();
  }//+++
  
  void ccSurroundText(String pxText, int pxX, int pxY){
    if(pxText==null){return;}
    final int lpMargin = 2;
    int lpWidth = ceil(textWidth(pxText)) + lpMargin*2;
    int lpReturnCount = 1;
    for(char it : pxText.toCharArray()){
      if(it == '\r' || it == '\n'){lpReturnCount++;}
    }//..~
    int lpHeight = ceil(textAscent()+textDescent())*lpReturnCount + lpMargin*2;
    pushStyle();
    {
      fill(0x55555555);
      stroke(0xFFAAAAAA);
      rect(pxX, pxY, lpWidth, lpHeight);
      fill(0xFFEEEEEE);
      text(pxText, pxX+lpMargin, pxY+lpMargin);
    }
    popStyle();
  }//+++
  
  class EcPumpIcon{
    float cmHeading = 1.0f;
    float cmSpeed = 1.0f;
    boolean cmActivated = false;
    Rectangle cmBound = new Rectangle(8, 8, 8, 8);
    void ccUpdate(){
      //[head]::
    }//+++
  }//***
  
  //=== overridden
  
  Rectangle cmHopper = new Rectangle( 48,  48);
  Rectangle cmBelcon = new Rectangle(140,  16);
  Rectangle cmTank   = new Rectangle( 60, 100);
  Rectangle cmPipe   = new Rectangle(110, 100);
  Rectangle cmDrum   = new Rectangle(140,  60);
  
  @Override public void setup() {

    //-- pre
    size(320, 240);
    noSmooth();
    self=this;
    
    //-- replace
    frameRate(16);
    textAlign(LEFT,TOP);
    ellipseMode(CENTER);
    noStroke();
    frame.setTitle("DemoAddtiveDrum");

    //-- swing ui **
    SwingUtilities.invokeLater(T_SWING_INIT);
    
    //-- local ui **
    
    ccSetLocation(cmHopper, 25, 12);
    ccFollowSouth(cmBelcon, cmHopper, 8);
    ccFollowEast(cmDrum,cmBelcon,8);
    ccSetLocation(cmTank,25,120);
    ccSetLocation(cmPipe,
      ccGetEndX(cmTank)-8,
      ccGetEndY(cmDrum)-14
    );
    
    //-- init ** 
    
    //-- post
    println(".setup() $ end");
    
  }//+++
  
  @Override public void draw() { 

    //-- pre
    vmRoller++;vmRoller&=0x0F;

    //-- update ** passive
    background(0);
    ssDrawHopper(cmHopper);
    ssDrawBelcon(cmBelcon);
    ssDrawTank(cmTank);
    ssDrawDrum(cmDrum);
    ssDrawPipe(cmPipe);
    
    //-- update ** active
    
    //-- update ** system
    fill(0xEE);
    text(nf(vmRoller,2),5,5);
    
    //-- tag

  }//+++
  
  @Override public void keyPressed() {
    switch(key){
      case 'q':exit();break;
      default:break;
    }//..?
  }//+++
  
  @Override public void mousePressed() {
    switch (mouseButton) {
      case LEFT:return;
      case RIGHT:SwingUtilities.invokeLater(T_SWING_FLIP);break;
      default:break; //+++
    }//..?
  }//+++
  
  //=== entry
  
  /* * todo list
   * 
   * ## pushed
   * 
   * - write the goddamned doc
   * - 
   * - 
   * - 
   * - 
   * - wire controller to simulator
   * - make controller
   * - make simulator
   * 
   * ## heading
   *
   * - 
   * - 
   * - 
   * - 
   * - 
   * - 
   * - wire model to local ui
   * - relocate text box relatively
   * - reloacate active element relatively
   * - classify pump icon as active element
   *
   * ## done
   *
   * - reloacate passive relatively
   * - instantiate passive element as built-in rectangle
   * - validate element draw subroutine
   * - make swing setting panel
   * - make swing operate panel
   * - make swing panel base
   *
   */
  
  static public void main(String[] passedArgs) {
    PApplet.main(DemoDrumInjection.class.getCanonicalName());
  }//+++

}//***eof
