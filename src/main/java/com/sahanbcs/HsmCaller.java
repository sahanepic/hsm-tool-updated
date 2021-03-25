package com.sahanbcs;

import org.jpos.iso.ISOUtil;
import java.net.InetAddress;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

// 
// Decompiled by Procyon v0.5.36
// 

public class HsmCaller {
	private static Socket hsmsocket;
	private static DataOutputStream dataout;
	private static DataInputStream datain;

	static {
		HsmCaller.hsmsocket = null;
		HsmCaller.dataout = null;
		HsmCaller.datain = null;
	}

	public static boolean checkHsmStatus() throws Exception {
		boolean ok = false;
		try {
			(HsmCaller.hsmsocket = new Socket(InetAddress.getByName(Server.HSM_IP), Server.HSM_PORT))
					.setSoTimeout(10000);
			HsmCaller.dataout = new DataOutputStream(HsmCaller.hsmsocket.getOutputStream());
			HsmCaller.datain = new DataInputStream(HsmCaller.hsmsocket.getInputStream());
			final byte[] request = ISOUtil.hex2byte("010100000003FFF000");
			final byte[] response = new byte[1024];
			LogFileCreator.writInforTologs("\nREQUEST :\n" + ISOUtil.hexdump(request));
			HsmCaller.dataout.write(request);
			HsmCaller.dataout.flush();
			final int len = HsmCaller.datain.read(response, 0, 1024);
			LogFileCreator.writInforTologs("\nRESPONSE :\n" + ISOUtil.hexdump(response));
			if (len >= 5) {
				final String rc = ISOUtil.hexString(response).substring(16, 18);
				if ("00".equals(rc)) {
					ok = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (HsmCaller.dataout != null) {
				HsmCaller.dataout.close();
			}
			if (HsmCaller.datain != null) {
				HsmCaller.datain.close();
			}
			if (HsmCaller.hsmsocket != null) {
				HsmCaller.hsmsocket.close();
			}
		}
		if (HsmCaller.dataout != null) {
			HsmCaller.dataout.close();
		}
		if (HsmCaller.datain != null) {
			HsmCaller.datain.close();
		}
		if (HsmCaller.hsmsocket != null) {
			HsmCaller.hsmsocket.close();
		}
		return ok;
	}

	public static boolean importKeys(final int keytype, final String KIR, final String eKIR_KEY) throws Exception {
		boolean ok = false;
		try {
			(HsmCaller.hsmsocket = new Socket(InetAddress.getByName(Server.HSM_IP), Server.HSM_PORT))
					.setSoTimeout(10000);
			HsmCaller.dataout = new DataOutputStream(HsmCaller.hsmsocket.getOutputStream());
			HsmCaller.datain = new DataInputStream(HsmCaller.hsmsocket.getInputStream());
			String KTYPE = "00";
			if (keytype == 1) {
				KTYPE = "01";
			} else if (keytype == 2) {
				KTYPE = "08";
			} else if (keytype == 3) {
				KTYPE = "09";
			} else if (keytype == 4) {
				KTYPE = "06";
			} else if (keytype == 5) {
				KTYPE = "30";
			} else if (keytype == 6) {
				KTYPE = "05";
			} else if (keytype == 7) {
				KTYPE = "04";
			} else if (keytype == 8) {
				KTYPE = "03";
			} else if (keytype == 9) {
				KTYPE = "07";
			}
			byte[] request = null;
			if (eKIR_KEY.length() == 48) {
				request = ISOUtil.hex2byte("EE0200001111" + KIR + KTYPE + "0018" + eKIR_KEY);
			} else if (eKIR_KEY.length() == 16) {
				request = ISOUtil.hex2byte("EE0200001111" + KIR + KTYPE + "0008" + eKIR_KEY);
			} else {
				request = ISOUtil.hex2byte("EE0200001111" + KIR + KTYPE + "0010" + eKIR_KEY);
			}
			final String hlen = Integer.toHexString(request.length);
			final String hd = "01010000" + ISOUtil.zeropad(hlen, 4);
			final byte[] response = new byte[1024];
			request = ISOUtil.concat(ISOUtil.hex2byte(hd), request);
			LogFileCreator.writInforTologs("\nREQUEST :\n" + ISOUtil.hexdump(request));
			HsmCaller.dataout.write(request);
			HsmCaller.dataout.flush();
			final int len = HsmCaller.datain.read(response, 0, 1024);
			LogFileCreator.writInforTologs("\nRESPONSE :\n" + ISOUtil.hexdump(response));
			if (len >= 10) {
				final String rc = Server.HSM_RC = ISOUtil.hexString(response).substring(18, 20);
				if ("00".equals(rc)) {
					if (eKIR_KEY.length() == 48) {
						Server.LMK_KEY = ISOUtil.hexString(response).substring(24, 70);
						Server.KVC_KEY = ISOUtil.hexString(response).substring(70, 76);
					} else {
						Server.LMK_KEY = ISOUtil.hexString(response).substring(24, 56);
						Server.KVC_KEY = ISOUtil.hexString(response).substring(56, 62);
					}
					ok = true;
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (HsmCaller.dataout != null) {
				HsmCaller.dataout.close();
			}
			if (HsmCaller.datain != null) {
				HsmCaller.datain.close();
			}
			if (HsmCaller.hsmsocket != null) {
				HsmCaller.hsmsocket.close();
			}
		}
		if (HsmCaller.dataout != null) {
			HsmCaller.dataout.close();
		}
		if (HsmCaller.datain != null) {
			HsmCaller.datain.close();
		}
		if (HsmCaller.hsmsocket != null) {
			HsmCaller.hsmsocket.close();
		}
		return ok;
	}

	public static boolean exportKeys(final int keytype, final String KIS, final String eLMKKEY) throws Exception {
		boolean ok = false;
		try {
			(HsmCaller.hsmsocket = new Socket(InetAddress.getByName(Server.HSM_IP), Server.HSM_PORT))
					.setSoTimeout(10000);
			HsmCaller.dataout = new DataOutputStream(HsmCaller.hsmsocket.getOutputStream());
			HsmCaller.datain = new DataInputStream(HsmCaller.hsmsocket.getInputStream());
			String KTYPE = "00";
			if (keytype == 1) {
				KTYPE = "01";
			} else if (keytype == 2) {
				KTYPE = "08";
			} else if (keytype == 3) {
				KTYPE = "09";
			} else if (keytype == 4) {
				KTYPE = "06";
			} else if (keytype == 5) {
				KTYPE = "30";
			} else if (keytype == 6) {
				KTYPE = "05";
			} else if (keytype == 7) {
				KTYPE = "04";
			} else if (keytype == 8) {
				KTYPE = "03";
			} else if (keytype == 9) {
				KTYPE = "07";
			}
			byte[] request = null;
			if (eLMKKEY.length() == 48) {
				if (KIS.length() == 48) {
					String ss;
					request = ISOUtil.hex2byte(ss = "EE0201001912" + KIS + KTYPE + "001912" + eLMKKEY);
					System.out.println("SSSSSSSS48  :" + ss);
				}
				else {
					String ss;
					request = ISOUtil.hex2byte(ss = "EE0201001111" + KIS + KTYPE + "001912" + eLMKKEY);
					System.out.println("SSSSSSSS48  :" + ss);
				}				
			} else if (eLMKKEY.length() == 16) {
				if (KIS.length() == 48) {
					String ss;
					request = ISOUtil.hex2byte(ss = "EE0201001912" + KIS + KTYPE + "000910" + eLMKKEY);
					System.out.println("SSSSSSSS16  :" + ss);
				} else if (KIS.length() == 16) {
					String ss;
					request = ISOUtil.hex2byte(ss = "EE0201000910" + KIS + KTYPE + "000910" + eLMKKEY);
					System.out.println("SSSSSSSS16  :" + ss);
				} else {
					String ss;
					request = ISOUtil.hex2byte(ss = "EE0201001111" + KIS + KTYPE + "000910" + eLMKKEY);
					System.out.println("SSSSSSSS16  :" + ss);
				}
			} else {
				// eLMKKEY.length() == 32
				if (KIS.length() == 48) {
					String ss;
					request = ISOUtil.hex2byte(ss = "EE0201001912" + KIS + KTYPE + "001111" + eLMKKEY);
					System.out.println("SSSSSSSS32  :" + ss);
				} else {
					String ss;
					request = ISOUtil.hex2byte(ss = "EE0201001111" + KIS + KTYPE + "001111" + eLMKKEY);
					System.out.println("SSSSSSSS32  :" + ss);
				}

			}
			final String hlen = Integer.toHexString(request.length);
			final String hd = "01010000" + ISOUtil.zeropad(hlen, 4);
			final byte[] response = new byte[1024];
			request = ISOUtil.concat(ISOUtil.hex2byte(hd), request);
			System.out.println("The Request header : " + ISOUtil.hexString(request));
			LogFileCreator.writInforTologs("\nREQUEST :\n" + ISOUtil.hexdump(request));
			HsmCaller.dataout.write(request);
			HsmCaller.dataout.flush();
			final int len = HsmCaller.datain.read(response, 0, 1024);
			LogFileCreator.writInforTologs("\nRESPONSE :\n" + ISOUtil.hexdump(response));
			if (len >= 10) {
				final String rc = Server.HSM_RC = ISOUtil.hexString(response).substring(18, 20);
				if ("00".equals(rc)) {
					if (eLMKKEY.length() == 48) {
					//	System.out.println("ssssssssss --->48");
						Server.KIS_KEY = ISOUtil.hexString(response).substring(22, 70);
						Server.KVC_KEY = ISOUtil.hexString(response).substring(70, 76);
					}
					else if (eLMKKEY.length() == 16) {
					//	System.out.println("ssssssssss --->32");
						Server.KIS_KEY = ISOUtil.hexString(response).substring(22, 38);
						Server.KVC_KEY = ISOUtil.hexString(response).substring(38, 44);
					} else {
					//	System.out.println("ssssssssss --->32");
						Server.KIS_KEY = ISOUtil.hexString(response).substring(22, 54);
						Server.KVC_KEY = ISOUtil.hexString(response).substring(54, 60);
					}
					ok = true;
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (HsmCaller.dataout != null) {
				HsmCaller.dataout.close();
			}
			if (HsmCaller.datain != null) {
				HsmCaller.datain.close();
			}
			if (HsmCaller.hsmsocket != null) {
				HsmCaller.hsmsocket.close();
			}
		}
		if (HsmCaller.dataout != null) {
			HsmCaller.dataout.close();
		}
		if (HsmCaller.datain != null) {
			HsmCaller.datain.close();
		}
		if (HsmCaller.hsmsocket != null) {
			HsmCaller.hsmsocket.close();
		}
		return ok;
	}
}
