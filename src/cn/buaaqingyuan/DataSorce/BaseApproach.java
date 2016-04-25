package cn.buaaqingyuan.DataSorce;

public interface BaseApproach {

	//查询单个实体属性值
	/*
	 * entity 实体类
	 * targetproperty 目标属性
	 * queryproperty  查询属性
	 * value          查询属性值
	 */
	public String queryEntityProperty(String entity,String targetproperty,String queryproperty,String value);
}
