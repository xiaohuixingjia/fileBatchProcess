package com.umpay.proxyservice.constant;

/**
 * 文件处理码常量类
 * 
 */
public enum Dealcode {
	SUCCESS {
		public String getName() {
			return "文件处理成功 ";
		}

		public String getCode() {
			return "0000";
		}
	},
	FILE_SIZE_ERROR {
		public String getName() {
			return "文件行数不匹配";
		}

		public String getCode() {
			return "2001";
		}
	},
	FILE_NOT_FOUND {
		public String getName() {
			return "文件没有找到";
		}

		public String getCode() {
			return "2002";
		}
	},
	FILE_PARAMETER_INCOMPLETE {
		public String getName() {
			return "文件输入项不全";
		}

		public String getCode() {
			return "2003";
		}
	};
	public abstract String getName();

	public abstract String getCode();

	public static void main(String[] args) {
		System.out.println(Dealcode.SUCCESS.toString());
	}
}
