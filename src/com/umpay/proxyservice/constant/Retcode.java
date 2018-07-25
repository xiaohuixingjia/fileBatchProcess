package com.umpay.proxyservice.constant;

/**
 * 返回码常量类
 * 
 */
public enum Retcode {
	SUCCESS {
		public String getName() {
			return "成功 ";
		}
		public String getCode() {
			return "0000";
		}
	},
	URL_ERROR {
		public String getName() {
			return "URL错误";
		}
		public String getCode() {
			return "0101";
		}
	},
	FUNCODE_ERROR {
		public String getName() {
			return "funcode错误";
		}
		public String getCode() {
			return "0102";
		}
	},
	DATETIME_ERROR {
		public String getName() {
			return "datetime格式错误";
		}
		public String getCode() {
			return "0103";
		}		
	},
	MERID_ERROR {
		public String getName() {
			return "merid格式错误";
		}
		public String getCode() {
			return "0104";
		}
	},
	TRANSID_ERROR {
		public String getName() {
			return "transid格式错误";
		}
		public String getCode() {
			return "0105";
		}
	},
	MOBILEID_ERROR {
		public String getName() {
			return "mobileid格式错误";
		}
		public String getCode() {
			return "0108";
		}
	},
	SIGN_ERROR {
		public String getName() {
			return "签名校验失败";
		}
		public String getCode() {
			return "0109";
		}
	},
	LICENSE_ERROR {
		public String getName() {
			return "license错误";
		}
		public String getCode() {
			return "0115";
		}
	},
	NO_UP_TO_MINSIZE {
		public String getName() {
			return "文件数量未达到最小的请求数";
		}
		public String getCode() {
			return "0116";
		}
	},
	OUT_OF_MAXSIZE {
		public String getName() {
			return "文件数量超过最大请求数";
		}
		public String getCode() {
			return "0117";
		}
	},
	IP_AUTH_ERROR {
		public String getName() {
			return "ip校验不通过";
		}
		public String getCode() {
			return "0118";
		}
	},
	PATTERN_AUTH_ERROR{
		public String getName() {
			return "正则校验不通过";
		}
		public String getCode() {
			return "0109";
		}
	},
	PARAMETER_INCOMPLETE  {
		public String getName() {
			return "参数不全";
		}
		public String getCode() {
			return "0601";
		}
	},
	PARAMETER_CHECH_ERROR  {
		public String getName() {
			return "参数格式不正确";
		}
		public String getCode() {
			return "0602";
		}
	},
	CONFIG_FILE_NOT_FOUND {
		public String getName() {
			return "配置文件未找到";
		}
		public String getCode() {
			return "0801";
		}
	},
	RELOAD_CLASS_IS_NULL {
		public String getName() {
			return "实时加载服务创建失败，需要实时加载的类为空";
		}
		public String getCode() {
			return "0802";
		}
	},
	CLASS_INIT_FAIL {
		public String getName() {
			return "配置类初始化失败";
		}
		public String getCode() {
			return "0803";
		}
	},
	CLASS_INSTANCE_NOT_FOUND {
		public String getName() {
			return "配置类未找到";
		}
		public String getCode() {
			return "0804";
		}
	},
	FILE_NAME_ERROR {
		public String getName() {
			return "文件名错误";
		}
		public String getCode() {
			return "0904";
		}
	},
	FILE_SIZE_ERROR{
		public String getName() {
			return "文件行数不匹配";
		}
		public String getCode() {
			return "2001";
		}
	},
	FILE_NOT_FOUND{
		public String getName() {
			return "文件没有找到";
		}
		public String getCode() {
			return "2002";
		}
	},
	FILE_FIRST_LINE_ERROR{
		public String getName() {
			return "文件输入项校验不通过";
		}
		public String getCode() {
			return "2003";
		}
	},
	FILE_DOWNLOAD_ERROR{
		public String getName() {
			return "文件下载失败";
		}
		public String getCode() {
			return "2004";
		}
	},
	FILE_UPLOAD_ERROR{
		public String getName() {
			return "文件上传失败";
		}
		public String getCode() {
			return "2005";
		}
	},
	FILE_COPY_ERROR{
		public String getName() {
			return "文件复制失败";
		}
		public String getCode() {
			return "2006";
		}
	},
	MER_RESP_ERROR{
		public String getName() {
			return "商户响应异常";
		}
		public String getCode() {
			return "3002";
		}
	},
	MER_RESP_RETCODE_ERROR{
		public String getName() {
			return "商户响应码错误";
		}
		public String getCode() {
			return "3003";
		}
	},
	INNER_ERROR {
		public String getName() {
			return "内部错误";
		}
		public String getCode() {
			return "9999";
		}
	};
	public abstract String getName();

	public abstract String getCode();
	
	public static void main(String[] args) {
		System.out.println(Retcode.SUCCESS.toString());
	}
}
