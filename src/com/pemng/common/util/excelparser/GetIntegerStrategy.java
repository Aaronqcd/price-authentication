package com.pemng.common.util.excelparser;

   

/**    
 * 获取整数方式，四舍五入，向上取整，向下取整

 * @author luohanbin</a>   
 * @version 1.0    
 */

public interface GetIntegerStrategy {

	public Integer toInteger(Double d);
	
	/** 直接取整 */
	public static final GetIntegerStrategy DIRECT = new GetIntegerStrategy(){
		public Integer toInteger(Double d) {
			// TODO Auto-generated method stub
			return null;
		}};
	/** 四舍五入 */
	public static final  GetIntegerStrategy ROUND = new GetIntegerStrategy(){
		public Integer toInteger(Double d) {
			return new Integer((int)Math.round(d.doubleValue()));
		}};
	public static final  GetIntegerStrategy FLOOR = new GetIntegerStrategy(){
		public Integer toInteger(Double d) {
			return new Integer((int)Math.floor(d.doubleValue()));
		}};
	public static final  GetIntegerStrategy CEIL = new GetIntegerStrategy(){
		public Integer toInteger(Double d) {
			return new Integer((int)Math.ceil(d.doubleValue()));
		}};
}
   