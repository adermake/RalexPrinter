package core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class Main {

	public static void main(String[] args) {

		//sendData(getPictureData());
		while(true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				WifiHandler.get("test");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public static void sendData(String s) {
		for (SerialPort comPort : SerialPort.getCommPorts()) {
			System.out.println("" + comPort.getSystemPortName());
			System.out.println("" + comPort.getSystemPortName());
		}

		SerialPort comPort = SerialPort.getCommPorts()[1];
		System.out.println("Getting Port: " + comPort.getSystemPortName());
		comPort.setBaudRate(9600);
		comPort.openPort(1000);
		comPort.clearDTR();
		comPort.clearRTS();
		comPort.clearBreak();
		System.out.println("Waiting 3 Seconds");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String a = "N";
		byte[] byteArray1 = a.getBytes();
		System.out.println("Sending Start Byte");
		System.out.println(comPort.writeBytes(byteArray1, byteArray1.length));

		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int size = (int) Math.sqrt(s.length());
		for (int i = 0; i < size; i++) {
			String l = s.substring(i * size, (i + 1) * size);

			byte[] byteArray = l.getBytes();
			System.out.println("Sending Data " + i + "/" + size);
			System.out.println(comPort.writeBytes(byteArray, byteArray.length));
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// System.out.println(comPort.isOpen());

		}

		String l = "D";
		byte[] byteArray = l.getBytes();
		System.out.println("Sending End Byte");
		System.out.println(comPort.writeBytes(byteArray, byteArray.length));

		// System.out.println(comPort.writeBytes(byteArray,byteArray.length));
		/*
		 * try { Thread.sleep(500); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } byte[] readBuffer = new
		 * byte[comPort.bytesAvailable()]; comPort.readBytes(readBuffer,
		 * readBuffer.length); String str = new String(readBuffer,
		 * StandardCharsets.UTF_8); System.out.print(str);
		 * 
		 * try { Thread.sleep(3000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

	}

	/*
	 * comPort.addDataListener(new SerialPortDataListener() {
	 * 
	 * @Override public int getListeningEvents() { return
	 * SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
	 * 
	 * @Override public void serialEvent(SerialPortEvent event) { if
	 * (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) return;
	 * 
	 * byte[] readBuffer = new byte[comPort.bytesAvailable()];
	 * comPort.readBytes(readBuffer, readBuffer.length); String str = new
	 * String(readBuffer, StandardCharsets.UTF_8); System.out.print(str); } });
	 */

	/*
	 * System.out.println("X");
	 * 
	 * SerialPort comPort = SerialPort.getCommPort("COM3"); comPort.openPort();
	 * comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
	 * comPort.setBaudRate(9600); byte[] sendData = new byte[]{(byte) 0xc0};
	 * comPort.writeBytes(sendData, 1);
	 * 
	 * System.out.println("A");
	 */

	/*
	 * try { while (true) {
	 * 
	 * while (comPort.bytesAvailable() <= 0) {
	 * 
	 * 
	 * Thread.sleep(20); }
	 * 
	 * 
	 * byte[] readBuffer = new byte[comPort.bytesAvailable()]; int numRead =
	 * comPort.readBytes(readBuffer, readBuffer.length); System.out.println("Read "
	 * + numRead + " bytes."); } } catch (Exception e) { e.printStackTrace(); }
	 */
	public static String getPictureData() {
		String s = "";
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("image.png"));
		} catch (IOException e) {
		}
		if (img.getHeight() != img.getWidth()) {
			System.out.print("IMAGE IS NOT SQUARE");
			return "";
		}

		for (int x = 0; x < img.getHeight(); x++) {
			for (int y = 0; y < img.getWidth(); y++) {

				Color c = new Color(img.getRGB(y, x));
				double r = c.getRed();
				double g = c.getGreen();
				double b = c.getBlue();

				double gray = r / 255 + g / 255 + b / 255;
				gray = gray / 3;
				if (gray >= 1) {
					s += "0";
				} else {
					s += "1";
				}

			}
		}
		/*
		 * for (int i = 0;i<img.getHeight();i++) { String l =
		 * s.substring(i*img.getHeight(),(i+1)*img.getHeight());
		 * 
		 * System.out.println(l);
		 * 
		 * }
		 */
		return s;

	}

}
/*

	

*/