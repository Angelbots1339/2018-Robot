package org.usfirst.frc.team1339.utils;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.CameraServer;

public class Cameras {
	UsbCamera topCamera, bottomCamera;
	CvSink topSink, bottomSink;
	VideoSink server;
	
	public Cameras() {
		topCamera = CameraServer.getInstance().startAutomaticCapture(0);
		bottomCamera = CameraServer.getInstance().startAutomaticCapture(1);
		
		topSink = new CvSink("TopCam");
		topSink.setSource(topCamera);
		topSink.setEnabled(true);
		bottomSink = new CvSink("BottomCam");
		topSink.setSource(bottomCamera);
		topSink.setEnabled(true);
		
		server = CameraServer.getInstance().getServer();
		server.setSource(topCamera);
	}
	
	public void showTopCam(boolean top) {
		server.setSource(top ? topCamera : bottomCamera);
	}
}
