package com.ai.baas.common.enums;

public class ProdSpecEnum {

	/**
	 * 产品规格特征值得类型
	 */
	public enum ProdSpecType{
		TEXT("1","TEXT"),
		NUMERIC("2","NUMERIC"),
		FORTH("3","FORTH");
		private String value;
		private String name;
		ProdSpecType(String value,String name){
			this.value = value;
			this.name = name;
		}
		public String getValue(){
			return this.value;
		}
		public String getName(){
			return this.name;
		}
	}
	
	/**
	 * 版本级别
	 * */
	public enum VersionLevel{
		MAJOR_VERSION("1","MAJOR_VERSION"),
		MINOR_VERSION("2","MINOR_VERSION"),
		PATCH_VERSION("3","PATCH_VERSION");
		private String value;
		private String name;
		VersionLevel(String value,String name){
			this.value = value;
			this.name = name;
		}
		public String getValue(){
			return this.value;
		}
		public String getName(){
			return this.name;
		}
	}
	
	/**
	 * 初始化产品规格时的状态
	 */
	public enum ProdSpecStatus{
		STATUS_INACTIVE("0","STATUS_INACTIVE"),
		STATUS_ACTIVE("1","STATUS_ACTIVE"),
		STATUS_PLANED("2","STATUS_PLANED");
		private String value;
		private String name;
		ProdSpecStatus(String value,String name){
			this.value = value;
			this.name = name;
		}
		public String getValue(){
			return this.value;
		}
		public String getName(){
			return this.name;
		}
	}
	
	/**
	 * char之间的relationship之间的类型定义
	 */
	public enum ProdSpecRelationship{
		AGGREGATION("1","AGGREGATION"),
		DEPENDENCY("2","DEPENDENCY"),
		MIGRATION("3","MIGRATION"),
		SUBSTITUTION("4","SUBSTITUTION"),
		EXCLUSIBITY("5","EXCLUSIBITY");
		private String value;
		private String name;
		ProdSpecRelationship(String value,String name){
			this.value = value;
			this.name = name;
		}
		public String getValue(){
			return this.value;
		}
		public String getName(){
			return this.name;
		}
	}

	/**
	 * the status of productOffering
	 */
	public enum ProdOfferingStatus{
		PORD_OFFERING_STATUS_OBSOLETE("0","obsolete"),
		PORD_OFFERING_STATUS_ACTIVE("1","active"),
		PORD_OFFERING_STATUS_PLANNED("2","planned");
		private String value;
		private String name;
		ProdOfferingStatus(String value,String name){
			this.value = value;
			this.name = name;
		}
		public String getValue(){
			return this.value;
		}
		public String getName(){
			return this.name;
		}
	}
}
