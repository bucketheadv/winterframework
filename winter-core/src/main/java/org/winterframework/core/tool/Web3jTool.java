package org.winterframework.core.tool;

import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author qinglinl
 * Created on 2022/9/26 3:14 PM
 */
public class Web3jTool {
	public static final String PERSONAL_MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";

	public static boolean validate(String signature, String message, String address) {
		String prefix = PERSONAL_MESSAGE_PREFIX + message.length();

		byte[] msgHash = Hash.sha3((prefix + message).getBytes(StandardCharsets.UTF_8));
		byte[] signBytes = Numeric.hexStringToByteArray(signature);
		byte v = signBytes[64];
		if (v < 27) {
			v += 27;
		}
		Sign.SignatureData sd = new Sign.SignatureData(v, Arrays.copyOfRange(signBytes, 0, 32), Arrays.copyOfRange(signBytes, 32, 64));

		String addressRecovered;
		boolean match = false;
		for (int i = 0; i < 4; i++) {
			BigInteger publicKey = Sign.recoverFromSignature((byte)i, new ECDSASignature(new BigInteger(1, sd.getR()), new BigInteger(1, sd.getS())), msgHash);
			if (publicKey != null) {
				addressRecovered = "0x" + Keys.getAddress(publicKey);
				if (addressRecovered.equals(address)) {
					match = true;
					break;
				}
			}
		}
		return match;
	}
}
