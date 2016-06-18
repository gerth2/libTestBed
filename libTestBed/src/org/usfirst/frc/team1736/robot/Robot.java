
package org.usfirst.frc.team1736.robot;

import org.usfirst.frc.team1736.lib.Calibration.CalWrangler;
import org.usfirst.frc.team1736.lib.Calibration.Calibration;
import org.usfirst.frc.team1736.lib.Hourmeter.CasseroleHourmeter;
import org.usfirst.frc.team1736.lib.WebServer.CasseroleDriverView;
import org.usfirst.frc.team1736.lib.WebServer.CasseroleWebServer;
import org.usfirst.frc.team1736.lib.WebServer.CassesroleWebStates;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.ControllerPower;
import edu.wpi.first.wpilibj.IterativeRobot;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	CalWrangler wrangler;
	CasseroleHourmeter hourmeter;
	CasseroleWebServer webserver;
	
	Calibration testCal1;
	Calibration testCal2;
	Calibration testCal3;

	BuiltInAccelerometer accel;
	
	double iter;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
    	wrangler = new CalWrangler();
    	//hourmeter = new CasseroleHourmeter(); // Still pretty broken...
    	accel = new BuiltInAccelerometer();
    	webserver = new CasseroleWebServer();
    	
    	testCal1 = new Calibration("testCal1", 5.5);
    	testCal2 = new Calibration("testCal2", 105.2);
    	testCal3 = new Calibration("testCal3", -30.826);
    	
    	CasseroleDriverView.newDial("SystemVoltage", 0.0, 14.0, 1);
    	CasseroleDriverView.newDial("AccelX(G)", -3, 3, 0.5);
    	CasseroleDriverView.newDial("AccelY(G)", -3, 3, 0.5);
    	CasseroleDriverView.newDial("AccelZ(G)", -3, 3, 0.5);
    	CasseroleDriverView.newWebcam("https://pbs.twimg.com/profile_images/562466745340817408/_nIu8KHX.jpeg", "Random Webcam");
    	
    	iter = 0;
    	
    	
    	webserver.startServer();
    	
    	
    }
    
    public void disabledInit(){
    	CalWrangler.loadCalValues();
    }
    
    public void disabledPeriodic(){
    	
    	iter++;
    	
    	CasseroleDriverView.setDialValue("SystemVoltage", iter/50.0 % 14);
    	CasseroleDriverView.setDialValue("AccelX(G)", (iter/100 % 6) - 3.0);
    	CasseroleDriverView.setDialValue("AccelY(G)", ((iter/100 + 1) % 6) - 3.0);
    	CasseroleDriverView.setDialValue("AccelZ(G)", ((iter/100 + 2) % 6) - 3.0);
    	
    	
    	CassesroleWebStates.putDouble("Accel X", (iter/100 % 6) - 3.0);
    	CassesroleWebStates.putDouble("Accel Y", ((iter/100 + 1) % 6) - 3.0);
    	CassesroleWebStates.putDouble("Accel Z", ((iter/100 + 2) % 6) - 3.0);
    	CassesroleWebStates.putDouble("SystemVoltage",iter/50.0 % 14);
    	CassesroleWebStates.putDouble("SystemCurrent", iter); 
    	

    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	CalWrangler.loadCalValues();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }
    
    public void teleopInit() {
    	CalWrangler.loadCalValues();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	
    	double sysv = ControllerPower.getInputVoltage();
    	double sysi = ControllerPower.getInputCurrent();
    	double accel_x = accel.getX();
    	double accel_y = accel.getY();
    	double accel_z = accel.getZ();
    	
    	CasseroleDriverView.setDialValue("SystemVoltage", sysv);
    	CasseroleDriverView.setDialValue("AccelX(G)", accel_x);
    	CasseroleDriverView.setDialValue("AccelY(G)", accel_y);
    	CasseroleDriverView.setDialValue("AccelZ(G)", accel_z);

    	
    	CassesroleWebStates.putDouble("Accel X", accel_x);
    	CassesroleWebStates.putDouble("Accel Y", accel_y);
    	CassesroleWebStates.putDouble("Accel Z", accel_z);
    	CassesroleWebStates.putDouble("SystemVoltage", sysv);
    	CassesroleWebStates.putDouble("SystemCurrent", sysi); 
    	
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
