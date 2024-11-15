package org.winterframework.core.tool;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.DecimalFormat;

/**
 * @author sven
 * Created on 2021/12/30 11:01 下午
 */
@UtilityClass
public final class StringTool extends StringUtils {

	public Integer convert2Int(Object o, Integer defaultVal) {
		return toBigDecimal(o, BigDecimal.valueOf(defaultVal)).intValue();
	}

	public int convert2Int(Object o) {
		return convert2Int(o, 0);
	}

	public Long convert2Long(Object o, Long defaultVal) {
		return toBigDecimal(o, BigDecimal.valueOf(defaultVal)).longValue();
	}

	public long convert2Long(Object o) {
		return convert2Long(o, 0L);
	}

	public Double convert2Double(Object o, Double defaultVal) {
		return toBigDecimal(o, BigDecimal.valueOf(defaultVal)).doubleValue();
	}

	public double convert2Double(Object o) {
		return convert2Double(o, 0.0);
	}

	public Float convert2Float(Object o, Float defaultVal) {
		return toBigDecimal(o, BigDecimal.valueOf(defaultVal)).floatValue();
	}

	public float convert2Float(Object o) {
		return convert2Float(o, 0.0f);
	}

	public Byte convert2Byte(Object o, Byte defaultVal) {
		return toBigDecimal(o, BigDecimal.valueOf(defaultVal)).byteValue();
	}

	public byte convert2Byte(Object o) {
		return convert2Byte(o, (byte) 0);
	}

	public Short convert2Short(Object o, Short defaultVal) {
		return toBigDecimal(o, BigDecimal.valueOf(defaultVal)).shortValue();
	}

	public short convert2Short(Object o) {
		return convert2Short(o, (short) 0);
	}

	public String toStringOrNull(Object obj) {
		return null == obj ? null : obj.toString();
	}

	public String formatBigDecimalKmbValue(BigDecimal value) {
		// 向下取整
		return formatKmbValue(value.doubleValue(), RoundingMode.DOWN);
	}

	public String md5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] result = md.digest(input.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (byte b : result) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将数字转化为kmb格式输出
	 * @param value
	 * @return
	 */
	public String formatKmbValue(double value, RoundingMode roundingMode) {
		String[] arr = {"", "K", "M", "B", "T", "P", "E"};
		int index = 0;
		while ((value / 1000) >= 1) {
			value = value / 1000;
			index++;
		}
		DecimalFormat decimalFormat = new DecimalFormat("#.#");
		decimalFormat.setRoundingMode(roundingMode);
		return String.format("%s%s", decimalFormat.format(value), arr[index]);
	}

	private BigDecimal toBigDecimal(Object o, BigDecimal defaultVal) {
		if (null == o) {
			return defaultVal;
		}
		return new BigDecimal(o.toString());
	}
}
