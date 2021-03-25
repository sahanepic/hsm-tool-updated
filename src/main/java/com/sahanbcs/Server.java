package com.sahanbcs;

import java.io.InputStreamReader;
import java.io.BufferedReader;

// 
// Decompiled by Procyon v0.5.36
// 

public class Server {
	public static String HSM_IP;
	public static int HSM_PORT;
	private static BufferedReader INPUT;
	public static String LMK_KEY;
	public static String KVC_KEY;
	public static String HSM_RC;
	private static String LMK_KIS;
	private static String LMK_KIR;
	public static String KIS_KEY;

	public static boolean isHex(final String input) {
		int count = 0;
		boolean bl = false;
		final char[] validVal = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'a',
				'b', 'c', 'd', 'e', 'f' };
		for (int k = 0; k < input.length(); ++k) {
			final char hexval = input.charAt(k);
			if (!bl) {
				for (int i = 0; i < validVal.length; ++i) {
					if (validVal[i] == hexval) {
						++count;
					}
				}
			}
			if (count == input.length()) {
				bl = true;
				return bl;
			}
		}
		if (!bl) {
			bl = false;
			return bl;
		}
		return bl;
	}

	public static void main(final String[] args) {
		System.out.println(Message.LOGIN_L);
		while (true) {
			try {
				Thread.sleep(1L);
				while (true) {
					Thread.sleep(1L);
					Server.INPUT = new BufferedReader(new InputStreamReader(System.in));
					System.out.print("Enter HSM-IP    : ");
					Server.HSM_IP = Server.INPUT.readLine().trim();
					System.out.print("Enter HSM-SPORT : ");
					Server.HSM_PORT = Integer.parseInt(Server.INPUT.readLine().trim());
					if (HsmCaller.checkHsmStatus()) {
						break;
					}
					System.out.println("Hsm connectiviy and status is failed.[FAIL]");
				}
				System.out.println("Hsm connectiviy and status is passed. [OK]");
				while (true) {
					System.out.println(Message.MAIN_LIST);
					System.out.print("Enter value : ");
					final String inp = Server.INPUT.readLine().trim();
					if (1 == Integer.parseInt(inp)) {
						System.out.println(Message.LIST_K_TYPE);
						System.out.print("Enter eLMK(KIR)  :");
						Server.LMK_KIR = Server.INPUT.readLine().trim();
						System.out.print("Enter key type   :");
						final int keytype = Integer.parseInt(Server.INPUT.readLine().trim());
						System.out.print("Enter eKIR(KEY)  :");
						final String kir_key = Server.INPUT.readLine().trim();
						System.out.println();
						if (1 <= keytype && keytype <= 9) {
							if (32 == Server.LMK_KIR.length() || 48 == Server.LMK_KIR.length()) {
								if (32 == kir_key.length() || 48 == kir_key.length() || 16 == kir_key.length()) {
									if (isHex(kir_key)) {
										if (isHex(Server.LMK_KIR)) {
											if (HsmCaller.importKeys(keytype, Server.LMK_KIR, kir_key)) {
												System.out.println("--->");
												System.out.println("eLMK(KEY) :" + Server.LMK_KEY);
												System.out.println("KVC       :" + Server.KVC_KEY);
												System.out.println("--->");
											} else {
												System.out.println("ERROR. BAD RESPONSE FROM HSM " + Server.HSM_RC);
											}
										} else {
											System.out.println("ERROR. INVALID KEY DIGITS OF eLMK(KIR)");
										}
									} else {
										System.out.println("ERROR. INVALID KEY DIGITS OF eKIR(KEY)");
									}
								} else {
									System.out.println("ERROR. INVALID KEY LENGTH OF eKIR(KEY)");
								}
							} else {
								System.out.println("ERROR. INVALID KEY LENGTH OF eLMK(KIR)");
							}
						} else {
							System.out.println("ERROR. INVALID KEY TYPE");
						}
					} else if (2 == Integer.parseInt(inp)) {
						System.out.println(Message.LIST_K_TYPE);
						System.out.print("Enter eLMK(KIS)   :");
						Server.LMK_KIS = Server.INPUT.readLine().trim();
						System.out.print("Enter key type    :");
						final int keytype = Integer.parseInt(Server.INPUT.readLine().trim());
						System.out.print("Enter eLMK(KEY)   :");
						final String lmk_key = Server.INPUT.readLine().trim();
						System.out.println();
						if (1 <= keytype && keytype <= 9) {
							if (32 == Server.LMK_KIS.length() || 48 == Server.LMK_KIS.length()
									|| 16 == Server.LMK_KIS.length()) {
								if (32 == lmk_key.length() || 48 == lmk_key.length() || 16 == lmk_key.length()) {
									if (isHex(lmk_key)) {
										if (isHex(Server.LMK_KIS)) {
											if (HsmCaller.exportKeys(keytype, Server.LMK_KIS, lmk_key)) {
												System.out.println("--->");
												System.out.println("eKIS(KEY) :" + Server.KIS_KEY);
												System.out.println("KVC       :" + Server.KVC_KEY);
												System.out.println("--->");
											} else {
												System.out.println("ERROR. BAD RESPONSE FROM HSM " + Server.HSM_RC);
											}
										} else {
											System.out.println("ERROR. INVALID KEY DIGITS OF eLMK(KIS)");
										}
									} else {
										System.out.println("ERROR. INVALID KEY DIGITS OF eLMK(KEY)");
									}
								} else {
									System.out.println("ERROR. INVALID KEY LENGTH OF eLMK(KEY)");
								}
							} else {
								System.out.println("ERROR. INVALID KEY LENGTH OF eLMK(KIS)");
							}
						} else {
							System.out.println("ERROR. INVALID KEY TYPE");
						}
					} else {
						if (3 != Integer.parseInt(inp)) {
							continue;
						}
						System.exit(0);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("ERROR. ON APPLICATION");
				continue;
			}
			// break;
		}
	}
}
